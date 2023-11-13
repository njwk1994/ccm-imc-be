package com.imc.schema.interfaces.bases;

import com.ccm.modules.COMContext;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCCMHandoverFile;

public abstract class ICIMCCMHandoverFileBase extends InterfaceDefault implements ICIMCCMHandoverFile {
    public ICIMCCMHandoverFileBase(boolean instantiateRequiredProperties) {
        super(COMContext.INTERFACE_CIM_CCM_HANDOVER_FILE, instantiateRequiredProperties);
    }

    @Override
    public String getHandoverFilePage() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMHandoverFile", "HandoverFilePage");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setHandoverFilePage(String HandoverFilePage) throws Exception {
        this.setPropertyValue("ICIMCCMHandoverFile", "HandoverFilePage", HandoverFilePage, null, true);

    }

    @Override
    public String getHandoverFileStatus() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMHandoverFile", "HandoverFileStatus");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setHandoverFileStatus(String HandoverFileStatus) throws Exception {
        this.setPropertyValue("ICIMCCMHandoverFile", "HandoverFileStatus", HandoverFileStatus, null, true);

    }
}
