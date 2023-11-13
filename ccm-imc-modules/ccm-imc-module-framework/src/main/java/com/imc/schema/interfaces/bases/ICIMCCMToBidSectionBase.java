package com.imc.schema.interfaces.bases;

import com.ccm.modules.COMContext;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCCMToBidSection;

public abstract class ICIMCCMToBidSectionBase extends InterfaceDefault implements ICIMCCMToBidSection {
    public ICIMCCMToBidSectionBase( boolean instantiateRequiredProperties) {
        super(COMContext.INTERFACE_CIM_CCM_TO_BID_SECTION, instantiateRequiredProperties);
    }

    @Override
    public String getToBidSectionUID() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMToBidSection", "ToBidSectionUID");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setToBidSectionUID(String ToBidSectionUID) throws Exception {
        this.setPropertyValue("ICIMCCMToBidSection", "ToBidSectionUID", ToBidSectionUID, null, true);

    }

    @Override
    public String getToBidSectionName() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMToBidSection", "ToBidSectionName");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setToBidSectionName(String ToBidSectionName) throws Exception {
        this.setPropertyValue("ICIMCCMToBidSection", "ToBidSectionName", ToBidSectionName, null, true);

    }
}
