package com.imc.schema.interfaces.bases;

import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCDWMPI;

public abstract class ICIMCDWMPIBase extends InterfaceDefault implements ICIMCDWMPI {
    public ICIMCDWMPIBase(boolean instantiateRequiredProperties) {
        super(ICIMCDWMPI.class.getSimpleName(),instantiateRequiredProperties);
    }
}
