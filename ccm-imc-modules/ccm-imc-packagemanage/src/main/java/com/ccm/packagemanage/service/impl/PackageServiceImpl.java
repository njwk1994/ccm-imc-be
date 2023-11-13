package com.ccm.packagemanage.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ccm.modules.packagemanage.BasicTargetContext;
import com.ccm.modules.packagemanage.ConstructionTypeContext;
import com.ccm.packagemanage.domain.*;
import com.ccm.modules.packagemanage.enums.PackageType;
import com.ccm.packagemanage.executor.PriorityWeightCalculationExecutor;
import com.ccm.packagemanage.helpers.IPackageQueryHelper;
import com.ccm.packagemanage.service.IPackageService;
import com.ccm.packagemanage.service.ITaskPackageService;
import com.ccm.packagemanage.service.IWorkPackageService;
import com.imc.common.core.exception.ServiceException;
import com.imc.common.core.utils.ExceptionUtil;
import com.imc.common.core.web.table.TableConfig;
import com.imc.common.core.web.table.TableData;
import com.imc.common.core.web.table.TableDataSource;
import com.imc.common.core.web.table.TableFieldColumn;
import com.imc.framework.utils.GeneralUtil;
import com.imc.schema.interfaces.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static com.ccm.modules.COMContext.*;
import static com.ccm.modules.packagemanage.TaskPackageContext.REL_TASK_PACKAGE_TO_DOCUMENT;

@Slf4j
@Service
public class PackageServiceImpl implements IPackageService {

    @Lazy
    @Autowired
    ITaskPackageService taskPackageService;

    @Lazy
    @Autowired
    IWorkPackageService workPackageService;

    @Autowired
    IPackageQueryHelper packageQueryHelper;

    ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public TableData<JSONObject> selectPackageConstructionTypes(ConstructionTypesQueryParamDTO constructionTypesQueryParamDTO) throws Exception {

        // 校验
        if (PackageType.TP != constructionTypesQueryParamDTO.getPackageType()
                && PackageType.WP != constructionTypesQueryParamDTO.getPackageType()
                && PackageType.PP != constructionTypesQueryParamDTO.getPackageType()  ) {
            throw new RuntimeException("包类型错误!只支持任务包、工作包和试压包!packageClassDefinitionUID:" + constructionTypesQueryParamDTO.getPackageType());
        }

        // 查询设计对象的施工分类信息
        List<ICIMCCMConstructionType> constructionTypes = packageQueryHelper.getAllConstructionTypes();
        if (constructionTypes.isEmpty()) {
            return null;
        }

        log.warn("开始查询包{}下是否存在有材料消耗的数据", constructionTypesQueryParamDTO.getUid());
        long startTime = System.currentTimeMillis();

        List<Future<String>> futures = new ArrayList<>();
        List<String> results = new ArrayList<>();
        for (IObject constructionType : constructionTypes) {
            Callable<String> verifyCallable = () -> verifyTask(constructionTypesQueryParamDTO, constructionType);
            Future<String> future = executor.submit(verifyCallable);
            futures.add(future);
        }
        futures.forEach(x -> {
            try {
                results.add(x.get());
            } catch (Exception e) {
                throw new ServiceException(ExceptionUtil.getExceptionMessage(e));
            }
        });
        log.warn("查询{}下所有类型材料消耗数据结束,总耗时{}ms", constructionTypesQueryParamDTO.getUid(), System.currentTimeMillis() - startTime);

        List<IObject> collect = constructionTypes.stream().filter(o -> results.contains(o.getUid())).collect(Collectors.toList());
        return GeneralUtil.generateTableData(collect, GeneralUtil.getUsername(), ConstructionTypeContext.CLASS_CIM_CCM_CONSTRUCTION_TYPE);
    }

    @Override
    public Map<IObject, List<ICIMCCMDesignObj>> ensureIFCDocuments(Map<IObject, List<ICIMCCMDesignObj>> documents) {
        List<String> ifcInfo = new ArrayList<String>() {{
            this.add("ELT_IFC_CIMDocState");
        }};
        documents.keySet().stream().filter(x -> ifcInfo.stream().noneMatch(c -> c.equalsIgnoreCase(x.getLatestValue(INTERFACE_CIM_DOCUMENT_MASTER,"CIMDocState").toString()))).findFirst().ifPresent(documents::remove);
        Map<IObject, List<ICIMCCMDesignObj>> docs = new HashMap<>();
        for (IObject doc : documents.keySet()) {
            // 排除已经关联其他任务包的图纸
            List<IRel> rels = doc.getEnd1Relationships().getRels(REL_TASK_PACKAGE_TO_DOCUMENT, false).getRelsByRelDef(REL_TASK_PACKAGE_TO_DOCUMENT);
            if (rels.size() > 0) continue;
            docs.put(doc, documents.get(doc));
        }

        return docs;
    }

