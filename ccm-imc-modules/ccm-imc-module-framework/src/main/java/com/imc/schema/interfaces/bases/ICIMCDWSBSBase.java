package com.imc.schema.interfaces.bases;

import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCDWSBS;

public abstract class ICIMCDWSBSBase extends InterfaceDefault implements ICIMCDWSBS {

    public ICIMCDWSBSBase(boolean instantiateRequiredProperties) {
        super(ICIMCDWSBS.class.getSimpleName(),instantiateRequiredProperties);
    }

    @Override
    public String getCIMCDWSubSystem() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCDWSBS", "CIMCDWSubSystem");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMCDWSubSystem(String CIMCDWSubSystem) throws Exception {
        this.setPropertyValue("ICIMDocument", "CIMCDWSubSystem", CIMCDWSubSystem, null, true);

    }

    @Override
    public String getCIMCDWSystem() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCDWSBS", "CIMCDWSystem");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMCDWSystem(String CIMCDWSystem) throws Exception {
        this.setPropertyValue("ICIMDocument", "CIMCDWSystem", CIMCDWSystem, null, true);

    }
}
