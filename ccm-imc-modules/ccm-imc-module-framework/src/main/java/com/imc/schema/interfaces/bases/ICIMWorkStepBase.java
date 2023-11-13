package com.imc.schema.interfaces.bases;

import com.ccm.modules.COMContext;
import com.imc.common.core.utils.NumberUtils;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMWorkStep;

public abstract class ICIMWorkStepBase extends InterfaceDefault implements ICIMWorkStep {
    public ICIMWorkStepBase(boolean instantiateRequiredProperties) {
        super(COMContext.INTERFACE_CIM_WORK_STEP, instantiateRequiredProperties);
    }

    @Override
    public Boolean getWSConsumeMaterial() {
        Object actualValue = this.getLatestValue("ICIMWorkStep", "WSConsumeMaterial");
        return actualValue.equals(actualValue);
    }

    @Override
    public void setWSConsumeMaterial(Boolean WSConsumeMaterial) throws Exception {
        this.setPropertyValue("ICIMWorkStep", "WSConsumeMaterial", WSConsumeMaterial, null, true);

    }

    @Override
    public String getWSROPRule() throws Exception {
        Object actualValue = this.getLatestValue("ICIMWorkStep", "WSROPRule");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setWSROPRule(String WSROPRule) throws Exception {
        this.setPropertyValue("ICIMWorkStep", "WSROPRule", WSROPRule, null, true);

    }

    @Override
    public String getWSTPProcessPhase() throws Exception {
        Object actualValue = this.getLatestValue("ICIMWorkStep", "WSTPProcessPhase");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setWSTPProcessPhase(String WSTPProcessPhase) throws Exception {
        this.setPropertyValue("ICIMWorkStep", "WSTPProcessPhase", WSTPProcessPhase, null, true);

    }

    @Override
    public String getWSWPProcessPhase() throws Exception {
        Object actualValue = this.getLatestValue("ICIMWorkStep", "WSWPProcessPhase");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setWSWPProcessPhase(String WSWPProcessPhase) throws Exception {
        this.setPropertyValue("ICIMWorkStep", "WSWPProcessPhase", WSWPProcessPhase, null, true);

    }

    @Override
    public String getWSStatus() throws Exception {
        Object actualValue = this.getLatestValue("ICIMWorkStep", "WSStatus");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setWSStatus(String WSStatus) throws Exception {
        this.setPropertyValue("ICIMWorkStep", "WSStatus", WSStatus, null, true);

    }

    @Override
    public String getWSComponentName() throws Exception {
        Object actualValue = this.getLatestValue("ICIMWorkStep", "WSComponentName");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setWSComponentName(String WSComponentName) throws Exception {
        this.setPropertyValue("ICIMWorkStep", "WSComponentName", WSComponentName, null, true);

    }

    @Override
    public String getWSComponentDesc() throws Exception {
        Object actualValue = this.getLatestValue("ICIMWorkStep", "WSComponentName");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setWSComponentDesc(String WSComponentDesc) throws Exception {
        this.setPropertyValue("ICIMWorkStep", "WSComponentDesc", WSComponentDesc, null, true);

    }

    @Override
    public Double getWSWeight() throws Exception {
        Object actualValue = this.getLatestValue("ICIMWorkStep", "WSWeight");
        return actualValue != null ? NumberUtils.toDouble(actualValue) : 0.0;
    }

    @Override
    public void setWSWeight(Double WSWeight) throws Exception {
        this.setPropertyValue("ICIMWorkStep", "WSWeight", WSWeight, null, true);

    }
}
