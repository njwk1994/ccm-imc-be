package com.ccm.document.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.ccm.document.domain.PackageRevisionParamDTO;
import com.ccm.document.helpers.IDocumentQueryHelper;
import com.ccm.document.service.IRevisedService;
import com.ccm.modules.documentmanage.IMCDocumentContext;
import com.ccm.modules.documentmanage.constant.DocumentCommon;
import com.ccm.modules.documentmanage.enums.CIMRevisionStatus;
import com.ccm.modules.documentmanage.enums.DesignObjOperateStatus;
import com.ccm.modules.documentmanage.enums.RevStateEnum;
import com.ccm.modules.packagemanage.TaskPackageContext;
import com.ccm.modules.packagemanage.TestPackageContext;
import com.ccm.modules.packagemanage.WBSContext;
import com.ccm.modules.packagemanage.WorkPackageContext;
import com.ccm.modules.packagemanage.enums.WSStatusEnum;
import com.imc.common.core.enums.frame.PropertyDefinitions;
import com.imc.common.core.enums.frame.RelDirection;
import com.imc.common.core.enums.frame.Splitters;
import com.imc.common.core.exception.ServiceException;
import com.imc.framework.collections.impl.ObjectCollection;
import com.imc.framework.context.Context;
import com.imc.framework.entity.loader.struct.LoadClassDefStruct;
import com.imc.framework.entity.loader.struct.LoadRelStruct;
import com.imc.framework.enums.RevStatus;
import com.imc.framework.model.qe.QueryRequest;
import com.imc.framework.utils.GeneralUtil;
import com.imc.framework.utils.SchemaUtil;
import com.imc.schema.interfaces.*;
import com.typesafe.config.ConfigException;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.ccm.modules.COMContext.*;

/**
 * @description：TODO
 * @author： kekai.huang
 * @create： 2023/11/7 10:12
 */
@Service
public class RevisedServiceImpl implements IRevisedService {

    @Autowired
    IDocumentQueryHelper documentQueryHelper;

    @Override
    public List<LoadClassDefStruct> getDesignsByDocumentUid(String documentUid, List<LoadClassDefStruct> designs, List<LoadRelStruct> relDocDesign) {
        // 根据文档UID获得此文档下的所有设计数据的UID
        List<String> designUIDs = relDocDesign.stream().filter(x -> x.getUid1().equals(documentUid)).map(LoadRelStruct::getUid2).collect(Collectors.toList());

        // 根据设计数据UID集合获得对应的集合
        return designs.stream().filter(x -> designUIDs.contains(x.getUid())).collect(Collectors.toList());
    }

    @Override
    public void handlePackageAndRelRevised(LoadClassDefStruct document, List<LoadClassDefStruct> designs) throws Exception {

        // 获得文档对象
        IObject documentObj = documentQueryHelper.getDocumentByUID(document.getUid());

        // 获得文档状态
        ICIMRevisionItem documentRevisionItem = documentObj.toInterface(ICIMRevisionItem.class);
        String documentRevState = documentRevisionItem.getCIMRevisionItemRevState();
        List<String> packageClassDefinitions = new ArrayList<>();
        packageClassDefinitions.add(TaskPackageContext.CLASS_CIM_CCM_TASK_PACKAGE);
        packageClassDefinitions.add(TestPackageContext.CLASS_CIM_CCM_PRESSURE_TEST_PACKAGE);
        packageClassDefinitions.add(WorkPackageContext.CLASS_CIM_CCM_WORK_PACKAGE);

        // 更新文档关联的包为升版状态
        if ( CIMRevisionStatus.REVISED.getCode().equalsIgnoreCase(documentRevState)) {
            for (String classDefinitionUid : packageClassDefinitions) {
                List<IRel> relTasks = documentObj.getEnd2Relationships().getRels(classDefinitionUid, false).getRelsByRelDef(classDefinitionUid);
                for (IRel rel : relTasks) {
                    IObject packageObj = Context.Instance.getQueryHelper().getObjectByUidAndDefinitionUid(rel.getUid1(),classDefinitionUid,IObject.class);
                    setObjectRevised(packageObj);
                }
            }
        }
    }

