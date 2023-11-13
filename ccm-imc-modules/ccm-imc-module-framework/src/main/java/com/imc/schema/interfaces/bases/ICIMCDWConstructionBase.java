package com.imc.schema.interfaces.bases;

import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCDWConstruction;

public abstract class ICIMCDWConstructionBase extends InterfaceDefault implements ICIMCDWConstruction {


    public ICIMCDWConstructionBase(boolean instantiateRequiredProperties) {
        super(ICIMCDWConstruction.class.getSimpleName(),instantiateRequiredProperties);
    }

    @Override
    public String getCIMCDWNextObj() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCDWConstruction", "CIMCDWNextObj");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMCDWNextObj(String CIMCDWNextObj) throws Exception {
        this.setPropertyValue("ICIMCDWConstruction", "CIMCDWNextObj", CIMCDWNextObj, null, true);

    }

    @Override
    public String getCIMCDWPreObj() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCDWConstruction", "CIMCDWPreObj");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMCDWPreObj(String CIMCDWPreObj) throws Exception {
        this.setPropertyValue("ICIMCDWConstruction", "CIMCDWPreObj", CIMCDWPreObj, null, true);

    }

    @Override
    public String getCIMCDWReworkCode() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCDWConstruction", "CIMCDWReworkCode");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMCDWReworkCode(String CIMCDWReworkCode) throws Exception {
        this.setPropertyValue("ICIMCDWConstruction", "CIMCDWReworkCode", CIMCDWReworkCode, null, true);

    }
}
