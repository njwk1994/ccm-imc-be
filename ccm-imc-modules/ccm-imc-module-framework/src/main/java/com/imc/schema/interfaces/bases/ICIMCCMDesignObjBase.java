package com.imc.schema.interfaces.bases;

import com.ccm.modules.COMContext;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCCMDesignObj;

public abstract class ICIMCCMDesignObjBase extends InterfaceDefault implements ICIMCCMDesignObj {

    public ICIMCCMDesignObjBase(boolean instantiateRequiredProperties) {
        super(COMContext.INTERFACE_CCM_BASIC_TARGET_OBJ, instantiateRequiredProperties);
    }



}
