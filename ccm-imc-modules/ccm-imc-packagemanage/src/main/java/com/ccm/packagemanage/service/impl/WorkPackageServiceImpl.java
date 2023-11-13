package com.ccm.packagemanage.service.impl;

import cn.hutool.core.util.NumberUtil;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.fastjson2.JSONObject;
import com.ccm.modules.COMContext;
import com.ccm.modules.packagemanage.BasicPackageContext;
import com.ccm.modules.packagemanage.CWAContext;
import com.ccm.modules.packagemanage.WBSContext;
import com.ccm.modules.packagemanage.WorkPackageContext;
import com.ccm.packagemanage.domain.*;
import com.ccm.modules.packagemanage.enums.DesignObjOperateStatusEnum;
import com.ccm.modules.packagemanage.enums.DesignPhaseEnum;
import com.ccm.modules.packagemanage.enums.DocStateEnum;
import com.ccm.modules.packagemanage.enums.WSStatusEnum;
import com.ccm.packagemanage.helpers.IPackageQueryHelper;
import com.ccm.packagemanage.service.IPackageService;
import com.ccm.packagemanage.service.IWorkPackageService;
import com.imc.common.core.enums.form.FormPurpose;
import com.imc.common.core.enums.frame.PropertyDefinitions;
import com.imc.common.core.enums.frame.Splitters;
import com.imc.common.core.exception.ServiceException;
import com.imc.common.core.model.parameters.FilterParam;
import com.imc.common.core.model.parameters.OrderParam;
import com.imc.common.core.utils.ExceptionUtil;
import com.imc.common.core.web.table.TableData;
import com.imc.common.core.web.table.TableDataSource;
import com.imc.framework.collections.IRelCollection;
import com.imc.framework.collections.impl.ObjectCollection;
import com.imc.framework.utils.GeneralUtil;
import com.imc.framework.utils.SchemaUtil;
import com.imc.schema.interfaces.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.ccm.modules.COMContext.*;

@Slf4j
@Service
public class WorkPackageServiceImpl implements IWorkPackageService {
    /**
     * 权重计算缓存
     */
    private final static Map<String, List<IObject>> cachedPriorityDetails = new ConcurrentHashMap<>();

    @Lazy
    @Autowired
    IPackageService packageService;


    @Autowired
    IPackageQueryHelper packageQueryHelper;

    /**
     * 线程池
     */
    ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public TableData<JSONObject> selectDocumentsForWorkPackage(QueryByPackageParamDTO documentsQueryByPackageParam) throws Exception {
        // 获得工作包下已经关联过的图纸
        ICIMCCMWorkPackage workPackage = packageQueryHelper.getWorkPackageByUid(documentsQueryByPackageParam.getUid());
        if (workPackage == null) {
            throw new ServiceException(String.format("获取工作包失败, uid:%s", documentsQueryByPackageParam.getUid()));
        }
        List<ICIMDocumentMaster> relatedDocuments = workPackage.getDocumentList();

        // 获得工作包关联的任务包
        List<ICIMCCMTaskPackage> taskPackages = workPackage.getTaskPackageList();

        // 排除图纸条件
        List<String> relatedDocumentNames = relatedDocuments.stream().map(ICIMDocumentMaster::getName).collect(Collectors.toList());
        List<String> relatedDocumentUids = relatedDocuments.stream().map(ICIMDocumentMaster::getUid).collect(Collectors.toList());
        String notInCondition = String.join(Splitters.T_COMMA.getMsg(), relatedDocumentUids);
        boolean flag = false;

        // 当存在任务包关联时从任务包图纸中查询
        if (!taskPackages.isEmpty()) {

            // 将关联任务包中的图纸全部查出
            List<String> documentNames = new ArrayList<>();
            for (ICIMCCMTaskPackage taskPackage: taskPackages) {
                List<ICIMDocumentMaster> documentMasterList = taskPackage.getDocumentList();
                if (documentMasterList.isEmpty()) continue;
                for (ICIMDocumentMaster documentMaster : documentMasterList) {
                    documentNames.add(documentMaster.getName());
                }
            }
            // 去除已添加的图纸OBID
            documentNames.removeAll(relatedDocumentNames);
            notInCondition = String.join(",", documentNames);
            if (StringUtils.isNotBlank(notInCondition)) {
                // 当任务包有图纸时 查询任务包下同名图纸的加设图纸
                List<ICIMDocumentMaster> documentMasters = packageQueryHelper.getDocumentsByDesignPhaseAndDocStateAndDocNames(DesignPhaseEnum.EN_SHOP_DESIGN.getCode(), DocStateEnum.EN_IFC.getCode(), notInCondition);
                notInCondition = String.join(",", documentMasters.stream().map(IObject::getUid).collect(Collectors.toList()));
            }
            flag = true;
        }

        // 添加过滤条件
        FilterParam filterParam = new FilterParam();
        filterParam.setPropertyDefUid(WorkPackageContext.PROPERTY_DOC_DESIGN_PHASE);
        filterParam.setOperator("=");
        filterParam.setValue(DesignPhaseEnum.EN_SHOP_DESIGN.getCode());
        documentsQueryByPackageParam.getFilterParams().add(filterParam);
        filterParam.setPropertyDefUid("CIMDocState");
        filterParam.setOperator("=");
        filterParam.setValue(DocStateEnum.EN_IFC.getCode());
        documentsQueryByPackageParam.getFilterParams().add(filterParam);

        if (flag) {
            // 当存在任务包关联时 从任务包图纸对应的加设图纸查询
            filterParam = new FilterParam();
            filterParam.setPropertyDefUid(PropertyDefinitions.uid.name());
            filterParam.setOperator("IN");
            filterParam.setValue(notInCondition);
            documentsQueryByPackageParam.getFilterParams().add(filterParam);
        } else {
            // 当不存在任务包关联时从所有图纸中查询 排除已添加图纸
            if (StringUtils.isNotBlank(notInCondition)) {
                filterParam = new FilterParam();
                filterParam.setPropertyDefUid(PropertyDefinitions.uid.name());
                filterParam.setOperator("NOT IN");
                filterParam.setValue(notInCondition);
                documentsQueryByPackageParam.getFilterParams().add(filterParam);
            }
        }

        documentsQueryByPackageParam.setClassDefUid(COMContext.CLASS_CIM_CCM_DOCUMENT_MASTER);
        documentsQueryByPackageParam.setPurpose(FormPurpose.list.name());

        // 详设过滤
        if (documentsQueryByPackageParam.getFilterParams() != null) {
            List<FilterParam> detailDesignParams = documentsQueryByPackageParam.getFilterParams().stream().filter(x -> WorkPackageContext.PROPERTY_DOC_DESIGN_PHASE.equals(x.getPropertyDefUid())
                    && DesignPhaseEnum.EN_DETAIL_DESIGN.getCode().equals(x.getValue())).collect(Collectors.toList());

            List<FilterParam> ShopDesignParams = documentsQueryByPackageParam.getFilterParams().stream().filter(x -> WorkPackageContext.PROPERTY_DOC_DESIGN_PHASE.equals(x.getPropertyDefUid())
                    && DesignPhaseEnum.EN_SHOP_DESIGN.getCode().equals(x.getValue())).collect(Collectors.toList());
            if (!detailDesignParams.isEmpty() && ShopDesignParams.isEmpty()) {
                documentsQueryByPackageParam.getFilterParams().removeAll(detailDesignParams);
                return GeneralUtil.generateTableData(documentsQueryByPackageParam, GeneralUtil.getUsername());
            }
        } else {
            documentsQueryByPackageParam.setFilterParams(new ArrayList<>());
        }

        return GeneralUtil.generateTableData(documentsQueryByPackageParam, GeneralUtil.getUsername());
    }