    @Override
    public void createDocumentRevisionByDocumentUid(String documentUid, List<LoadClassDefStruct> designs, ObjectCollection objectCollection) throws Exception {
        if (StringUtils.isEmpty(documentUid)) throw new ServiceException("请传入文档UID");

        // 获得图纸
        IObject document = documentQueryHelper.getDocumentByUID(documentUid);
        if (document == null) throw new ServiceException("未找到文档");

        // 创建设计文档版本
        IObject docRevision = createDocRevisionObj(document, designs, objectCollection);

        // 创建设计文档修订
        IObject docVersion = createDocVersionObj(document, objectCollection);

        // 获得创建规则
        IObject revisionSchema = documentQueryHelper.getRevisionSchemeByUid(DocumentCommon.VERSION_SCHEMA_UID);

        // 创建Revision与RevisionScheme的关联
        IRel relRevisionRevisionScheme = SchemaUtil.newRelationship(IMCDocumentContext.REL_CIM_REVISION_SCHEMA, docRevision, revisionSchema);
        relRevisionRevisionScheme.finishCreate(objectCollection);

        // 创建Master与Revision的关联
        IRel relMasterRevision = SchemaUtil.newRelationship(IMCDocumentContext.REL_CIM_DOCUMENT_REVISIONS, document, docRevision);
        relMasterRevision.finishCreate(objectCollection);

        // 创建Revision与Version的关联
        IRel relRevisionVersion = SchemaUtil.newRelationship(IMCDocumentContext.REL_CIM_REVISION_VERSIONS, docRevision, docVersion);
        relRevisionVersion.finishCreate(objectCollection);
    }

    @Override
    public void packageRevisionHandler(PackageRevisionParamDTO packageRevisionParamDTO) throws Exception {
        if (StringUtils.isEmpty(packageRevisionParamDTO.getPackageUid())) throw new ServiceException("请输入包UID！");
        if (StringUtils.isEmpty(packageRevisionParamDTO.getClassDefinitionUid())) throw new ServiceException("请输入包类型！");
        if (packageRevisionParamDTO.getPackageRevisionType() == null) throw new ServiceException("请输入升版类型！");

        // 获得包对象
        IObject packageObj = Context.Instance.getQueryHelper().getObjectByUidAndDefinitionUid(packageRevisionParamDTO.getPackageUid(), packageRevisionParamDTO.getClassDefinitionUid(), IObject.class);

        switch (packageRevisionParamDTO.getPackageRevisionType()) {
            case DELETE:
                this.batchDeleteWorkSteps(packageObj, packageRevisionParamDTO.getClassDefinitionUid());
                break;
            case UPDATE:
                this.batchUpdateWorkSteps(packageObj, packageRevisionParamDTO.getClassDefinitionUid());
                break;
            case REFRESH:
                this.batchDeleteWorkSteps(packageObj, packageRevisionParamDTO.getClassDefinitionUid());
                this.batchUpdateWorkSteps(packageObj, packageRevisionParamDTO.getClassDefinitionUid());
                break;
        }
    }

    @Override
    public void packageConfirmRevision(PackageRevisionParamDTO packageRevisionParamDTO) throws Exception {
        // 获得包对象
        IObject packageObj = Context.Instance.getQueryHelper().getObjectByUidAndDefinitionUid(packageRevisionParamDTO.getPackageUid(), packageRevisionParamDTO.getClassDefinitionUid(), IObject.class);

        // 确认升版
        ObjectCollection objectCollection = new ObjectCollection();
        ICIMRevisionItem icimRevisionItem = packageObj.toInterface(ICIMRevisionItem.class);
        icimRevisionItem.BeginUpdate(objectCollection);
        icimRevisionItem.setCIMRevisionItemRevState(CIMRevisionStatus.CURRENT.getCode());
        icimRevisionItem.FinishUpdate(objectCollection);

        objectCollection.commit();
    }

