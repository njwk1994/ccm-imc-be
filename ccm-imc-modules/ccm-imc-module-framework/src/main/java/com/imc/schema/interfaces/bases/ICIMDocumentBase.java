package com.imc.schema.interfaces.bases;


import com.imc.framework.entity.schema.EnumListType;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMDocument;

public abstract class ICIMDocumentBase extends InterfaceDefault implements ICIMDocument {

    public ICIMDocumentBase(boolean instantiateRequiredProperties) {
        super(ICIMDocument.class.getSimpleName(),instantiateRequiredProperties);
    }


    @Override
    public String getCIMDocCategory() throws Exception {
        Object actualValue = this.getLatestValue("ICIMDocument", "CIMDocCategory");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMDocCategory(String CIMDocCategory) throws Exception {
        this.setPropertyValue("ICIMDocument", "CIMDocCategory", CIMDocCategory, null, true);
    }

    @Override
    public String getCIMDocState() throws Exception {
        Object actualValue = this.getLatestValue("ICIMDocument", "CIMDocState");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMDocState(String CIMDocState) throws Exception {
        this.setPropertyValue("ICIMDocument", "CIMDocState", CIMDocState, null, true);
    }

    @Override
    public String getCIMDocTitle() throws Exception {
        Object actualValue = this.getLatestValue("ICIMDocument", "CIMDocTitle");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMDocTitle(String CIMDocTitle) throws Exception {
        this.setPropertyValue("ICIMDocument", "CIMDocTitle", CIMDocTitle, null, true);

    }
}
