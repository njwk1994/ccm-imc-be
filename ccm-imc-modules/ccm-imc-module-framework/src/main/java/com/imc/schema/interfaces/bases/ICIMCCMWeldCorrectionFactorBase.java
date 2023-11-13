package com.imc.schema.interfaces.bases;

import com.ccm.modules.COMContext;
import com.imc.common.core.utils.NumberUtils;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCCMWeldCorrectionFactor;


public abstract class ICIMCCMWeldCorrectionFactorBase extends InterfaceDefault implements ICIMCCMWeldCorrectionFactor {
    public ICIMCCMWeldCorrectionFactorBase(boolean instantiateRequiredProperties) {
        super(COMContext.INTERFACE_CIM_CCM_WELD_CORRECTION_FACTOR, instantiateRequiredProperties);
    }

    @Override
    public Integer getCorrectionFactorNum() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMWeldCorrectionFactor", "Quantity");
        return actualValue != null ? NumberUtils.toInteger(actualValue): 0;
    }

    @Override
    public void setCorrectionFactorNum(Integer CorrectionFactorNum) throws Exception {
        this.setPropertyValue("ICIMCCMWeldCorrectionFactor", "CorrectionFactorNum", CorrectionFactorNum, null, true);

    }

    @Override
    public Double getThickness() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMWeldCorrectionFactor", "Thickness");
        return actualValue != null ? NumberUtils.toDouble(actualValue) : 0.0;
    }

    @Override
    public void setThickness(Double Thickness) throws Exception {
        this.setPropertyValue("ICIMCCMWeldCorrectionFactor", "Thickness", Thickness, null, true);

    }

    @Override
    public String getWeldType() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMWeldCorrectionFactor", "WeldType");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setWeldType(String WeldType) throws Exception {
        this.setPropertyValue("ICIMCCMWeldCorrectionFactor", "WeldType", WeldType, null, true);

    }

    @Override
    public Double getCorrectionFactor1() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMWeldCorrectionFactor", "CorrectionFactor1");
        return actualValue != null ? NumberUtils.toDouble(actualValue) : 0.0;
    }

    @Override
    public void setCorrectionFactor1(Double CorrectionFactor1) throws Exception {
        this.setPropertyValue("ICIMCCMWeldCorrectionFactor", "CorrectionFactor1", CorrectionFactor1, null, true);

    }

    @Override
    public Double getCorrectionFactor2() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMWeldCorrectionFactor", "Thickness");
        return actualValue != null ? NumberUtils.toDouble(actualValue) : 0.0;
    }

    @Override
    public void setCorrectionFactor2(Double CorrectionFactor2) throws Exception {
        this.setPropertyValue("ICIMCCMWeldCorrectionFactor", "CorrectionFactor2", CorrectionFactor2, null, true);

    }
}
