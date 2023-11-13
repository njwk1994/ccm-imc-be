package com.imc.schema.interfaces.bases;

import com.ccm.modules.COMContext;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCCMSchedulePolicyItem;

public class ICIMCCMSchedulePolicyItemBase extends InterfaceDefault implements ICIMCCMSchedulePolicyItem {

    public ICIMCCMSchedulePolicyItemBase(boolean instantiateRequiredProperties) {
        super(COMContext.INTERFACE_CIM_CCM_SCHEDULE_POLICY_ITEM, instantiateRequiredProperties);
    }
}
