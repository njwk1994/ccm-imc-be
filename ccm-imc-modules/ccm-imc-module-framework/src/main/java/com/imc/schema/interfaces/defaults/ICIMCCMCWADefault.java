package com.imc.schema.interfaces.defaults;

import com.ccm.modules.packagemanage.CWAContext;
import com.imc.schema.interfaces.bases.ICIMCCMCWABase;

/**
 * @description：TODO
 * @author： kekai.huang
 * @create： 2023/10/27 15:52
 */
public class ICIMCCMCWADefault extends ICIMCCMCWABase {
    public ICIMCCMCWADefault(boolean instantiateRequiredProperties) {
        super(instantiateRequiredProperties);
    }

    @Override
    public String getCWA() throws Exception {
        Object actualValue = this.getLatestValue(CWAContext.INTERFACE_CIM_CCM_ICIMCCMCWA, CWAContext.PROPERTY_CWA);
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCWA(String CWA) throws Exception {
        this.setPropertyValue(CWAContext.INTERFACE_CIM_CCM_ICIMCCMCWA, CWAContext.PROPERTY_CWA, CWA, null, true);

    }
}
