package com.imc.schema.interfaces.bases;

import com.ccm.modules.COMContext;
import com.imc.common.core.utils.NumberUtils;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCCMPTPackageMaterial;

public abstract class ICIMCCMPTPackageMaterialBase extends InterfaceDefault implements ICIMCCMPTPackageMaterial {


    public ICIMCCMPTPackageMaterialBase( boolean instantiateRequiredProperties) {
        super(COMContext.INTERFACE_CIM_CCM_PTPACKAGE_MATERIAL, instantiateRequiredProperties);
    }

    @Override
    public Integer getCCMPTPMaterialLength() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPTPackageMaterial", "CCMPTPMaterialLength");
        return actualValue != null ? NumberUtils.toInteger(actualValue): 0;
    }

    @Override
    public void setCCMPTPMaterialLength(Integer CCMPTPMaterialLength) throws Exception {
        this.setPropertyValue("ICIMCCMPTPackageMaterial", "CCMPTPMaterialLength", CCMPTPMaterialLength, null, true);

    }
}
