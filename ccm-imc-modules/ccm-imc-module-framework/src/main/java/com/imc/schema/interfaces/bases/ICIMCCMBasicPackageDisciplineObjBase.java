package com.imc.schema.interfaces.bases;

import com.ccm.modules.COMContext;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCCMBasicPackageDisciplineObj;

public abstract class ICIMCCMBasicPackageDisciplineObjBase extends InterfaceDefault implements ICIMCCMBasicPackageDisciplineObj {

    public ICIMCCMBasicPackageDisciplineObjBase(boolean instantiateRequiredProperties) {
        super(COMContext.INTERFACE_CIM_CCM_BASIC_PACKAGE_DISCIPLINE_OBJ, instantiateRequiredProperties);
    }
}
