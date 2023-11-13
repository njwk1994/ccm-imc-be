package com.ccm.packagemanage.service.impl;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.util.NumberUtil;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson2.JSONObject;
import com.ccm.modules.packagemanage.*;
import com.ccm.modules.packagemanage.enums.DesignPhaseEnum;
import com.ccm.modules.schedulermanage.ScheduleContext;
import com.ccm.packagemanage.domain.*;
import com.ccm.modules.packagemanage.enums.PackageType;
import com.ccm.packagemanage.helpers.IPackageQueryHelper;
import com.ccm.packagemanage.service.IPackageService;
import com.ccm.packagemanage.service.ITaskPackageService;
import com.imc.common.core.enums.frame.PropertyDefinitions;
import com.imc.common.core.enums.frame.RelDirection;
import com.imc.common.core.enums.frame.Splitters;
import com.imc.common.core.exception.ServiceException;
import com.imc.common.core.model.parameters.FilterParam;
import com.imc.common.core.model.parameters.OrderParam;
import com.imc.common.core.web.table.TableData;
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
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.ccm.modules.COMContext.*;
import static com.ccm.modules.packagemanage.TaskPackageContext.REL_TASK_PACKAGE_TO_DOCUMENT;

@Slf4j
@Service
public class TaskPackageServiceImpl implements ITaskPackageService {

    @Lazy
    @Autowired
    IPackageService packageService;

    @Autowired
    IPackageQueryHelper packageQueryHelper;

    private final static Map<String, List<IObject>> cachedPriorityDetails = new ConcurrentHashMap<>();

    @Override
    public TableData<JSONObject> selectDocumentsForTaskPackage(QueryByPackageParamDTO documentsQueryByTask) throws Exception {
        // 根据ID获得任务包
        ICIMCCMTaskPackage taskPackage = packageQueryHelper.getTaskPackageByUid(documentsQueryByTask.getUid());
        if (taskPackage == null) {
            throw new ServiceException(String.format("获取任务包失败, uid:%s", documentsQueryByTask.getUid()));
        }

        // 获得计划关系
        String scheduleUID = "";
        IRel scheduleTask = taskPackage.getEnd2Relationships().getRel(ScheduleContext.REL_SCHEDULE_2_TASK_PACKAGE, false);
        if (scheduleTask != null) {
            scheduleUID = scheduleTask.getUid1();
        }

        // 获得未关联到当前任务包并且符合当前任务包施工阶段和施工区域的图纸
        String purpose = String.valueOf(taskPackage.getLatestValue(WBSContext.INTERFACE_CIM_CCM_ICIMCCMWBS,WBSContext.PROPERTY_PURPOSE));
        String cwa = String.valueOf(taskPackage.getLatestValue(CWAContext.INTERFACE_CIM_CCM_ICIMCCMCWA,CWAContext.PROPERTY_CWA));
        List<ICIMDocumentMaster> relDocuments = packageQueryHelper.getDocumentsByTaskPackageUidAndPurposeAndCWAAndScheduleUID(taskPackage.getUid(), purpose, cwa, scheduleUID);

        // 排除已经关联当前任务包的图纸
        relDocuments.removeAll(taskPackage.toInterface(ICIMCCMTaskPackage.class).getDocumentList());
        List<String> docUidList = relDocuments.stream().map(IObject::getUid).collect(Collectors.toList());

        // 查询范围设置
//        if (!docUidList.isEmpty()) {
            FilterParam filterParam = new FilterParam();
            filterParam.setPropertyDefUid(PropertyDefinitions.uid.name());
            filterParam.setOperator("IN");
            filterParam.setValue(String.join(Splitters.T_COMMA.getMsg(), docUidList));
            documentsQueryByTask.setFilterParams(Arrays.asList(filterParam));
//        }
        documentsQueryByTask.setClassDefUid(CLASS_CIM_CCM_DOCUMENT_MASTER);
        return GeneralUtil.generateTableData(documentsQueryByTask, GeneralUtil.getUsername());
    }

