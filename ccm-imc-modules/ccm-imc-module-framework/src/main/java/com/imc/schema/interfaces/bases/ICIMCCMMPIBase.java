package com.imc.schema.interfaces.bases;

import com.ccm.modules.COMContext;
import com.imc.common.core.utils.NumberUtils;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCCMMPI;

public abstract class ICIMCCMMPIBase extends InterfaceDefault implements ICIMCCMMPI {

    public ICIMCCMMPIBase(boolean instantiateRequiredProperties) {
        super(COMContext.INTERFACE_CIM_CCM_MPI, instantiateRequiredProperties);
    }

    @Override
    public String getMaterialCode() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMMPI", "MaterialCode");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setMaterialCode(String MaterialCode) throws Exception {
        this.setPropertyValue("ICIMCCMMPI", "MaterialCode", MaterialCode, null, true);

    }

    @Override
    public Double getPQuantity() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMMPI", "PQuantity");
        return actualValue != null ? NumberUtils.toDouble(actualValue) : 0.0;
    }

    @Override
    public void setPQuantity(Double PQuantity) throws Exception {
        this.setPropertyValue("ICIMCCMMPI", "PQuantity", PQuantity, null, true);

    }

    @Override
    public String getPSize1() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMMPI", "PSize1");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setPSize1(String PSize1) throws Exception {
        this.setPropertyValue("ICIMCCMMPI", "PSize1", PSize1, null, true);

    }

    @Override
    public String getPSize2() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMMPI", "PSize2");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setgetPSize2(String getPSize2) throws Exception {
        this.setPropertyValue("ICIMCCMMPI", "getPSize2", getPSize2, null, true);

    }
}
