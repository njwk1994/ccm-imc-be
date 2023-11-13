package com.imc.schema.interfaces.bases;

import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMPhysicalFile;

public class ICIMPhysicalFileBase extends InterfaceDefault implements ICIMPhysicalFile {
    public ICIMPhysicalFileBase(boolean instantiateRequiredProperties) {
        super(ICIMPhysicalFile.class.getSimpleName(),instantiateRequiredProperties);
    }
}