    @Override
    public void documentConfirmRevision(String documentUID) throws Exception {
        if (StringUtils.isEmpty(documentUID)) throw new ServiceException("请填入文档UID");

        // 获得文档信息
        IObject document = documentQueryHelper.getDocumentByUID(documentUID);
        if (document == null) throw new ServiceException("未找到文档对象UID：" + documentUID);

        // 事务
        ObjectCollection objectCollection = new ObjectCollection();

        // 文档信息确认升版
        ICIMRevisionItem icimRevisionItem = document.toInterface(ICIMRevisionItem.class);
        icimRevisionItem.BeginUpdate(objectCollection);
        icimRevisionItem.setCIMRevisionItemRevState(CIMRevisionStatus.CURRENT.getCode());
        icimRevisionItem.FinishUpdate(objectCollection);

        // 查询文档下为升版状态的设计数据
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addInterfaceDefForQuery(INTERFACE_CIM_DESIGN_OBJ);
        queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + REL_DOCUMENT_TO_DESIGN_OBJ, PropertyDefinitions.uid1.name(), SqlKeyword.EQ, document.getUid());
        queryRequest.addQueryCriteria(null, IMCDocumentContext.REVISION_ITEM_REV_STATE, SqlKeyword.NE, CIMRevisionStatus.REVISED.getCode());
        List<IObject> designs = Context.Instance.getQueryHelper().query(queryRequest, IObject.class);
        for (IObject design : designs) {
            icimRevisionItem = design.toInterface(ICIMRevisionItem.class);
            icimRevisionItem.BeginUpdate(objectCollection);
            icimRevisionItem.setCIMRevisionItemRevState(CIMRevisionStatus.CURRENT.getCode());
            icimRevisionItem.FinishUpdate(objectCollection);
        }

