package com.imc.schema.interfaces.bases;

import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMSimpleDocument;

public abstract class ICIMSimpleDocumentBase extends InterfaceDefault implements ICIMSimpleDocument {


    public ICIMSimpleDocumentBase(boolean instantiateRequiredProperties) {
        super(ICIMSimpleDocument.class.getSimpleName(),instantiateRequiredProperties);
    }

    @Override
    public String getCIMSimpleDocumentRev() throws Exception {
        Object actualValue = this.getLatestValue("ICIMSimpleDocument", "CIMSimpleDocumentRev");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMSimpleDocumentRev(String CIMSimpleDocumentRev) throws Exception {
        this.setPropertyValue("ICIMSimpleDocument", "CIMSimpleDocumentRev", CIMSimpleDocumentRev, null, true);

    }

    @Override
    public String getCIMSimpleDocumentVer() throws Exception {
        Object actualValue = this.getLatestValue("ICIMSimpleDocument", "CIMSimpleDocumentVer");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMSimpleDocumentVer(String CIMSimpleDocumentVer) throws Exception {
        this.setPropertyValue("ICIMSimpleDocument", "CIMSimpleDocumentVer", CIMSimpleDocumentVer, null, true);

    }

    @Override
    public String getCIMSimpleDocumentRemark() throws Exception {
        Object actualValue = this.getLatestValue("ICIMSimpleDocument", "CIMSimpleDocumentRemark");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMSimpleDocumentRemark(String CIMSimpleDocumentRemark) throws Exception {
        this.setPropertyValue("ICIMSimpleDocument", "CIMSimpleDocumentRemark", CIMSimpleDocumentRemark, null, true);

    }

    @Override
    public String getCIMSimpleDocumentDesignPhase() throws Exception {
        Object actualValue = this.getLatestValue("ICIMSimpleDocument", "CIMSimpleDocumentDesignPhase");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMSimpleDocumentDesignPhase(String CIMSimpleDocumentDesignPhase) throws Exception {
        this.setPropertyValue("ICIMSimpleDocument", "CIMSimpleDocumentDesignPhase", CIMSimpleDocumentDesignPhase, null, true);

    }
}
