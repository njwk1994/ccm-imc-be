package com.imc.schema.interfaces.bases;

import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCDWWeld;

public class ICIMCDWWeldBase extends InterfaceDefault implements ICIMCDWWeld {
    public ICIMCDWWeldBase(boolean instantiateRequiredProperties) {
        super(ICIMCDWWeld.class.getSimpleName(),instantiateRequiredProperties);
    }
}
