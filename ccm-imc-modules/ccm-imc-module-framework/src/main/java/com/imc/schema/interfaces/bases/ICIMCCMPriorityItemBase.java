package com.imc.schema.interfaces.bases;

import com.ccm.modules.COMContext;
import com.imc.common.core.utils.NumberUtils;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCCMPriorityItem;

public abstract class ICIMCCMPriorityItemBase extends InterfaceDefault implements ICIMCCMPriorityItem {
    public ICIMCCMPriorityItemBase(boolean instantiateRequiredProperties) {
        super(COMContext.INTERFACE_CIM_CCM_PRIORITY_ITEM, instantiateRequiredProperties);
    }

    @Override
    public String getICIMCCMPriority() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPriorityItem", "ICIMCCMPriority");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setICIMCCMPriority(String ICIMCCMPriority) throws Exception {
        this.setPropertyValue("ICIMCCMPriorityItem", "ICIMCCMPriority", ICIMCCMPriority, null, true);

    }

    @Override
    public String getPriorityTargetProperty() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPriorityItem", "PriorityTargetProperty");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setPriorityTargetProperty(String PriorityTargetProperty) throws Exception {
        this.setPropertyValue("ICIMCCMPriorityItem", "PriorityTargetProperty", PriorityTargetProperty, null, true);

    }

    @Override
    public String getPriorityExpectedValue() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPriorityItem", "PriorityExpectedValue");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setPriorityExpectedValue(String PriorityExpectedValue) throws Exception {
        this.setPropertyValue("ICIMCCMPriorityItem", "PriorityExpectedValue", PriorityExpectedValue, null, true);

    }

    @Override
    public String getPriorityCalculateType() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPriorityItem", "PriorityCalculateType");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setPriorityCalculateType(String PriorityCalculateType) throws Exception {
        this.setPropertyValue("ICIMCCMPriorityItem", "PriorityCalculateType", PriorityCalculateType, null, true);

    }

    @Override
    public Double getPriorityWeight() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPriorityItem", "PriorityWeight");
        return actualValue != null ? NumberUtils.toDouble(actualValue) : 0.0;
    }

    @Override
    public void setPriorityWeight(Double PriorityWeight) throws Exception {
        this.setPropertyValue("ICIMCCMPriorityItem", "PriorityWeight", PriorityWeight, null, true);
    }

    @Override
    public String getOperator() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPriorityItem", "Operator");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setOperator(String operator) throws Exception {
        this.setPropertyValue("ICIMCCMPriorityItem", "Operator", operator, null, true);
    }
}