    @Override
    public void assignDocumentsIntoWorkPackage(PackageRelDocumentsParamDTO taskAddDocumentsParam) throws Exception {
        if (com.alibaba.druid.util.StringUtils.isEmpty(taskAddDocumentsParam.getUid())) {
            throw new Exception("请输入工作包uid");
        }
        if (taskAddDocumentsParam.getDocIds() != null && taskAddDocumentsParam.getDocIds().isEmpty()) {
            throw new Exception("请输入关联图纸的uid");
        }

        // 获得工作包
        ICIMCCMWorkPackage workPackage = packageQueryHelper.getWorkPackageByUid(taskAddDocumentsParam.getUid());
        if (workPackage == null) {
            throw new ServiceException(String.format("获取任务包失败, uid:%s", taskAddDocumentsParam.getUid()));
        }

        // 获得已经存在关系
        List<IRel> relatedDocuments = workPackage.getEnd2Relationships().getRels(WorkPackageContext.REL_WORK_PACKAGE_TO_DOCUMENT, false).getIncludeAllRels();
        List<IRel> relatedDesign = workPackage.getEnd2Relationships().getRels(WorkPackageContext.REL_WORK_PACKAGE_TO_DESIGN, false).getIncludeAllRels();
        List<IRel> relatedWorkStep = workPackage.getEnd2Relationships().getRels(WorkPackageContext.REL_WORK_PACKAGE_TO_WORK_STEP, false).getIncludeAllRels();

        // 获得施工区域和施工阶段
        String workPackagePurposeValue = String.valueOf(workPackage.getLatestValue(WBSContext.INTERFACE_CIM_CCM_ICIMCCMWBS, WBSContext.PROPERTY_WP_PURPOSE));
        String workPackageCWA = String.valueOf(workPackage.getLatestValue(CWAContext.INTERFACE_CIM_CCM_ICIMCCMCWA, CWAContext.PROPERTY_CWA));

        // 添加图纸关系，已经添加过的排除
        List<String> toCreateRelDocumentOBIDs = new ArrayList<>(taskAddDocumentsParam.getDocIds());
        toCreateRelDocumentOBIDs.removeAll(relatedDocuments);

        // 获取指定范围图纸
        log.debug("工作包添加图纸,开始获取指定图纸.");
        long getDocStart = System.currentTimeMillis();

        // 查询图纸对象
        List<ICIMDocumentMaster> documents = packageQueryHelper.getDocumentsByUIDs(toCreateRelDocumentOBIDs);
        if (documents.size() <= 0) {
            throw new Exception("未找到对应的图纸：" + String.join(Splitters.T_COMMA.getMsg(), toCreateRelDocumentOBIDs));
        }
        log.debug("工作包添加图纸,获取指定图纸耗时:{}ms", System.currentTimeMillis() - getDocStart);

        // 获取指定范围设计数据
        log.debug("工作包添加图纸,开始获取指定范围设计数据.");
        long getDDStart = System.currentTimeMillis();
        List<ICIMCCMDesignObj> designs = packageQueryHelper.getDesignsByDocumentUIDsAndCWA(toCreateRelDocumentOBIDs, workPackageCWA);
        log.debug("工作包添加图纸,获取指定范围设计数据耗时:{}ms", System.currentTimeMillis() - getDDStart);

        // 获取可关联的设计数据和工作步骤
        log.debug("工作包添加图纸,开始获取可关联的设计数据和工作步骤.");
        long getWSStart = System.currentTimeMillis();
        Callable<Map<IObject, List<IObject>>> designAndWs = () -> handleDesignData(designs, workPackagePurposeValue, relatedWorkStep);
        Future<Map<IObject, List<IObject>>> future = executor.submit(designAndWs);
        Map<IObject, List<IObject>> resultDesignDataAndWsList = future.get();
        log.debug("工作包添加图纸,获取可关联的设计数据和工作步骤耗时:{}ms", System.currentTimeMillis() - getWSStart);

        // 开始进行关联
        log.debug("工作包开始进行关联.");
        ObjectCollection objectCollection = new ObjectCollection(GeneralUtil.getUsername());
        long createRel = System.currentTimeMillis();

        // 关联图纸
        for (IObject doc : documents) {
            IRel relationship = SchemaUtil.newRelationship(WorkPackageContext.REL_WORK_PACKAGE_TO_DOCUMENT, workPackage, doc);
            relationship.finishCreate(objectCollection);
        }

        // 关联设计数据和工作步骤
        int ddCount = 0;
        int wsCount = 0;
        for (IObject design : resultDesignDataAndWsList.keySet()) {
            // 关联设计数据(只做未关联的设计数据和工作包关联)
            if (!(relatedDesign.stream().filter(x-> x.getUid2().equals(design.getUid())).count() > 0)) {
                IRel relationship = SchemaUtil.newRelationship(WorkPackageContext.REL_WORK_PACKAGE_TO_DESIGN, workPackage, design);
                relationship.finishCreate(objectCollection);
                ddCount++;
            }
            // 关联工作步骤
            List<IObject> wsObjects = resultDesignDataAndWsList.get(design);
            for (IObject workStep: wsObjects) {
                IRel relationship = SchemaUtil.newRelationship(WorkPackageContext.REL_WORK_PACKAGE_TO_WORK_STEP, workPackage, workStep);
                relationship.finishCreate(objectCollection);
            }
            wsCount += wsObjects.size();

        }

        log.debug("工作包添加图纸,开始提交事务.");
        long commit = System.currentTimeMillis();
        objectCollection.commit();
        log.debug("工作包添加图纸,提交事务耗时:{}ms", System.currentTimeMillis() - commit);
        log.warn("工作包添加图纸,关联图纸{}张,关联设计数据{}个,关联工作步骤{}个,进行关联总耗时:{}ms", documents.size(), ddCount, wsCount, System.currentTimeMillis() - createRel);
    }

