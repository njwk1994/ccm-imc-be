package com.imc.schema.interfaces.bases;

import com.ccm.modules.packagemanage.BasicContractContext;
import com.ccm.modules.COMContext;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCCMBasicContractObj;

public abstract class ICIMCCMBasicContractObjBase extends InterfaceDefault implements ICIMCCMBasicContractObj {


    public ICIMCCMBasicContractObjBase(boolean instantiateRequiredProperties) {
        super(COMContext.INTERFACE_CIM_CCM_BASIC_CONTRACT_OBJ, instantiateRequiredProperties);
    }

    @Override
    public String getContract() throws Exception {
        Object actualValue = this.getLatestValue(BasicContractContext.INTERFACE_BASIC_CONTRACT_OBJ, BasicContractContext.PROPERTY_CONTRACT);
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setContract(String Contract) throws Exception {
        this.setPropertyValue(BasicContractContext.INTERFACE_BASIC_CONTRACT_OBJ, BasicContractContext.PROPERTY_CONTRACT, Contract, null, true);

    }

    @Override
    public String getContractor() throws Exception {
        Object actualValue = this.getLatestValue(BasicContractContext.INTERFACE_BASIC_CONTRACT_OBJ, BasicContractContext.PROPERTY_CONTRACTOR);
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setContractor(String Contractor) throws Exception {
        this.setPropertyValue(BasicContractContext.INTERFACE_BASIC_CONTRACT_OBJ, BasicContractContext.PROPERTY_CONTRACTOR, Contractor, null, true);
    }
}