    @Override
    public void assignDocumentsIntoTaskPackage(PackageRelDocumentsParamDTO taskAddDocumentsParam) throws Exception {
        if (StringUtils.isEmpty(taskAddDocumentsParam.getUid())) {
            throw new Exception("请输入任务包uid");
        }
        if (taskAddDocumentsParam.getDocIds() != null && taskAddDocumentsParam.getDocIds().isEmpty()) {
            throw new Exception("请输入关联图纸的uid");
        }

        // 获得任务包
        ICIMCCMTaskPackage taskPackage = packageQueryHelper.getTaskPackageByUid(taskAddDocumentsParam.getUid());
        if (taskPackage == null) {
            throw new ServiceException(String.format("获取任务包失败, uid:%s", taskAddDocumentsParam.getUid()));
        }
        String cwa = String.valueOf(taskPackage.getLatestValue(CWAContext.INTERFACE_CIM_CCM_ICIMCCMCWA, CWAContext.PROPERTY_CWA));

        // 创建事务
        ObjectCollection objectCollection = new ObjectCollection(GeneralUtil.getUsername());

        // 创建关系
        for (String docId : taskAddDocumentsParam.getDocIds()) {
            IObject docObj = packageQueryHelper.getDocumentByUID(docId);
            IRel relationship = SchemaUtil.newRelationship(REL_TASK_PACKAGE_TO_DOCUMENT, taskPackage, docObj);
            relationship.finishCreate(objectCollection);

            // 获得图纸中的设计数据并关联任务包
            List<ICIMCCMDesignObj> designOBJs = packageQueryHelper.getDesignsByDocumentUIDAndCWA(docObj.getUid(), cwa);
            for (ICIMCCMDesignObj designObj : designOBJs) {
                IRel designRel = SchemaUtil.newRelationship(TaskPackageContext.REL_TASK_PACKAGE_TO_DESIGN_OBJ, taskPackage, designObj);
                designRel.finishCreate(objectCollection);
            }
        }

        // 提交事务
        objectCollection.commit();
    }

    @Override
    public void removeDocumentsFromTaskPackage(PackageRelDocumentsParamDTO taskAddDocumentsParam) throws Exception {
        if (StringUtils.isEmpty(taskAddDocumentsParam.getUid())) {
            throw new Exception("请输入任务包uid");
        }
        if (taskAddDocumentsParam.getDocIds() != null && taskAddDocumentsParam.getDocIds().isEmpty()) {
            throw new Exception("请输入关联图纸的uid");
        }

        // 获得任务包
        ICIMCCMTaskPackage taskPackage = packageQueryHelper.getTaskPackageByUid(taskAddDocumentsParam.getUid());
        if (taskPackage == null) {
            throw new ServiceException(String.format("获取任务包失败, uid:%s", taskAddDocumentsParam.getUid()));
        }

        // 获得任务包下图纸关系
        List<IRel> rels = taskPackage.getRels(RelDirection.From1To2,TaskPackageContext.REL_TASK_PACKAGE_TO_DOCUMENT,false).getRelsByRelDef(TaskPackageContext.REL_TASK_PACKAGE_TO_DOCUMENT);

        // 创建事务
        ObjectCollection objectCollection = new ObjectCollection(GeneralUtil.getUsername());

        // 删除关系,包括图纸和设计数据
        for (IRel rel : rels) {
            if (taskAddDocumentsParam.getDocIds().contains(rel.getUid2())) {
                rel.Delete(objectCollection);
                List<ICIMCCMDesignObj> designOBJs = packageQueryHelper.getDesignsByTaskPackageUIDAndDocumentUID(taskPackage.getUid(), rel.getUid2());
                for (ICIMCCMDesignObj designObj : designOBJs) {
                    List<IRel> designRels = designObj.getEnd2Relationships().getRels(TaskPackageContext.REL_TASK_PACKAGE_TO_DESIGN_OBJ, false).getRelsByRelDef(TaskPackageContext.REL_TASK_PACKAGE_TO_DESIGN_OBJ);
                    for (IRel designRel : designRels) {
                        if (designRel.getUid1().equals(taskPackage.getUid())) {
                            designRel.Delete(objectCollection);
                        }
                    }
                }
            }
        }

        // 提交事务
        objectCollection.commit();
    }

