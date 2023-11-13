package com.ccm.modules;

import com.imc.schema.interfaces.*;

/**
 * 通用hardcode
 *
 * @author HuangTao
 * @version 1.0
 * @since 2023/4/4 6:39
 */
public class COMContext {
    /*
    class def hardcode
     */
    public static final String CLASS_COM_DEMO_USER = "COMDemoUser";
    //设计数据对象-螺栓
    public static final String CLASS_CIM_CCM_BOLT = "CIMCCMBolt";

    //设计对象的施工分类
    public static final String CLASS_CIM_CCM_CONSTRUCTION_TYPE = "CIMCCMConstructionType";
    //设计文档
    public static final String CLASS_CIM_DESIGN_DOC_MASTER = "CIMDesignDocMaster";

    //管件
    public static final String CLASS_CIM_CCM_PIPE_COMPONENT = "CIMCCMPipeComponent";

    //基础目标对象
    public static final String CLASS_CIM_CCM_BASIC_TARGET_OBJ = "CIMCCMBasicTargetObj";

    // 工作步骤
    public static final String CLASS_CIM_CCM_WORK_STEP = "CIMCCMWorkStep";

    // 文档
    public static final String CLASS_CIM_CCM_DOCUMENT_MASTER = "CIMDesignDocMaster";

    // 优先级
    public static final String CLASS_CIM_CCM_PRIORITY = "CIMCCMPriority";

    /**
     * 施工优先级规则
     */
    public static final String CLASS_CIM_CCM_PRIORITY_ITEM = "CIMCCMPriorityItem";


    /*
    interface def hardcode
     */
    //
    public static final String INTERFACE_COM_DEMO_USER = "ICOMDemoUser";

    // 文档对象
    public static final String INTERFACE_CIM_DOCUMENT_MASTER = "ICIMDocumentMaster";


    //设计对象
    public static final String INTERFACE_CCM_BASIC_TARGET_OBJ = "ICCMBasicTargetObj";

    //工厂分解结构
    public static final String INTERFACE_CIM_CCM_PBS = "ICIMCCMPBS";
    //系统分解结构
    public static final String INTERFACE_CIM_CCM_SBS = "ICIMCCMSBS";
    //工作分解结构
    public static final String INTERFACE_CIM_CCM_WBS = "ICIMCCMWBS";

    //设计工具对象基本数据
    public static final String INTERFACE_CIM_CCM_DESIGN_TOOLS_DATA = "ICIMCCMDesignToolsData";

    //管道基础材料信息
    public static final String INTERFACE_CIM_CCM_PIPE_MATERIAL = "ICIMCCMPipeMaterial";

    //材料采购信息
    public static final String INTERFACE_CIM_CCM_MPI = "ICIMCCMMPI";

    //管道外形尺寸信息
    public static final String INTERFACE_CIM_CCM_PIPE_DIMENSION = "ICIMCCMPipeDimension";

    //施工信息
    public static final String INTERFACE_CIM_CCM_CONSTRUCTION = "ICIMCCMConstruction";

    //管道工艺信息
    public static final String INTERFACE_CIM_CCM_PIPE_PROCESS = "ICIMCCMPipeProcess";

    //焊口基本信息
    public static final String INTERFACE_CIM_CCM_WELD = "ICIMCCMWeld";

    //设计数据类型
    public static final String INTERFACE_CIM_CCM_COMPONENT_CATEGORY = "ICIMCCMComponentCategory";


    //基础合同对象
    public static final String INTERFACE_CIM_CCM_BASIC_CONTRACT_OBJ = "ICIMCCMBasicContractObj";

    //基础车间班组对象
    public static final String INTERFACE_CIM_CCM_BASIC_CREW_OBJ = "ICIMCCMBasicCrewObj";

    //基础备注对象
    public static final String INTERFACE_CIM_CCM_BASIC_NOTE_OBJ = "ICIMCCMBasicNoteObj";

    //基础图纸和包对象
    public static final String INTERFACE_CIM_CCM_BASIC_PACKAGE_DISCIPLINE_OBJ = "ICIMCCMBasicPackageDisciplineObj";

    //基础包对象
    public static final String INTERFACE_CIM_CCM_BASIC_PACKAGE_OBJ = "ICIMCCMBasicPackageObj";
    //基础计划和包对象
    public static final String INTERFACE_CIM_CCM_BASIC_PLAN_PACKAGE_OBJ = "ICIMCCMBasicPlanPackageObj";
    //基础目标对象
    public static final String INTERFACE_CIM_CCM_BASIC_TARGET_OBJ = "ICIMCCMBasicTargetObj";

    //设计对象的施工分类
    public static final String INTERFACE_CIM_CCM_CONSTRUCTION_TYPE = "ICIMCCMConstructionType";

