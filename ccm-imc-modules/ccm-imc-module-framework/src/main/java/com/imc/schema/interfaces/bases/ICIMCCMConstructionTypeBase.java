package com.imc.schema.interfaces.bases;

import com.ccm.modules.COMContext;
import com.ccm.modules.packagemanage.ConstructionTypeContext;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCCMConstructionType;

public abstract class ICIMCCMConstructionTypeBase extends InterfaceDefault implements ICIMCCMConstructionType {


    public ICIMCCMConstructionTypeBase(boolean instantiateRequiredProperties) {
        super(COMContext.INTERFACE_CIM_CCM_CONSTRUCTION_TYPE, instantiateRequiredProperties);
    }

    @Override
    public Boolean getManaged() throws Exception {
        Object actualValue = this.getLatestValue(ConstructionTypeContext.INTERFACE_CIM_CCM_CONSTRUCTION_TYPE, ConstructionTypeContext.PROPERTY_MANAGED);
        return actualValue.equals(actualValue);
    }

    @Override
    public void setManaged(Boolean Managed) throws Exception {
        this.setPropertyValue(ConstructionTypeContext.INTERFACE_CIM_CCM_CONSTRUCTION_TYPE, ConstructionTypeContext.PROPERTY_MANAGED, Managed, null, true);

    }

}
