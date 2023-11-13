package com.imc.schema.interfaces;

public interface ICIMROPRuleGroupItem {


    /**
     * 获取所需计算对象的属性
     * @return
     */
    String getCIMROPTargetPropertyDefinitionUID() ;

    /**
     * 设置所需对象计算的属性
     * @param CIMROPTargetPropertyDefinitionUID
     * @throws Exception
     */
    void setCIMROPTargetPropertyDefinitionUID(String CIMROPTargetPropertyDefinitionUID) throws Exception;


    /**
     * 获取过滤条件值
     * @return
     */
    String getCIMROPCalculationValue() ;

    /**
     * 设置过滤条件值
     * @param CIMROPCalculationValue
     * @throws Exception
     */
    void setCIMROPCalculationValue(String CIMROPCalculationValue) throws Exception;


    /**
     * 获取单位
     * @return
     */
    String getCIMROPTargetPropertyValueUoM() ;

    /**
     * 设置单位
     * @param CIMROPTargetPropertyValueUoM
     * @throws Exception
     */
    void setCIMROPTargetPropertyValueUoM(String CIMROPTargetPropertyValueUoM) throws Exception;

}