    //试压包
    public static final String INTERFACE_CIM_CCM_PRESSURE_TEST_PACKAGE = "ICIMCCMPressureTestPackage";
    //试压包文件
    public static final String INTERFACE_CIM_CCM_PRESSURE_TEST_PACKAGE_FILE = "ICIMCCMPressureTestPackageFile";
    //施工优先级策略
    public static final String INTERFACE_CIM_CCM_PRIORITY = "ICIMCCMPriority";
    //施工优先级规则
    public static final String INTERFACE_CIM_CCM_PRIORITY_ITEM = "ICIMCCMPriorityItem";

    //计划
    public static final String INTERFACE_CIM_CCM_SCHEDULE = "ICIMCCMSchedule";

    //工作包
    public static final String INTERFACE_CIM_CCM_WORK_PACKAGE = "ICIMCCMWorkPackage";

    //工作包资源
    public static final String INTERFACE_CIM_CCM_WORK_PACKAGE_RESOURCES = "ICIMCCMWorkPackageResources";

    //试压包材料规格
    public static final String INTERFACE_CIM_CCM_PTPACKAGE_MATERIAL_SPECIFICATION = "ICIMCCMPTPackageMaterialSpecification";

    //试压包材料模板对象
    public static final String INTERFACE_CIM_CCM_PTPACKAGE_MATERIAL_TEMPLATE = "ICIMCCMPTPackageMaterialTemplate";

    //试压包材料
    public static final String INTERFACE_CIM_CCM_PTPACKAGE_MATERIAL = "ICIMCCMPTPackageMaterial";

    //标段
    public static final String INTERFACE_CIM_CCM_BID_SECTION = "ICIMCCMBidSection";

    //管段标准尺寸
    public static final String INTERFACE_CIM_CCM_PIPE_SIZE_STANDARD = "ICIMCCMPipeSizeStandard";

    //焊口当量修正系数
    public static final String INTERFACE_CIM_CCM_WELD_CORRECTION_FACTOR = "ICIMCCMWeldCorrectionFactor";

    //用于关联标段
    public static final String INTERFACE_CIM_CCM_TO_BID_SECTION = "ICIMCCMToBidSection";

    //计划策略项
    public static final String INTERFACE_CIM_CCM_SCHEDULE_POLICY_ITEM = "ICIMCCMSchedulePolicyItem";
    //移交邮件
    public static final String INTERFACE_CIM_CCM_HANDOVER_MAIL = "ICIMCCMHandoverMail";

    //移交邮件
    public static final String INTERFACE_CIM_CCM_HANDOVER_FILE = "ICIMCCMHandoverFile";

    //工作步骤
    public static final String INTERFACE_CIM_WORK_STEP = "ICIMWorkStep";
    //版本管理对象集合接口
    public static final String INTERFACE_CIM_REVISION_COLLECTIONS = "ICIMRevisionCollections";
    //版本管理接口
    public static final String INTERFACE_CIM_REVISION_ITEM = "ICIMRevisionItem";
//    //版本规则
//    public static final String CIM_REVISION_SCHEME = "ICIMRevisionScheme";
    // 设计数据
    public static final String INTERFACE_CIM_DESIGN_OBJ = "ICIMCCMDesignObj";





    /*
    property def hardcode
     */
    public static final String PROPERTY_COM_SEX = "COMSex";

    public static final String PROPERTY_TARGET_CLASS_DEF = "TargetClassDef";

    public static final String PROPERTY_MATERIAL_CODE = "MaterialCode";

    public static final String PROPERTY_WS_TP_PROCESS_PHASE = "WSTPProcessPhase";

    public static final String PROPERTY_WS_WP_PROCESS_PHASE = "WSWPProcessPhase";

    public static final String  PROPERTY_WS_STATUS = "WSStatus";
    /*
    rel def hardcode
     */
    public static final String REL_COM_USER_TO_HOUSE = "COMUser2House";

    /**
     * 工作包-设计图纸
     */
    public static final String REL_CCM_PACKAGE_TO_DOCUMENT = "CCMWorkPackage2Document";

    /**
     * 设计数据对象-工作步骤
     */
    public static final String REL_DESIGN_OBJ_TO_WORK_STEP = "CCMDesignObj2WorkStep";

    /**
     * 图纸下设计数据
     */
    public static final String REL_DOCUMENT_TO_DESIGN_OBJ = "CIMCCMDocument2DesignObj";

    /**
     * 关联关系：任务包-工作步骤
     */
    public static final String REL_WORK_PACKAGE_2_WORK_STEP = "CCMWorkPackage2WorkStep";

    /**
     * 施工优先级策略-施工优先级规则
     */
    public static final String REL_PRIORITY_TO_ITEM = "CCMPriority2Item";

    /**
     * 设计数据层级关系
     */
    public static final String REL_DESIGN_HIERARCHY = "CIMCCMDesignObjHierarchy";
}
