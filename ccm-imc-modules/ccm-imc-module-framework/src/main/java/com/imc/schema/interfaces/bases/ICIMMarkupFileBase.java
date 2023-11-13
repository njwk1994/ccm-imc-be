package com.imc.schema.interfaces.bases;

import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMMarkupFile;

public class ICIMMarkupFileBase extends InterfaceDefault implements ICIMMarkupFile {
    public ICIMMarkupFileBase(boolean instantiateRequiredProperties) {
        super(ICIMMarkupFile.class.getSimpleName(),instantiateRequiredProperties);
    }
}