    private Map<IObject, List<IObject>> handleDesignData(List<ICIMCCMDesignObj> designDataList, String workPackagePurposeValue, List<IRel> relatedWorkStep) {
        Map<IObject, List<IObject>> result = new HashMap<>();
        try {
            log.debug("开始处理{}个设计数据", designDataList.size());
            long dd = System.currentTimeMillis();
            int wsCount = 0;
            for (IObject designData : designDataList) {
                List<IObject> wsList = new ArrayList<>();
                // 获得设计数据下的工作步骤
                List<ICIMWorkStep> workStepCollection = packageQueryHelper.getWorkStepsByDesignUID(designData.getUid());
                log.debug("开始处理单个设计数据下工作步骤.");
                long ws = System.currentTimeMillis();
                List<List<IObject>> toAddWsLists = new ArrayList<>();
                for (IObject workStepObj : workStepCollection) {
                    ICIMWorkStep workStep = workStepObj.toInterface(ICIMWorkStep.class);
                    toAddWsLists.add(handleWorkSteps(workStep, workPackagePurposeValue, relatedWorkStep));
                }
                for (List<IObject> toAddWs : toAddWsLists) {
                    wsList.addAll(toAddWs);
                }
                if (wsList.size() > 0) {
                    result.put(designData, wsList);
                    wsCount += wsList.size();
                }
                log.debug("单个设计数据下工作步骤处理耗时{}ms.", System.currentTimeMillis() - ws);
            }
            log.debug("处理{}个设计数据耗时{}ms,获取到{}个设计数据,{}个工作步骤.",
                    designDataList.size(), System.currentTimeMillis() - dd, result.keySet().size(), wsCount);
        } catch (Exception exception) {
            log.error("处理设计数据失败!{}", ExceptionUtil.getExceptionMessage(exception), ExceptionUtil.getRootErrorMessage(exception));
        }
        return result;
    }

