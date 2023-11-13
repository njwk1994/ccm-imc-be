package com.imc.schema.interfaces.bases;

import com.ccm.modules.COMContext;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCCMComponentCategory;

public  abstract class ICIMCCMComponentCategoryBase extends InterfaceDefault implements ICIMCCMComponentCategory {

    public ICIMCCMComponentCategoryBase(boolean instantiateRequiredProperties) {
        super(COMContext.INTERFACE_CIM_CCM_COMPONENT_CATEGORY, instantiateRequiredProperties);
    }
    @Override
    public String getCCClassType() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMComponentCategory", "CCClassType");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCCClassType(String CCClassType) throws Exception {
        this.setPropertyValue("ICIMCCMComponentCategory", "CCClassType", CCClassType, null, true);

    }

    @Override
    public String getCCDesignTool() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMComponentCategory", "CCDesignTool");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCCDesignTool(String CCDesignTool) throws Exception {
        this.setPropertyValue("ICIMCCMComponentCategory", "CCDesignTool", CCDesignTool, null, true);

    }


}
