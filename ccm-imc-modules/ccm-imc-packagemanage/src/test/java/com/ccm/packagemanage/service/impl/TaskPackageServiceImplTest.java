//package com.ccm.packagemanage.service.impl;
//
//import com.alibaba.fastjson2.JSON;
//import com.alibaba.fastjson2.JSONObject;
//import com.ccm.modules.packagemanage.*;
//import com.ccm.packagemanage.domain.CalculateByPriorityParamDTO;
//import com.ccm.packagemanage.domain.PackageRelDocumentsParamDTO;
//import com.ccm.packagemanage.domain.QueryByPackageParamDTO;
//import com.ccm.packagemanage.service.ITaskPackageService;
//import com.imc.common.core.model.parameters.FilterParam;
//import com.imc.common.core.model.parameters.OrderParam;
//import com.imc.common.core.web.table.TableData;
//import com.imc.framework.collections.impl.ObjectCollection;
//import com.imc.framework.context.Context;
//import com.imc.framework.utils.SchemaUtil;
//import com.imc.schema.interfaces.IObject;
//import com.imc.schema.interfaces.IRel;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Arrays;
//
//import static com.ccm.modules.COMContext.*;
//import static com.ccm.modules.packagemanage.TaskPackageContext.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class TaskPackageServiceImplTest {
//    @Autowired
//    ITaskPackageService taskPackageService;
//
//    /**
//     * 插入任务包测试数据
//     */
//    @Test
//    public void testInsertTaskPackage() throws Exception {
//        ObjectCollection objectCollection = new ObjectCollection("superuser");
//        IObject object = SchemaUtil.newIObject(CLASS_CIM_CCM_TASK_PACKAGE, "TP-fab-001", "TP-fab-001", "TP_TP-fab-001_性能测试");
//        // 施工信息
//        object.setValue(WBSContext.INTERFACE_CIM_CCM_ICIMCCMWBS, WBSContext.PROPERTY_PURPOSE, "EN_Fabrication1", null);
//        object.setValue(WBSContext.INTERFACE_CIM_CCM_ICIMCCMWBS, WBSContext.PROPERTY_CWA, "340", null);
//        object.setValue(WBSContext.INTERFACE_CIM_CCM_ICIMCCMWBS, WBSContext.PROPERTY_DESIGN_DISCIPLINE, "Piping", null);
//
//        // 任务包信息
//        object.setValue(TaskPackageContext.INTERFACE_CIM_CCM_TASK_PACKAGE, TaskPackageContext.PROPERTY_TTP_STATUS, "EN_Execution", null);
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
//    /**
//     * 测试权重计算
//     * @throws Exception
//     */
//    @Test
//    public void testCalculatePriorityForTaskPackage() throws Exception {
//        CalculateByPriorityParamDTO calculateByPriorityParamDTO = new CalculateByPriorityParamDTO();
//        calculateByPriorityParamDTO.setUid("1706543957950717954");
//        calculateByPriorityParamDTO.setPriorityId("1710891568392409090");
//        calculateByPriorityParamDTO.setFromCache(false);
//        calculateByPriorityParamDTO.setToken("12345678");
//
//        Object oo = taskPackageService.calculatePriorityForTaskPackage(calculateByPriorityParamDTO);
//        System.out.println(com.alibaba.fastjson.JSON.toJSONString(oo));
//    }
//
//    /**
//     * 插入设计图纸
//     */
//    @Test
//    public void testInsertDocument() throws Exception {
//        ObjectCollection objectCollection = new ObjectCollection("superuser");
//        IObject workstep = Context.Instance.getQueryHelper().getObjectByUidAndDefinitionUid("1710891568392409090", CLASS_CIM_CCM_PRIORITY, IObject.class);
//        IObject design = Context.Instance.getQueryHelper().getObjectByUidAndDefinitionUid("1714466347418497025", CLASS_CIM_CCM_PRIORITY_ITEM, IObject.class);
//        IRel relationship = SchemaUtil.newRelationship(REL_PRIORITY_TO_ITEM, design, workstep);
//        relationship.finishCreate(objectCollection);
//        objectCollection.commit();
////        QueryRequest queryRequest = new QueryRequest();
////        queryRequest.addClassDefForQuery(CLASS_CIM_DESIGN_DOC_MASTER);
//////        queryRequest.addQueryCriteria(null, WBSContext.PROPERTY_CWA, SqlKeyword.EQ, "ELT_340_CWA");
////        queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + REL_TASK_PACKAGE_TO_DOCUMENT, PropertyDefinitions.uid1.toString(), SqlKeyword.NE, null);
////        Context.Instance.getQueryEngine().prepareQuery(queryRequest);
////        List<QueryTableWrapper> queryTables = queryRequest.getRouteTables();
////        for (QueryTableWrapper queryTableWrapper : queryTables) {
////            if (!queryTableWrapper.getChildren().isEmpty()) {
////                for (QueryTableWrapper queryCriteria : queryTableWrapper.getChildren()) {
////                    queryCriteria.setJoinMode("left");
////                }
////            }
////        }
////        List<IObject> docs = Context.Instance.getQueryHelper().query(queryRequest, IObject.class);
////        System.out.println(docs);
//    }
//
//    @Test
//    public void testInsertWorkStep() throws Exception {
//        ObjectCollection objectCollection = new ObjectCollection("superuser");
//        IObject object = SchemaUtil.newIObject(CLASS_CIM_CCM_PRIORITY_ITEM, "规则1", "规则1", "规则1");
//        object.finishCreate(objectCollection);
//        objectCollection.commit();
//    }
//
//    /**
//     * 查询任务包下推荐的设计图纸
//     * @throws Exception
//     */
//    @Test
//    public void testSelectDocumentsForTaskPackage() throws Exception {
//        QueryByPackageParamDTO documentsQueryByPackageParam = new QueryByPackageParamDTO();
//        documentsQueryByPackageParam.setUid("1706543957950717954");
////        documentsQueryByTaskParam.setUid("1706508496247050241");
//
//        // 分页
//        documentsQueryByPackageParam.setPageSize(3L);
//        documentsQueryByPackageParam.setCurrent(1L);
//
//        // 排序
//        OrderParam orderParam = new OrderParam();
//        orderParam.setField(WBSContext.PROPERTY_DESIGN_DISCIPLINE);
//        orderParam.setAsc(true);
//        documentsQueryByPackageParam.setOrderParams(Arrays.asList(orderParam));
//
//        // 筛选
//        FilterParam filterParam = new FilterParam();
//        filterParam.setPropertyDefUid(WBSContext.PROPERTY_DESIGN_DISCIPLINE);
//        filterParam.setOperator("EQ");
//        filterParam.setValue("ELT_Piping_Discipline");
//        documentsQueryByPackageParam.setFilterParams(Arrays.asList(filterParam));
//
//        // 查询
//        TableData<JSONObject> tableData = taskPackageService.selectDocumentsForTaskPackage(documentsQueryByPackageParam);
//
//        System.out.println(JSON.toJSON(tableData));
//    }
//
//    /**
//     * 添加图纸到任务包
//     */
//    @Test
//    public void testAssignDocumentsIntoTaskPackage() throws Exception {
//        PackageRelDocumentsParamDTO documentDTOs = new PackageRelDocumentsParamDTO();
//        documentDTOs.setUid("1714444206488748033");
//        documentDTOs.setDocIds(Arrays.asList("DA"));
//        taskPackageService.assignDocumentsIntoTaskPackage(documentDTOs);
//    }
//
//    /**
//     * 删除任务包下的图纸
//     */
//    @Test
//    public void testRemoveDocumentsFromTaskPackage() throws Exception {
//        PackageRelDocumentsParamDTO documentDTOs = new PackageRelDocumentsParamDTO();
//        documentDTOs.setUid("1706508496247050241");
//        documentDTOs.setDocIds(Arrays.asList("DA"));
//        taskPackageService.removeDocumentsFromTaskPackage(documentDTOs);
//    }
//}