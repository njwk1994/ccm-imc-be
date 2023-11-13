package com.ccm.packagemanage.helpers.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.ccm.modules.COMContext;
import com.ccm.modules.packagemanage.*;
import com.ccm.modules.packagemanage.enums.DocStateEnum;
import com.ccm.modules.packagemanage.enums.PackageType;
import com.ccm.modules.packagemanage.enums.WSStatusEnum;
import com.ccm.modules.schedulermanage.ScheduleContext;
import com.ccm.packagemanage.helpers.IPackageQueryHelper;
import com.imc.common.core.enums.frame.PropertyDefinitions;
import com.imc.common.core.enums.frame.RelDirection;
import com.imc.common.core.enums.frame.Splitters;
import com.imc.framework.helpers.query.impl.QueryHelper;
import com.imc.framework.model.qe.QueryRequest;
import com.imc.schema.interfaces.*;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ccm.modules.COMContext.*;
import static com.ccm.modules.packagemanage.TaskPackageContext.CLASS_CIM_CCM_TASK_PACKAGE;

@Service
public class PackageQueryHelper extends QueryHelper implements IPackageQueryHelper {
    @Override
    public ICIMCCMTaskPackage getTaskPackageByUid(String uid) {
        return this.getObjectByUidAndDefinitionUid(uid, CLASS_CIM_CCM_TASK_PACKAGE, ICIMCCMTaskPackage.class);
    }

    @Override
    public ICIMCCMTaskPackage getTaskPackageByName(String name) {
        return this.getObjectByNameAndClassDefUid(name, CLASS_CIM_CCM_TASK_PACKAGE, ICIMCCMTaskPackage.class);
    }

    @Override
    public ICIMCCMWorkPackage getWorkPackageByUid(String uid) {
        return this.getObjectByUidAndDefinitionUid(uid, WorkPackageContext.CLASS_CIM_CCM_WORK_PACKAGE, ICIMCCMWorkPackage.class);
    }

    @Override
    public ICIMCCMWorkPackage getWorkPackageByName(String name) {
        return this.getObjectByNameAndClassDefUid(name, WorkPackageContext.CLASS_CIM_CCM_WORK_PACKAGE, ICIMCCMWorkPackage.class);
    }

    @Override
    public ICIMCCMPressureTestPackage getPressureTestPackageByUid(String uid) {
        return this.getObjectByUidAndDefinitionUid(uid, TestPackageContext.CLASS_CIM_CCM_PRESSURE_TEST_PACKAGE, ICIMCCMPressureTestPackage.class);
    }

    @Override
    public List<ICIMDocumentMaster> getDocumentsByTaskPackageUidAndPurposeAndCWAAndScheduleUID(String uid, String purpose, String cwa, String scheduleUID) {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addClassDefForQuery(CLASS_CIM_CCM_DOCUMENT_MASTER);
//        queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + REL_TASK_PACKAGE_TO_DOCUMENT, PropertyDefinitions.uid1.name(), SqlKeyword.NE, uid);

        if (!StringUtils.isEmpty(scheduleUID)) {
            queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + ScheduleContext.REL_SCHEDULE_TO_DOCUMENT, PropertyDefinitions.uid1.name(), SqlKeyword.EQ, scheduleUID);
        }

