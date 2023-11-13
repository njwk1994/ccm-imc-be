package com.imc.schema.interfaces.bases;


import com.imc.common.core.utils.NumberUtils;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMROPWorkStep;

public abstract class ICIMROPWorkStepBase extends InterfaceDefault implements ICIMROPWorkStep {

    public ICIMROPWorkStepBase(boolean instantiateRequiredProperties) {
        super(ICIMROPWorkStep.class.getSimpleName(),instantiateRequiredProperties);
    }

    @Override
    public String getCIMROPWorkStepTPPhase() {
        Object actualValue = this.getLatestValue("ICIMROPWorkStep", "CIMROPWorkStepTPPhase");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMCIMROPWorkStepTPPhase(String CIMROPWorkStepTPPhase) throws Exception {
        this.setPropertyValue("ICIMROPWorkStep", "CIMROPWorkStepTPPhase", CIMROPWorkStepTPPhase, null, true);

    }

    @Override
    public String getCIMROPWorkStepWPPhase() {
        Object actualValue = this.getLatestValue("ICIMROPWorkStep", "CIMROPWorkStepWPPhase");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMROPWorkStepWPPhase(String CIMROPWorkStepWPPhase) throws Exception {
        this.setPropertyValue("ICIMROPWorkStep", "CIMROPWorkStepWPPhase", CIMROPWorkStepWPPhase, null, true);

    }

    @Override
    public String getCIMROPWorkStepName() {
        Object actualValue = this.getLatestValue("ICIMROPWorkStep", "CIMROPWorkStepName");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMROPWorkStepName(String CIMROPWorkStepName) throws Exception {
        this.setPropertyValue("ICIMROPWorkStep", "CIMROPWorkStepName", CIMROPWorkStepName, null, true);

    }

    @Override
    public String getCIMROPWorkStepType() {
        Object actualValue = this.getLatestValue("ICIMROPWorkStep", "CIMROPWorkStepType");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMROPWorkStepType(String CIMROPWorkStepType) throws Exception {
        this.setPropertyValue("ICIMROPWorkStep", "CIMROPWorkStepType", CIMROPWorkStepType, null, true);

    }

    @Override
    public Boolean getCIMROPWorkStepAllowInd() {
        Object actualValue = this.getLatestValue("ICIMROPWorkStep", "CIMROPWorkStepAllowInd");
        return actualValue.equals(actualValue);
    }

    @Override
    public void setCIMROPWorkStepAllowInd(Boolean CIMROPWorkStepAllowInd) throws Exception {
        this.setPropertyValue("ICIMROPWorkStep", "CIMROPWorkStepAllowInd", CIMROPWorkStepAllowInd, null, true);

    }

    @Override
    public Boolean getCIMROPWorkStepConsumeMaterialInd() {
        Object actualValue = this.getLatestValue("ICIMROPWorkStep", "CIMROPWorkStepConsumeMaterialInd");
        return actualValue.equals(actualValue);
    }

    @Override
    public void setCIMROPWorkStepConsumeMaterialInd(Boolean CIMROPWorkStepConsumeMaterialInd) throws Exception {
        this.setPropertyValue("ICIMROPWorkStep", "CIMROPWorkStepAllowInd", CIMROPWorkStepConsumeMaterialInd, null, true);

    }

    @Override
    public String getCIMROPWorkStepWeightCalculateProperty() {
        Object actualValue = this.getLatestValue("ICIMROPWorkStep", "CIMROPWorkStepWeightCalculateProperty");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMROPWorkStepWeightCalculateProperty(String CIMROPWorkStepWeightCalculateProperty) throws Exception {
        this.setPropertyValue("ICIMROPWorkStep", "CIMROPWorkStepWeightCalculateProperty", CIMROPWorkStepWeightCalculateProperty, null, true);

    }

    @Override
    public Double getCIMROPWorkStepBaseWeight() {
        Object actualValue = this.getLatestValue("ICIMROPWorkStep", "CIMROPWorkStepWeightCalculateProperty");
        return actualValue != null ? NumberUtils.toDouble(actualValue) :0;
    }

    @Override
    public void setCIMROPWorkStepBaseWeight(Double CIMROPWorkStepBaseWeight) throws Exception {
        this.setPropertyValue("ICIMROPWorkStep", "CIMROPWorkStepBaseWeight", CIMROPWorkStepBaseWeight, null, true);

    }

    @Override
    public Integer getCIMROPWorkStepOrderValue() {
        Object actualValue = this.getLatestValue("ICIMROPWorkStep", "CIMROPWorkStepOrderValue");
        return actualValue != null ? NumberUtils.toInteger(actualValue) :0;
    }

    @Override
    public void setCIMROPWorkStepOrderValue(Integer CIMROPWorkStepOrderValue) throws Exception {
        this.setPropertyValue("ICIMROPWorkStep", "CIMROPWorkStepOrderValue", CIMROPWorkStepOrderValue, null, true);

    }

    @Override
    public String getCIMROPWorkStepMaterialIssue() {
        Object actualValue = this.getLatestValue("ICIMROPWorkStep", "CIMROPWorkStepMaterialIssue");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMROPWorkStepMaterialIssue(String CIMROPWorkStepMaterialIssue) throws Exception {
        this.setPropertyValue("ICIMROPWorkStep", "CIMROPWorkStepMaterialIssue", CIMROPWorkStepMaterialIssue, null, true);

    }

    @Override
    public String getCIMROPWorkStepGenerateMode() {
        Object actualValue = this.getLatestValue("ICIMROPWorkStep", "CIMROPWorkStepGenerateMode");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMROPWorkStepGenerateMode(String CIMROPWorkStepGenerateMod) throws Exception {
        this.setPropertyValue("ICIMROPWorkStep", "CIMROPWorkStepGenerateMod", CIMROPWorkStepGenerateMod, null, true);

    }
}
