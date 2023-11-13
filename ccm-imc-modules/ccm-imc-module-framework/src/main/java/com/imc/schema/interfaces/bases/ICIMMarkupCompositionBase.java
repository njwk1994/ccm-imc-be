package com.imc.schema.interfaces.bases;

import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMMarkupComposition;

public abstract  class ICIMMarkupCompositionBase extends InterfaceDefault implements ICIMMarkupComposition {


    public ICIMMarkupCompositionBase(boolean instantiateRequiredProperties) {
        super(ICIMMarkupComposition.class.getSimpleName(),instantiateRequiredProperties);
    }

    @Override
    public String getCIMExternalFileId() throws Exception {
        Object actualValue = this.getLatestValue("ICIMRevisionScheme", "CIMMajorSequencePadChar");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMExternalFileId(String CIMExternalFileId) throws Exception {
        this.setPropertyValue("ICIMMarkupComposition", "CIMExternalFileId", CIMExternalFileId, null, true);
    }
}
