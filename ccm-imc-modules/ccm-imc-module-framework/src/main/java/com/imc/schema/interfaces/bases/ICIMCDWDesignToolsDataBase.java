package com.imc.schema.interfaces.bases;

import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCDWDesignToolsData;

public abstract class ICIMCDWDesignToolsDataBase extends InterfaceDefault implements ICIMCDWDesignToolsData {


    public ICIMCDWDesignToolsDataBase(boolean instantiateRequiredProperties) {
        super(ICIMCDWDesignToolsData.class.getSimpleName(),instantiateRequiredProperties);
    }

    @Override
    public String getCIMCDWDesignToolsClassType() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCDWDesignToolsData", "CIMCDWDesignToolsClassType");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMCDWDesignToolsClassType(String CIMCDWDesignToolsClassType) throws Exception {
        this.setPropertyValue("ICIMCDWDesignToolsData", "CIMCDWDesignToolsClassType", CIMCDWDesignToolsClassType, null, true);

    }

    @Override
    public String getCIMCDWPUCI() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCDWDesignToolsData", "CIMCDWPUCI");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMCDWPUCI(String CIMCDWPUCI) throws Exception {
        this.setPropertyValue("ICIMCDWDesignToolsData", "CIMCDWPUCI", CIMCDWPUCI, null, true);

    }

    @Override
    public String getCIMCDWUCI() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCDWDesignToolsData", "CIMCDWUCI");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMCDWUCI(String CIMCDWUCI) throws Exception {
        this.setPropertyValue("ICIMCDWDesignToolsData", "CIMCDWUCI", CIMCDWUCI, null, true);

    }

    @Override
    public String getCIMCDWSubType() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCDWDesignToolsData", "CIMCDWSubType");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMCDWSubType(String CIMCDWSubType) throws Exception {
        this.setPropertyValue("ICIMCDWDesignToolsData", "CIMCDWSubType", CIMCDWSubType, null, true);
    }
}
