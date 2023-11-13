package com.imc.schema.interfaces.bases;

import com.imc.common.core.utils.NumberUtils;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMROPRuleGroup;

public abstract class ICIMROPRuleGroupBase extends InterfaceDefault implements ICIMROPRuleGroup {
    public ICIMROPRuleGroupBase(boolean instantiateRequiredProperties) {
        super(ICIMROPRuleGroup.class.getSimpleName(),instantiateRequiredProperties);
    }

    @Override
    public String getCIMROPGroupClassDefinitionUID() {
        Object actualValue = this.getLatestValue("ICIMROPRuleGroup", "CIMROPGroupClassDefinitionUID");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMROPGroupClassDefinitionUID(String CIMROPGroupClassDefinitionUID) throws Exception {
        this.setPropertyValue("ICIMROPRuleGroup", "CIMROPGroupClassDefinitionUID", CIMROPGroupClassDefinitionUID, null, true);

    }

    @Override
    public String getCIMROPGroupItemRevState() {
        Object actualValue = this.getLatestValue("ICIMROPRuleGroup", "CIMROPGroupItemRevState");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMROPGroupItemRevStat(String CIMROPGroupItemRevStat) throws Exception {
        this.setPropertyValue("ICIMROPRuleGroup", "CIMROPGroupItemRevStat", CIMROPGroupItemRevStat, null, true);

    }

    @Override
    public Boolean getCIMROPGroupItemsHasUpdated() {
        Object actualValue = this.getLatestValue("ICIMROPRuleGroup", "CIMROPGroupItemsHasUpdated");
        return actualValue.equals(actualValue);
    }

    @Override
    public void setCIMROPGroupItemsHasUpdated(Boolean CIMROPGroupItemsHasUpdated) throws Exception {
        this.setPropertyValue("ICIMROPRuleGroup", "ROPGroupItemsHasUpdated", CIMROPGroupItemsHasUpdated, null, true);

    }

    @Override
    public Boolean getCIMROPGroupWorkStepHasUpdated() {
        Object actualValue = this.getLatestValue("ICIMROPRuleGroup", "CIMROPGroupWorkStepHasUpdated");
        return actualValue.equals(actualValue);
    }

    @Override
    public void setCIMROPGroupWorkStepHasUpdated(Boolean CIMROPGroupWorkStepHasUpdated) throws Exception {
        this.setPropertyValue("ICIMROPRuleGroup", "CIMROPGroupWorkStepHasUpdated", CIMROPGroupWorkStepHasUpdated, null, true);

    }

    @Override
    public Boolean getCIMROPHasHandleChange() {
        Object actualValue = this.getLatestValue("ICIMROPRuleGroup", "CIMROPHasHandleChange");
        return actualValue.equals(actualValue);
    }

    @Override
    public void setCIMROPHasHandleChange(Boolean CIMROPHasHandleChange) throws Exception {
        this.setPropertyValue("ICIMROPRuleGroup", "CIMROPHasHandleChange", CIMROPHasHandleChange, null, true);

    }

    @Override
    public String getCIMROPGroupWorkStepRevState() {
        Object actualValue = this.getLatestValue("ICIMROPRuleGroup", "CIMROPGroupWorkStepRevState");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMROPGroupWorkStepRevState(String CIMROPGroupWorkStepRevState) throws Exception {
        this.setPropertyValue("ICIMROPRuleGroup", "CIMROPHasHandleChange", CIMROPGroupWorkStepRevState, null, true);

    }

    @Override
    public Integer getCIMROPGroupOrder() {
        Object actualValue = this.getLatestValue("ICIMROPRuleGroup", "CIMROPGroupOrder");
        return actualValue != null ? NumberUtils.toInteger(actualValue):0;
    }

    @Override
    public void setCIMROPGroupOrder(Integer CIMROPGroupOrder) throws Exception {
        this.setPropertyValue("ICIMROPRuleGroup", "CIMROPGroupOrder", CIMROPGroupOrder, null, true);

    }
}
