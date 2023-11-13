package com.imc.schema.interfaces.bases;

import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCDWPipeMaterial;


public abstract class ICIMCDWPipeMaterialBase extends InterfaceDefault implements ICIMCDWPipeMaterial {


    public ICIMCDWPipeMaterialBase(boolean instantiateRequiredProperties) {
        super(ICIMCDWPipeMaterial.class.getSimpleName(),instantiateRequiredProperties);
    }

    @Override
    public String getCIMCDWMaterialCategory() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCDWPipeMaterial", "CIMCDWMaterialCategory");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMCDWMaterialCategory(String CIMCDWMaterialCategory) throws Exception {
        this.setPropertyValue("ICIMCDWPipeMaterial", "CIMCDWMaterialCategory", CIMCDWMaterialCategory, null, true);

    }

    @Override
    public String getCIMCDWMaterialsClass() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCDWPipeMaterial", "CIMCDWMaterialsClass");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMCDWMaterialsClass(String CIMCDWMaterialsClass) throws Exception {
        this.setPropertyValue("ICIMCDWPipeMaterial", "CIMCDWMaterialsClass", CIMCDWMaterialsClass, null, true);

    }

    @Override
    public String getCIMCDWMaterialsGrade() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCDWPipeMaterial", "CIMCDWMaterialsGrade");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMCDWMaterialsGrade(String CIMCDWMaterialsGrade) throws Exception {
        this.setPropertyValue("ICIMCDWPipeMaterial", "CIMCDWMaterialsGrade", CIMCDWMaterialsGrade, null, true);

    }

    @Override
    public String getCIMCDWManufacturingCriteria() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCDWPipeMaterial", "CIMCDWManufacturingCriteria");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMCDWManufacturingCriteria(String CIMCDWManufacturingCriteria) throws Exception {
        this.setPropertyValue("ICIMCDWPipeMaterial", "CIMCDWManufacturingCriteria", CIMCDWManufacturingCriteria, null, true);

    }

    @Override
    public String getCIMCDWRatSchedule() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCDWPipeMaterial", "CIMCDWRatSchedule");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMCDWRatSchedule(String CIMCDWRatSchedule) throws Exception {
        this.setPropertyValue("ICIMCDWPipeMaterial", "CIMCDWRatSchedule", CIMCDWRatSchedule, null, true);

    }
}
