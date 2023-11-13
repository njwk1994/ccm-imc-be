package com.imc.schema.interfaces.bases;

import com.imc.common.core.utils.NumberUtils;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMFile;

public abstract class ICIMFileBase  extends InterfaceDefault  implements ICIMFile {


    public ICIMFileBase(boolean instantiateRequiredProperties) {
        super(ICIMFile.class.getSimpleName(),instantiateRequiredProperties);
    }


    @Override
    public String getCIMFileExt() throws Exception {
        Object actualValue = this.getLatestValue("ICIMFile", "CIMFileExt");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMFileExt(String CIMFileExt) throws Exception {
        this.setPropertyValue("ICIMFile", "CIMFileExt", CIMFileExt, null, true);

    }

    @Override
    public String getCIMFileContent() throws Exception {
        Object actualValue = this.getLatestValue("ICIMFile", "CIMFileContent");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMFileContent(String CIMFileContent) throws Exception {
        this.setPropertyValue("CIMFileContent", "CIMFileExt", CIMFileContent, null, true);
    }

    @Override
    public String getCIMFileName() throws Exception {
        Object actualValue = this.getLatestValue("ICIMFile", "CIMFileName");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMFileName(String CIMFileName) throws Exception {
        this.setPropertyValue("CIMFileContent", "CIMFileName", CIMFileName, null, true);
    }


}
