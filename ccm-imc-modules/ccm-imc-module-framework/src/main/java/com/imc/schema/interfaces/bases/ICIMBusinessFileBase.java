package com.imc.schema.interfaces.bases;

import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMBusinessFile;
import org.springframework.stereotype.Service;

@Service
public abstract class ICIMBusinessFileBase extends InterfaceDefault implements ICIMBusinessFile {

    public ICIMBusinessFileBase(boolean instantiateRequiredProperties) {
        super(ICIMBusinessFile.class.getSimpleName(),instantiateRequiredProperties);
    }
}
