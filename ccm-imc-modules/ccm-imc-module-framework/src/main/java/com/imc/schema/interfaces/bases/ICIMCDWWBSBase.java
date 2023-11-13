package com.imc.schema.interfaces.bases;

import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCDWWBS;

public abstract class ICIMCDWWBSBase extends InterfaceDefault implements ICIMCDWWBS {
    public ICIMCDWWBSBase(boolean instantiateRequiredProperties) {
        super(ICIMCDWWBS.class.getSimpleName(),instantiateRequiredProperties);
    }

    @Override
    public String getCIMCDWDesignDiscipline() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCDWWBS", "CIMCDWDesignDiscipline");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMCDWDesignDiscipline(String CIMCDWDesignDiscipline) throws Exception {
        this.setPropertyValue("ICIMCDWWBS", "CIMCDWDesignDiscipline", CIMCDWDesignDiscipline, null, true);

    }
}
