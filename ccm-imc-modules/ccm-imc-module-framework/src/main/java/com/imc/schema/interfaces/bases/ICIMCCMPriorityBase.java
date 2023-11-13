package com.imc.schema.interfaces.bases;

import com.ccm.modules.COMContext;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCCMPriority;

public abstract class ICIMCCMPriorityBase extends InterfaceDefault implements ICIMCCMPriority {


    public ICIMCCMPriorityBase( boolean instantiateRequiredProperties) {
        super(COMContext.INTERFACE_CIM_CCM_PRIORITY, instantiateRequiredProperties);
    }
}