    /**
     * 处理工作步骤
     *
     * @param workStep
     * @param workPackagePurposeValue
     * @param relatedWorkStep
     * @return
     */
    private List<IObject> handleWorkSteps(ICIMWorkStep workStep, String workPackagePurposeValue, List<IRel> relatedWorkStep) throws Exception {
        List<IObject> result = new ArrayList<>();
        // 不计算删除状态的工作步骤
        String wsStatus = workStep.getWSStatus();
        if (wsStatus.equalsIgnoreCase(WSStatusEnum.REVISED_DELETE.getCode()) || wsStatus.equalsIgnoreCase(WSStatusEnum.ROP_DELETE.getCode())) {
            return result;
        }
        // 工作步骤阶段
        String workStepPurpose = String.valueOf(workStep.getLatestValue(INTERFACE_CIM_WORK_STEP, "WSWPProcessPhase"));
        // 只添加相同阶段的工作步骤
        if (!workStepPurpose.equals(workPackagePurposeValue)) {
            return result;
        }
        IRelCollection iObjectCollection = null;
        try {
            iObjectCollection = workStep.getEnd1Relationships().getRels(WorkPackageContext.REL_WORK_PACKAGE_TO_WORK_STEP, false);
        } catch (Exception e) {
            log.error("工作包添加图纸,子线程获取工作步骤关联关系失败");
            return result;
        }
        if (iObjectCollection.size() > 0) {
            // 已经关联的忽略
            return result;
        }
        // 当和工作包不存在关联关系时添加工作步骤关联
        if (!(relatedWorkStep.stream().filter(x -> x.getUid2().equals(workStep.getUid())).count() > 0)) {
            result.add(workStep);
        }
        return result;
    }

    @Override
    public void removeDocumentsFromWorkPackage(PackageRelDocumentsParamDTO taskAddDocumentsParam) throws Exception {
        if (com.alibaba.druid.util.StringUtils.isEmpty(taskAddDocumentsParam.getUid())) {
            throw new Exception("请输入工作包uid");
        }
        if (taskAddDocumentsParam.getDocIds() != null && taskAddDocumentsParam.getDocIds().isEmpty()) {
            throw new Exception("请输入关联图纸的uid");
        }

        // 获得工作包
        ICIMCCMWorkPackage workPackage = packageQueryHelper.getWorkPackageByUid(taskAddDocumentsParam.getUid());
        if (workPackage == null) {
            throw new ServiceException(String.format("获取任务包失败, uid:%s", taskAddDocumentsParam.getUid()));
        }

        log.debug("开始移除工作包下图纸.");
        long start = System.currentTimeMillis();
        AtomicInteger docCount = new AtomicInteger();
        AtomicInteger designCount = new AtomicInteger();
        AtomicInteger wsCount = new AtomicInteger();
        log.debug("开始获取图纸和工作包的关联关系及工作包下的设计数据.");
        long docDDStart = System.currentTimeMillis();

        // 获取工作包下图纸的关联关系
        List<IRel> deletes = new ArrayList<>();
        // 获得工作包下图纸关系
        List<IRel> rels = workPackage.getEnd1Relationships().getRels(WorkPackageContext.REL_WORK_PACKAGE_TO_DOCUMENT, false).getRelsByRelDef(WorkPackageContext.REL_WORK_PACKAGE_TO_DOCUMENT);
        // 删除关系
        for (IRel rel : rels) {
            if (taskAddDocumentsParam.getDocIds().contains(rel.getUid2())) {
                deletes.add(rel);
                docCount.getAndIncrement();
            }
        }

        List<ICIMCCMDesignObj> designs = packageQueryHelper.getDesignsByWorkPackageUidAndDocumentUIDs(taskAddDocumentsParam.getUid(), taskAddDocumentsParam.getDocIds());
        for (IObject design: designs) {
            // 判断此设计数据是否为当前图纸的设计数据
            List<IRel> docDesignRels = design.getEnd2Relationships().getRels(REL_DOCUMENT_TO_DESIGN_OBJ, false).getRelsByRelDef(REL_DOCUMENT_TO_DESIGN_OBJ);
            if (docDesignRels.stream().filter(x -> taskAddDocumentsParam.getDocIds().contains(x.getUid1())).count() <= 0) continue;

            // 删除设计数据
            List<IRel> designRels = design.getEnd2Relationships().getRels(WorkPackageContext.REL_WORK_PACKAGE_TO_DESIGN, false).getRelsByRelDef(WorkPackageContext.REL_WORK_PACKAGE_TO_DESIGN);
            if (designRels.isEmpty()) continue;
            designCount.addAndGet((int)designRels.stream().filter(x->x.getUid1().equals(workPackage.getUid())).count());
            deletes.addAll(designRels.stream().filter(x->x.getUid1().equals(workPackage.getUid())).collect(Collectors.toList()));
            List<ICIMWorkStep> workStepCollection = packageQueryHelper.getWorkStepsByDesignUID(design.getUid());

            for (IObject workStep : workStepCollection) {
                List<IRel> wsRels = workStep.getEnd2Relationships().getRels(WorkPackageContext.REL_WORK_PACKAGE_TO_WORK_STEP, false).getRelsByRelDef(WorkPackageContext.REL_WORK_PACKAGE_TO_WORK_STEP);
                deletes.addAll(wsRels.stream().filter(x->x.getUid1().equals(workPackage.getUid())).collect(Collectors.toList()));
                wsCount.addAndGet((int)wsRels.stream().filter(x->x.getUid1().equals(workPackage.getUid())).count());
            }
        }

        log.debug("获取图纸和工作包的关联关系及工作包下的设计数据结束,耗时:{}ms.", System.currentTimeMillis() - docDDStart);

        log.debug("开始删除关系.");
        ObjectCollection objectCollection = new ObjectCollection(GeneralUtil.getUsername());
        if (rels.size() <= 0) return;
        for (IRel rel : deletes) {
            rel.Delete(objectCollection);
        }
        log.debug("开始提交事务.");
        long trans = System.currentTimeMillis();
        objectCollection.commit();
        log.debug("提交事务结束,耗时:{}ms.", System.currentTimeMillis() - trans);
        log.warn("移除工作包下图纸结束,耗时:{}ms,移除{}张图纸,{}个设计数据,{}个工作步骤.", System.currentTimeMillis() - start, docCount, designCount, wsCount);
    }

