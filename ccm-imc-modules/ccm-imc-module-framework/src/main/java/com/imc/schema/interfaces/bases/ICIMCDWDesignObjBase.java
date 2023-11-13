package com.imc.schema.interfaces.bases;

import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCDWDesignObj;

public  abstract class ICIMCDWDesignObjBase extends InterfaceDefault implements ICIMCDWDesignObj {
    public ICIMCDWDesignObjBase(boolean instantiateRequiredProperties) {
        super(ICIMCDWDesignObj.class.getSimpleName(),instantiateRequiredProperties);
    }
}
