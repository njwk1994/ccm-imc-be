//package com.ccm.packagemanage.service.impl;
//
//import com.alibaba.fastjson2.JSON;
//import com.alibaba.fastjson2.JSONObject;
//import com.ccm.modules.packagemanage.*;
//import com.ccm.packagemanage.domain.PackageRelDocumentsParamDTO;
//import com.ccm.packagemanage.domain.QueryByPackageParamDTO;
//import com.ccm.packagemanage.service.IWorkPackageService;
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
//import static com.ccm.modules.COMContext.CLASS_CIM_CCM_WORK_STEP;
//import static com.ccm.modules.COMContext.INTERFACE_CIM_DESIGN_OBJ;
//import static com.ccm.modules.packagemanage.TaskPackageContext.CLASS_CIM_CCM_TASK_PACKAGE;
//import static com.ccm.modules.packagemanage.WorkPackageContext.REL_TASK_PACKAGE_TO_WORK_PACKAGE;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class WorkPackageServiceImplTest {
//    @Autowired
//    IWorkPackageService workPackageService;
//
//    @Test
//    public void testInsertWorkPackage() throws Exception {
//        ObjectCollection objectCollection = new ObjectCollection("superuser");
//        IObject object = SchemaUtil.newIObject(WorkPackageContext.CLASS_CIM_CCM_WORK_PACKAGE, "WP-fab-001", "WP-fab-001", "WP-fab-001_性能测试");
//
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
//    @Test
//    public void insertWorkStep() throws Exception {
//        ObjectCollection objectCollection = new ObjectCollection("superuser");
//        IObject object = SchemaUtil.newIObject(CLASS_CIM_CCM_WORK_STEP, "工作步骤1", "工作步骤1", "工作步骤1");
//
//        // 查询设计数据
//        IObject designObj = Context.Instance.getQueryHelper().getObjectByUidAndDefinitionUid("DA", INTERFACE_CIM_DESIGN_OBJ, IObject.class);
//
//        IRel relationship = SchemaUtil.newRelationship("CCMDesignObj2WorkStep", designObj, object);
//        // 2.2 创建完关联关系对象后必须标记创建结束,否则提交事务时无法成功写入数据
//        relationship.finishCreate(objectCollection);
//
//        objectCollection.commit();
//    }
//
//    @Test
//    public void testRelWorkPackage2TaskPackage() throws Exception {
//        ObjectCollection objectCollection = new ObjectCollection("superuser");
//        // 查询工作包
//        IObject workPackage = Context.Instance.getQueryHelper().getObjectByUidAndDefinitionUid("1714513493874270210", WorkPackageContext.CLASS_CIM_CCM_WORK_PACKAGE, IObject.class);
//
//        // 查询任务包
//        IObject taskPackage = Context.Instance.getQueryHelper().getObjectByUidAndDefinitionUid("1714444206488748033", CLASS_CIM_CCM_TASK_PACKAGE, IObject.class);
//
//        // 创建关联关系
//        IRel relationship = SchemaUtil.newRelationship(REL_TASK_PACKAGE_TO_WORK_PACKAGE, taskPackage, workPackage);
//        // 2.2 创建完关联关系对象后必须标记创建结束,否则提交事务时无法成功写入数据
//        relationship.finishCreate(objectCollection);
//        objectCollection.commit();
//    }
//
//    /**
//     * 测试查询可以添加的图纸
//     * @throws Exception
//     */
//    @Test
//    public void testSelectDocumentsForWorkPackage() throws Exception {
//        QueryByPackageParamDTO documentsQueryByPackageParam = new QueryByPackageParamDTO();
//        documentsQueryByPackageParam.setUid("1706942778119643138");
//
//        // 分页
//        documentsQueryByPackageParam.setPageSize(3L);
//        documentsQueryByPackageParam.setCurrent(1L);
//        TableData<JSONObject> tableData = workPackageService.selectDocumentsForWorkPackage(documentsQueryByPackageParam);
//
//        System.out.println(JSON.toJSON(tableData));
//    }
//
//    /**
//     * 测试添加图纸
//     */
//    @Test
//    public void testAssignDocumentsIntoWorkPackage() throws Exception {
//        PackageRelDocumentsParamDTO workRelDocumentsParam = new PackageRelDocumentsParamDTO();
//        workRelDocumentsParam.setUid("1706942778119643138");
//        workRelDocumentsParam.setDocIds(Arrays.asList("DA"));
//        workPackageService.assignDocumentsIntoWorkPackage(workRelDocumentsParam);
//    }
//
//    /**
//     * 测试删除图纸
//     * @throws Exception
//     */
//    @Test
//    public void testRemoveDocumentsFromWorkPackage() throws Exception {
//        PackageRelDocumentsParamDTO workRelDocumentsParam = new PackageRelDocumentsParamDTO();
//        workRelDocumentsParam.setUid("1706942778119643138");
//        workRelDocumentsParam.setDocIds(Arrays.asList("DA"));
//        workPackageService.removeDocumentsFromWorkPackage(workRelDocumentsParam);
//    }
//}