    @Override
    public TableData<JSONObject> calculatePriorityForWorkPackage(CalculateByPriorityParamDTO calculateByPriorityParamDTO) throws Exception {
        if (com.alibaba.druid.util.StringUtils.isEmpty(calculateByPriorityParamDTO.getUid())) {
            throw new Exception("请输入工作包uid");
        }
        if (com.alibaba.druid.util.StringUtils.isEmpty(calculateByPriorityParamDTO.getPriorityId())) {
            throw new Exception("请输入优先级uid");
        }
        if (com.alibaba.druid.util.StringUtils.isEmpty(calculateByPriorityParamDTO.getToken())) {
            throw new Exception("global unique key not provided for cache process");
        }

        // 判断是否从
        if (calculateByPriorityParamDTO.getFromCache()) {
            return filterDocument(calculateByPriorityParamDTO, cachedPriorityDetails.getOrDefault(calculateByPriorityParamDTO.getToken(), null));
        }

        // 获取优先级
        ICIMCCMPriority priority = packageQueryHelper.getPriorityByUid(calculateByPriorityParamDTO.getPriorityId());
        if (priority == null) {
            throw new ServiceException(String.format("获得优先级失败, uid:%s", calculateByPriorityParamDTO.getPriorityId()));
        }

        // 获得工作包
        ICIMCCMWorkPackage workPackage = packageQueryHelper.getWorkPackageByUid(calculateByPriorityParamDTO.getUid());
        if (workPackage == null) {
            throw new ServiceException(String.format("获取工作包失败, uid:%s", calculateByPriorityParamDTO.getUid()));
        }

        // 根据工作包获得关联的任务包
        List<ICIMCCMTaskPackage> taskPackages = workPackage.getTaskPackageList();

        // 关联的设计数据集合
        List<ICIMCCMDesignObj> designs = new ArrayList<>();

        if (taskPackages.size() > 0) {
            // 获得任务包下的所有文档信息
            for (ICIMCCMTaskPackage taskPackage : taskPackages) {
                List<ICIMDocumentMaster> documents = taskPackage.getDocumentList();
                List<String> documentUIDs = documents.stream().map(IObject::getUid).collect(Collectors.toList());
                List<ICIMCCMDesignObj> designOBJs = packageQueryHelper.getDesignsByDocumentUIDsAndCWA(documentUIDs, String.valueOf(workPackage.getLatestValue(CWAContext.INTERFACE_CIM_CCM_ICIMCCMCWA,CWAContext.PROPERTY_CWA)));
                designs.addAll(designOBJs);
            }
        } else {
            // 根据施工阶段获得符合条件的设计数据
            String wpPurpose = String.valueOf(workPackage.getLatestValue(WBSContext.INTERFACE_CIM_CCM_ICIMCCMWBS, WBSContext.PROPERTY_WP_PURPOSE));
            String cwa = String.valueOf(workPackage.getLatestValue(CWAContext.INTERFACE_CIM_CCM_ICIMCCMCWA, CWAContext.PROPERTY_CWA));
            designs = packageQueryHelper.getDesignsByWPPurposeAndCWA(wpPurpose, cwa);
        }

        // 根据设计数据获取对应的设计图纸
        Map<IObject, List<ICIMCCMDesignObj>> documents = packageService.splitCompObjsByDocument(designs);
        if (documents.isEmpty()) log.warn("no document(s) related to provided components");

        // 排除非ifc状态的设计图纸
        Map<IObject, List<ICIMCCMDesignObj>> docs = packageService.ensureIFCDocuments(documents);

        // 将筛选出来的图纸进行计算并返回结果
        Map<IObject, Double> mapDocumentWeight = packageService.doCalculateWeightAsPerDocument(docs, priority);

        // 给图纸添加权重字段
        List<IObject> documentList = packageService.renderCollectionWithPriority(mapDocumentWeight);

        // 缓存结果
        cachedPriorityDetails.put(calculateByPriorityParamDTO.getToken(), documentList);

        // 返回结果
        return filterDocument(calculateByPriorityParamDTO, documentList);
    }

