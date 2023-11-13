package com.imc.schema.interfaces.bases;

import com.ccm.modules.COMContext;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCCMConstruction;

public abstract class ICIMCCMConstructionBase extends InterfaceDefault implements ICIMCCMConstruction {

    public ICIMCCMConstructionBase( boolean instantiateRequiredProperties) {
        super(COMContext.INTERFACE_CIM_CCM_CONSTRUCTION, instantiateRequiredProperties);
    }

    @Override
    public String getNextObj() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMConstruction", "NextObj");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setNextObj(String NextObj) throws Exception {
        this.setPropertyValue("ICIMCCMConstruction", "NextObj", NextObj, null, true);

    }

    @Override
    public String getPreObj() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMConstruction", "PreObj");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setPreObj(String PreObj) throws Exception {
        this.setPropertyValue("ICIMCCMConstruction", "PreObj", PreObj, null, true);

    }

    @Override
    public String getReworkCode() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMConstruction", "ReworkCode");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setReworkCode(String ReworkCode) throws Exception {
        this.setPropertyValue("ICIMCCMConstruction", "ReworkCode", ReworkCode, null, true);

    }
}
