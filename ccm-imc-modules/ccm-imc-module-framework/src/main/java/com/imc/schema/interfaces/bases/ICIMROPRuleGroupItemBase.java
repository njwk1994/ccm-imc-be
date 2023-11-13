package com.imc.schema.interfaces.bases;

import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMROPRuleGroupItem;

public abstract class ICIMROPRuleGroupItemBase  extends InterfaceDefault implements ICIMROPRuleGroupItem {
    public ICIMROPRuleGroupItemBase(boolean instantiateRequiredProperties) {
        super(ICIMROPRuleGroupItem.class.getSimpleName(),instantiateRequiredProperties);
    }
    @Override
    public String getCIMROPTargetPropertyDefinitionUID() {
        Object actualValue = this.getLatestValue("ICIMROPRuleGroupItem", "CIMROPTargetPropertyDefinitionUID");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMROPTargetPropertyDefinitionUID(String CIMROPTargetPropertyDefinitionUID) throws Exception {
        this.setPropertyValue("ICIMROPRuleGroupItem", "CIMROPTargetPropertyDefinitionUID", CIMROPTargetPropertyDefinitionUID, null, true);

    }

    @Override
    public String getCIMROPCalculationValue() {
        Object actualValue = this.getLatestValue("ICIMROPRuleGroupItem", "getCIMROPCalculationValue");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMROPCalculationValue(String CIMROPCalculationValue) throws Exception {
        this.setPropertyValue("ICIMROPRuleGroupItem", "CIMROPCalculationValue", CIMROPCalculationValue, null, true);

    }

    @Override
    public String getCIMROPTargetPropertyValueUoM() {
        Object actualValue = this.getLatestValue("ICIMROPRuleGroupItem", "CIMROPTargetPropertyValueUoM");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMROPTargetPropertyValueUoM(String CIMROPTargetPropertyValueUoM) throws Exception {
        this.setPropertyValue("ICIMROPRuleGroupItem", "CIMROPTargetPropertyValueUoM", CIMROPTargetPropertyValueUoM, null, true);

    }
}