    private TableData<JSONObject> filterDocument(CalculateByPriorityParamDTO calculateByPriorityParamDTO, List<IObject> documents) throws Exception {
        if (documents == null || documents.isEmpty()) {
            return GeneralUtil.generateTableData(null, GeneralUtil.getUsername(), CLASS_CIM_CCM_DOCUMENT_MASTER);
        }

        // 筛选
        Stream<IObject> stream = documents.stream();
        if (calculateByPriorityParamDTO.getFilterParams() != null && !calculateByPriorityParamDTO.getFilterParams().isEmpty()) {
            for (FilterParam filterParam : calculateByPriorityParamDTO.getFilterParams()) {
                stream = stream.filter(doc -> doc.getLatestValue(WorkPackageContext.INTERFACE_CIM_CCM_WORK_PACKAGE, filterParam.getPropertyDefUid()).equals(filterParam.getValue()));
            }
        }

        // 排序
        if (calculateByPriorityParamDTO.getOrderParams() != null && !calculateByPriorityParamDTO.getOrderParams().isEmpty()) {
            for (OrderParam orderParam : calculateByPriorityParamDTO.getOrderParams()) {
                stream = stream.sorted((s1, s2) -> s2.getLatestValue(WorkPackageContext.INTERFACE_CIM_CCM_WORK_PACKAGE, orderParam.getField()).toString().compareTo(s1.getLatestValue(WorkPackageContext.INTERFACE_CIM_CCM_WORK_PACKAGE, orderParam.getField()).toString()));
            }
        }

        // 分页
        List<IObject> documentMasters = stream.skip((calculateByPriorityParamDTO.getCurrent() - 1) * calculateByPriorityParamDTO.getPageSize()).limit(calculateByPriorityParamDTO.getPageSize()).collect(Collectors.toList());
        return GeneralUtil.generateTableData(documentMasters, GeneralUtil.getUsername(), CLASS_CIM_CCM_DOCUMENT_MASTER);
    }

    @Override
    public TableData<JSONObject> selectDesignDataByPurposeAndConsumeMaterialForWP(MaterialQueryParamDTO materialQueryParamDTO, boolean needConsumeMaterial) throws Exception {
        // 根据ID获得工作包
        ICIMCCMWorkPackage workPackage = packageQueryHelper.getWorkPackageByUid(materialQueryParamDTO.getUid());
        if (workPackage == null) {
            throw new ServiceException(String.format("获取工作包失败, uid:%s", materialQueryParamDTO.getUid()));
        }
        String workPackagePurpose = String.valueOf(workPackage.getLatestValue(WBSContext.INTERFACE_CIM_CCM_ICIMCCMWBS, WBSContext.PROPERTY_WP_PURPOSE));

        // 获取工作包下工作步骤
        List<ICIMWorkStep> workSteps = workPackage.getWorkStepsWithoutDeleted();

        // 筛选条件
        FilterParam filterParam = new FilterParam();
        List<String> uids = new ArrayList<>();

        // 遍历工作步骤
        for (ICIMWorkStep workStep : workSteps) {
            // 不计算删除状态的工作步骤
            String wsStatus = workStep.getWSStatus();
            if (wsStatus.equalsIgnoreCase(WSStatusEnum.REVISED_DELETE.getCode()) || wsStatus.equalsIgnoreCase(WSStatusEnum.ROP_DELETE.getCode())) {
                continue;
            }
            String ropWorkStepPhase = workStep.getLatestValue(INTERFACE_CIM_WORK_STEP, "WSWPProcessPhase").toString();

            if (workPackagePurpose.equalsIgnoreCase(ropWorkStepPhase)) {
                if (needConsumeMaterial) {
                    if (!workStep.getWSConsumeMaterial()) {
                        continue;
                    }
                }
                IRel rel = workStep.getEnd2Relationships().getRel(REL_DESIGN_OBJ_TO_WORK_STEP, false);
                IObject designData = packageQueryHelper.getDesignByUid(rel.getUid1());
                // 设计数据 版本状态
                String designRevisionItemOperationState = String.valueOf(designData.getLatestValue("ICIMRevisionItem","CIMRevisionItemOperationState"));
                // 不计算删除状态的设计数据
                if (DesignObjOperateStatusEnum.DELETE.getCode().equalsIgnoreCase(designRevisionItemOperationState)) {
                    continue;
                }
                // 匹配对应的ClassDef
                if (materialQueryParamDTO.getClassDefinitionUID().equalsIgnoreCase(designData.getClassDefinitionUid())) {
                    uids.add(designData.getUid());
                }
            }
        }
        filterParam.setPropertyDefUid(PropertyDefinitions.uid.name());
        filterParam.setOperator("IN");
        filterParam.setValue(String.join(Splitters.T_COMMA.getMsg(), uids));
        materialQueryParamDTO.setFilterParams(Arrays.asList(filterParam));
        materialQueryParamDTO.setClassDefUid(materialQueryParamDTO.getClassDefinitionUID());
        return GeneralUtil.generateTableData(materialQueryParamDTO, GeneralUtil.getUsername());
    }

