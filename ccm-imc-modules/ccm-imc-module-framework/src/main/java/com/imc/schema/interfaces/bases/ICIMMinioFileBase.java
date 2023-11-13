package com.imc.schema.interfaces.bases;

import cn.hutool.core.date.DateTime;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMMinioFile;
import java.time.DateTimeException;

public abstract class ICIMMinioFileBase  extends InterfaceDefault implements ICIMMinioFile {


    public ICIMMinioFileBase(boolean instantiateRequiredProperties) {
        super(ICIMMinioFile.class.getSimpleName(),instantiateRequiredProperties);
    }


    @Override
    public String getCIMFileBucketName() throws Exception {
        Object actualValue = this.getLatestValue("ICIMMinioFile", "CIMFileBucketName");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMFileBucketName(String CIMFileBucketName) throws Exception {
        this.setPropertyValue("ICIMMinioFile", "CIMFileBucketName", CIMFileBucketName, null, true);

    }

    @Override
    public String getCIMFileBucketObjName() throws Exception {
        Object actualValue = this.getLatestValue("ICIMMinioFile", "CIMFileBucketObjName");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMFileBucketObjName(String CIMFileBucketObjName) throws Exception {
        this.setPropertyValue("ICIMMinioFile", "CIMFileBucketObjName", CIMFileBucketObjName, null, true);
    }

    @Override
    public DateTime getCIMFileUploadDate() throws Exception {
        Object actualValue = this.getLatestValue("ICIMMinioFile", "CIMFileUploadDate");
        if (actualValue != null && actualValue instanceof DateTime) { return (DateTime) actualValue; } else { throw new DateTimeException("Failed to retrieve the CIM revision issue date."); }

    }

    @Override
    public void setCIMFileUploadDate(DateTime CIMFileUploadDate) throws Exception {
        this.setPropertyValue("ICIMMinioFile", "CIMFileUploadDate", CIMFileUploadDate, null, true);

    }

    @Override
    public String getCIMFileMinioUrl() throws Exception {
        Object actualValue = this.getLatestValue("ICIMMinioFile", "CIMFileMinioUrl");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMFileMinioUrl(String CIMFileMinioUrl) throws Exception {

        this.setPropertyValue("ICIMMinioFile", "CIMFileMinioUrl", CIMFileMinioUrl, null, true);


    }


}
