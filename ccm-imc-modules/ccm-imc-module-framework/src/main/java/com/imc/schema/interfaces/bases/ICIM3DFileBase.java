package com.imc.schema.interfaces.bases;

import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIM3DFile;

public abstract class ICIM3DFileBase extends InterfaceDefault implements ICIM3DFile {
    public ICIM3DFileBase(boolean instantiateRequiredProperties) {
        super(ICIM3DFile.class.getSimpleName(),instantiateRequiredProperties);
    }
}
