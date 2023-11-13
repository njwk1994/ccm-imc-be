package com.imc.schema.interfaces.bases;

import com.ccm.modules.packagemanage.BasicPackageContext;
import com.ccm.modules.COMContext;
import com.imc.common.core.utils.NumberUtils;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCCMBasicPackageObj;

public abstract class ICIMCCMBasicPackageObjBase extends InterfaceDefault implements ICIMCCMBasicPackageObj {
    public ICIMCCMBasicPackageObjBase( boolean instantiateRequiredProperties) {
        super(COMContext.INTERFACE_CIM_CCM_BASIC_PACKAGE_OBJ, instantiateRequiredProperties);
    }

    @Override
    public String getParentPlan() throws Exception {
        Object actualValue = this.getLatestValue(BasicPackageContext.INTERFACE_BASIC_PLAN_PACKAGE_OBJ, BasicPackageContext.PROPERTY_PARENT_PLAN);
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setParentPlan(String ParentPlan) throws Exception {
        this.setPropertyValue(BasicPackageContext.INTERFACE_BASIC_PLAN_PACKAGE_OBJ, BasicPackageContext.PROPERTY_PARENT_PLAN, ParentPlan, null, true);

    }

    @Override
    public String getParentPlanName() throws Exception {
        Object actualValue = this.getLatestValue(BasicPackageContext.INTERFACE_BASIC_PLAN_PACKAGE_OBJ, BasicPackageContext.PROPERTY_PARENT_PLAN_NAME);
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setParentPlanName(String ParentPlanName) throws Exception {
        this.setPropertyValue(BasicPackageContext.INTERFACE_BASIC_PLAN_PACKAGE_OBJ, BasicPackageContext.PROPERTY_PARENT_PLAN_NAME, ParentPlanName, null, true);
    }

    @Override
    public Double getPlannedWeight() throws Exception {
        Object actualValue = this.getLatestValue(BasicPackageContext.INTERFACE_BASIC_PLAN_PACKAGE_OBJ, BasicPackageContext.PROPERTY_PLANNED_WEIGHT);
        return actualValue != null ? NumberUtils.toDouble(actualValue) : 0.0;
    }

    @Override
    public void setPlannedWeight(Double PlannedWeight) throws Exception {
        this.setPropertyValue(BasicPackageContext.INTERFACE_BASIC_PLAN_PACKAGE_OBJ, BasicPackageContext.PROPERTY_PLANNED_WEIGHT, PlannedWeight, null, true);

    }

    @Override
    public Double getProgress() throws Exception {
        Object actualValue = this.getLatestValue(BasicPackageContext.INTERFACE_BASIC_PLAN_PACKAGE_OBJ, BasicPackageContext.PROPERTY_PROGRESS);
        return actualValue != null ? NumberUtils.toDouble(actualValue) : 0.0;
    }

    @Override
    public void setProgress(Double Progress) throws Exception {
        this.setPropertyValue(BasicPackageContext.INTERFACE_BASIC_PLAN_PACKAGE_OBJ, BasicPackageContext.PROPERTY_PROGRESS, Progress, null, true);

    }

    @Override
    public Double getEstimatedProgress() throws Exception {
        Object actualValue = this.getLatestValue(BasicPackageContext.INTERFACE_BASIC_PLAN_PACKAGE_OBJ, BasicPackageContext.PROPERTY_ESTIMATED_PROGRESS);
        return actualValue != null ? NumberUtils.toDouble(actualValue) : 0.0;
    }

    @Override
    public void setEstimatedProgress(Double EstimatedProgress) throws Exception {
        this.setPropertyValue(BasicPackageContext.INTERFACE_BASIC_PLAN_PACKAGE_OBJ, BasicPackageContext.PROPERTY_ESTIMATED_PROGRESS, EstimatedProgress, null, true);

    }

    @Override
    public String getScope() throws Exception {
        Object actualValue = this.getLatestValue(BasicPackageContext.INTERFACE_BASIC_PLAN_PACKAGE_OBJ, BasicPackageContext.PROPERTY_SCOPE);
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setScope(String Scope) throws Exception {
        this.setPropertyValue(BasicPackageContext.INTERFACE_BASIC_PLAN_PACKAGE_OBJ, BasicPackageContext.PROPERTY_SCOPE, Scope, null, true);

    }

    @Override
    public String getState() throws Exception {
        Object actualValue = this.getLatestValue(BasicPackageContext.INTERFACE_BASIC_PLAN_PACKAGE_OBJ, BasicPackageContext.PROPERTY_STATE);
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setState(String State) throws Exception {
        this.setPropertyValue(BasicPackageContext.INTERFACE_BASIC_PLAN_PACKAGE_OBJ, BasicPackageContext.PROPERTY_STATE, State, null, true);

    }
}
