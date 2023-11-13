package com.imc.schema.interfaces.bases;

import com.ccm.modules.COMContext;
import com.imc.common.core.utils.NumberUtils;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCCMPipeSizeStandard;

public abstract class ICIMCCMPipeSizeStandardBase extends InterfaceDefault implements ICIMCCMPipeSizeStandard {
    public ICIMCCMPipeSizeStandardBase(boolean instantiateRequiredProperties) {
        super(COMContext.INTERFACE_CIM_CCM_PIPE_SIZE_STANDARD, instantiateRequiredProperties);
    }

    @Override
    public String getStandard() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPipeSizeStandard", "Standard");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setStandard(String Standard) throws Exception {
        this.setPropertyValue("ICIMCCMPipeSizeStandard", "Standard", Standard, null, true);

    }

    @Override
    public Double getWallThickness() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPipeSizeStandard", "WallThickness");
        return actualValue != null ? NumberUtils.toDouble(actualValue) : 0.0;
    }

    @Override
    public void setWallThickness(Double WallThickness) throws Exception {
        this.setPropertyValue("ICIMCCMPipeSizeStandard", "WallThickness", WallThickness, null, true);

    }

    @Override
    public Double getInch() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPipeSizeStandard", "Inch");
        return actualValue != null ? NumberUtils.toDouble(actualValue) : 0.0;
    }

    @Override
    public void setInch(Double Inch) throws Exception {
        this.setPropertyValue("ICIMCCMPipeSizeStandard", "Inch", Inch, null, true);

    }

    @Override
    public Double getDN() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPipeSizeStandard", "DN");
        return actualValue != null ? NumberUtils.toDouble(actualValue) : 0.0;
    }

    @Override
    public void setDN(Double DN) throws Exception {
        this.setPropertyValue("ICIMCCMPipeSizeStandard", "DN", DN, null, true);

    }

    @Override
    public Double getod() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPipeSizeStandard", "od");
        return actualValue != null ? NumberUtils.toDouble(actualValue) : 0.0;
    }

    @Override
    public void setod(Double od) throws Exception {
        this.setPropertyValue("ICIMCCMPipeSizeStandard", "od", od, null, true);

    }

    @Override
    public String getRatingSchedule() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPipeSizeStandard", "RatingSchedule");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setRatingSchedule(String RatingSchedule) throws Exception {
        this.setPropertyValue("ICIMCCMPipeSizeStandard", "RatingSchedule", RatingSchedule, null, true);

    }
}