        // 提交事务
        objectCollection.commit();
    }

    /**
     * 批量更新工作步骤
     * @param packageObj
     * @param classDef
     */
    private void batchUpdateWorkSteps(IObject packageObj, String classDef) throws Exception {
        if (TaskPackageContext.CLASS_CIM_CCM_TASK_PACKAGE.equalsIgnoreCase(classDef)) return;

        // 获得包下的设计数据
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addInterfaceDefForQuery(INTERFACE_CIM_DESIGN_OBJ);
        if (TestPackageContext.CLASS_CIM_CCM_PRESSURE_TEST_PACKAGE.equalsIgnoreCase(classDef)) {
            queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + TestPackageContext.REL_PRESSURE_TEST_PACKAGE_TO_DESIGN_OBJ, PropertyDefinitions.uid1.name(), SqlKeyword.EQ, packageObj.getUid());
        } else {
            queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + WorkPackageContext.REL_WORK_PACKAGE_TO_DESIGN, PropertyDefinitions.uid1.name(), SqlKeyword.EQ, packageObj.getUid());
        }
        List<IObject> designObjList = Context.Instance.getQueryHelper().query(queryRequest, IObject.class);
        List<String> designObjUIDList = designObjList.stream().map(IObject::getUid).collect(Collectors.toList());

        // 获得包下图纸
        List<String> documentUIDS = new ArrayList<>();
        if (TestPackageContext.CLASS_CIM_CCM_PRESSURE_TEST_PACKAGE.equalsIgnoreCase(classDef)) {
            documentUIDS = packageObj.getEnd1Relationships().getRels(TestPackageContext.REL_PRESSURE_TEST_PACKAGE_TO_DOCUMENT, false).getRelsByRelDef(TestPackageContext.REL_PRESSURE_TEST_PACKAGE_TO_DOCUMENT).stream().map(IRel::getUid2).collect(Collectors.toList());
        } else {
            documentUIDS = packageObj.getEnd1Relationships().getRels(WorkPackageContext.REL_WORK_PACKAGE_TO_DOCUMENT, false).getRelsByRelDef(WorkPackageContext.REL_WORK_PACKAGE_TO_DOCUMENT).stream().map(IRel::getUid2).collect(Collectors.toList());
        }

        // 事务
        ObjectCollection objectCollection = new ObjectCollection(GeneralUtil.getUsername());
        List<IObject> toCheckChildObj = new ArrayList<>();

        // 为包添加未关联的工作步骤
        for (IObject designObj : designObjList) {
            createPackage2WsRel(packageObj, designObj, classDef, objectCollection, toCheckChildObj);
        }

        // 递归存在升版临时步骤工作步骤的设计数据
        List<IObject> reserveDesignObjs = new ArrayList<>();
        for (IObject iObject : toCheckChildObj) {
            getRevisedReserveDesignObjs(iObject, reserveDesignObjs);
        }

        // 筛选出包未关联的设计数据
        List<IObject> notRelDesigns = reserveDesignObjs.stream().filter(x -> !designObjUIDList.contains(x.getUid())).collect(Collectors.toList());

        // 添加设计数据到包中
        List<String> finalDocumentUIDS = documentUIDS;
        for (IObject reserveDesignObj : notRelDesigns) {

            // 获得设计数据关联的图纸
            List<IRel> relDocs = reserveDesignObj.getEnd2Relationships().getRels(REL_DOCUMENT_TO_DESIGN_OBJ, false).getRelsByRelDef(REL_DOCUMENT_TO_DESIGN_OBJ);
            List<IRel> notRelDocs = relDocs.stream().filter(x -> !finalDocumentUIDS.contains(x.getUid1())).collect(Collectors.toList());
            for (IRel notRelDoc : notRelDocs) {
                IObject document = Context.Instance.getQueryHelper().getObjectByUidAndDefinitionUid(notRelDoc.getUid1(), CLASS_CIM_CCM_DOCUMENT_MASTER, IObject.class);
                // 创建包和工作步骤的关联关系
                if (TestPackageContext.CLASS_CIM_CCM_PRESSURE_TEST_PACKAGE.equalsIgnoreCase(classDef)) {
                    IRel relationship = SchemaUtil.newRelationship(TestPackageContext.REL_PRESSURE_TEST_PACKAGE_TO_DOCUMENT, packageObj, document);
                    relationship.finishCreate(objectCollection);
                } else {
                    IRel relationship = SchemaUtil.newRelationship(WorkPackageContext.REL_WORK_PACKAGE_TO_DOCUMENT, packageObj, document);
                    relationship.finishCreate(objectCollection);
                }

                // 已添加图纸
                finalDocumentUIDS.add(notRelDoc.getUid1());
            }

            // 创建包和工作步骤的关联关系
            if (TestPackageContext.CLASS_CIM_CCM_PRESSURE_TEST_PACKAGE.equalsIgnoreCase(classDef)) {
                IRel relationship = SchemaUtil.newRelationship(TestPackageContext.REL_PRESSURE_TEST_PACKAGE_TO_DESIGN_OBJ, packageObj, reserveDesignObj);
                relationship.finishCreate(objectCollection);
            } else {
                IRel relationship = SchemaUtil.newRelationship(WorkPackageContext.REL_WORK_PACKAGE_TO_DESIGN, packageObj, reserveDesignObj);
                relationship.finishCreate(objectCollection);
            }

            // 已添加设计数据
            designObjUIDList.add(reserveDesignObj.getUid());

            // 添加工作步骤
            createPackage2WsRel(packageObj, reserveDesignObj, classDef, objectCollection, null);
        }

        // 工作包特殊处理
        if (WorkPackageContext.CLASS_CIM_CCM_WORK_PACKAGE.equalsIgnoreCase(classDef)) {
            workPackageDocumentRelHandle(designObjUIDList, packageObj, finalDocumentUIDS, objectCollection);
        }

        // 提交事务
        objectCollection.commit();
    }

    /**
     * 工作包特殊处理
     * @param designObjUIDList
     * @param packageObj
     */
    private void workPackageDocumentRelHandle(List<String> designObjUIDList, IObject packageObj, List<String> documentUIDS, ObjectCollection objectCollection) throws Exception {
        // 查询文档下所有的设计数据
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addInterfaceDefForQuery(INTERFACE_CIM_DESIGN_OBJ);
        queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + REL_DOCUMENT_TO_DESIGN_OBJ, PropertyDefinitions.uid1.name(), SqlKeyword.IN, String.join(Splitters.T_COMMA.getMsg(), documentUIDS));
//        queryRequest.addQueryCriteria(null, IMCDocumentContext.REVISION_ITEM_OPERATION_STATE, SqlKeyword.NE, DesignObjOperateStatus.DELETE.getCode());
        List<IObject> designs = Context.Instance.getQueryHelper().query(queryRequest, IObject.class);

        // 获取未关联的设计数据
        List<IObject> notRelDesigns = designs.stream().filter(x -> !designObjUIDList.contains(x.getUid())).collect(Collectors.toList());
