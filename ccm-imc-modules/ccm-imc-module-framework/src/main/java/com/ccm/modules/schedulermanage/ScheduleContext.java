package com.ccm.modules.schedulermanage;

/**
 * @Author kekai.huang
 * @Date 2023/9/13 11:32
 * @PackageName:com.ccm.modules
 * @ClassName: ScheduleContext
 * @Description: TODO
 * @Version 1.0
 */
public class ScheduleContext {

    /**
     * 类
     */
    public static final String CLASS_CIM_CCM_SCHEDULE = "CIMCCMSchedule";

    /**
     * 施工优先级规则类
     */
    public static final String CLASS_CIM_CCM_BID_SCHEDULE_POLICY_ITEM = "CIMCCMSchedulePolicyItem";

    /**
     * 接口
     */
    public static final String INTERFACE_CIM_CCM_SCHEDULE = "ICIMCCMSchedule";

    /**
     * propertyDef-属性:合同描述
     */
    public static final String PROPERTY_CONTRACT_DESCRIPTION = "ContractDescription";
    /**
     * propertyDef-属性:承包商描述
     */
    public static final String PROPERTY_CONTRACTOR_DESCRIPTION = "ContractorDescription";
    /**
     * propertyDef-属性:施工计划
     */
    public static final String PROPERTY_CWP = "CWP";
    /**
     * propertyDef-属性:施工计划描述
     */
    public static final String PROPERTY_CWP_DESCRIPTION = "CWPDescription";
    /**
     * propertyDef-属性:施工计划EWP
     */
    public static final String PROPERTY_CWP_EWP = "CWPEWP";
    /**
     * propertyDef-属性:施工计划区域
     */
    public static final String PROPERTY_CWP_AREA = "CWPArea";
    /**
     * propertyDef-属性:施工计划专业
     */
    public static final String PROPERTY_CWP_DISCIPLINE = "CWPDiscipline";
    /**
     * propertyDef-属性:最早开始时间
     */
    public static final String PROPERTY_EARLY_START = "EarlyStart";
    /**
     * propertyDef-属性:最早结束时间
     */
    public static final String PROPERTY_EARLY_END = "EarlyEnd";
    /**
     * propertyDef-属性:最迟开始时间
     */
    public static final String PROPERTY_LATE_START = "LateStart";
    /**
     * propertyDef-属性:最迟结束时间
     */
    public static final String PROPERTY_LATE_END = "LateEnd";
    /**
     * propertyDef-属性:工作预算
     */
    public static final String PROPERTY_BUDGETED_LABOR = "BudgetedLabor";
    /**
     * propertyDef-属性:WBS路径
     */
    public static final String PROPERTY_WBS_PATH = "WBSPath";
    /* =========================================== 计划 end =========================================== */
    /**
     * 关联关系：计划-任务包
     */
    public static final String REL_SCHEDULE_2_TASK_PACKAGE = "CCMSchedule2TaskPackage";
    /**
     * 关联关系：计划-任务包
     */
    public static final String REL_SCHEDULE_2_WORK_PACKAGE = "CCMSchedule2WorkPackage";
    /**
     * 关联关系：计划-任务包
     */
    public static final String REL_SCHEDULE_2_PRESSURE_TEST_PACKAGE = "CCMSchedule2PressureTestPackage";

    /**
     * 计划下图纸信息
     */
    public static final String REL_SCHEDULE_TO_DOCUMENT = "CCMSchedule2Document";

    /**
     * 计划与计划策略项关联关系
     */
    public static final String REL_SCHEDULE_TO_SCHEDULE_POLICY_ITEM = "CCMSchedule2SchedulePolicyItem";
}
