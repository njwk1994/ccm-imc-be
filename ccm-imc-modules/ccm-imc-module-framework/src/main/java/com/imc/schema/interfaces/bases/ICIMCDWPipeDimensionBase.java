package com.imc.schema.interfaces.bases;

import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCDWPipeDimension;

public abstract class ICIMCDWPipeDimensionBase extends InterfaceDefault implements ICIMCDWPipeDimension {
    public ICIMCDWPipeDimensionBase(boolean instantiateRequiredProperties) {
        super(ICIMCDWPipeDimension.class.getSimpleName(),instantiateRequiredProperties);
    }
}
