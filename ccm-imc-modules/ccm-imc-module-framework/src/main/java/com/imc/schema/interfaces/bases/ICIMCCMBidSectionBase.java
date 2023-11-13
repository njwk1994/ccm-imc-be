package com.imc.schema.interfaces.bases;

import com.ccm.modules.COMContext;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCCMBidSection;

public abstract class ICIMCCMBidSectionBase extends InterfaceDefault implements ICIMCCMBidSection {
    public ICIMCCMBidSectionBase(boolean instantiateRequiredProperties) {
        super(COMContext.INTERFACE_CIM_CCM_BID_SECTION, instantiateRequiredProperties);
    }

    @Override
    public String getPCode() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMBidSection", "PCode");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setPCode(String PCode) throws Exception {
        this.setPropertyValue("ICIMCCMBidSection", "PCode", PCode, null, true);

    }
}
