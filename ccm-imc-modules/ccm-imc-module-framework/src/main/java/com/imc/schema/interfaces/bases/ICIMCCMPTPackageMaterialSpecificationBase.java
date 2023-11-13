package com.imc.schema.interfaces.bases;

import com.ccm.modules.COMContext;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCCMPTPackageMaterialSpecification;

public abstract class ICIMCCMPTPackageMaterialSpecificationBase extends InterfaceDefault implements ICIMCCMPTPackageMaterialSpecification {

    public ICIMCCMPTPackageMaterialSpecificationBase(boolean instantiateRequiredProperties) {
            super(COMContext.INTERFACE_CIM_CCM_PTPACKAGE_MATERIAL_SPECIFICATION, instantiateRequiredProperties);
    }

    @Override
    public String getCCMPTPMSCategory() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPTPackageMaterialSpecification", "CCMPTPMSCategory");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCCMPTPMSCategory(String CCMPTPMSCategory) throws Exception {
        this.setPropertyValue("ICIMCCMPTPackageMaterialSpecification", "CCMPTPMSCategory", CCMPTPMSCategory, null, true);
    }
}
