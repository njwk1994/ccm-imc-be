package com.imc.schema.interfaces.bases;

import com.ccm.modules.COMContext;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCCMHandoverMail;

public class ICIMCCMHandoverMailBase extends InterfaceDefault implements ICIMCCMHandoverMail {

    public ICIMCCMHandoverMailBase(boolean instantiateRequiredProperties) {
        super(COMContext.INTERFACE_CIM_CCM_HANDOVER_MAIL, instantiateRequiredProperties);
    }
}
