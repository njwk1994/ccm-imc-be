package com.imc.schema.interfaces.bases;

import com.ccm.modules.packagemanage.BasicNoteContext;
import com.ccm.modules.COMContext;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCCMBasicNoteObj;

public abstract class ICIMCCMBasicNoteObjBase extends InterfaceDefault implements ICIMCCMBasicNoteObj {


    public ICIMCCMBasicNoteObjBase(boolean instantiateRequiredProperties) {
        super(COMContext.INTERFACE_CIM_CCM_BASIC_NOTE_OBJ, instantiateRequiredProperties);
    }

    @Override
    public String getNotes() throws Exception {
        Object actualValue = this.getLatestValue(BasicNoteContext.INTERFACE_BASIC_NOTE_OBJ, BasicNoteContext.PROPERTY_NOTES);
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setNotes(String Notes) throws Exception {
        this.setPropertyValue(BasicNoteContext.INTERFACE_BASIC_NOTE_OBJ, BasicNoteContext.PROPERTY_NOTES, Notes, null, true);

    }
}