    @Override
    public Map<IObject, Double> doCalculateWeightAsPerDocument(Map<IObject, List<ICIMCCMDesignObj>> documents, ICIMCCMPriority priority) {
        Map<IObject, Double> mapOfDocumentWeight = new HashMap<>();
        if (documents != null && priority != null) {
            log.trace("enter to calculate weight for document" + documents.size());
            List<Future<DocumentPriorityWeightDTO>> executors = new ArrayList<>();
            List<DocumentPriorityWeightDTO> weightVos = new ArrayList<>();
            for (Map.Entry<IObject, List<ICIMCCMDesignObj>> collectionEntry : documents.entrySet()) {
                Future<DocumentPriorityWeightDTO> future = executor.submit(new PriorityWeightCalculationExecutor(collectionEntry.getKey(), collectionEntry.getValue(), priority));
                executors.add(future);
            }

            executors.forEach(future -> {
                try {
                    weightVos.add(future.get());
                } catch (Exception e) {
                    throw new ServiceException(ExceptionUtil.getExceptionMessage(e));
                }
            });

            if (!weightVos.isEmpty()) {
                for (DocumentPriorityWeightDTO weightVo : weightVos) {
                    mapOfDocumentWeight.put(weightVo.getDocument(), weightVo.getWeight());
                }
            }
            log.info("complete to do calculation and final status: " + mapOfDocumentWeight.size());
        }
        return mapOfDocumentWeight;
    }

    @Override
    public List<IObject> renderCollectionWithPriority(Map<IObject, Double> mapDocumentWeight) {
        List<IObject> documents = new ArrayList<>();
        if (mapDocumentWeight != null) {
            for (Map.Entry<IObject, Double> doubleEntry : mapDocumentWeight.entrySet()) {

                try {
//                    IInterface dynInterface = doubleEntry.getKey().getInterface(INTERFACE_CIM_DOCUMENT_MASTER);
//                    if (dynInterface != null) {
//                        PropertyBase propertyBase = new PropertyBase("DynWeight", dynInterface.getInterfaceDefUid(), PropertyValueTypes.Double, "", false,false);
//                        propertyBase.setParent(dynInterface);
//                        propertyBase.setValue(doubleEntry.getValue(), null);
//                        dynInterface.getProperties().add(propertyBase);
//                    }
                    documents.add(doubleEntry.getKey());
                } catch (Exception e) {
                    throw new ServiceException(ExceptionUtil.getExceptionMessage(e));
                }


            }
        }
        log.info("render collection for priority");
        return documents;
    }

    @Override
    public Map<IObject, List<ICIMCCMDesignObj>> splitCompObjsByDocument(List<ICIMCCMDesignObj> designOBJs) throws Exception {
        Map<IObject, List<ICIMCCMDesignObj>> documents = new HashMap<>();

        for (ICIMCCMDesignObj design : designOBJs) {
            IRel rel = design.getEnd1Relationships().getRel(REL_DOCUMENT_TO_DESIGN_OBJ, false);
            IObject document = packageQueryHelper.getDocumentByUID(rel.getUid1());
            if (documents.get(document) != null) {
                documents.get(document).add(design);
            } else {
                documents.put(document, new ArrayList<>());
                documents.get(document).add(design);
            }
        }
        return documents;
    }

    private String verifyTask(ConstructionTypesQueryParamDTO constructionTypesQueryParamDTO, IObject constructionType) throws Exception {
        long s = System.currentTimeMillis();
        String result = null;
        ICIMCCMBasicTargetObj targetObj = constructionType.toInterface(ICIMCCMBasicTargetObj.class);
        log.warn("开始查询包{}下{}类型是否存在有材料消耗的数据", constructionTypesQueryParamDTO.getUid(), targetObj.getName());
        MaterialQueryParamDTO materialQueryParamDTO = new MaterialQueryParamDTO();
        materialQueryParamDTO.setUid(constructionTypesQueryParamDTO.getUid());
        materialQueryParamDTO.setPageSize(1L);
        materialQueryParamDTO.setCurrent(1L);
        materialQueryParamDTO.setClassDefinitionUID(targetObj.getTargetClassDef());
        materialQueryParamDTO.setClassDefUid(targetObj.getTargetClassDef());
        switch (constructionTypesQueryParamDTO.getPackageType()) {
            case TP: {
                TableData<JSONObject> tableData = taskPackageService.selectDesignDataByPurposeAndConsumeMaterial(materialQueryParamDTO, constructionTypesQueryParamDTO.getNeedConsumeMaterial());
                if (!tableData.getTableData().getData().isEmpty()) {
                    result = constructionType.getUid();
                } else {
                    result = "";
                }
                break;
            }
            case WP: {
                TableData<JSONObject> tableData = workPackageService.selectDesignDataByPurposeAndConsumeMaterialForWP(materialQueryParamDTO, constructionTypesQueryParamDTO.getNeedConsumeMaterial());
                if (!tableData.getTableData().getData().isEmpty()) {
                    result = constructionType.getUid();
                } else {
                    result = "";
                }
                break;
            }
            case PP:
                result = "";
                break;
            default:
                throw new ServiceException("获取设计数据" + constructionType.getName() + "类型失败!packageUid:" + constructionType.getUid() + ",packageType:" + constructionTypesQueryParamDTO.getPackageType());
        }
        log.warn("查询{}下{}类型是否存在有材料消耗的数据结束,耗时{}ms",  constructionTypesQueryParamDTO.getUid(), targetObj.getName(), System.currentTimeMillis() - s);

        return result;
    }

