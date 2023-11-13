package com.imc.schema.interfaces.bases;

import com.imc.common.core.utils.NumberUtils;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCDWPipeProcess;

public abstract class ICIMCDWPipeProcessBase extends InterfaceDefault implements ICIMCDWPipeProcess {


    public ICIMCDWPipeProcessBase(boolean instantiateRequiredProperties) {
        super(ICIMCDWPipeProcess.class.getSimpleName(),instantiateRequiredProperties);
    }

    @Override
    public Double getCIMCDWCoatingArea() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCDWPipeProcess", "CIMCDWCoatingArea");
        return actualValue != null ? NumberUtils.toDouble(actualValue) : 0.0;
    }

    @Override
    public void setCIMCDWCoatingArea(Double CIMCDWCoatingArea) throws Exception {
        this.setPropertyValue("ICIMCDWPipeProcess", "CIMCDWCoatingArea", CIMCDWCoatingArea, null, true);
    }

    @Override
    public String getCIMCDWCoatingColor() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCDWPipeProcess", "CIMCDWCoatingColor");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMCDWCoatingColor(String CIMCDWCoatingColor) throws Exception {
        this.setPropertyValue("ICIMCDWPipeProcess", "CIMCDWCoatingColor", CIMCDWCoatingColor, null, true);

    }

    @Override
    public String getCIMCDWContaminationLevel() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCDWPipeProcess", "CIMCDWContaminationLevel");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMCDWContaminationLevel(String CIMCDWContaminationLevel) throws Exception {
        this.setPropertyValue("ICIMCDWPipeProcess", "CIMCDWContaminationLevel", CIMCDWContaminationLevel, null, true);

    }

    @Override
    public Double getCIMCDWDesignPressure() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCDWPipeProcess", "CIMCDWCoatingArea");
        return actualValue != null ? NumberUtils.toDouble(actualValue) : 0.0;
    }

    @Override
    public void setCIMCDWDesignPressure(Double CIMCDWDesignPressure) throws Exception {
        this.setPropertyValue("ICIMCDWPipeProcess", "CIMCDWDesignPressure", CIMCDWDesignPressure, null, true);

    }

    @Override
    public Double getCIMCDWDesignTemperature() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCDWPipeProcess", "CIMCDWCoatingArea");
        return actualValue != null ? NumberUtils.toDouble(actualValue) : 0.0;
    }

    @Override
    public void setCIMCDWDesignTemperature(Double CIMCDWDesignTemperature) throws Exception {
        this.setPropertyValue("ICIMCDWPipeProcess", "CIMCDWDesignTemperature", CIMCDWDesignTemperature, null, true);

    }

    @Override
    public String getCIMCDWFluidSystem() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCDWPipeProcess", "CIMCDWFluidSystem");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMCDWFluidSystem(String CIMCDWFluidSystem) throws Exception {
        this.setPropertyValue("ICIMCDWPipeProcess", "CIMCDWFluidSystem", CIMCDWFluidSystem, null, true);

    }

    @Override
    public Double getCIMCDWInsulationArea() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCDWPipeProcess", "CIMCDWInsulationArea");
        return actualValue != null ? NumberUtils.toDouble(actualValue) : 0.0;
    }

    @Override
    public void setCIMCDWInsulationArea(Double CIMCDWInsulationArea) throws Exception {
        this.setPropertyValue("ICIMCDWPipeProcess", "CIMCDWInsulationArea", CIMCDWInsulationArea, null, true);

    }

    @Override
    public String getCIMCDWInsulationCode() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCDWPipeProcess", "CIMCDWInsulationCode");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMCDWInsulationCode(String CIMCDWInsulationCode) throws Exception {
        this.setPropertyValue("ICIMCDWPipeProcess", "CIMCDWInsulationCode", CIMCDWInsulationCode, null, true);

    }

    @Override
    public String getCIMCDWTestMedium() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCDWPipeProcess", "CIMCDWTestMedium");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMCDWTestMedium(String CIMCDWTestMedium) throws Exception {
        this.setPropertyValue("ICIMCDWPipeProcess", "CIMCDWTestMedium", CIMCDWTestMedium, null, true);

    }

    @Override
    public Double getCIMCDWTestPressure() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCDWPipeProcess", "CIMCDWTestPressure");
        return actualValue != null ? NumberUtils.toDouble(actualValue) : 0.0;
    }

    @Override
    public void setCIMCDWTestPressure(Double CIMCDWTestPressure) throws Exception {
        this.setPropertyValue("ICIMCDWPipeProcess", "CIMCDWTestPressure", CIMCDWTestPressure, null, true);

    }

    @Override
    public Double getCIMCDWTotalInsulThick() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCDWPipeProcess", "CIMCDWTotalInsulThick");
        return actualValue != null ? NumberUtils.toDouble(actualValue) : 0.0;
    }

    @Override
    public void setCIMCDWTotalInsulThick(Double CIMCDWTotalInsulThick) throws Exception {
        this.setPropertyValue("ICIMCDWPipeProcess", "CIMCDWTotalInsulThick", CIMCDWTotalInsulThick, null, true);

    }

    @Override
    public Boolean getCIMCDWTraceRqmt() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCDWPipeProcess", "CIMCDWTraceRqmt");
        return actualValue.equals(actualValue);
    }

    @Override
    public void setCIMCDWTraceRqmt(Boolean CIMCDWTraceRqmt) throws Exception {
        this.setPropertyValue("ICIMCDWPipeProcess", "CIMCDWTraceRqmt", CIMCDWTraceRqmt, null, true);

    }
}
