package com.imc.schema.interfaces;

/**
 * 施工优先级规则
 */
public interface ICIMCCMPriorityItem extends IObject {

    //条件
    String getICIMCCMPriority() throws Exception;

    void setICIMCCMPriority(String ICIMCCMPriority) throws Exception;

    //判断目标的属性
    String getPriorityTargetProperty() throws Exception;

    void setPriorityTargetProperty(String PriorityTargetProperty) throws Exception;

    //判断的目标值
    String getPriorityExpectedValue() throws Exception;

    void setPriorityExpectedValue(String PriorityExpectedValue) throws Exception;

    //优先级计算属性类别
    String getPriorityCalculateType() throws Exception;

    void setPriorityCalculateType(String PriorityCalculateType) throws Exception;

    //优先级权重
    Double  getPriorityWeight() throws Exception;

    void setPriorityWeight(Double PriorityWeight) throws Exception;

    // 条件
    String getOperator() throws Exception;
    void setOperator(String operator) throws Exception;

    public boolean isHint(IObject object) throws Exception;

}
