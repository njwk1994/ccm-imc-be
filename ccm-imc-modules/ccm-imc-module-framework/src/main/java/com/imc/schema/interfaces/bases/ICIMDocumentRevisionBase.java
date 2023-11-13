package com.imc.schema.interfaces.bases;

import cn.hutool.core.date.DateTime;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMDocumentRevision;
import java.time.DateTimeException;

public abstract class ICIMDocumentRevisionBase extends InterfaceDefault implements ICIMDocumentRevision {

    public ICIMDocumentRevisionBase(boolean instantiateRequiredProperties) {
        super(ICIMDocumentRevisionBase.class.getSimpleName(),instantiateRequiredProperties);
    }

    @Override
    public String getCIMExternalRevision() throws Exception {
        Object actualValue = this.getLatestValue("ICIMDocumentRevision", "CIMExternalRevision");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMExternalRevision(String CIMExternalRevision) throws Exception {
        this.setPropertyValue("ICIMDocumentRevision", "CIMExternalRevision", CIMExternalRevision, null, true);
    }

    @Override
    public String getCIMMajorRevision() throws Exception {
        Object actualValue = this.getLatestValue("ICIMDocumentRevision", "getCIMMajorRevision");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMMajorRevision(String CIMMajorRevision) throws Exception {
        this.setPropertyValue("ICIMDocumentRevision", "CIMMajorRevision", CIMMajorRevision, null, true);
    }

    @Override
    public String getCIMMinorRevision() throws Exception {
        Object actualValue = this.getLatestValue("ICIMDocumentRevision", "CIMMinorRevision");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMMinorRevision(String CIMMinorRevision) throws Exception {
        this.setPropertyValue("ICIMDocumentRevision", "CIMMinorRevision", CIMMinorRevision, null, true);

    }

    @Override
    public String getCIMRevisionSchema() throws Exception {
        Object actualValue = this.getLatestValue("ICIMDocumentRevision", "CIMRevisionSchema");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMRevisionSchema(String CIMRevisionSchema) throws Exception {
        this.setPropertyValue("ICIMDocumentRevision", "CIMRevisionSchema", CIMRevisionSchema, null, true);

    }

    @Override
    public DateTime getCIMRevIssueDate() throws Exception {
        Object actualValue = this.getLatestValue("ICIMDocumentRevision", "CIMRevIssueDate");
        if (actualValue != null && actualValue instanceof DateTime) { return (DateTime) actualValue; } else { throw new DateTimeException("Failed to retrieve the CIM revision issue date."); }

    }

    @Override
    public void setCIMRevIssueDate(DateTime CIMRevIssueDate) throws Exception {
        this.setPropertyValue("ICIMDocumentRevision", "CIMRevIssueDate", CIMRevIssueDate, null, true);

    }

    @Override
    public String getCIMRevState() throws Exception {
        Object actualValue = this.getLatestValue("ICIMDocumentRevision", "CIMRevState");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMRevState(String CIMRevState) throws Exception {
        this.setPropertyValue("ICIMDocumentRevision", "CIMRevState", CIMRevState, null, true);

    }

    @Override
    public String getCIMSignOffComments() throws Exception {
        Object actualValue = this.getLatestValue("ICIMDocumentRevision", "CIMSignOffComments");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMSignOffComments(String CIMSignOffComments) throws Exception {
        this.setPropertyValue("ICIMDocumentRevision", "CIMSignOffComments", CIMSignOffComments, null, true);
    }

}
