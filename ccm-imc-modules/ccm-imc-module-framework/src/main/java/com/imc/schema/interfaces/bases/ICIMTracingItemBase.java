package com.imc.schema.interfaces.bases;

import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMTracingItem;

public abstract class ICIMTracingItemBase extends InterfaceDefault implements ICIMTracingItem {


    public ICIMTracingItemBase(boolean instantiateRequiredProperties) {
        super(ICIMTracingItem.class.getSimpleName(),instantiateRequiredProperties);
    }

    @Override
    public String getCIMTracingStatus() throws Exception {
        Object actualValue = this.getLatestValue("ICIMTracingItem", "CIMTracingStatus");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMTracingStatus(String CIMTracingStatus) throws Exception {
        this.setPropertyValue("ICIMTracingItem", "CIMTracingStatus", CIMTracingStatus, null, true);

    }
}
