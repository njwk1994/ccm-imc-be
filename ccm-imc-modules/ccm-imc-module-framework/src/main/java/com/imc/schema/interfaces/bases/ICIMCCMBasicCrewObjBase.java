package com.imc.schema.interfaces.bases;

import com.ccm.modules.packagemanage.BasicCrewContext;
import com.ccm.modules.COMContext;
import com.imc.common.core.utils.NumberUtils;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCCMBasicCrewObj;

public abstract class ICIMCCMBasicCrewObjBase extends InterfaceDefault implements ICIMCCMBasicCrewObj {

    public ICIMCCMBasicCrewObjBase( boolean instantiateRequiredProperties) {
        super(COMContext.INTERFACE_CIM_CCM_BASIC_CREW_OBJ, instantiateRequiredProperties);
    }

    @Override
    public String getCrew() throws Exception {
        Object actualValue = this.getLatestValue(BasicCrewContext.INTERFACE_BASIC_CREW_OBJ, BasicCrewContext.PROPERTY_CREW);
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCrew(String Crew) throws Exception {
        this.setPropertyValue(BasicCrewContext.INTERFACE_BASIC_CREW_OBJ, BasicCrewContext.PROPERTY_CREW, Crew, null, true);

    }

    @Override
    public Integer getCrewsize() throws Exception {
        Object actualValue = this.getLatestValue(BasicCrewContext.INTERFACE_BASIC_CREW_OBJ, BasicCrewContext.PROPERTY_CREW_SIZE);
        return actualValue != null ? NumberUtils.toInteger(actualValue): 0;
    }

    @Override
    public void setCrewsize(Integer Crewsize) throws Exception {
        this.setPropertyValue(BasicCrewContext.INTERFACE_BASIC_CREW_OBJ, BasicCrewContext.PROPERTY_CREW_SIZE, Crewsize, null, true);

    }
}
