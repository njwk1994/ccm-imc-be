package com.imc.schema.interfaces.bases;

import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMFileType;

/**
 * 文件类型
 */
public abstract class ICIMFileTypeBase extends InterfaceDefault implements ICIMFileType {


    public ICIMFileTypeBase(boolean instantiateRequiredProperties) {
        super(ICIMFileType.class.getSimpleName(),instantiateRequiredProperties);
    }



    @Override
    public Boolean getCIMFileEditable() throws Exception {
        Object actualValue = this.getLatestValue("ICIMFileType", "CIMFileEditable");
        return  actualValue.equals(actualValue);
    }

    @Override
    public void setCIMFileEditable(Boolean CIMFileEditable) throws Exception {
        this.setPropertyValue("ICIMFileType", "CIMFileEditable", CIMFileEditable, null, true);

    }

    @Override
    public Boolean getCIMFileViewable() throws Exception {
        Object actualValue = this.getLatestValue("ICIMFileType", "CIMFileViewable");
        return  actualValue.equals(actualValue);
    }

    @Override
    public void setCIMFileViewable(Boolean CIMFileViewable) throws Exception {
        this.setPropertyValue("ICIMFileType", "CIMFileViewable", CIMFileViewable, null, true);

    }



}
