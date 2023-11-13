package com.imc.schema.interfaces.bases;

import cn.hutool.core.date.DateTime;
import com.imc.common.core.utils.NumberUtils;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMDocumentVersion;
import java.time.DateTimeException;
public abstract class ICIMDocumentVersionBase  extends InterfaceDefault  implements ICIMDocumentVersion {


    public ICIMDocumentVersionBase(boolean instantiateRequiredProperties) {
        super(ICIMDocumentVersion.class.getSimpleName(),instantiateRequiredProperties);
    }

    @Override
    public Integer getCIMDocVersion() throws Exception {
        Object actualValue = this.getLatestValue("ICIMDocumentRevision", "CIMDocVersion");
        return actualValue != null ? NumberUtils.toInteger(actualValue): 0;

    }

    @Override
    public void setCIMDocVersion(Integer CIMDocVersion) throws Exception {
        this.setPropertyValue("ICIMDocumentVersion", "CIMDocVersion", CIMDocVersion, null, true);
    }

    @Override
    public Boolean getCIMIsDocVersionCheckedOut() throws Exception {
        Object actualValue = this.getLatestValue("ICIMDocumentRevision", "CIMIsDocVersionCheckedOut");
        return  actualValue.equals(actualValue);
    }


    @Override
    public void setCIMIsDocVersionCheckedOut(Boolean CIMIsDocVersionCheckedOut) throws Exception {
        this.setPropertyValue("ICIMDocumentVersion", "CIMIsDocVersionCheckedOut", CIMIsDocVersionCheckedOut, null, true);

    }

    @Override
    public Boolean getCIMIsDocVersionSuperseded() throws Exception {
        Object actualValue = this.getLatestValue("ICIMDocumentRevision", "CIMIsDocVersionSuperseded");
        return  actualValue.equals(actualValue) ;
    }

    @Override
    public void setCIMIsDocVersionSuperseded(Boolean CIMIsDocVersionSuperseded) throws Exception {
        this.setPropertyValue("ICIMDocumentVersion", "CIMIsDocVersionSuperseded", CIMIsDocVersionSuperseded, null, true);

    }

    @Override
    public DateTime getCIMVersionCheckInDate() throws Exception {
        Object actualValue = this.getLatestValue("ICIMDocumentRevision", "CIMVersionCheckInDate");
        if (actualValue != null && actualValue instanceof DateTime) { return (DateTime) actualValue; } else { throw new DateTimeException("Failed to retrieve the CIM revision issue date."); }
    }

    @Override
    public void setCIMVersionCheckInDate(DateTime CIMVersionCheckInDate) throws Exception {
        this.setPropertyValue("ICIMDocumentVersion", "CIMVersionCheckInDate", CIMVersionCheckInDate, null, true);

    }

    @Override
    public String getCIMVersionCheckInUser() throws Exception {
        Object actualValue = this.getLatestValue("ICIMDocumentRevision", "CIMVersionCheckInUser");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMVersionCheckInUser(String CIMVersionCheckInUser) throws Exception {
        this.setPropertyValue("ICIMDocumentVersion", "CIMVersionCheckInUser", CIMVersionCheckInUser, null, true);

    }

    @Override
    public DateTime getCIMVersionSupersededDate() throws Exception {
        Object actualValue = this.getLatestValue("ICIMDocumentRevision", "CIMVersionSupersededDate");
        if (actualValue != null && actualValue instanceof DateTime) { return (DateTime) actualValue; } else { throw new DateTimeException("Failed to retrieve the CIM revision issue date."); }
    }

    @Override
    public void setCIMVersionSupersededDate(DateTime CIMVersionSupersededDate) throws Exception {
        this.setPropertyValue("ICIMDocumentVersion", "CIMVersionSupersededDate", CIMVersionSupersededDate, null, true);

    }
}
