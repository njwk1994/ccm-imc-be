package com.ccm.modules.documentmanage;

import java.math.BigDecimal;

/**
 * @description：TODO
 * @author： kekai.huang
 * @create： 2023/11/1 9:14
 */
public class CCMPipeSizeStandardContext {
    /**
     * InterfaceDef-管段标准尺寸
     */
    public static final String INTERFACE_CIM_CCM_PIPE_SIZE_STANDARD = "ICIMCCMPipeSizeStandard";
    /**
     * classDef-管段标准尺寸
     */
    public static final String CLASS_CIM_CCM_PIPE_SIZE_STANDARD = "CIMCCMPipeSizeStandard";
    /**
     * propertyDef-标准
     */
    public static final String STANDARD = "Standard";
    /**
     * propertyDef-壁厚
     */
    public static final String WALL_THICKNESS = "WallThickness";
    /**
     * propertyDef-英制尺寸
     */
    public static final String INCH = "Inch";
    /**
     * propertyDef-公称直径
     */
    public static final String DN = "DN";
    /**
     * propertyDef-外径
     */
    public static final String OD = "od";
    /**
     * propertyDef-壁厚等级
     */
    public static final String RATING_SCHEDULE = "RatingSchedule";

    /**
     * 计算英制通用系数( 公制尺寸/25.4 转为英制)
     */
    public static final BigDecimal INCH_COEFFICIENT = new BigDecimal("25.4");
}
