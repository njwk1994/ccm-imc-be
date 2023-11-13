package com.imc.schema.interfaces.bases;

import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMDocumentMaster;

public  abstract class ICIMDocumentMasterBase extends InterfaceDefault implements ICIMDocumentMaster {
    public ICIMDocumentMasterBase(boolean instantiateRequiredProperties) {
        //第二种方法    super("ICIMDocumentMaster",instantiateRequiredProperties);
        super(ICIMDocumentMaster.class.getSimpleName(),instantiateRequiredProperties);
    }
}