    @Override
    public TableData<JSONObject> selectDesignDataByPurposeAndConsumeMaterial(MaterialQueryParamDTO materialQueryParamDTO, boolean needConsumeMaterial) throws Exception {
        // 根据ID获得任务包
        ICIMCCMTaskPackage taskPackage = packageQueryHelper.getTaskPackageByUid(materialQueryParamDTO.getUid());
        if (taskPackage == null) {
            throw new ServiceException(String.format("获取任务包失败, uid:%s", materialQueryParamDTO.getUid()));
        }

        // 筛选条件
        FilterParam filterParam = new FilterParam();
        List<String> uids = new ArrayList<>();

        // 获得设计数据
        List<ICIMCCMDesignObj> designObjs = taskPackage.getDesignDataList();
        String taskPackagePurpose = String.valueOf(taskPackage.getLatestValue(WBSContext.INTERFACE_CIM_CCM_ICIMCCMWBS, WBSContext.PROPERTY_PURPOSE));
        for (ICIMCCMDesignObj designObj: designObjs) {
            // 筛选对应的材料类
            if (!designObj.getClassDefinitionUid().equalsIgnoreCase(materialQueryParamDTO.getClassDefinitionUID())) continue;

            // 获得设计数据下的工作步骤
            List<ICIMWorkStep> workSteps = packageQueryHelper.getWorkStepsByDesignUidAndRopWorkStepPhaseAndConsumeMaterial(designObj.getUid(), PackageType.TP, taskPackagePurpose, needConsumeMaterial);
            if (!workSteps.isEmpty()) {
                uids.add(designObj.getUid());
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
    public List<IObject> getWorkStepsWithSamePurposeAndConsumeMaterial(IObject taskPackage) throws Exception {
        ICIMCCMTaskPackage icimccmTaskPackage = taskPackage.toInterface(ICIMCCMTaskPackage.class);
        List<ICIMWorkStep> icimWorkSteps = icimccmTaskPackage.getWorkStepsWithoutDeletedList();
        return icimWorkSteps.stream().filter(w -> {
            String workStepPurpose = w.getLatestValue(WBSContext.INTERFACE_CIM_CCM_ICIMCCMWBS, WBSContext.PROPERTY_PURPOSE).toString();
            // 返回相同施工阶段并且有材料消耗的
            return icimccmTaskPackage.getLatestValue(WBSContext.INTERFACE_CIM_CCM_ICIMCCMWBS, WBSContext.PROPERTY_PURPOSE).equals(workStepPurpose) && w.getWSConsumeMaterial();
        }).collect(Collectors.toList());
    }

    @Override
    public TableData<JSONObject> calculatePriorityForTaskPackage(CalculateByPriorityParamDTO calculateByPriorityParamDTO) throws Exception {
        if (StringUtils.isEmpty(calculateByPriorityParamDTO.getUid())) {
            throw new Exception("请输入任务包uid");
        }
        if (StringUtils.isEmpty(calculateByPriorityParamDTO.getPriorityId())) {
            throw new Exception("请输入优先级uid");
        }
        if (StringUtils.isEmpty(calculateByPriorityParamDTO.getToken())) {
            throw new Exception("global unique key not provided for cache process");
        }

        // 判断是否从缓存获得数据
        if (calculateByPriorityParamDTO.getFromCache()) {
            return filterDocument(calculateByPriorityParamDTO, cachedPriorityDetails.getOrDefault(calculateByPriorityParamDTO.getToken(), null));
        }

        // 获取优先级
        ICIMCCMPriority priority = packageQueryHelper.getPriorityByUid(calculateByPriorityParamDTO.getPriorityId());
        if (priority == null) {
            throw new ServiceException(String.format("获得优先级失败, uid:%s", calculateByPriorityParamDTO.getPriorityId()));
        }

        // 获得任务包
        ICIMCCMTaskPackage taskPackage = packageQueryHelper.getTaskPackageByUid(calculateByPriorityParamDTO.getUid());
        if (taskPackage == null) {
            throw new ServiceException(String.format("获取任务包失败, uid:%s", calculateByPriorityParamDTO.getUid()));
        }

        TableData<JSONObject> tableData = null;
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Map<IObject, List<ICIMCCMDesignObj>> documents = new HashMap<>();

        String purpose = String.valueOf(taskPackage.getLatestValue(WBSContext.INTERFACE_CIM_CCM_ICIMCCMWBS,WBSContext.PROPERTY_PURPOSE));
        String cwa = String.valueOf(taskPackage.getLatestValue(CWAContext.INTERFACE_CIM_CCM_ICIMCCMCWA,CWAContext.PROPERTY_CWA));

        // 获取设计数据
        List<ICIMCCMDesignObj> designOBJs = packageQueryHelper.getDesignsByPurposeAndCWA(purpose, cwa);

        // 根据设计数据获取对应的设计图纸
        documents = packageService.splitCompObjsByDocument(designOBJs);

        // 排除掉已经关联当前任务包的的设计图纸
        ICIMCCMTaskPackage icimccmTaskPackage = taskPackage.toInterface(ICIMCCMTaskPackage.class);
        List<ICIMDocumentMaster> documentMasters = icimccmTaskPackage.getDocumentList();
        for (ICIMDocumentMaster relDoc: documentMasters) {
            documents.keySet().stream().filter(x -> x.getUid().equalsIgnoreCase(relDoc.getUid())).findFirst().ifPresent(documents::remove);
        }

        // 排除非ifc状态的设计图纸
        Map<IObject, List<ICIMCCMDesignObj>> docs = packageService.ensureIFCDocuments(documents);

        // 将筛选出来的图纸进行计算并返回结果
        Map<IObject, Double> mapDocumentWeight = packageService.doCalculateWeightAsPerDocument(docs, priority);

        // 给图纸添加权重字段
        List<IObject> documentList = packageService.renderCollectionWithPriority(mapDocumentWeight);

        // 缓存结果
        cachedPriorityDetails.put(calculateByPriorityParamDTO.getToken(), documentList);

        // 返回结果
        tableData = filterDocument(calculateByPriorityParamDTO, documentList);

        stopWatch.stop();
        log.info("finally priority calculation completed" + stopWatch.getTotalTimeMillis());
        return tableData;
    }

    @Override
    public Double refreshPackageProgress(String packageUid) throws Exception {
        if (StringUtils.isEmpty(packageUid)) {
            throw new ServiceException("请输入任务包UID!");
        }

        // 工作包信息
        Map<String, WorkPackageData> workPackageDataMap = new HashMap<>();

        // 获得任务包计划权重
        Double planWeight =this.refreshPackagePlanWeight(packageUid);

        // 详设所有已完成权重
        BigDecimal detailDesignTruncatedAllWeight = new BigDecimal("0.0");

        // 获得任务包
        ICIMCCMTaskPackage taskPackage = packageQueryHelper.getTaskPackageByUid(packageUid);
        String taskPackagePurpose = String.valueOf(taskPackage.getLatestValue(WBSContext.INTERFACE_CIM_CCM_ICIMCCMWBS, WBSContext.PROPERTY_PURPOSE));
        String taskPackageCWA = String.valueOf(taskPackage.getLatestValue(CWAContext.INTERFACE_CIM_CCM_ICIMCCMCWA, CWAContext.PROPERTY_CWA));
        String oldProgressValue = String.valueOf(taskPackage.getLatestValue(BasicPackageContext.INTERFACE_BASIC_PLAN_PACKAGE_OBJ, BasicPackageContext.PROPERTY_PROGRESS));
        BigDecimal oldProgress = StringUtils.isEmpty(oldProgressValue) ? new BigDecimal("0.0") : new BigDecimal(oldProgressValue);

        // 获得任务包下所有图纸
        List<ICIMDocumentMaster> documentMasters = taskPackage.getDocumentList();

        // 遍历图纸
        List<String> docNames = new ArrayList<>();
        Map<String, BigDecimal> documentNameToWeight = new HashMap<>();
        for (ICIMDocumentMaster documentMaster : documentMasters) {
            // 获取详设图纸名称
            docNames.add(documentMaster.getName());

            // 计算详设图纸各图纸的权重
            BigDecimal detailDesignWeight = new BigDecimal("0.0");

            // 获得图纸下设计数据
            List<ICIMCCMDesignObj> designObjs = packageQueryHelper.getDesignsByDocumentUIDAndCWA(documentMaster.getUid(), taskPackageCWA);

            // 遍历图纸下设计数据
            for (ICIMCCMDesignObj designObj : designObjs) {
                // 累加当前详设图纸的权重
                detailDesignWeight = NumberUtil.add(detailDesignWeight, getDesignWeight(designObj, taskPackagePurpose));
            }

            // 详设图纸名称及对应设计数据
            documentNameToWeight.put(documentMaster.getName(), detailDesignWeight);
        }

        // 判断是否有图纸
        if (docNames.isEmpty()) {
            throw new ServiceException("未找到任务包下图纸,刷新进度失败!");
        }

        // 获得加设图纸
        List<ICIMDocumentMaster> shopDocs = packageQueryHelper.getDocumentsByDesignPhaseAndDocNames(DesignPhaseEnum.EN_SHOP_DESIGN.getCode(), docNames);

        // 遍历加设图纸
        for (ICIMDocumentMaster documentMaster : shopDocs) {
            // 详设和加设同名的权重
            BigDecimal detailDesignWeightByName = documentNameToWeight.get(documentMaster.getName());
            if (null == detailDesignWeightByName) {
                throw new ServiceException("通过加设图纸名称获取权重失败!未找到加设图纸在任务包中同名图纸的权重!");
            }
            // 每张图纸加设总权重
            BigDecimal shopDesignAllWeight = new BigDecimal("0.0");
            // 每张图纸加设已完成权重
            BigDecimal truncatedWeight = new BigDecimal("0.0");

            // 获得图纸下设计数据
            List<ICIMCCMDesignObj> designObjs = packageQueryHelper.getDesignsByDocumentUIDAndCWA(documentMaster.getUid(), null);
            for (ICIMCCMDesignObj designObj : designObjs) {
                BigDecimal designTruncatedWeight = new BigDecimal("0.0");

                // 获得设计数据下工作步骤
                List<ICIMWorkStep> workSteps = packageQueryHelper.getWorkStepsByDesignUidAndRopWorkStepPhaseAndConsumeMaterial(designObj.getUid(), PackageType.TP, taskPackagePurpose, false);
                for (ICIMWorkStep workStep : workSteps) {
                    BigDecimal toAddWeightBigDecimal = new BigDecimal(workStep.getWSWeight() == null ? 0.0 : workStep.getWSWeight());
                    // 计算加设总权重
                    shopDesignAllWeight = NumberUtil.add(shopDesignAllWeight, toAddWeightBigDecimal);

                    if (!workStep.hasActualCompletedDate()) continue;
                    // 检查工作步骤是否在工作包中,并且检测工作包是否有修正进度,如果有修正进度则实际完成权重应为修正进度
                    IRel wsWp = workStep.getEnd2Relationships().getRel(WorkPackageContext.REL_WORK_PACKAGE_TO_WORK_STEP, false);
                    if (null == wsWp)  continue;

                    // 获得工作包
                    ICIMCCMWorkPackage icimccmWorkPackage = packageQueryHelper.getWorkPackageByUid(wsWp.getUid1());
                    if (icimccmWorkPackage == null) continue;

                    WorkPackageData workPackageData = workPackageDataMap.get(icimccmWorkPackage.getUid());
                    if (null == workPackageData) {
                        workPackageData = new WorkPackageData(icimccmWorkPackage);
                        workPackageDataMap.put(icimccmWorkPackage.getUid(), workPackageData);
                    }
                    workPackageData.hasNull();

                    // 修正进度不为空并且不为负数的时候计算修正进度
                    if (null != workPackageData.getEstimatedProgress() && 0 <= workPackageData.getEstimatedProgress().compareTo(BigDecimal.ZERO)) continue;
                    BigDecimal estimatedProgress = workPackageData.getEstimatedProgress();
                    // 如果有修正进度则修正已完成权重根据进度计算
                    // 修正完成工作项权重 = 计划权重*修正进度/已完成工作项总权重*工作项权重
                    if (!workPackageData.getInnerWorkStepOBIDs().contains(workStep.getUid())) {
                        workPackageData.getInnerWorkStepOBIDs().add(workStep.getUid());
                        workPackageData.setCompletedWeightCount(NumberUtil.add(workPackageData.getCompletedWeightCount(), toAddWeightBigDecimal));
                        workPackageData.setPlanWeight(NumberUtil.add(workPackageData.getPlanWeight(), toAddWeightBigDecimal));
                    }
                    BigDecimal totalEstimatedWeight = NumberUtil.mul(workPackageData.getPlanWeight(), estimatedProgress);
                    BigDecimal perEstimatedWeight = NumberUtil.div(totalEstimatedWeight, workPackageData.getCompletedWeightCount());
                    toAddWeightBigDecimal = NumberUtil.mul(perEstimatedWeight, toAddWeightBigDecimal);

                    // 单个设计数据已完成权重
                    designTruncatedWeight = NumberUtil.add(designTruncatedWeight, toAddWeightBigDecimal);
                }

                // 汇总加设已完成权重
                truncatedWeight = NumberUtil.add(truncatedWeight, designTruncatedWeight);
            }

            // 每张图纸加设进度 = 每张图纸加设已完成权重/每张图纸加设总权重
            BigDecimal shopDesignProgress = NumberUtil.div(truncatedWeight, shopDesignAllWeight);
            log.warn("每张图纸加设进度:{}=每张图纸加设已完成权重:{}/每张图纸加设总权重:{}", shopDesignProgress, truncatedWeight, shopDesignAllWeight);
            // 每张图纸详设已完成权重 = 每张图纸加设进度 * 详设和加设同名的权重
            BigDecimal detailDesignTruncatedWeight = NumberUtil.mul(shopDesignProgress, detailDesignWeightByName);
            log.warn("每张图纸详设已完成权重:{}=每张图纸加设进度:{}*详设和加设同名的权重:{}", detailDesignTruncatedWeight, shopDesignProgress, detailDesignWeightByName);
            // 详设所有已完成权重 = 每张图纸已完成权重累加
            detailDesignTruncatedAllWeight = NumberUtil.add(detailDesignTruncatedAllWeight, detailDesignTruncatedWeight);
            log.warn("详设所有已完成权重:{}=已统计图纸已完成权重:{}+当前图纸已完成权重:{}",
                    detailDesignTruncatedAllWeight.toString(), NumberUtil.sub(detailDesignTruncatedAllWeight, detailDesignTruncatedWeight).doubleValue(), detailDesignTruncatedWeight);
        }

        // 任务包实际进度 详设实际完成权重/详设图纸总权重
        double progress = NumberUtil.div(detailDesignTruncatedAllWeight, planWeight, 4).doubleValue();
        log.warn("任务包实际进度:{}=详设实际完成权重:{}/详设图纸总权重:{}", progress, detailDesignTruncatedAllWeight, planWeight);
        // 计算和原进度不等时更新
        if (0 != NumberUtil.compare(progress, oldProgress.doubleValue())) {
            taskPackage.updateProgress(progress, true);
        }
        return progress;
    }

    /**
     * 获取单个设计数据权重
     * @param designData
     * @param taskPackagePurpose
     * @return
     */
    private Double getDesignWeight(ICIMCCMDesignObj designData, String taskPackagePurpose) throws Exception {
        // 获得设计数据下工作步骤
        List<ICIMWorkStep> workSteps = packageQueryHelper.getWorkStepsByDesignUidAndRopWorkStepPhaseAndConsumeMaterial(designData.getUid(), PackageType.TP, taskPackagePurpose, false);
        // 计算权重
        BigDecimal weight = new BigDecimal("0.0");
        for (ICIMWorkStep workStep : workSteps) {
            BigDecimal toAddWeight = BigDecimal.valueOf(workStep.getWSWeight() == null ? 0.0 : workStep.getWSWeight());
            weight = NumberUtil.add(weight, toAddWeight);
        }
        return weight.doubleValue();
    }

    @Override
    public Double refreshPackagePlanWeight(String packageUid) throws Exception {
        if (StringUtils.isEmpty(packageUid)) {
            throw new ServiceException("请输入任务包UID!");
        }
        // 获得任务包
        ICIMCCMTaskPackage taskPackage = packageQueryHelper.getTaskPackageByUid(packageUid);

        // 原本权重
        String oldPlannedWeightValue = String.valueOf(taskPackage.getLatestValue(BasicPackageContext.INTERFACE_BASIC_PLAN_PACKAGE_OBJ, BasicPackageContext.PROPERTY_PLANNED_WEIGHT));
        BigDecimal oldPlannedWeight = StringUtils.isEmpty(oldPlannedWeightValue) ? new BigDecimal("0.0") : new BigDecimal(oldPlannedWeightValue);

        // 获得施工阶段相同的工作步骤列表
        List<ICIMWorkStep> workSteps = taskPackage.getWorkStepsWithSamePurpose();

        // 计算权重
        BigDecimal weight = new BigDecimal("0.0");
        for (ICIMWorkStep workStep : workSteps) {
            BigDecimal toAddWeight = BigDecimal.valueOf(workStep.getWSWeight() == null ? 0.0 : workStep.getWSWeight());
            weight = NumberUtil.add(weight, toAddWeight);
        }

        // 更新任务包权重
        if (0 != weight.compareTo(oldPlannedWeight)) {
            ObjectCollection objectCollection = new ObjectCollection(GeneralUtil.getUsername());
            taskPackage.BeginUpdate(objectCollection);
            taskPackage.setValue(BasicPackageContext.INTERFACE_BASIC_PLAN_PACKAGE_OBJ, BasicPackageContext.PROPERTY_PLANNED_WEIGHT, weight, null);
            taskPackage.FinishUpdate(objectCollection);
            objectCollection.commit();
        }
        return weight.doubleValue();
    }

    private TableData<JSONObject> filterDocument(CalculateByPriorityParamDTO calculateByPriorityParamDTO, List<IObject> documents) throws Exception {
        if (documents == null || documents.isEmpty()) {
            return GeneralUtil.generateTableData(null, GeneralUtil.getUsername(), CLASS_CIM_CCM_DOCUMENT_MASTER);
        }

        // 筛选
        Stream<IObject> stream = documents.stream();
        if (calculateByPriorityParamDTO.getFilterParams() != null && !calculateByPriorityParamDTO.getFilterParams().isEmpty()) {
            for (FilterParam filterParam : calculateByPriorityParamDTO.getFilterParams()) {
                stream = stream.filter(doc -> doc.getLatestValue(TaskPackageContext.INTERFACE_CIM_CCM_TASK_PACKAGE, filterParam.getPropertyDefUid()).equals(filterParam.getValue()));
            }
        }

        // 排序
        if (calculateByPriorityParamDTO.getOrderParams() != null && !calculateByPriorityParamDTO.getOrderParams().isEmpty()) {
            for (OrderParam orderParam : calculateByPriorityParamDTO.getOrderParams()) {
                stream = stream.sorted((s1, s2) -> s2.getLatestValue(TaskPackageContext.INTERFACE_CIM_CCM_TASK_PACKAGE, orderParam.getField()).toString().compareTo(s1.getLatestValue(TaskPackageContext.INTERFACE_CIM_CCM_TASK_PACKAGE, orderParam.getField()).toString()));
            }
        }

        // 分页
        List<IObject> documentMasters = stream.skip((calculateByPriorityParamDTO.getCurrent() - 1) * calculateByPriorityParamDTO.getPageSize()).limit(calculateByPriorityParamDTO.getPageSize()).collect(Collectors.toList());
        return GeneralUtil.generateTableData(documentMasters, GeneralUtil.getUsername(), CLASS_CIM_CCM_DOCUMENT_MASTER);
    }
}
