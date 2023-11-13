package com.imc.schema.interfaces.bases;

import com.ccm.modules.COMContext;
import com.ccm.modules.packagemanage.CWAContext;
import com.ccm.modules.packagemanage.WBSContext;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCCMWBS;

public abstract class ICIMCCMWBSBase extends InterfaceDefault implements ICIMCCMWBS {


    public ICIMCCMWBSBase(boolean instantiateRequiredProperties) {
        super(COMContext.INTERFACE_CIM_CCM_WBS, instantiateRequiredProperties);
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

    @Override
    public String getDesignDiscipline() throws Exception {
        Object actualValue = this.getLatestValue(WBSContext.INTERFACE_CIM_CCM_ICIMCCMWBS, WBSContext.PROPERTY_DESIGN_DISCIPLINE);
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setDesignDiscipline(String DesignDiscipline) throws Exception {
        this.setPropertyValue(WBSContext.INTERFACE_CIM_CCM_ICIMCCMWBS, WBSContext.PROPERTY_DESIGN_DISCIPLINE, DesignDiscipline, null, true);

    }

    @Override
    public String getPurpose() throws Exception {
        Object actualValue = this.getLatestValue(WBSContext.INTERFACE_CIM_CCM_ICIMCCMWBS, WBSContext.PROPERTY_PURPOSE);
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setPurpose(String Purpose) throws Exception {
        this.setPropertyValue(WBSContext.INTERFACE_CIM_CCM_ICIMCCMWBS, WBSContext.PROPERTY_PURPOSE, Purpose, null, true);

    }

    @Override
    public String getWPPurpose() throws Exception {
        Object actualValue = this.getLatestValue(WBSContext.INTERFACE_CIM_CCM_ICIMCCMWBS, WBSContext.PROPERTY_WP_PURPOSE);
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setWPPurpose(String WPPurpose) throws Exception {
        this.setPropertyValue(WBSContext.INTERFACE_CIM_CCM_ICIMCCMWBS, WBSContext.PROPERTY_WP_PURPOSE, WPPurpose, null, true);
    }
}
