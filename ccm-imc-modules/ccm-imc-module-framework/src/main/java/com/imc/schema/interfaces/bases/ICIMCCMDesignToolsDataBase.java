package com.imc.schema.interfaces.bases;

import com.ccm.modules.COMContext;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCCMDesignToolsData;

public abstract class ICIMCCMDesignToolsDataBase extends InterfaceDefault implements ICIMCCMDesignToolsData {
    public ICIMCCMDesignToolsDataBase(boolean instantiateRequiredProperties) {
        super(COMContext.INTERFACE_CIM_CCM_DESIGN_TOOLS_DATA, instantiateRequiredProperties);
    }

    @Override
    public String getDesignToolsClassType() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMDesignToolsData", "DesignToolsClassType");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setDesignToolsClassType(String DesignToolsClassType) throws Exception {
        this.setPropertyValue("ICIMCCMDesignToolsData", "DesignToolsClassType", DesignToolsClassType, null, true);

    }

    @Override
    public String getPUCI() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMDesignToolsData", "PUCI");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setPUCI(String PUCI) throws Exception {
        this.setPropertyValue("ICIMCCMDesignToolsData", "PUCI", PUCI, null, true);

    }

    @Override
    public String getUCI() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMDesignToolsData", "UCI");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setUCI(String UCI) throws Exception {
        this.setPropertyValue("ICIMCCMDesignToolsData", "UCI", UCI, null, true);

    }

    @Override
    public String getSubType() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMDesignToolsData", "SubType");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setSubType(String SubType) throws Exception {
        this.setPropertyValue("ICIMCCMDesignToolsData", "SubType", SubType, null, true);

    }
}
