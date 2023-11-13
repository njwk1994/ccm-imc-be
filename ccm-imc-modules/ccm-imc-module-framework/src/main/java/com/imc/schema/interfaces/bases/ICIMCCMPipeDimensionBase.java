package com.imc.schema.interfaces.bases;

import com.ccm.modules.COMContext;
import com.imc.common.core.utils.NumberUtils;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCCMPipeDimension;

public abstract class ICIMCCMPipeDimensionBase extends InterfaceDefault implements ICIMCCMPipeDimension {

    public ICIMCCMPipeDimensionBase(boolean instantiateRequiredProperties) {
        super(COMContext.INTERFACE_CIM_CCM_PIPE_DIMENSION, instantiateRequiredProperties);
    }

    @Override
    public Double getLength() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPipeDimension", "Length");
        return actualValue != null ? NumberUtils.toDouble(actualValue) : 0.0;
    }

    @Override
    public void setLength(Double Length) throws Exception {
        this.setPropertyValue("ICIMCCMPipeDimension", "Length", Length, null, true);

    }

    @Override
    public Double getWeight() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPipeDimension", "Length");
        return actualValue != null ? NumberUtils.toDouble(actualValue) : 0.0;
    }

    @Override
    public void setWeight(Double Weight) throws Exception {
        this.setPropertyValue("ICIMCCMPipeDimension", "Weight", Weight, null, true);

    }

    @Override
    public Double getSize1() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPipeDimension", "Size1");
        return actualValue != null ? NumberUtils.toDouble(actualValue) : 0.0;
    }

    @Override
    public void setSize1(Double Size1) throws Exception {
        this.setPropertyValue("ICIMCCMPipeDimension", "Size1", Size1, null, true);

    }

    @Override
    public Double getSize2() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPipeDimension", "Size2");
        return actualValue != null ? NumberUtils.toDouble(actualValue) : 0.0;
    }

    @Override
    public void setSize2(Double Size2) throws Exception {
        this.setPropertyValue("ICIMCCMPipeDimension", "Size2", Size2, null, true);

    }

    @Override
    public Double getSize3() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPipeDimension", "Size3");
        return actualValue != null ? NumberUtils.toDouble(actualValue) : 0.0;
    }

    @Override
    public void setSize3(Double Size3) throws Exception {
        this.setPropertyValue("ICIMCCMPipeDimension", "Size3", Size3, null, true);

    }

    @Override
    public Double getSize4() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPipeDimension", "Size4");
        return actualValue != null ? NumberUtils.toDouble(actualValue) : 0.0;
    }

    @Override
    public void setSize4(Double Size4) throws Exception {
        this.setPropertyValue("ICIMCCMPipeDimension", "Size4", Size4, null, true);

    }

    @Override
    public Double getSize5() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPipeDimension", "Size5");
        return actualValue != null ? NumberUtils.toDouble(actualValue) : 0.0;
    }

    @Override
    public void setSize5(Double Size5) throws Exception {
        this.setPropertyValue("ICIMCCMPipeDimension", "Size5", Size5, null, true);

    }
}
