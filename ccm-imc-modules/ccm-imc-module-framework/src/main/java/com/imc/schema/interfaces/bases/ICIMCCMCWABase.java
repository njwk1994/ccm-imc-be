package com.imc.schema.interfaces.bases;

import com.ccm.modules.packagemanage.CWAContext;
import com.ccm.modules.schedulermanage.BidSectionContext;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCCMCWA;
import com.imc.schema.interfaces.ICIMCCMConstructionType;

/**
 * @description：TODO
 * @author： kekai.huang
 * @create： 2023/10/27 15:49
 */
public abstract class ICIMCCMCWABase extends InterfaceDefault implements ICIMCCMCWA {

    public ICIMCCMCWABase(boolean instantiateRequiredProperties) {
        super(BidSectionContext.INTERFACE_CIM_CCM_BID_SECTION, instantiateRequiredProperties);
    }
}