    @Override
    public Double refreshPackageProgress(String packageUid) throws Exception {
        if (com.alibaba.druid.util.StringUtils.isEmpty(packageUid)) {
            throw new ServiceException("请输入工作包UID!");
        }

        // 获得工作包计划权重
        Double planWeight =this.refreshPackagePlanWeight(packageUid);

        // 获得工作包
        ICIMCCMWorkPackage workPackage = packageQueryHelper.getWorkPackageByUid(packageUid);
        if (workPackage == null) {
            throw new ServiceException(String.format("获取工作包失败, uid:%s", packageUid));
        }

        // 原本权重
        String oldProgresstValue = String.valueOf(workPackage.getLatestValue(BasicPackageContext.INTERFACE_BASIC_PLAN_PACKAGE_OBJ, BasicPackageContext.PROPERTY_PROGRESS));
        BigDecimal oldProgress = com.alibaba.druid.util.StringUtils.isEmpty(oldProgresstValue) ? new BigDecimal("0.0") : new BigDecimal(oldProgresstValue);

        // 获得工作包下工作步骤
        List<ICIMWorkStep> workSteps = workPackage.getWorkStepsWithoutDeleted();
        BigDecimal truncatedWeight = new BigDecimal("0.0");
        for (ICIMWorkStep iWorkStep : workSteps) {
            // 不计算删除状态的工作步骤
            String wsStatus = iWorkStep.getWSStatus();
            if (wsStatus.equalsIgnoreCase(WSStatusEnum.REVISED_DELETE.getCode()) || wsStatus.equalsIgnoreCase(WSStatusEnum.ROP_DELETE.getCode())) {
                continue;
            }
            if (!iWorkStep.hasActualCompletedDate()) continue;
            BigDecimal toAddWeight = BigDecimal.valueOf(iWorkStep.getWSWeight() == null ? 0.0 : iWorkStep.getWSWeight());
            truncatedWeight = NumberUtil.add(truncatedWeight, toAddWeight);
        }
        double progress = NumberUtil.div(truncatedWeight, planWeight, 4).doubleValue();
        if (0 != NumberUtil.compare(progress, oldProgress.doubleValue())) {
            workPackage.updateProgress(progress, true);
        }
        return progress;
    }

    @Override
    public Double refreshPackagePlanWeight(String packageUid) throws Exception {
        // 获得工作包
        ICIMCCMWorkPackage workPackage = packageQueryHelper.getWorkPackageByUid(packageUid);
        if (workPackage == null) {
            throw new ServiceException(String.format("获取工作包失败, uid:%s", packageUid));
        }

        // 原本权重
        String oldPlannedWeightValue = String.valueOf(workPackage.getLatestValue(BasicPackageContext.INTERFACE_BASIC_PLAN_PACKAGE_OBJ, BasicPackageContext.PROPERTY_PLANNED_WEIGHT));
        BigDecimal oldPlannedWeight = com.alibaba.druid.util.StringUtils.isEmpty(oldPlannedWeightValue) ? new BigDecimal("0.0") : new BigDecimal(oldPlannedWeightValue);

        // 获得施工阶段相同的工作步骤列表
        List<ICIMWorkStep> workSteps = workPackage.getWorkStepsWithoutDeleted();

        // 计算权重
        BigDecimal weight = new BigDecimal("0.0");
        for (ICIMWorkStep workStep : workSteps) {
            BigDecimal toAddWeight = BigDecimal.valueOf(workStep.getWSWeight() == null ? 0.0 : workStep.getWSWeight());
            weight = NumberUtil.add(weight, toAddWeight);
        }

        // 更新工作包权重
        if (0 != weight.compareTo(oldPlannedWeight)) {
            ObjectCollection objectCollection = new ObjectCollection(GeneralUtil.getUsername());
            workPackage.BeginUpdate(objectCollection);
            workPackage.setValue(BasicPackageContext.INTERFACE_BASIC_PLAN_PACKAGE_OBJ, BasicPackageContext.PROPERTY_PLANNED_WEIGHT, weight, null);
            workPackage.FinishUpdate(objectCollection);
            objectCollection.commit();
        }
        return weight.doubleValue();
    }

