package com.imc.schema.interfaces.bases;

import com.imc.common.core.utils.NumberUtils;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMFileComposition;

public abstract class ICIMFileCompositionBase extends InterfaceDefault implements ICIMFileComposition {


    public ICIMFileCompositionBase(boolean instantiateRequiredProperties) {
        super(ICIMFileComposition.class.getSimpleName(),instantiateRequiredProperties);
    }


    @Override
    public Integer getCIMFileCount() throws Exception {
        Object actualValue = this.getLatestValue("ICIMFileComposition", "CIMFileContent");
        return actualValue != null ? NumberUtils.toInteger(actualValue) : 0;
    }

    @Override
    public void setCIMFileCount(Integer CIMFileCount) throws Exception {
        this.setPropertyValue("ICIMFileComposition", "CIMFileCount", CIMFileCount, null, true);

    }
}