    @Override
    public TableData<JSONObject> selectDocumentConstructionTypes(String uid, boolean showDeleted) throws Exception {
        if (StringUtils.isEmpty(uid)) {
            throw new ServiceException("请填写图纸UID");
        }

        // 获得所有设计类型
        List<ICIMCCMConstructionType> constructionTypes = packageQueryHelper.getAllConstructionTypes();
        List<IObject> result = new ArrayList<>();

        // 遍历所有设计类型
        for (ICIMCCMConstructionType constructionType: constructionTypes) {
            // 获得设计类型对应的类型
            String classDef = String.valueOf(constructionType.getLatestValue(BasicTargetContext.INTERFACE_BASIC_BASIC_TARGET_OBJ, BasicTargetContext.PROPERTY_TARGET_CLASS_DEF));

            // 查询当前图纸下是否有此类设计数据
            List<ICIMCCMDesignObj> designOBJs = packageQueryHelper.getDesignsByDocumentUIDAndClassDef(uid, classDef);

            if (!designOBJs.isEmpty()) {
                result.add(constructionType);
            }
        }
        return GeneralUtil.generateTableData(result, GeneralUtil.getUsername(), ConstructionTypeContext.CLASS_CIM_CCM_CONSTRUCTION_TYPE);
    }

    @Override
    public TableData<JSONObject> getPartialStatusRequestByObjects(Map<String, Object> objectMap) {
        TableData<JSONObject> tableData = new TableData<>();
        // 属性列
        List<TableFieldColumn> tableFieldColumns = new ArrayList<>();
        tableFieldColumns.add(new TableFieldColumn("Msg", "状态", true));
        tableFieldColumns.add(new TableFieldColumn("LN_CODE", "图纸号", true));
        tableFieldColumns.add(new TableFieldColumn("CC_SHORT_DESC", "描述", true));
        tableFieldColumns.add(new TableFieldColumn("COMMODITY_CODE", "CC码", true));
        tableFieldColumns.add(new TableFieldColumn("IDENT_CODE", "IC码", true));
        tableFieldColumns.add(new TableFieldColumn("INPUT_1", "尺寸1", true));
        tableFieldColumns.add(new TableFieldColumn("INPUT_2", "尺寸2", true));
        tableFieldColumns.add(new TableFieldColumn("INPUT_3", "尺寸3", true));
        tableFieldColumns.add(new TableFieldColumn("INPUT_4", "尺寸4", true));
        tableFieldColumns.add(new TableFieldColumn("INPUT_5", "尺寸5", true));
        tableFieldColumns.add(new TableFieldColumn("WH_CODE", "仓库", true));
        tableFieldColumns.add(new TableFieldColumn("LOC_CODE", "库位", true));
        tableFieldColumns.add(new TableFieldColumn("LP_QTY", "设计量", true));
        tableFieldColumns.add(new TableFieldColumn("ONHAND_QTY", "库存量", true));
        tableFieldColumns.add(new TableFieldColumn("RESV_QTY", "预留量", true));
        tableFieldColumns.add(new TableFieldColumn("ETA_DATE", "预计到货日期", true));
        tableFieldColumns.add(new TableFieldColumn("REQ_SITE_DATE", "现场需求日期", true));
        tableFieldColumns.add(new TableFieldColumn("TAG_NUMBER", "位号", true));
        tableFieldColumns.add(new TableFieldColumn("UNIT_CODE", "单位", true));
        tableFieldColumns.add(new TableFieldColumn("IIS_TYPE", "库存类型", true));
        tableFieldColumns.add(new TableFieldColumn("LP_ID", "合同行号", true));
        tableFieldColumns.add(new TableFieldColumn("BOM_PATH", "BOM路径", true));
        tableData.setTableColumns(tableFieldColumns);

        // 表格配置
        tableData.setTableConfig(new TableConfig());

        // 表格数据
        JSONArray datas = (JSONArray)objectMap.get("data");
        if (datas.isEmpty()) return tableData;
        TableDataSource<JSONObject> dataSource = new TableDataSource<>();
        dataSource.setData(datas.toList(JSONObject.class));
        tableData.setTableData(dataSource);
        return tableData;
    }
}
