package com.imc.schema.interfaces.bases;

import com.ccm.modules.COMContext;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMRevisionItem;

public abstract class ICIMRevisionItemBase extends InterfaceDefault implements ICIMRevisionItem {
    public ICIMRevisionItemBase( boolean instantiateRequiredProperties) {
        super(COMContext.INTERFACE_CIM_REVISION_ITEM, instantiateRequiredProperties);
    }

    @Override
    public String getCIMRevisionItemMajorRevision() throws Exception {
        Object actualValue = this.getLatestValue("ICIMRevisionItem", "CIMRevisionItemMajorRevision");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMRevisionItemMajorRevision(String CIMRevisionItemMajorRevision) throws Exception {
        this.setPropertyValue("ICIMRevisionItem", "CIMRevisionItemMajorRevision", CIMRevisionItemMajorRevision, null, true);

    }

    @Override
    public String getCIMRevisionItemMinorRevision() throws Exception {
        Object actualValue = this.getLatestValue("ICIMRevisionItem", "CIMRevisionItemMinorRevision");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMRevisionItemMinorRevision(String CIMRevisionItemMinorRevision) throws Exception {
        this.setPropertyValue("ICIMRevisionItem", "CIMRevisionItemMinorRevision", CIMRevisionItemMinorRevision, null, true);

    }

    @Override
    public String getCIMRevisionItemOperationState() throws Exception {
        Object actualValue = this.getLatestValue("ICIMRevisionItem", "CIMRevisionItemOperationState");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMRevisionItemOperationState(String CIMRevisionItemOperationState) throws Exception {
        this.setPropertyValue("ICIMRevisionItem", "CIMRevisionItemOperationState", CIMRevisionItemOperationState, null, true);

    }

    @Override
    public String getCIMRevisionItemRevState() throws Exception {
        Object actualValue = this.getLatestValue("ICIMRevisionItem", "CIMRevisionItemRevState");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMRevisionItemRevState(String CIMRevisionItemRevState) throws Exception {
        this.setPropertyValue("ICIMRevisionItem", "CIMRevisionItemRevState", CIMRevisionItemRevState, null, true);

    }
}
