package com.imc.schema.interfaces.bases;

import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMDrawingFile;

public class ICIMDrawingFileBase extends InterfaceDefault implements ICIMDrawingFile {
    public ICIMDrawingFileBase(boolean instantiateRequiredProperties) {
        super(ICIMDrawingFile.class.getSimpleName(),instantiateRequiredProperties);
    }
}