        // 筛选出相同施工阶段的图纸
        if (!StringUtils.isEmpty(purpose)) {
            // 筛选出相同施工区域的图纸
            if (!StringUtils.isEmpty(cwa)) {
                queryRequest.addQueryCriteria(null, WBSContext.PROPERTY_PURPOSE, SqlKeyword.EQ, purpose);
                queryRequest.addQueryCriteria(null, CWAContext.PROPERTY_CWA, SqlKeyword.EQ, cwa);
            }
        }
       return this.query(queryRequest, ICIMDocumentMaster.class);
    }

    @Override
    public List<ICIMDocumentMaster> getDocumentsByUIDs(List<String> uids) {
        return this.getObjectsByUIDsAndClassDefinitionUID(uids, CLASS_CIM_CCM_DOCUMENT_MASTER, ICIMDocumentMaster.class);
    }

    @Override
    public ICIMDocumentMaster getDocumentByUID(String uid) {
        return this.getObjectByUidAndDefinitionUid(uid, CLASS_CIM_CCM_DOCUMENT_MASTER, ICIMDocumentMaster.class);
    }

    @Override
    public List<ICIMDocumentMaster> getDocumentsByDesignPhaseAndDocNames(String designPhase, List<String> docNames) {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addClassDefForQuery(CLASS_CIM_CCM_DOCUMENT_MASTER);
        queryRequest.addQueryCriteria(null, WorkPackageContext.PROPERTY_DOC_DESIGN_PHASE, SqlKeyword.EQ, designPhase);
        queryRequest.addQueryCriteria(null, PropertyDefinitions.name.toString(), SqlKeyword.IN, String.join(Splitters.T_COMMA.getMsg(), docNames));
        return this.query(queryRequest, ICIMDocumentMaster.class);
    }

    @Override
    public List<ICIMDocumentMaster> getDocumentsByDesignPhaseAndDocStateAndDocNames(String designPhase, String docState, String docNames) {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addClassDefForQuery(CLASS_CIM_CCM_DOCUMENT_MASTER);
        queryRequest.addQueryCriteria(null, WorkPackageContext.PROPERTY_DOC_DESIGN_PHASE, SqlKeyword.EQ, designPhase);
        queryRequest.addQueryCriteria(null, "CIMDocState", SqlKeyword.EQ, DocStateEnum.EN_IFC.getCode());
        queryRequest.addQueryCriteria(null, PropertyDefinitions.name.toString(), SqlKeyword.IN, String.join(Splitters.T_COMMA.getMsg(), docNames));
        return this.query(queryRequest, ICIMDocumentMaster.class);
    }

    @Override
    public List<ICIMWorkStep> getWorkStepsByDesignUidAndRopWorkStepPhaseAndConsumeMaterial(String uid, PackageType packageType, String ropWorkStepPhase, boolean needConsumeMaterial) {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addClassDefForQuery(CLASS_CIM_CCM_WORK_STEP);
        queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + COMContext.REL_DESIGN_OBJ_TO_WORK_STEP, PropertyDefinitions.uid1.toString(), SqlKeyword.EQ, uid);
        queryRequest.addQueryCriteria(null, getPackageROPWorkStepPhaseType(packageType), SqlKeyword.EQ, ropWorkStepPhase);
        queryRequest.addQueryCriteria(null, "WSStatus", SqlKeyword.NE, WSStatusEnum.REVISED_DELETE.getCode());
        if (needConsumeMaterial) {
            queryRequest.addQueryCriteria(null, "WSConsumeMaterial", SqlKeyword.EQ, false);
        }
        return this.query(queryRequest, ICIMWorkStep.class);
    }

    private String getPackageROPWorkStepPhaseType(PackageType packageType) {
        String result = "";
        switch (packageType) {
            case TP:
                result = PROPERTY_WS_TP_PROCESS_PHASE;
                break;
            case WP:
                result = PROPERTY_WS_WP_PROCESS_PHASE;
                break;
        }
        if (com.imc.common.core.utils.StringUtils.isEmpty(result)) {
            throw new RuntimeException("获取ROP工作步骤阶段类型失败!");
        }
        return result;
    }

    @Override
    public List<ICIMWorkStep> getWorkStepsByDesignUID(String designUID) {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addClassDefForQuery(CLASS_CIM_CCM_WORK_STEP);
        queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + REL_DESIGN_OBJ_TO_WORK_STEP, PropertyDefinitions.uid1.toString(), SqlKeyword.EQ, designUID);
        return this.query(queryRequest, ICIMWorkStep.class);
    }

    @Override
    public List<ICIMWorkStep> getWorkStepsByDesignUIDAndPressureTestPackage(String pressureTestPackageUID, String designUID) {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addClassDefForQuery(CLASS_CIM_CCM_WORK_STEP);
//        queryRequest.addQueryCriteria(null, "CIMRevisionItemOperationState", SqlKeyword.NE, DesignObjOperateStatusEnum.DELETE.getCode());
        queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() +TestPackageContext.REL_PRESSURE_TEST_PACKAGE_TO_DOCUMENT, PropertyDefinitions.uid1.toString(), SqlKeyword.EQ, pressureTestPackageUID);
        queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + REL_DESIGN_OBJ_TO_WORK_STEP, PropertyDefinitions.uid1.name(), SqlKeyword.EQ, designUID);
        return this.query(queryRequest, ICIMWorkStep.class);
    }

    @Override
    public ICIMWorkStep getWorkStepByUID(String uid) {
        return this.getObjectByUidAndDefinitionUid(uid, CLASS_CIM_CCM_WORK_STEP, ICIMWorkStep.class);
    }

    @Override
    public ICIMCCMPriority getPriorityByUid(String uid) {
        return this.getObjectByUidAndDefinitionUid(uid, CLASS_CIM_CCM_PRIORITY, ICIMCCMPriority.class);
    }

    @Override
    public List<ICIMCCMConstructionType> getAllConstructionTypes() {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addClassDefForQuery(ConstructionTypeContext.CLASS_CIM_CCM_CONSTRUCTION_TYPE);
        return this.query(queryRequest, ICIMCCMConstructionType.class);
    }

    @Override
    public List<ICIMCCMDesignObj> getDesignsByWorkPackageUidAndDocumentUIDs(String workPackageUID, List<String> documentUIDs) {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addInterfaceDefForQuery(INTERFACE_CIM_DESIGN_OBJ);
        queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + WorkPackageContext.REL_WORK_PACKAGE_TO_DESIGN, PropertyDefinitions.uid1.toString(), SqlKeyword.EQ, workPackageUID);
