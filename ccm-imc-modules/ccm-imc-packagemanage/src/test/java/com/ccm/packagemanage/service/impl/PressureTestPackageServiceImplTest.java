//package com.ccm.packagemanage.service.impl;
//
//import com.alibaba.fastjson2.JSONObject;
//import com.ccm.modules.packagemanage.*;
//import com.ccm.packagemanage.domain.DesignsQueryByDocumentAndPackageParamDTODTO;
//import com.ccm.packagemanage.domain.PackageRelDocumentsParamDTO;
//import com.ccm.packagemanage.domain.QueryByPackageParamDTO;
//import com.ccm.packagemanage.domain.TestRelDocsAndDesignsParamDTO;
//import com.ccm.packagemanage.service.IPressureTestPackageService;
//import com.imc.common.core.web.table.TableData;
//import com.imc.framework.collections.impl.ObjectCollection;
//import com.imc.framework.context.Context;
//import com.imc.framework.utils.SchemaUtil;
//import com.imc.schema.interfaces.IEnumListType;
//import com.imc.schema.interfaces.IObject;
//import com.imc.schema.interfaces.IRel;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static com.ccm.modules.COMContext.CLASS_CIM_CCM_DOCUMENT_MASTER;
//import static com.ccm.modules.COMContext.REL_DOCUMENT_TO_DESIGN_OBJ;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class PressureTestPackageServiceImplTest {
//
//    @Autowired
//    IPressureTestPackageService pressureTestPackageService;
//
//    @Test
//    public void testInsertPressureTestPackage() throws Exception {
//        ObjectCollection objectCollection = new ObjectCollection("superuser");
//        IObject object = SchemaUtil.newIObject(TestPackageContext.CLASS_CIM_CCM_PRESSURE_TEST_PACKAGE, "test-002", "test-002", "test-002");
//
//        // 施工信息
//        object.setValue(WBSContext.INTERFACE_CIM_CCM_ICIMCCMWBS, WBSContext.PROPERTY_PURPOSE, "EN_Fabrication1", null);
//        object.setValue(WBSContext.INTERFACE_CIM_CCM_ICIMCCMWBS, WBSContext.PROPERTY_CWA, "340", null);
//        object.setValue(WBSContext.INTERFACE_CIM_CCM_ICIMCCMWBS, WBSContext.PROPERTY_DESIGN_DISCIPLINE, "Piping", null);
//
//        // 试压包信息
//        object.setValue(TestPackageContext.INTERFACE_CIM_CCM_PRESSURE_TEST_PACKAGE, TestPackageContext.PROPERTY_CHEMICAL_CLEANING, true, null);
//        object.setValue(TestPackageContext.INTERFACE_CIM_CCM_PRESSURE_TEST_PACKAGE, TestPackageContext.PROPERTY_HOT_OIL_FLUSHING, false, null);
////        object.setValue(TestPackageContext.INTERFACE_CIM_CCM_PRESSURE_TEST_PACKAGE, TestPackageContext.PROPERTY_TP_TP_STATUS, "EN_Execution", null);
//
//        // 计划信息
//        object.setValue(BasicPlanPackageContext.INTERFACE_BASIC_PLAN_PACKAGE_OBJ, BasicPlanPackageContext.PROPERTY_PLANNED_START, "2022-12-22 00:00:00", null);
//        object.setValue(BasicPlanPackageContext.INTERFACE_BASIC_PLAN_PACKAGE_OBJ, BasicPlanPackageContext.PROPERTY_PLANNED_END, "2022-12-24 00:00:00", null);
//
//        // 包信息
//        object.setValue(BasicPackageContext.INTERFACE_BASIC_PLAN_PACKAGE_OBJ, BasicPackageContext.PROPERTY_PROGRESS, "0.0", null);
//        object.setValue(BasicPackageContext.INTERFACE_BASIC_PLAN_PACKAGE_OBJ, BasicPackageContext.PROPERTY_PLANNED_WEIGHT, "11.0", null);
//
//
//        // 团队信息
//        object.setValue(BasicCrewContext.INTERFACE_BASIC_CREW_OBJ, BasicCrewContext.PROPERTY_CREW, "一号车间", null);
//        object.setValue(BasicCrewContext.INTERFACE_BASIC_CREW_OBJ, BasicCrewContext.PROPERTY_CREW_SIZE, "20", null);
//
//        // 合同信息
//        object.setValue(BasicContractContext.INTERFACE_BASIC_CONTRACT_OBJ, BasicContractContext.PROPERTY_CONTRACT, "01", null);
//        object.setValue(BasicContractContext.INTERFACE_BASIC_CONTRACT_OBJ, BasicContractContext.PROPERTY_CONTRACTOR, "xxx公司", null);
//
//        // 专业信息
//        object.setValue(BasicPackageContext.INTERFACE_BASIC_PLAN_PACKAGE_OBJ, BasicPackageContext.PROPERTY_DISCIPLINE, "Piping", null);
//        object.setValue(BasicPackageContext.INTERFACE_BASIC_PLAN_PACKAGE_OBJ, BasicPackageContext.PROPERTY_ESTIMATED_WEIGHT, "11.0", null);
//
//        // 备注信息
//        object.setValue(BasicNoteContext.INTERFACE_BASIC_NOTE_OBJ, BasicNoteContext.PROPERTY_NOTES, "备注", null);
//
//        object.finishCreate(objectCollection);
//        objectCollection.commit();
//    }
//
//    @Test
//    public void testRelDocAndDesign() throws Exception {
//        ObjectCollection objectCollection = new ObjectCollection("superuser");
//        IObject design = Context.Instance.getQueryHelper().getObjectByUidAndDefinitionUid("DA", "CIMCCMWeld", IObject.class);
//        IObject doc = Context.Instance.getQueryHelper().getObjectByUidAndDefinitionUid("DC", CLASS_CIM_CCM_DOCUMENT_MASTER, IObject.class);
//        IRel workStepRel = SchemaUtil.newRelationship(REL_DOCUMENT_TO_DESIGN_OBJ, doc, design);
//        workStepRel.finishCreate(objectCollection);
//        objectCollection.commit();
//    }
//
//    @Test
//    public void testSelectableDocumentsForPressureTestPackage() throws Exception {
//        QueryByPackageParamDTO documentsQueryByPackageParam = new QueryByPackageParamDTO();
//        documentsQueryByPackageParam.setUid("1711999033439690753");
//        documentsQueryByPackageParam.setClassDefUid(CLASS_CIM_CCM_DOCUMENT_MASTER);
//        TableData<JSONObject> tableData = pressureTestPackageService.selectableDocumentsForPressureTestPackage(documentsQueryByPackageParam);
//        System.out.println(com.alibaba.fastjson.JSON.toJSONString(tableData));
//    }
//
//    @Test
//    public void testSelectableComponentsForPressureTestPackage() throws Exception {
//        DesignsQueryByDocumentAndPackageParamDTODTO designsQueryByDocumentAndPackageParamDTO = new DesignsQueryByDocumentAndPackageParamDTODTO();
//        designsQueryByDocumentAndPackageParamDTO.setUid("1712009615958126593");
//        designsQueryByDocumentAndPackageParamDTO.setClassDefUid("CIMCCMWeld");
//        designsQueryByDocumentAndPackageParamDTO.setDocumentOBID("DC");
//        designsQueryByDocumentAndPackageParamDTO.setOrderParams(new ArrayList<>());
////        IObject ptp = Context.Instance.getQueryHelper().getObjectByUidAndDefinitionUid("1711999033439690753", TestPackageContext.CLASS_CIM_CCM_PRESSURE_TEST_PACKAGE, IObject.class);
//        TableData<JSONObject> tableData = pressureTestPackageService.selectableComponentsForPressureTestPackage(designsQueryByDocumentAndPackageParamDTO);
//        System.out.println(com.alibaba.fastjson.JSON.toJSONString(tableData));
//    }
//
//    @Test
//    public void testAssignDocumentsAndDesignsToPressureTestPackage() throws Exception {
//        TestRelDocsAndDesignsParamDTO testRelDocsAndDesignsParamDTO = new TestRelDocsAndDesignsParamDTO();
//        testRelDocsAndDesignsParamDTO.setUid("1712009615958126593");
//        testRelDocsAndDesignsParamDTO.setDocumentUID("DC");
//        List<String> stringList = new ArrayList<>();
//        stringList.add("DA");
//        testRelDocsAndDesignsParamDTO.setDesignUIDS(stringList);
//        pressureTestPackageService.assignDocumentsAndDesignsToPressureTestPackage(testRelDocsAndDesignsParamDTO);
//    }
//
//    @Test
//    public void testRemoveDocumentsFromPressureTestPackage() throws Exception {
//        PackageRelDocumentsParamDTO packageRelDocumentsParamDTO = new PackageRelDocumentsParamDTO();
//        packageRelDocumentsParamDTO.setUid("1712009615958126593");
//        packageRelDocumentsParamDTO.setDocIds(Arrays.asList("DC"));
//        pressureTestPackageService.removeDocumentsFromPressureTestPackage(packageRelDocumentsParamDTO);
//    }
//
//    @Test
//    public void getEnums() throws Exception {
//        IEnumListType enumListType = Context.Instance.getCacheHelper().getSchema("ELT_Discipline", IEnumListType.class);
//        List<IObject> entries = enumListType.getEntries();
//        System.out.println(entries.stream().map(IObject::getName).collect(Collectors.toList()));
//    }
//}