package com.imc.schema.interfaces.bases;

import com.ccm.modules.COMContext;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCCMWeld;

public abstract class ICIMCCMWeldBase extends InterfaceDefault implements ICIMCCMWeld {

    public ICIMCCMWeldBase( boolean instantiateRequiredProperties) {
        super(COMContext.INTERFACE_CIM_CCM_WELD, instantiateRequiredProperties);
    }
}
