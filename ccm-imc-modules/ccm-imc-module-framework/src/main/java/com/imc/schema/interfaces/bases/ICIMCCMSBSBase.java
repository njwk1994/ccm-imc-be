package com.imc.schema.interfaces.bases;

import com.ccm.modules.COMContext;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCCMSBS;

public abstract class ICIMCCMSBSBase extends InterfaceDefault implements ICIMCCMSBS {

    public ICIMCCMSBSBase( boolean instantiateRequiredProperties) {
        super(COMContext.INTERFACE_CIM_CCM_SBS, instantiateRequiredProperties);
    }
    @Override
    public String getSubSystem() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMSBS", "SubSystem");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setSubSystem(String SubSystem) throws Exception {
        this.setPropertyValue("ICIMCCMSBS", "SubSystem", SubSystem, null, true);
    }

    @Override
    public String getSystem() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMSBS", "System");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setSystem(String System) throws Exception {
        this.setPropertyValue("ICIMCCMSBS", "System", System, null, true);
    }

}
