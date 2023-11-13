package com.imc.schema.interfaces.bases;

import com.ccm.modules.COMContext;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCCMPTPackageMaterialTemplate;

public abstract class ICIMCCMPTPackageMaterialTemplateBase extends InterfaceDefault implements ICIMCCMPTPackageMaterialTemplate {
    public ICIMCCMPTPackageMaterialTemplateBase(boolean instantiateRequiredProperties) {
        super(COMContext.INTERFACE_CIM_CCM_PTPACKAGE_MATERIAL_TEMPLATE, instantiateRequiredProperties);
    }

    @Override
    public String getCCMPTPMDesignToolsClassType() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPTPackageMaterialTemplate", "CCMPTPMDesignToolsClassType");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCCMPTPMDesignToolsClassType(String CCMPTPMDesignToolsClassType) throws Exception {
        this.setPropertyValue("ICIMCCMPTPackageMaterialTemplate", "CCMPTPMDesignToolsClassType", CCMPTPMDesignToolsClassType, null, true);

    }

    @Override
    public String getCCMPTPMPSize2() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPTPackageMaterialTemplate", "CCMPTPMPSize2");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCCMPTPMPSize2(String CCMPTPMPSize2) throws Exception {
        this.setPropertyValue("ICIMCCMPTPackageMaterialTemplate", "CCMPTPMPSize2", CCMPTPMPSize2, null, true);

    }

    @Override
    public String getCCMPTPMPSize1() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPTPackageMaterialTemplate", "CCMPTPMPSize1");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCCMPTPMPSize1(String CCMPTPMPSize1) throws Exception {
        this.setPropertyValue("ICIMCCMPTPackageMaterialTemplate", "CCMPTPMPSize1", CCMPTPMPSize1, null, true);

    }

    @Override
    public String getCCMPTPMMaterialCode() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPTPackageMaterialTemplate", "CCMPTPMMaterialCode");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCCMPTPMMaterialCode(String CCMPTPMMaterialCode) throws Exception {
        this.setPropertyValue("ICIMCCMPTPackageMaterialTemplate", "CCMPTPMMaterialCode", CCMPTPMMaterialCode, null, true);

    }

    @Override
    public String getCCMPTPMDescription() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPTPackageMaterialTemplate", "CCMPTPMDescription");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCCMPTPMDescription(String CCMPTPMDescription) throws Exception {
        this.setPropertyValue("ICIMCCMPTPackageMaterialTemplate", "CCMPTPMDescription", CCMPTPMDescription, null, true);

    }

    @Override
    public String getCCMPTPMBelongsMS() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPTPackageMaterialTemplate", "CCMPTPMBelongsMS");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCCMPTPMBelongsMS(String CCMPTPMBelongsMS) throws Exception {
        this.setPropertyValue("ICIMCCMPTPackageMaterialTemplate", "CCMPTPMBelongsMS", CCMPTPMBelongsMS, null, true);

    }
}
