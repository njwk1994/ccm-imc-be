package com.ccm.packagemanage.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson2.JSONObject;
import com.ccm.modules.packagemanage.TestPackageContext;
import com.ccm.modules.packagemanage.WBSContext;
import com.ccm.modules.packagemanage.WorkPackageContext;
import com.ccm.packagemanage.domain.*;
import com.ccm.modules.packagemanage.enums.DesignPhaseEnum;
import com.ccm.modules.packagemanage.enums.DocStateEnum;
import com.ccm.modules.packagemanage.enums.PackageType;
import com.ccm.packagemanage.helpers.IPackageQueryHelper;
import com.ccm.packagemanage.service.IPressureTestPackageService;
import com.imc.common.core.enums.frame.PropertyDefinitions;
import com.imc.common.core.enums.frame.RelDirection;
import com.imc.common.core.enums.frame.Splitters;
import com.imc.common.core.exception.ServiceException;
import com.imc.common.core.model.parameters.FilterParam;
import com.imc.common.core.model.parameters.OrderParam;
import com.imc.common.core.model.parameters.UserEnvParameter;
import com.imc.common.core.web.table.TableData;
import com.imc.framework.collections.IRelCollection;
import com.imc.framework.collections.impl.ObjectCollection;
import com.imc.framework.context.Context;
import com.imc.framework.entity.schema.Rel;
import com.imc.framework.utils.GeneralUtil;
import com.imc.framework.utils.SchemaUtil;
import com.imc.schema.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.ccm.modules.COMContext.*;

@Service
public class PressureTestPackageServiceImpl implements IPressureTestPackageService {

    @Autowired
    IPackageQueryHelper packageQueryHelper;

