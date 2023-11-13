package com.imc.schema.interfaces.bases;

import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMDocumentClass;

public class ICIMDocumentClassBase extends InterfaceDefault implements ICIMDocumentClass {
    public ICIMDocumentClassBase(boolean instantiateRequiredProperties) {
        super(ICIMDocumentClass.class.getSimpleName(),instantiateRequiredProperties);
    }
}
