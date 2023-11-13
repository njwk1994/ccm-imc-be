package com.imc.schema.interfaces.bases;

import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCDWPBS;

public abstract class ICIMCDWPBSBase  extends InterfaceDefault implements ICIMCDWPBS {


    public ICIMCDWPBSBase(boolean instantiateRequiredProperties) {
        super(ICIMCDWPBS.class.getSimpleName(),instantiateRequiredProperties);
    }

    @Override
    public String getCIMCDWArea() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCDWPBS", "CIMCDWArea");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMCDWArea(String CIMCDWArea) throws Exception {
        this.setPropertyValue("ICIMDocument", "CIMCDWArea", CIMCDWArea, null, true);

    }

    @Override
    public String getCIMCDWUnit() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCDWPBS", "CIMCDWUnit");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMCDWUnit(String CIMCDWUnit) throws Exception {
        this.setPropertyValue("ICIMDocument", "CIMCDWUnit", CIMCDWUnit, null, true);

    }
}
