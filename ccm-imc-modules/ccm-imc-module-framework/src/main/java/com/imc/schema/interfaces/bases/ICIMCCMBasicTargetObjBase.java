package com.imc.schema.interfaces.bases;

import com.ccm.modules.packagemanage.BasicTargetContext;
import com.ccm.modules.COMContext;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCCMBasicTargetObj;

public abstract class ICIMCCMBasicTargetObjBase extends InterfaceDefault implements ICIMCCMBasicTargetObj {


    public ICIMCCMBasicTargetObjBase(boolean instantiateRequiredProperties) {
        super(COMContext.INTERFACE_CIM_CCM_BASIC_TARGET_OBJ, instantiateRequiredProperties);
    }

    @Override
    public String getTargetClassDef() throws Exception {
        Object actualValue = this.getLatestValue(BasicTargetContext.INTERFACE_BASIC_BASIC_TARGET_OBJ, BasicTargetContext.PROPERTY_TARGET_CLASS_DEF);
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setTargetClassDef(String TargetClassDef) throws Exception {
        this.setPropertyValue(BasicTargetContext.INTERFACE_BASIC_BASIC_TARGET_OBJ, BasicTargetContext.PROPERTY_TARGET_CLASS_DEF, TargetClassDef, null, true);

    }

    @Override
    public String getTargetProperty() throws Exception {
        Object actualValue = this.getLatestValue(BasicTargetContext.INTERFACE_BASIC_BASIC_TARGET_OBJ, BasicTargetContext.PROPERTY_TARGET_PROPERTY);
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setTargetProperty(String TargetProperty) throws Exception {
        this.setPropertyValue(BasicTargetContext.INTERFACE_BASIC_BASIC_TARGET_OBJ, BasicTargetContext.PROPERTY_TARGET_PROPERTY, TargetProperty, null, true);

    }

    @Override
    public String getTargetValue() throws Exception {
        Object actualValue = this.getLatestValue(BasicTargetContext.INTERFACE_BASIC_BASIC_TARGET_OBJ, BasicTargetContext.PROPERTY_TARGET_VALUE);
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setTargetValue(String TargetValue) throws Exception {
        this.setPropertyValue(BasicTargetContext.INTERFACE_BASIC_BASIC_TARGET_OBJ, BasicTargetContext.PROPERTY_TARGET_VALUE, TargetValue, null, true);

    }
}