    @Override
    public TableData<JSONObject> selectableDocumentsForPressureTestPackage(QueryByPackageParamDTO documentsQueryByPackageParam) throws Exception {
        // 详设过滤
        if (documentsQueryByPackageParam.getFilterParams() != null) {
            List<FilterParam> detailDesignParams = documentsQueryByPackageParam.getFilterParams().stream().filter(x -> WorkPackageContext.PROPERTY_DOC_DESIGN_PHASE.equals(x.getPropertyDefUid())
                    && DesignPhaseEnum.EN_DETAIL_DESIGN.getCode().equals(x.getValue())).collect(Collectors.toList());

            List<FilterParam> ShopDesignParams = documentsQueryByPackageParam.getFilterParams().stream().filter(x -> WorkPackageContext.PROPERTY_DOC_DESIGN_PHASE.equals(x.getPropertyDefUid())
                    && DesignPhaseEnum.EN_SHOP_DESIGN.getCode().equals(x.getValue())).collect(Collectors.toList());
            if (!detailDesignParams.isEmpty() && ShopDesignParams.isEmpty()) {
                return new TableData<>();
            }
        } else {
            documentsQueryByPackageParam.setFilterParams(new ArrayList<>());
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
        return GeneralUtil.generateTableData(documentsQueryByPackageParam, GeneralUtil.getUsername());
    }

    @Override
    public TableData<JSONObject> selectableComponentsForPressureTestPackage(DesignsQueryByDocumentAndPackageParamDTODTO designsQueryByDocumentAndPackageParamDTO) throws Exception {
        // 获得试压包
        ICIMCCMPressureTestPackage pressureTestPackage = packageQueryHelper.getPressureTestPackageByUid(designsQueryByDocumentAndPackageParamDTO.getUid());
        if (pressureTestPackage == null) {
            throw new Exception("未能获取到试压包:" + pressureTestPackage);
        }

        // 获取试压包可以关联的设计数据
        List<IRel> designs = pressureTestPackage.getEnd1Relationships().getRels(TestPackageContext.REL_PRESSURE_TEST_PACKAGE_TO_DESIGN_OBJ, false).getRelsByRelDef(TestPackageContext.REL_PRESSURE_TEST_PACKAGE_TO_DESIGN_OBJ);
        List<String> designUIDS = designs.stream().map(IRel::getUid2).collect(Collectors.toList());
        List<ICIMCCMDesignObj> designOBJs = packageQueryHelper.getDesignsByDocumentUIDAndNotRelPressureTestPackageUID(designsQueryByDocumentAndPackageParamDTO.getDocumentOBID(), designsQueryByDocumentAndPackageParamDTO.getUid(), designUIDS);

        // 打包条件
        List<String> uids = designOBJs.stream().map(IObject::getUid).collect(Collectors.toList());
        FilterParam filterParam = new FilterParam();
        filterParam.setPropertyDefUid(PropertyDefinitions.uid.name());
        filterParam.setOperator("IN");
        filterParam.setValue(String.join(Splitters.T_COMMA.getMsg(), uids));
        designsQueryByDocumentAndPackageParamDTO.getFilterParams().add(filterParam);
        return GeneralUtil.generateTableData(designsQueryByDocumentAndPackageParamDTO, GeneralUtil.getUsername());
    }

    @Override
    public TableData<JSONObject> getPTPMaterialTemplatesByPTPackage(QueryByPackageParamDTO query) throws Exception {
        if (StringUtils.isEmpty(query.getUid())) {
            throw new ServiceException("请填写试压包UID");
        }
        ICIMCCMPressureTestPackage pressureTestPackage = packageQueryHelper.getPressureTestPackageByUid(query.getUid());
        if (pressureTestPackage == null) {
            throw new ServiceException("未找到试压包对象,uid:" + query.getUid());
        }

        // 试压包设计数据
        List<ICIMCCMDesignObj> designOBJs = pressureTestPackage.getDesignOBJs();

        // 试压包材料模板
        List<ICIMCCMPTPackageMaterialTemplate> ptpMaterialTemplates = pressureTestPackage.getPTPackageMaterials();

        // 根据试压包关联的设计数据获得对应的材料规格
        List<ICIMCCMPTPackageMaterialSpecification> lcolPTPMS = this.getPTPMaterialSpecificationsByDesignObjs(designOBJs);

        // 根据试压包关联的材料模板和对应的材料规格获取可供添加的材料模板
        return getMaterialTemplateByPTPMS(query, lcolPTPMS, ptpMaterialTemplates);
    }

    @Override
    public void createPTPackageMaterials(PTPackageMaterialsParamDTO ptPackageMaterialsParamDTO) throws Exception {
        if (StringUtils.isEmpty(ptPackageMaterialsParamDTO.getUid())) {
            throw new ServiceException("请填写试压包UID");
        }

        if (ptPackageMaterialsParamDTO.getMaterialTemplateUIDs() == null || ptPackageMaterialsParamDTO.getMaterialTemplateUIDs().isEmpty()) {
            throw new ServiceException("请填写需要关联的材料UID集合");
        }

        if (ptPackageMaterialsParamDTO.getCounts() == null || ptPackageMaterialsParamDTO.getCounts().isEmpty()) {
            throw new ServiceException("请填写需要关联的材料数量集合");
        }

        // 获取试压包
        ICIMCCMPressureTestPackage pressureTestPackage = packageQueryHelper.getPressureTestPackageByUid(ptPackageMaterialsParamDTO.getUid());
        if (pressureTestPackage == null) {
            throw new ServiceException("获取试压包失败");
        }

        // 获取试压包已经关联的材料
        List<ICIMCCMPTPackageMaterialTemplate> ptpRelMaterialTemplates = pressureTestPackage.getPTPackageMaterials();

        // 根据材料UID集合获取材料模板
        int index = 0;
        ObjectCollection objectCollection = new ObjectCollection(GeneralUtil.getUsername());
        List<IObject> materialTemplates = packageQueryHelper.getPTPackageMaterialTemplatesByUIDs(ptPackageMaterialsParamDTO.getMaterialTemplateUIDs());
        for (String uid : ptPackageMaterialsParamDTO.getMaterialTemplateUIDs()) {
            Optional<IObject> materialTemplate = materialTemplates.stream().filter(x-> x.getUid().equals(uid)).findFirst();
            if (!materialTemplate.isPresent()) {
                throw new ServiceException("未找到uid:" + uid + "的材料模板对象");
            }
            IObject packageMaterialTemplate = this.getSameMaterialFromContainerByMaterialTemplate(ptpRelMaterialTemplates, materialTemplate.get().toInterface(ICIMCCMPTPackageMaterialTemplate.class));
            if (packageMaterialTemplate != null) {
                // 标记更新开始
                packageMaterialTemplate.BeginUpdate(objectCollection);
                packageMaterialTemplate.setValue(TestPackageContext.INTERFACE_CIM_CCM_PRESSURE_TEST_PACKAGE_MATERIAL, TestPackageContext.PROPERTY_PTP_MATERIAL_LENGTH, ptPackageMaterialsParamDTO.getCounts().get(index), null);
                // 标记更新结束
                packageMaterialTemplate.FinishUpdate(objectCollection);
            } else {
                packageMaterialTemplate = SchemaUtil.newIObject(TestPackageContext.CLASS_CIM_CCM_PRESSURE_TEST_PACKAGE_MATERIAL, materialTemplate.get().getName(), materialTemplate.get().getDescription(), materialTemplate.get().getDisplayName());
                if (packageMaterialTemplate == null) throw new ServiceException("创建试压包材料对象失败!");
                ICIMCCMPTPackageMaterialTemplate icimccmptPackageMaterialTemplate = materialTemplate.get().toInterface(ICIMCCMPTPackageMaterialTemplate.class);
                packageMaterialTemplate.setValue(TestPackageContext.INTERFACE_CIM_CCM_PRESSURE_TEST_PACKAGE_MATERIAL_TEMPLATE, TestPackageContext.PROPERTY_PTP_DESIGN_TOOLS_CLASS_TYPE, icimccmptPackageMaterialTemplate.getCCMPTPMDesignToolsClassType(), null);
                packageMaterialTemplate.setValue(TestPackageContext.INTERFACE_CIM_CCM_PRESSURE_TEST_PACKAGE_MATERIAL_TEMPLATE, TestPackageContext.PROPERTY_PTP_SIZE_1, icimccmptPackageMaterialTemplate.getCCMPTPMPSize1(), null);
                packageMaterialTemplate.setValue(TestPackageContext.INTERFACE_CIM_CCM_PRESSURE_TEST_PACKAGE_MATERIAL_TEMPLATE, TestPackageContext.PROPERTY_PTP_SIZE_2, icimccmptPackageMaterialTemplate.getCCMPTPMPSize2(), null);
                packageMaterialTemplate.setValue(TestPackageContext.INTERFACE_CIM_CCM_PRESSURE_TEST_PACKAGE_MATERIAL_TEMPLATE, TestPackageContext.PROPERTY_PTP_MATERIAL_CODE, icimccmptPackageMaterialTemplate.getCCMPTPMMaterialCode(), null);
                packageMaterialTemplate.setValue(TestPackageContext.INTERFACE_CIM_CCM_PRESSURE_TEST_PACKAGE_MATERIAL_TEMPLATE, TestPackageContext.PROPERTY_PTP_DESCRIPTION, icimccmptPackageMaterialTemplate.getCCMPTPMDescription(), null);
                packageMaterialTemplate.setValue(TestPackageContext.INTERFACE_CIM_CCM_PRESSURE_TEST_PACKAGE_MATERIAL_TEMPLATE, TestPackageContext.PROPERTY_PTP_BELONGS_MS, icimccmptPackageMaterialTemplate.getCCMPTPMBelongsMS(), null);
                packageMaterialTemplate.setValue(TestPackageContext.INTERFACE_CIM_CCM_PRESSURE_TEST_PACKAGE_MATERIAL, TestPackageContext.PROPERTY_PTP_MATERIAL_LENGTH, ptPackageMaterialsParamDTO.getCounts().get(index), null);
                packageMaterialTemplate.finishCreate(objectCollection);

                // 创建试压包-材料关联关系
                IRel relationship = SchemaUtil.newRelationship(TestPackageContext.REL_PRESSURE_TEST_PACKAGE_TO_MATERIAL, pressureTestPackage, packageMaterialTemplate);
                relationship.finishCreate(objectCollection);
            }
            index++;
        }
        objectCollection.commit();
    }

    @Override
    public void deletePTPMaterials(PTPackageMaterialsParamDTO ptPackageMaterialsParamDTO) throws Exception {
        if (ptPackageMaterialsParamDTO.getMaterialTemplateUIDs() == null || ptPackageMaterialsParamDTO.getMaterialTemplateUIDs().isEmpty()) {
            throw new ServiceException("请填写需要关联的材料UID集合");
        }

        ObjectCollection objectCollection = new ObjectCollection(GeneralUtil.getUsername());
        for (String materialUID: ptPackageMaterialsParamDTO.getMaterialTemplateUIDs()) {
            // 获得材料对象
            ICIMCCMPTPackageMaterial material = packageQueryHelper.getPTPackageMaterialByUID(materialUID);
            if (material == null) {
                throw new ServiceException("未找到对应的材料：" + materialUID);
            }

            // 获得材料对象与试压包关联关系
            List<IRel> materialRels = material.getEnd2Relationships().getRels(TestPackageContext.REL_PRESSURE_TEST_PACKAGE_TO_MATERIAL, false).getRelsByRelDef(TestPackageContext.REL_PRESSURE_TEST_PACKAGE_TO_MATERIAL);

            // 删除
            for (IRel rel : materialRels) {
                rel.Delete(objectCollection);
            }
            material.Delete(objectCollection);
        }

        objectCollection.commit();
    }

    private TableData<JSONObject> getMaterialTemplateByPTPMS(QueryByPackageParamDTO query, List<ICIMCCMPTPackageMaterialSpecification> lcolPTPMS, List<ICIMCCMPTPackageMaterialTemplate> ptpMaterialTemplates) throws Exception {
        if (lcolPTPMS.isEmpty()) return new TableData<>();
        List<IObject> lcolItems = new ArrayList<>();

        // 遍历材料规格
        for (ICIMCCMPTPackageMaterialSpecification materialSpecification: lcolPTPMS) {
            List<ICIMCCMPTPackageMaterialTemplate> materialTemplates = materialSpecification.getPTPMaterialTemplates();
            if (!materialTemplates.isEmpty()) {
                for (ICIMCCMPTPackageMaterialTemplate packageMaterialTemplate : materialTemplates) {
                    IObject sameMaterial = this.getSameMaterialFromContainerByMaterialTemplate(ptpMaterialTemplates, packageMaterialTemplate);
                    if (sameMaterial != null) {
                        ICIMCCMPTPackageMaterial packageMaterial = sameMaterial.toInterface(ICIMCCMPTPackageMaterial.class);
//                        packageMaterial.setCCMPTPMaterialLength();
//                        packageMaterialTemplate.addItemIfNotExist("count", packageMaterial.getCCMPTPMaterialLength());
                    }
                    lcolItems.add(packageMaterialTemplate);
                }
            }
        }
        return filterMaterialTemplate(query, lcolItems);
    }

    private TableData<JSONObject> filterMaterialTemplate(QueryByPackageParamDTO query, List<IObject> documents) throws Exception {
        if (documents == null || documents.isEmpty()) {
            return GeneralUtil.generateTableData(null, GeneralUtil.getUsername(), CLASS_CIM_CCM_DOCUMENT_MASTER);
        }

        // 筛选
        Stream<IObject> stream = documents.stream();
        if (query.getFilterParams() != null && !query.getFilterParams().isEmpty()) {
            for (FilterParam filterParam : query.getFilterParams()) {
                stream = stream.filter(doc -> doc.getLatestValue(TestPackageContext.INTERFACE_CIM_CCM_PRESSURE_TEST_PACKAGE_MATERIAL_TEMPLATE, filterParam.getPropertyDefUid()).equals(filterParam.getValue()));
            }
        }

        // 排序
        if (query.getOrderParams() != null && !query.getOrderParams().isEmpty()) {
            for (OrderParam orderParam : query.getOrderParams()) {
                stream = stream.sorted((s1, s2) -> s2.getLatestValue(TestPackageContext.INTERFACE_CIM_CCM_PRESSURE_TEST_PACKAGE_MATERIAL_TEMPLATE, orderParam.getField()).toString().compareTo(s1.getLatestValue(TestPackageContext.INTERFACE_CIM_CCM_PRESSURE_TEST_PACKAGE_MATERIAL_TEMPLATE, orderParam.getField()).toString()));
            }
        }

        // 分页
        List<IObject> objects = stream.skip((query.getCurrent() - 1) * query.getPageSize()).limit(query.getPageSize()).collect(Collectors.toList());
        return GeneralUtil.generateTableData(objects, GeneralUtil.getUsername(), CLASS_CIM_CCM_DOCUMENT_MASTER);
    }

    private IObject getSameMaterialFromContainerByMaterialTemplate(List<ICIMCCMPTPackageMaterialTemplate> pcolContainer, ICIMCCMPTPackageMaterialTemplate materialTemplate) throws Exception {
        if (pcolContainer.isEmpty() && materialTemplate == null) return null;
        for (ICIMCCMPTPackageMaterialTemplate template : pcolContainer) {
            if (template.sameAsOtherTemplate(materialTemplate)) {
                return template;
            }
        }
        return null;
    }

    private List<ICIMCCMPTPackageMaterialSpecification> getPTPMaterialSpecificationsByDesignObjs(List<ICIMCCMDesignObj> designObjs) throws Exception {
        //根据规格获取规格条目信息的计算属性定义
        List<String> lcolPTPMSItemCalculatePropDefs = this.getPTPMaterialSpecificationCalculateProps();
        //根据获取的过滤属性定义,生成由设计属性生成的过滤条件
        List<String> lcolFilterPropValues = this.generateFilterPropValues(lcolPTPMSItemCalculatePropDefs, designObjs);
        //判断符合条件的规格对象 过滤条件的值就是材料规格的名称
        List<ICIMCCMPTPackageMaterialSpecification> lcolAllPTPMS = packageQueryHelper.getMaterialSpecifications();
        List<ICIMCCMPTPackageMaterialSpecification> lcolContainer = new ArrayList<>();
        if (!lcolAllPTPMS.isEmpty()) {
            for (ICIMCCMPTPackageMaterialSpecification lobjPTPMS : lcolAllPTPMS) {
                if (lcolFilterPropValues.contains(lobjPTPMS.getName())) {
                    lcolContainer.add(lobjPTPMS);
                }
            }
        }
        return lcolContainer;
    }


    private List<String> generateFilterPropValues(@NotNull List<String> pcolPTPMSItemCalculatePropDefs, List<ICIMCCMDesignObj> pcolDesignObjs) {
        List<String> lcolResult = new ArrayList<>();
        for (IObject lobjDesignObj : pcolDesignObjs) {
            String lstrFilter = this.generateFilterForDesign(lobjDesignObj, pcolPTPMSItemCalculatePropDefs);
            lcolResult.add(lstrFilter);
        }

        return lcolResult.stream().distinct().collect(Collectors.toList());
    }

    private String generateFilterForDesign(@NotNull IObject pobjDesignObj, @NotNull List<String> pcolPTPMSItemCalculatePropDefs) {
        JSONObject jsonObject = new JSONObject();
        for (String pstrPropDef : pcolPTPMSItemCalculatePropDefs) {
            Object propertyValue = pobjDesignObj.getMyLatestValue(pstrPropDef);
            jsonObject.put(pstrPropDef, propertyValue != null ? propertyValue.toString() : "");
        }
        return jsonObject.toJSONString();
    }

    private List<String> getPTPMaterialSpecificationCalculateProps() throws Exception {
        List<String> lcolResult = new ArrayList<>();
        IEnumListType enumListType = Context.Instance.getCacheHelper().getSchema("ELT_CCMPTPMSCategory", IEnumListType.class);
        if (enumListType == null)
            throw new Exception("未在系统找到UID:ELT_CCMPTPMSCategory的EnumListType对象");
        List<IObject> entries = enumListType.getEntries();
        if (entries == null || entries.isEmpty()) {
            throw new Exception("未找到有效的Enum信息!");
        }
        for (IObject enumItem : entries) {
            lcolResult.addAll(Arrays.asList(enumItem.getName().split(",")));
        }
        return lcolResult.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public void assignDocumentsAndDesignsToPressureTestPackage(TestRelDocsAndDesignsParamDTO testRelDocsAndDesignsParamDTO) throws Exception {
        // 获得试压包
        ICIMCCMPressureTestPackage pressureTestPackage = packageQueryHelper.getPressureTestPackageByUid(testRelDocsAndDesignsParamDTO.getUid());
        if (pressureTestPackage == null) {
            throw new ServiceException("未能获取到试压包:" + pressureTestPackage);
        }
        String pressureTestPackagePurpose = String.valueOf(pressureTestPackage.getLatestValue(WBSContext.INTERFACE_CIM_CCM_ICIMCCMWBS, WBSContext.PROPERTY_PURPOSE));

        // 获得试压包下关联的图纸
        IRelCollection ppDocumentsRelCollection = pressureTestPackage.getEnd2Relationships().getRels(TestPackageContext.REL_PRESSURE_TEST_PACKAGE_TO_DOCUMENT, false);
        List<String> documentUIDS = ppDocumentsRelCollection.getIncludeAllRels().stream().map(IRel::getUid2).collect(Collectors.toList());

        // 事务
        ObjectCollection objectCollection = new ObjectCollection(GeneralUtil.getUsername());

        // 关联图纸
        if (!documentUIDS.contains(testRelDocsAndDesignsParamDTO.getDocumentUID())) {
            IObject docObj = packageQueryHelper.getDocumentByUID(testRelDocsAndDesignsParamDTO.getDocumentUID());
            IRel relationship = SchemaUtil.newRelationship(TestPackageContext.REL_PRESSURE_TEST_PACKAGE_TO_DOCUMENT, pressureTestPackage, docObj);
            relationship.finishCreate(objectCollection);
        }

        // 关联选中的设计数据
        for (String designObjUID : testRelDocsAndDesignsParamDTO.getDesignUIDS()) {
            ICIMCCMDesignObj designObj = packageQueryHelper.getDesignByUid(designObjUID);
            IRel designRel = SchemaUtil.newRelationship(TestPackageContext.REL_PRESSURE_TEST_PACKAGE_TO_DESIGN_OBJ, pressureTestPackage, designObj);
            designRel.finishCreate(objectCollection);

            // 关联选中设计数据下可以关联的工作步骤
            List<ICIMWorkStep> workSteps = packageQueryHelper.getWorkStepsByDesignUidAndRopWorkStepPhaseAndConsumeMaterial(designObj.getUid(), PackageType.WP, pressureTestPackagePurpose, false);
            for (ICIMWorkStep workStep : workSteps) {
                IRel workStepRel = SchemaUtil.newRelationship(TestPackageContext.REL_PRESSURE_TEST_PACKAGE_TO_WORK_STEP, pressureTestPackage, workStep);
                workStepRel.finishCreate(objectCollection);
            }
        }

        objectCollection.commit();
    }

    @Override
    public void removeDocumentsFromPressureTestPackage(PackageRelDocumentsParamDTO packageRelDocumentsParamDTO) throws Exception {
        if (StringUtils.isEmpty(packageRelDocumentsParamDTO.getUid())) {
            throw new ServiceException("请输入任务包uid");
        }
        if (packageRelDocumentsParamDTO.getDocIds() != null && packageRelDocumentsParamDTO.getDocIds().isEmpty()) {
            throw new ServiceException("请输入关联图纸的uid");
        }

        // 获得试压包
        ICIMCCMPressureTestPackage pressureTestPackage = packageQueryHelper.getPressureTestPackageByUid(packageRelDocumentsParamDTO.getUid());
        if (pressureTestPackage == null) {
            throw new ServiceException(String.format("获取试压包失败, uid:%s", packageRelDocumentsParamDTO.getUid()));
        }

        // 获得任务包下图纸关系
        List<IRel> rels = pressureTestPackage.getRels(RelDirection.From1To2, TestPackageContext.REL_PRESSURE_TEST_PACKAGE_TO_DOCUMENT,false).getRelsByRelDef(TestPackageContext.REL_PRESSURE_TEST_PACKAGE_TO_DOCUMENT);

        // 创建事务
        ObjectCollection objectCollection = new ObjectCollection(GeneralUtil.getUsername());

        // 删除关系,包括图纸和设计数据
        for (IRel rel : rels) {
            if (packageRelDocumentsParamDTO.getDocIds().contains(rel.getUid2())) {
                rel.Delete(objectCollection);
                List<ICIMCCMDesignObj> designOBJs = packageQueryHelper.getDesignsByPressureTestPackageUIDAndDocumentUID(pressureTestPackage.getUid(), rel.getUid2());
                for (ICIMCCMDesignObj designObj : designOBJs) {
                    List<IRel> designRels = designObj.getRels(RelDirection.From2To1,TestPackageContext.REL_PRESSURE_TEST_PACKAGE_TO_DESIGN_OBJ, false).getRelsByRelDef(TestPackageContext.REL_PRESSURE_TEST_PACKAGE_TO_DESIGN_OBJ);
                    for (IRel designRel : designRels) {
                        if (designRel.getUid1().equals(pressureTestPackage.getUid())) {
                            designRel.Delete(objectCollection);
                        }
                    }
                    List<ICIMWorkStep> workSteps = packageQueryHelper.getWorkStepsByDesignUIDAndPressureTestPackage(pressureTestPackage.getUid(), designObj.getUid());
                    for (ICIMWorkStep workStep : workSteps) {
                        List<IRel> workStepRels = workStep.getEnd1Relationships().getRels(TestPackageContext.REL_PRESSURE_TEST_PACKAGE_TO_WORK_STEP, false).getRelsByRelDef(TestPackageContext.REL_PRESSURE_TEST_PACKAGE_TO_WORK_STEP);
                        for (IRel workStepRel : workStepRels) {
                            if (workStepRel.getUid1().equals(designObj.getUid())) {
                                workStepRel.Delete(objectCollection);
                            }
                        }
                    }
                }
            }
        }

        // 提交事务
        objectCollection.commit();
    }

    @Override
    public void removeComponentsFromPackage(TestRelDocsAndDesignsParamDTO testRelDocsAndDesignsParamDTO) throws Exception {
        ICIMCCMPressureTestPackage pressureTestPackage = packageQueryHelper.getPressureTestPackageByUid(testRelDocsAndDesignsParamDTO.getUid());
        if (pressureTestPackage == null) {
            throw new ServiceException(String.format("获取试压包失败, uid:%s", testRelDocsAndDesignsParamDTO.getUid()));
        }
        if (testRelDocsAndDesignsParamDTO.getDesignUIDS().isEmpty()) {
            throw new ServiceException(String.format("请输入要删除的设计数据！"));
        }

        // 获取试压包图纸
        List<ICIMDocumentMaster> documentMasters = pressureTestPackage.getDocuments();

        // 获取试压包设计数据
        List<ICIMCCMDesignObj> designObjs = pressureTestPackage.getDesignOBJs();
        if (designObjs.isEmpty()) return;
        List<String> designInPtp = designObjs.stream().map(IObject::getUid).collect(Collectors.toList());

        // 获取试压包工作步骤
        List<ICIMWorkStep> workSteps = pressureTestPackage.getWorkSteps();

        // 创建事务
        ObjectCollection objectCollection = new ObjectCollection(GeneralUtil.getUsername());

        // 遍历要删除的设计数据
        for (String designUID : testRelDocsAndDesignsParamDTO.getDesignUIDS()) {

            // 获得设计数据
            ICIMCCMDesignObj designObj = packageQueryHelper.getDesignByUid(designUID);

            // 根据设计数据获得设计数据下的工作步骤
            List<IRel> workStepRels = designObj.getEnd1Relationships().getRels(REL_DESIGN_OBJ_TO_WORK_STEP, false).getRelsByRelDef(REL_DESIGN_OBJ_TO_WORK_STEP);
            List<String> workStepIds = workStepRels.stream().map(x -> x.getUid2()).collect(Collectors.toList());
            List<ICIMWorkStep> needDeleteWorkStep = workSteps.stream().filter(x -> workStepIds.contains(x.getUid())).collect(Collectors.toList());
            for (ICIMWorkStep workStep : needDeleteWorkStep) {
                IRel rel = workStep.getEnd2Relationships().getRels(TestPackageContext.REL_PRESSURE_TEST_PACKAGE_TO_WORK_STEP, false).getRel(TestPackageContext.REL_PRESSURE_TEST_PACKAGE_TO_WORK_STEP, false);
                rel.Delete(objectCollection);
            }

            // 获得设计数据的关系
            IRel rel = designObj.getEnd2Relationships().getRel(TestPackageContext.REL_PRESSURE_TEST_PACKAGE_TO_DESIGN_OBJ, false);
            rel.Delete(objectCollection);

            // 获得设计数据图纸
            IRel docRel = designObj.getEnd2Relationships().getRel(REL_DOCUMENT_TO_DESIGN_OBJ, false);
            Optional<ICIMDocumentMaster> documentMaster = documentMasters.stream().filter(x -> x.getUid().equals(docRel.getUid1())).findFirst();
            if (!documentMaster.isPresent()) continue;

            // 判断试压包绑定的图纸下是否还有设计数据
            List<IRel> docDesingRel = documentMaster.get().getEnd1Relationships().getRels(REL_DOCUMENT_TO_DESIGN_OBJ, false).getRelsByRelDef(REL_DOCUMENT_TO_DESIGN_OBJ);
            List<String> designInDoc = docDesingRel.stream().map(x -> x.getUid2()).collect(Collectors.toList());
            designInPtp.remove(designObj.getUid());
            List<String> collect = designInPtp.stream().filter(designInDoc::contains).collect(Collectors.toList());

            // 当不存在图纸中的设计数据时 删除图纸关联关系
            if (collect.isEmpty()) {
                for (ICIMDocumentMaster document : documentMasters) {
                    IRel docPTPRel = document.getEnd2Relationships().getRels(TestPackageContext.REL_PRESSURE_TEST_PACKAGE_TO_DOCUMENT, false).getRel(TestPackageContext.REL_PRESSURE_TEST_PACKAGE_TO_DOCUMENT, false);
                    if (docPTPRel.getUid2().equals(docRel.getUid())) {
                        docPTPRel.Delete(objectCollection);
                    }
                }
            }
        }
        objectCollection.commit();
    }
}