    @Override
    public void removeWorkStepUnderWorkPackage(PackageDeleteRelWokStepDTO packageDeleteRelWokStepDTO) throws Exception {
        if (com.alibaba.druid.util.StringUtils.isEmpty(packageDeleteRelWokStepDTO.getUid())) {
            throw new ServiceException("请输入工作包uid");
        }
        if (packageDeleteRelWokStepDTO.getWorkSteps() == null || packageDeleteRelWokStepDTO.getWorkSteps().isEmpty()) {
            throw new ServiceException("请输入工作步骤uid");
        }

        // 事务
        ObjectCollection objectCollection = new ObjectCollection(GeneralUtil.getUsername());

        // 获得工作包
        ICIMCCMWorkPackage workPackage = packageQueryHelper.getWorkPackageByUid(packageDeleteRelWokStepDTO.getUid());

        // 获得工作包的工作步骤
        List<IRel> rels = workPackage.getEnd1Relationships().getRels(WorkPackageContext.REL_WORK_PACKAGE_TO_WORK_STEP, false).getRelsByRelDef(WorkPackageContext.REL_WORK_PACKAGE_TO_WORK_STEP);
        if (rels.isEmpty()) throw new ServiceException("工作包" + packageDeleteRelWokStepDTO.getUid() + ":没有关联工作步骤");
        for (IRel rel : rels) {
            if (packageDeleteRelWokStepDTO.getWorkSteps().contains(rel.getUid2())) {
                rel.Delete(objectCollection);
                // 删除状态的工作步骤解除关联时直接删除
                ICIMWorkStep workStep = packageQueryHelper.getWorkStepByUID(rel.getUid2());
                if (workStep == null) continue;
                String wsStatus = workStep.getWSStatus();
                if (wsStatus.equalsIgnoreCase(WSStatusEnum.REVISED_DELETE.getCode()) || wsStatus.equalsIgnoreCase(WSStatusEnum.ROP_DELETE.getCode())) {
                    workStep.Delete(objectCollection);
                }
            }
        }
        objectCollection.commit();
    }

    @Override
    public void removeComponentsUnderWorkPackage(PackageDeleteRelDesignDTO packageDeleteRelDesignDTO) throws Exception {
        if (com.alibaba.druid.util.StringUtils.isEmpty(packageDeleteRelDesignDTO.getUid())) {
            throw new ServiceException("请输入工作包uid");
        }
        if (packageDeleteRelDesignDTO.getDesigns() == null || packageDeleteRelDesignDTO.getDesigns().isEmpty()) {
            throw new ServiceException("请输入设计数据uid");
        }

        // 事务
        ObjectCollection objectCollection = new ObjectCollection(GeneralUtil.getUsername());

        // 获得工作包
        ICIMCCMWorkPackage workPackage = packageQueryHelper.getWorkPackageByUid(packageDeleteRelDesignDTO.getUid());

        // 获得工作包的工作步骤
        List<IRel> wpWorkStepRels = workPackage.getEnd1Relationships().getRels(WorkPackageContext.REL_WORK_PACKAGE_TO_WORK_STEP, false).getRelsByRelDef(WorkPackageContext.REL_WORK_PACKAGE_TO_WORK_STEP);

        // 获得工作包的设计数据
        List<IRel> wpDesignRels = workPackage.getEnd1Relationships().getRels(WorkPackageContext.REL_WORK_PACKAGE_TO_DESIGN, false).getRelsByRelDef(WorkPackageContext.REL_WORK_PACKAGE_TO_DESIGN);
        if (wpDesignRels.isEmpty()) throw new ServiceException("工作包" + packageDeleteRelDesignDTO.getUid() + ":没有关联设计数据");
        for (IRel rel : wpDesignRels) {
            if (packageDeleteRelDesignDTO.getDesigns().contains(rel.getUid2())) {

                // 获得工作步骤
                ICIMCCMDesignObj designObj = packageQueryHelper.getDesignByUid(rel.getUid2());

                // 获得设计数据下的工作步骤
                List<IRel> workStepRels = designObj.getEnd1Relationships().getRels(REL_DESIGN_OBJ_TO_WORK_STEP, false).getRelsByRelDef(REL_DESIGN_OBJ_TO_WORK_STEP);
                if (!workStepRels.isEmpty()) {
                    for (IRel workStepRel : workStepRels) {
                        // 判断此工作步骤是否关联工作包
                        Optional<IRel> relOptional = wpWorkStepRels.stream().filter(x -> x.getUid2().equalsIgnoreCase(workStepRel.getUid2())).findFirst();
                        if (!relOptional.isPresent()) continue;

                        // 关联则删除
                        relOptional.get().Delete(objectCollection);

                        // 删除状态的工作步骤解除关联时直接删除
                        ICIMWorkStep workStep = packageQueryHelper.getWorkStepByUID(relOptional.get().getUid2());
                        if (workStep == null) continue;
                        String wsStatus = workStep.getWSStatus();
                        if (wsStatus.equalsIgnoreCase(WSStatusEnum.REVISED_DELETE.getCode()) || wsStatus.equalsIgnoreCase(WSStatusEnum.ROP_DELETE.getCode())) {
                            workStep.Delete(objectCollection);
                        }
                    }
                }

                rel.Delete(objectCollection);
            }
        }

        objectCollection.commit();
    }
}
