package com.imc.schema.interfaces.bases;

import com.ccm.modules.COMContext;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCCMPBS;

/**
 *
 */
public abstract class ICIMCCMPBSBase extends InterfaceDefault implements ICIMCCMPBS {

    public ICIMCCMPBSBase(boolean instantiateRequiredProperties) {
        super(COMContext.INTERFACE_CIM_CCM_PBS, instantiateRequiredProperties);
    }
    @Override
    public String getArea() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPBS", "Area");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setArea(String Area) throws Exception {
        this.setPropertyValue("ICIMCCMPBS", "Area", Area, null, true);

    }

    @Override
    public String getUnit() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPBS", "Unit");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setUnit(String Unit) throws Exception {
        this.setPropertyValue("ICIMCCMPBS", "Unit", Unit, null, true);

    }
}
