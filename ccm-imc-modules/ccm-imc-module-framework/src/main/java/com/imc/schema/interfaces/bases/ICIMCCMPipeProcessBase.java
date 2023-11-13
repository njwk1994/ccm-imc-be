package com.imc.schema.interfaces.bases;

import com.ccm.modules.COMContext;
import com.imc.common.core.utils.NumberUtils;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCCMPipeProcess;

public abstract class ICIMCCMPipeProcessBase extends InterfaceDefault implements ICIMCCMPipeProcess {

    public ICIMCCMPipeProcessBase(boolean instantiateRequiredProperties) {
        super(COMContext.INTERFACE_CIM_CCM_PIPE_PROCESS, instantiateRequiredProperties);
    }

    @Override
    public Double getCoatingArea() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPipeProcess", "CoatingArea");
        return actualValue != null ? NumberUtils.toDouble(actualValue) : 0.0;
    }

    @Override
    public void setCoatingArea(Double CoatingArea) throws Exception {
        this.setPropertyValue("ICIMCCMPipeProcess", "CoatingArea", CoatingArea, null, true);

    }

    @Override
    public String getCoatingColor() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPipeProcess", "CoatingColor");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCoatingColor(String CoatingColor) throws Exception {
        this.setPropertyValue("ICIMCCMPipeProcess", "CoatingColor", CoatingColor, null, true);

    }

    @Override
    public String getContaminationLevel() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPipeProcess", "ContaminationLevel");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setContaminationLevel(String ContaminationLevel) throws Exception {
        this.setPropertyValue("ICIMCCMPipeProcess", "ContaminationLevel", ContaminationLevel, null, true);

    }

    @Override
    public Double getDesignPressure() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPipeProcess", "DesignPressure");
        return actualValue != null ? NumberUtils.toDouble(actualValue) : 0.0;
    }

    @Override
    public void setDesignPressure(Double DesignPressure) throws Exception {
        this.setPropertyValue("ICIMCCMPipeProcess", "DesignPressure", DesignPressure, null, true);

    }

    @Override
    public Double getDesignTemperature() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPipeProcess", "DesignTemperature");
        return actualValue != null ? NumberUtils.toDouble(actualValue) : 0.0;
    }

    @Override
    public void setDesignTemperature(Double DesignTemperature) throws Exception {
        this.setPropertyValue("ICIMCCMPipeProcess", "DesignTemperature", DesignTemperature, null, true);

    }

    @Override
    public String getFluidSystem() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPipeProcess", "FluidSystem");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setFluidSystem(String FluidSystem) throws Exception {
        this.setPropertyValue("ICIMCCMPipeProcess", "FluidSystem", FluidSystem, null, true);

    }

    @Override
    public Double getInsulationArea() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPipeProcess", "InsulationArea");
        return actualValue != null ? NumberUtils.toDouble(actualValue) : 0.0;
    }

    @Override
    public void setInsulationArea(Double InsulationArea) throws Exception {
        this.setPropertyValue("ICIMCCMPipeProcess", "InsulationArea", InsulationArea, null, true);

    }

    @Override
    public String getInsulationCode() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPipeProcess", "InsulationCode");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setInsulationCode(String InsulationCode) throws Exception {
        this.setPropertyValue("ICIMCCMPipeProcess", "InsulationCode", InsulationCode, null, true);

    }

    @Override
    public String getTestMedium() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPipeProcess", "TestMedium");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setTestMedium(String TestMedium) throws Exception {
        this.setPropertyValue("ICIMCCMPipeProcess", "TestMedium", TestMedium, null, true);

    }

    @Override
    public Double getTestPressure() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPipeProcess", "TestPressure");
        return actualValue != null ? NumberUtils.toDouble(actualValue) : 0.0;
    }

    @Override
    public void setTestPressure(Double TestPressure) throws Exception {
        this.setPropertyValue("ICIMCCMPipeProcess", "TestPressure", TestPressure, null, true);

    }

    @Override
    public Double getTotalInsulThick() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPipeProcess", "TotalInsulThick");
        return actualValue != null ? NumberUtils.toDouble(actualValue) : 0.0;
    }

    @Override
    public void setTotalInsulThick(Double TotalInsulThick) throws Exception {
        this.setPropertyValue("ICIMCCMPipeProcess", "TotalInsulThick", TotalInsulThick, null, true);

    }

    @Override
    public Boolean getTraceRqmt() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPipeProcess", "TraceRqmt");
        return actualValue.equals(actualValue);
    }

    @Override
    public void setTraceRqmt(Boolean TraceRqmt) throws Exception {
        this.setPropertyValue("ICIMCCMPipeProcess", "TraceRqmt", TraceRqmt, null, true);

    }
}