//        queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + REL_DOCUMENT_TO_DESIGN_OBJ, PropertyDefinitions.uid1.name(), SqlKeyword.IN, String.join(Splitters.T_COMMA.getMsg(), documentUIDs));
        return this.query(queryRequest, ICIMCCMDesignObj.class);
    }

    @Override
    public List<ICIMCCMDesignObj> getDesignsByPurposeAndCWA(String purpose, String cwa) {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addInterfaceDefForQuery(INTERFACE_CIM_DESIGN_OBJ);

        // 根据施工阶段获取设计数据
        queryRequest.addQueryCriteria(null, WBSContext.PROPERTY_PURPOSE, SqlKeyword.EQ, purpose);

        // 根据施工区域筛选出对设计数据进行筛选
        queryRequest.addQueryCriteria(null, CWAContext.PROPERTY_CWA, SqlKeyword.EQ, cwa);

        // 获取设计数据
        return this.query(queryRequest, ICIMCCMDesignObj.class);
    }

    @Override
    public List<ICIMCCMDesignObj> getDesignsByWPPurposeAndCWA(String wpPurpose, String cwa) {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addInterfaceDefForQuery(INTERFACE_CIM_DESIGN_OBJ);

        // 根据施工阶段获取设计数据
        queryRequest.addQueryCriteria(null, WBSContext.PROPERTY_WP_PURPOSE, SqlKeyword.EQ, wpPurpose);

        // 根据施工区域筛选出对设计数据进行筛选
        queryRequest.addQueryCriteria(null, CWAContext.PROPERTY_CWA, SqlKeyword.EQ, cwa);

        // 获取设计数据
        return this.query(queryRequest, ICIMCCMDesignObj.class);
    }

    @Override
    public List<ICIMCCMDesignObj> getDesignsByDocumentUIDsAndCWA(List<String> documentUIDs, String cwa) {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addInterfaceDefForQuery(INTERFACE_CIM_DESIGN_OBJ);
//        queryRequest.addQueryCriteria(null, "CIMRevisionItemOperationState", SqlKeyword.NE, DesignObjOperateStatusEnum.DELETE.getCode());
        queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + REL_DOCUMENT_TO_DESIGN_OBJ, PropertyDefinitions.uid1.toString(), SqlKeyword.IN, String.join(Splitters.T_COMMA.getMsg(), documentUIDs));
        // 工作包施工区域不为空时,需要同施工区域
        if (!com.alibaba.excel.util.StringUtils.isEmpty(cwa)) {
            queryRequest.addQueryCriteria(null, CWAContext.PROPERTY_CWA, SqlKeyword.EQ, cwa);
        }
        return this.query(queryRequest, ICIMCCMDesignObj.class);
    }

    @Override
    public List<ICIMCCMDesignObj> getDesignsByDocumentUIDAndCWA(String documentUID, String cwa) {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addInterfaceDefForQuery(INTERFACE_CIM_DESIGN_OBJ);
//        queryRequest.addQueryCriteria(null, "CIMRevisionItemOperationState", SqlKeyword.NE, DesignObjOperateStatusEnum.DELETE.getCode());
        queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + REL_DOCUMENT_TO_DESIGN_OBJ, PropertyDefinitions.uid1.toString(), SqlKeyword.EQ, documentUID);
        if (!StringUtils.isEmpty(cwa)) {
            queryRequest.addQueryCriteria(null, CWAContext.PROPERTY_CWA, SqlKeyword.EQ, cwa);
        }
        return this.query(queryRequest, ICIMCCMDesignObj.class);
    }

    @Override
    public List<ICIMCCMDesignObj> getDesignsByTaskPackageUIDAndDocumentUID(String taskPackageUID, String documentUID) {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addInterfaceDefForQuery(INTERFACE_CIM_DESIGN_OBJ);
//        queryRequest.addQueryCriteria(null, "CIMRevisionItemOperationState", SqlKeyword.NE, DesignObjOperateStatusEnum.DELETE.getCode());
        queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + TaskPackageContext.REL_TASK_PACKAGE_TO_DESIGN_OBJ, PropertyDefinitions.uid1.toString(), SqlKeyword.EQ, taskPackageUID);
        queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + REL_DOCUMENT_TO_DESIGN_OBJ, PropertyDefinitions.uid1.name(), SqlKeyword.EQ, documentUID);
        return this.query(queryRequest, ICIMCCMDesignObj.class);
    }

    @Override
    public List<ICIMCCMDesignObj> getDesignsByPressureTestPackageUIDAndDocumentUID(String pressureTestPackageUID, String documentUID) {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addInterfaceDefForQuery(INTERFACE_CIM_DESIGN_OBJ);
//        queryRequest.addQueryCriteria(null, "CIMRevisionItemOperationState", SqlKeyword.NE, DesignObjOperateStatusEnum.DELETE.getCode());
        queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + TestPackageContext.REL_PRESSURE_TEST_PACKAGE_TO_DESIGN_OBJ, PropertyDefinitions.uid1.toString(), SqlKeyword.EQ, pressureTestPackageUID);
        queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + REL_DOCUMENT_TO_DESIGN_OBJ, PropertyDefinitions.uid1.name(), SqlKeyword.EQ, documentUID);
        return this.query(queryRequest, ICIMCCMDesignObj.class);
    }

    @Override
    public List<ICIMCCMDesignObj> getDesignsByDocumentUIDAndClassDef(String documentUID, String classDef) {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addClassDefForQuery(classDef);
//        queryRequest.addQueryCriteria(null, "CIMRevisionItemOperationState", SqlKeyword.NE, DesignObjOperateStatusEnum.DELETE.getCode());
        queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + REL_DOCUMENT_TO_DESIGN_OBJ, PropertyDefinitions.uid1.name(), SqlKeyword.EQ, documentUID);
        return this.query(queryRequest, ICIMCCMDesignObj.class);
    }

    @Override
    public List<ICIMCCMDesignObj> getDesignsByDocumentUIDAndNotRelPressureTestPackageUID(String documentUID, String uid, List<String> designUIDS) throws Exception {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addInterfaceDefForQuery(INTERFACE_CIM_DESIGN_OBJ);
        if (!designUIDS.isEmpty()) {
            queryRequest.addQueryCriteria(null, PropertyDefinitions.uid.toString(), SqlKeyword.NOT_IN, String.join(Splitters.T_COMMA.getMsg(), designUIDS));
        }
//        queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + REL_DOCUMENT_TO_DESIGN_OBJ, PropertyDefinitions.uid1.toString(), SqlKeyword.EQ, documentUID);
//        queryRequest.addQueryCriteria(null, "CIMRevisionItemOperationState", SqlKeyword.NE, DesignObjOperateStatusEnum.DELETE.getCode());
//        getQueryEngine().prepareQuery(queryRequest);
//        List<QueryTableWrapper> queryTables = queryRequest.getRouteTables();
//        for (QueryTableWrapper queryTableWrapper : queryTables) {
//            if (!queryTableWrapper.getChildren().isEmpty()) {
//                for (QueryTableWrapper queryCriteria : queryTableWrapper.getChildren()) {
//                    queryCriteria.setJoinMode("left");
//                }
//            }
//        }
//        routePart = new QueryTableWrapper();
//        routePart.setPartType(CriteriaPartType.None);
//        routePart.setClassOrRelDef(uid);
//        queryRequest.getRouteTables().add(routePart);
        return this.query(queryRequest, ICIMCCMDesignObj.class);
    }

    @Override
    public ICIMCCMDesignObj getDesignByUid(String uid) {
        return this.getObjectByUidAndDefinitionUid(uid, INTERFACE_CIM_DESIGN_OBJ, ICIMCCMDesignObj.class);
    }

    @Override
    public List<ICIMCCMPTPackageMaterialSpecification> getMaterialSpecifications() {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addClassDefForQuery(TestPackageContext.CLASS_CIM_CCM_PRESSURE_TEST_PACKAGE_MATERIAL_SPECIFICATION);
        return this.query(queryRequest, ICIMCCMPTPackageMaterialSpecification.class);
    }

    @Override
    public List<IObject> getPTPackageMaterialTemplatesByUIDs(List<String> uids) {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addClassDefForQuery(TestPackageContext.CLASS_CIM_CCM_PRESSURE_TEST_PACKAGE_MATERIAL_TEMPLATE);
        queryRequest.addQueryCriteria(null, PropertyDefinitions.uid.name(), SqlKeyword.IN, String.join(Splitters.T_COMMA.getMsg(), uids));
        return this.query(queryRequest, IObject.class);
    }

    @Override
    public ICIMCCMPTPackageMaterial getPTPackageMaterialByUID(String uid) {
        return this.getObjectByUidAndDefinitionUid(uid, TestPackageContext.CLASS_CIM_CCM_PRESSURE_TEST_PACKAGE_MATERIAL, ICIMCCMPTPackageMaterial.class);
    }
}
