package com.imc.schema.interfaces.bases;

import com.ccm.modules.COMContext;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCCMPipeMaterial;

public abstract class ICIMCCMPipeMaterialBase extends InterfaceDefault implements ICIMCCMPipeMaterial {

    public ICIMCCMPipeMaterialBase( boolean instantiateRequiredProperties) {
        super(COMContext.INTERFACE_CIM_CCM_PIPE_MATERIAL, instantiateRequiredProperties);
    }

    @Override
    public String getMaterialCategory() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPipeMaterial", "MaterialCategory");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setMaterialCategory(String MaterialCategory) throws Exception {
        this.setPropertyValue("ICIMCCMPipeMaterial", "MaterialCategory", MaterialCategory, null, true);

    }

    @Override
    public String getMaterialsClass() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPipeMaterial", "MaterialsClass");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setMaterialsClass(String MaterialsClass) throws Exception {
        this.setPropertyValue("ICIMCCMPipeMaterial", "MaterialsClass", MaterialsClass, null, true);

    }

    @Override
    public String getMaterialsGrade() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPipeMaterial", "MaterialsGrade");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setMaterialsGrade(String MaterialsGrade) throws Exception {
        this.setPropertyValue("ICIMCCMPipeMaterial", "MaterialsGrade", MaterialsGrade, null, true);

    }

    @Override
    public String getManufacturingCriteria() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPipeMaterial", "ManufacturingCriteria");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setManufacturingCriteria(String ManufacturingCriteria) throws Exception {
        this.setPropertyValue("ICIMCCMPipeMaterial", "ManufacturingCriteria", ManufacturingCriteria, null, true);

    }

    @Override
    public String getRatSchedule() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPipeMaterial", "RatSchedule");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setRatSchedule(String RatSchedule) throws Exception {
        this.setPropertyValue("ICIMCCMPipeMaterial", "RatSchedule", RatSchedule, null, true);

    }
}