//        List<String> notRelDesignUIDs = notRelDesigns.stream().map(IObject::getUid).collect(Collectors.toList());

        // 添加设计数据与工作包的关系
        for (IObject design : notRelDesigns) {
            ICIMRevisionItem revisionItem = design.toInterface(ICIMRevisionItem.class);
            if (revisionItem != null) {
                if (DesignObjOperateStatus.DELETE.getCode().equalsIgnoreCase(revisionItem.getCIMRevisionItemOperationState())) continue;
            }

            // 添加关系
            IRel relationship = SchemaUtil.newRelationship(WorkPackageContext.REL_WORK_PACKAGE_TO_DESIGN, packageObj, design);
            relationship.finishCreate(objectCollection);

            // 添加工作步骤
            createPackage2WsRel(packageObj, design, WorkPackageContext.REL_WORK_PACKAGE_TO_DESIGN, objectCollection, null);
        }

    }

    /**
     * 递归所有子设计数据
     * @param toCheckChildObj
     * @param reserveDesignObjs
     */
    private void getRevisedReserveDesignObjs(IObject toCheckChildObj, List<IObject> reserveDesignObjs) {

        // 查询设计数据对应的子设计数据
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addInterfaceDefForQuery(INTERFACE_CIM_DESIGN_OBJ);
        queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + REL_DESIGN_HIERARCHY, PropertyDefinitions.uid1.name(), SqlKeyword.EQ, toCheckChildObj.getUid());
        List<IObject> childDesigns = Context.Instance.getQueryHelper().query(queryRequest, IObject.class);

        // 递归出口
        if (childDesigns.isEmpty()) return;

        for (IObject design : childDesigns) {
            QueryRequest queryWorkStep = new QueryRequest();
            queryWorkStep.addClassDefForQuery(CLASS_CIM_CCM_WORK_STEP);
            queryWorkStep.addQueryCriteria(RelDirection.From2To1.getPrefix() + REL_DESIGN_OBJ_TO_WORK_STEP, PropertyDefinitions.uid1.name(), SqlKeyword.EQ, design.getUid());
            queryWorkStep.addQueryCriteria(null, PROPERTY_WS_STATUS, SqlKeyword.NE, WSStatusEnum.REVISED_RESERVE.getCode());
            List<ICIMWorkStep> workSteps = Context.Instance.getQueryHelper().query(queryWorkStep, ICIMWorkStep.class);
            for (ICIMWorkStep workStep : workSteps) {
                // 有升版遗留的就建立关联关系
                reserveDesignObjs.add(design);
            }

            // 递归此设计数据对应的子设计数据
            getRevisedReserveDesignObjs(design, reserveDesignObjs);
        }
    }

    /**
     * 创建包和工作步骤关联关系
     * @param packageObj
     * @param designObj
     */
    void createPackage2WsRel(IObject packageObj, IObject designObj, String classDef, ObjectCollection objectCollection, List<IObject> toCheckChildObj) throws Exception {
        if (TaskPackageContext.CLASS_CIM_CCM_TASK_PACKAGE.equalsIgnoreCase(classDef)) return;
        Object packagePurpose = packageObj.getLatestValue(WBSContext.INTERFACE_CIM_CCM_ICIMCCMWBS, WBSContext.PROPERTY_WP_PURPOSE);

        // 获得设计数据下关联的工作步骤
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addClassDefForQuery(CLASS_CIM_CCM_WORK_STEP);
        queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + REL_DESIGN_OBJ_TO_WORK_STEP, PropertyDefinitions.uid1.name(), SqlKeyword.EQ, designObj.getUid());
        queryRequest.addQueryCriteria(null, PROPERTY_WS_WP_PROCESS_PHASE, SqlKeyword.EQ, packagePurpose);
        queryRequest.addQueryCriteria(null, PROPERTY_WS_STATUS, SqlKeyword.NE, WSStatusEnum.REVISED_DELETE.getCode());
        queryRequest.addQueryCriteria(null, PROPERTY_WS_STATUS, SqlKeyword.NE, WSStatusEnum.ROP_DELETE.getCode());
        List<ICIMWorkStep> workSteps = Context.Instance.getQueryHelper().query(queryRequest, ICIMWorkStep.class);

        // 获得包下工作步骤的关联关系
        List<IRel> packageWorkSteps = new ArrayList<>();
        if (TestPackageContext.CLASS_CIM_CCM_PRESSURE_TEST_PACKAGE.equalsIgnoreCase(classDef)) {
            packageWorkSteps = packageObj.getEnd1Relationships().getRels(TestPackageContext.REL_PRESSURE_TEST_PACKAGE_TO_WORK_STEP, false).getRelsByRelDef(TestPackageContext.REL_PRESSURE_TEST_PACKAGE_TO_WORK_STEP);
        }
        if (WorkPackageContext.CLASS_CIM_CCM_WORK_PACKAGE.equalsIgnoreCase(classDef)) {
            packageWorkSteps = packageObj.getEnd1Relationships().getRels(WorkPackageContext.REL_WORK_PACKAGE_TO_WORK_STEP, false).getRelsByRelDef(WorkPackageContext.REL_WORK_PACKAGE_TO_WORK_STEP);
        }
        List<String> relWorkStepUIDs = packageWorkSteps.stream().map(IRel::getUid2).collect(Collectors.toList());

        // 获取未关联的工作步骤
        List<ICIMWorkStep> notRelWorkSteps = workSteps.stream().filter(x -> !relWorkStepUIDs.contains(x.getUid())).collect(Collectors.toList());

        for (ICIMWorkStep workStep : notRelWorkSteps) {
            // 创建包和工作步骤的关联关系
            if (TestPackageContext.CLASS_CIM_CCM_PRESSURE_TEST_PACKAGE.equalsIgnoreCase(classDef)) {
                IRel relationship = SchemaUtil.newRelationship(TestPackageContext.REL_PRESSURE_TEST_PACKAGE_TO_WORK_STEP, packageObj, workStep);
                relationship.finishCreate(objectCollection);
            } else {
                IRel relationship = SchemaUtil.newRelationship(WorkPackageContext.REL_WORK_PACKAGE_TO_WORK_STEP, packageObj, workStep);
                relationship.finishCreate(objectCollection);
            }

            if (null == toCheckChildObj) {
                throw new Exception("检查子设计对象失败,列表不可为空对象!");
            }

        }

        // 存在升版临时步骤的需要进一步处理
        if (toCheckChildObj == null) return;
        long count = workSteps.stream().filter(x -> {
            try {
                return WSStatusEnum.REVISED_TEMP_PROCESS.getCode().equalsIgnoreCase(x.getWSStatus());
            } catch (Exception e) {
                return false;
            }
        }).count();
        if (count > 0) {
            toCheckChildObj.add(designObj);
        }
    }

    /**
     * 批量删除包下的工作步骤
     * @param packageObj
     */
    private void batchDeleteWorkSteps(IObject packageObj, String classDef) throws Exception {
        String relWsDefUid = getRelPackageWorkStepUidByPackageClassDefUid(classDef);
        ObjectCollection objectCollection = new ObjectCollection(GeneralUtil.getUsername());
        // 获得包与工作步骤的关联关系
        List<IRel> rels = packageObj.getEnd1Relationships().getRels(relWsDefUid, false).getRelsByRelDef(relWsDefUid);
        for (IRel rel : rels) {
            ICIMWorkStep workStep = Context.Instance.getQueryHelper().getObjectByUidAndDefinitionUid(rel.getUid2(), CLASS_CIM_CCM_WORK_STEP, ICIMWorkStep.class);
            if (workStep == null) continue;
            if (!workStep.isDeleteStatus()) continue;
            setObjectRevised(packageObj);
            if (!workStep.hasActualCompletedDate()) {
                workStep.Delete(objectCollection);
            }
        }
        objectCollection.commit();
    }

    /**
     * 设置对象为当前
     * @param object
     * @throws Exception
     */
    private void setObjectCurrent(IObject object) throws Exception {
        ObjectCollection objectCollection = new ObjectCollection();
        ICIMRevisionItem icimRevisionItem = object.toInterface(ICIMRevisionItem.class);
        icimRevisionItem.BeginUpdate(objectCollection);
        icimRevisionItem.setCIMRevisionItemRevState(CIMRevisionStatus.CURRENT.getCode());
        icimRevisionItem.FinishUpdate(objectCollection);
        objectCollection.commit();
    }

    /**
     * 设置对象为升版状态
     *
     * @param object
     * @throws Exception
     */
    public void setObjectRevised(IObject object) throws Exception {
        ObjectCollection objectCollection = new ObjectCollection();
        ICIMRevisionItem icimRevisionItem = object.toInterface(ICIMRevisionItem.class);
        icimRevisionItem.BeginUpdate(objectCollection);
        icimRevisionItem.setCIMRevisionItemRevState(CIMRevisionStatus.REVISED.getCode());
        icimRevisionItem.FinishUpdate(objectCollection);
        objectCollection.commit();
    }

    /**
     *
     * @param classDefUid
     * @return
     */
    private String getRelPackageWorkStepUidByPackageClassDefUid(String classDefUid) {
        switch (classDefUid) {
            case TaskPackageContext.CLASS_CIM_CCM_TASK_PACKAGE:
                return null;
            case TestPackageContext.CLASS_CIM_CCM_PRESSURE_TEST_PACKAGE:
                return TestPackageContext.REL_PRESSURE_TEST_PACKAGE_TO_WORK_STEP;
            case WorkPackageContext.CLASS_CIM_CCM_WORK_PACKAGE:
                return WorkPackageContext.REL_WORK_PACKAGE_TO_WORK_STEP;
            default:
                return null;
        }
    }

    /**
     * 创建设计文档版本
     * @param document
     * @return
     * @throws Exception
     */
    private IObject createDocRevisionObj(IObject document, List<LoadClassDefStruct> designs, ObjectCollection objectCollection) throws Exception {
        ICIMRevisionItem revisionItem = document.toInterface(ICIMRevisionItem.class);
        if (revisionItem == null) return null;
        IObject object = SchemaUtil.newIObject(IMCDocumentContext.CLASS_CIM_DESIGN_DOC_REVISION,document.getName(), document.getDescription(), document.getDisplayName());
        //设置必要属性
        ICIMRevisionCollections revisionCollections = object.toInterface(ICIMRevisionCollections.class);
        revisionCollections.setDesignObjUIDs(designs);
        object.setValue(IMCDocumentContext.INTERFACE_CIM_DOCUMENT_REVISION, IMCDocumentContext.MAJOR_REVISION, revisionItem.getCIMRevisionItemMajorRevision(), null);
        object.setValue(IMCDocumentContext.INTERFACE_CIM_DOCUMENT_REVISION, IMCDocumentContext.MINOR_REVISION, revisionItem.getCIMRevisionItemMinorRevision(), null);
        object.setValue(IMCDocumentContext.INTERFACE_CIM_DOCUMENT_REVISION, IMCDocumentContext.EXTERNAL_REVISION, revisionItem.getCIMRevisionItemMajorRevision() + revisionItem.getCIMRevisionItemMinorRevision(), null);
        object.setValue(IMCDocumentContext.INTERFACE_CIM_DOCUMENT_REVISION, IMCDocumentContext.REV_STATE, RevStatus.ELT_RSCurrent, null);
        object.setValue(IMCDocumentContext.INTERFACE_CIM_DOCUMENT_REVISION, IMCDocumentContext.REV_ISSUE_DATE, new Date(), null);
        object.setValue(IMCDocumentContext.INTERFACE_CIM_DOCUMENT_REVISION, IMCDocumentContext.REV_ISSUE_DATE, new Date(), null);
        object.finishCreate(objectCollection);
        return object;
    }

    /**
     * 创建设计文档修订
     * @param document
     * @return
     */
    private IObject createDocVersionObj(IObject document, ObjectCollection objectCollection) throws Exception {
        IObject object = SchemaUtil.newIObject(IMCDocumentContext.CLASS_CIM_DOCUMENT_VERSION,document.getName(), document.getDescription(), document.getDisplayName());
        object.setValue(IMCDocumentContext.INTERFACE_CIM_DOCUMENT_VERSION, IMCDocumentContext.IS_DOC_VERSION_SUPERSEDED, false, null);
        object.setValue(IMCDocumentContext.INTERFACE_CIM_DOCUMENT_VERSION, IMCDocumentContext.VERSION_CHECK_IN_DATE, new Date(), null);
        object.finishCreate(objectCollection);
        return object;
    }
}
