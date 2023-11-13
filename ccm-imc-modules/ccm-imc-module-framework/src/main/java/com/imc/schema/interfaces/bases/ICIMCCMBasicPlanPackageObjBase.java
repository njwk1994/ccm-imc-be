package com.imc.schema.interfaces.bases;

import cn.hutool.core.date.DateTime;
import com.ccm.modules.packagemanage.BasicPlanPackageContext;
import com.ccm.modules.COMContext;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCCMBasicPlanPackageObj;

import java.time.DateTimeException;

public abstract class ICIMCCMBasicPlanPackageObjBase extends InterfaceDefault implements ICIMCCMBasicPlanPackageObj {


    public ICIMCCMBasicPlanPackageObjBase(boolean instantiateRequiredProperties) {
        super(COMContext.INTERFACE_CIM_CCM_BASIC_PLAN_PACKAGE_OBJ, instantiateRequiredProperties);
    }

    @Override
    public DateTime getActualEnd() throws Exception {
        Object actualValue = this.getLatestValue(BasicPlanPackageContext.INTERFACE_BASIC_PLAN_PACKAGE_OBJ, BasicPlanPackageContext.PROPERTY_ACTUAL_END);
        if (actualValue != null && actualValue instanceof DateTime) { return (DateTime) actualValue; } else { throw new DateTimeException("Failed to retrieve the CIM revision issue date."); }

    }

    @Override
    public void setActualEnd(DateTime ActualEnd) throws Exception {
        this.setPropertyValue(BasicPlanPackageContext.INTERFACE_BASIC_PLAN_PACKAGE_OBJ, BasicPlanPackageContext.PROPERTY_ACTUAL_END, ActualEnd, null, true);

    }

    @Override
    public DateTime getActualStart() throws Exception {
        Object actualValue = this.getLatestValue(BasicPlanPackageContext.INTERFACE_BASIC_PLAN_PACKAGE_OBJ, BasicPlanPackageContext.PROPERTY_ACTUAL_START);
        if (actualValue != null && actualValue instanceof DateTime) { return (DateTime) actualValue; } else { throw new DateTimeException("Failed to retrieve the CIM revision issue date."); }

    }

    @Override
    public void setActualStart(DateTime ActualStart) throws Exception {
        this.setPropertyValue(BasicPlanPackageContext.INTERFACE_BASIC_PLAN_PACKAGE_OBJ, BasicPlanPackageContext.PROPERTY_ACTUAL_START, ActualStart, null, true);

    }

    @Override
    public DateTime getPlannedEnd() throws Exception {
        Object actualValue = this.getLatestValue(BasicPlanPackageContext.INTERFACE_BASIC_PLAN_PACKAGE_OBJ, BasicPlanPackageContext.PROPERTY_PLANNED_END);
        if (actualValue != null && actualValue instanceof DateTime) { return (DateTime) actualValue; } else { throw new DateTimeException("Failed to retrieve the CIM revision issue date."); }

    }

    @Override
    public void setPlannedEnd(DateTime PlannedEnd) throws Exception {
        this.setPropertyValue(BasicPlanPackageContext.INTERFACE_BASIC_PLAN_PACKAGE_OBJ, BasicPlanPackageContext.PROPERTY_PLANNED_END, PlannedEnd, null, true);

    }

    @Override
    public DateTime getPlannedStart() throws Exception {
        Object actualValue = this.getLatestValue(BasicPlanPackageContext.INTERFACE_BASIC_PLAN_PACKAGE_OBJ, BasicPlanPackageContext.PROPERTY_PLANNED_START);
        if (actualValue != null && actualValue instanceof DateTime) { return (DateTime) actualValue; } else { throw new DateTimeException("Failed to retrieve the CIM revision issue date."); }

    }

    @Override
    public void setPlannedStart(DateTime PlannedStart) throws Exception {
        this.setPropertyValue(BasicPlanPackageContext.INTERFACE_BASIC_PLAN_PACKAGE_OBJ, BasicPlanPackageContext.PROPERTY_PLANNED_START, PlannedStart, null, true);

    }
}
