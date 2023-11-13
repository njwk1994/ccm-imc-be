package com.imc.schema.interfaces.bases;

import com.ccm.modules.COMContext;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCCMPressureTestPackage;

public abstract class ICIMCCMPressureTestPackageBase extends InterfaceDefault implements ICIMCCMPressureTestPackage {

    public ICIMCCMPressureTestPackageBase(boolean instantiateRequiredProperties) {
        super(COMContext.INTERFACE_CIM_CCM_PRESSURE_TEST_PACKAGE, instantiateRequiredProperties);
    }

    @Override
    public Boolean getChemicalCleaning() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPressureTestPackage", "ChemicalCleaning");
        return  actualValue.equals(actualValue);
    }

    @Override
    public void setChemicalCleaning(Boolean ChemicalCleaning) throws Exception {
        this.setPropertyValue("ICIMCCMPressureTestPackage", "ChemicalCleaning", ChemicalCleaning, null, true);

    }

    @Override
    public Boolean getHotOilFlushing() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPressureTestPackage", "HotOilFlushing");
        return  actualValue.equals(actualValue);
    }

    @Override
    public void setHotOilFlushing(Boolean HotOilFlushing) throws Exception {
        this.setPropertyValue("ICIMCCMPressureTestPackage", "HotOilFlushing", HotOilFlushing, null, true);

    }

    @Override
    public String getTPTPStatus() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPressureTestPackage", "TPTPStatus");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setTPTPStatus(String TPTPStatus) throws Exception {
        this.setPropertyValue("ICIMDocumentVersion", "TPTPStatus", TPTPStatus, null, true);

    }
}
