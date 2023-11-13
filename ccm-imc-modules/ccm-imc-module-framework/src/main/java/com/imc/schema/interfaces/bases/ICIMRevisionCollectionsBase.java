package com.imc.schema.interfaces.bases;

import com.alibaba.druid.util.StringUtils;
import com.ccm.modules.COMContext;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMRevisionCollections;

import javax.validation.constraints.NotNull;

public abstract class ICIMRevisionCollectionsBase extends InterfaceDefault implements ICIMRevisionCollections {

    public ICIMRevisionCollectionsBase(boolean instantiateRequiredProperties) {
        super(COMContext.INTERFACE_CIM_REVISION_COLLECTIONS, instantiateRequiredProperties);
    }

    @Override
    public String[] getCIMWeldUIDs() throws Exception {
        Object actualValue = this.getLatestValue("ICIMRevisionCollections", "ResourcesType");
        if (!StringUtils.isEmpty(actualValue.toString())) {
            return actualValue.toString().split(",");
        }
        return new String[0];
    }

    @Override
    public void setCIMWeldUIDs(String... CIMWeldUIDs) throws Exception {
        String lstrUIDs = getUiDs(CIMWeldUIDs);
        this.setPropertyValue("ICIMRevisionCollections", "CIMWeldUIDs", lstrUIDs, null, true);
    }

    @Override
    public String[] getCIMSupportUIDs() throws Exception {
        Object actualValue = this.getLatestValue("ICIMRevisionCollections", "CIMSupportUIDs");
        if (!StringUtils.isEmpty(actualValue.toString())) {
            return actualValue.toString().split(",");
        }
        return new String[0];
    }

    @Override
    public void setCIMSupportUIDs(String... CIMSupportUIDs) throws Exception {
        String lstrUIDs = getUiDs(CIMSupportUIDs);
        this.setPropertyValue("ICIMRevisionCollections", "CIMSupportUIDs", lstrUIDs, null, true);
    }

    @Override
    public String[] getCIMSpoolUIDs() throws Exception {
        Object actualValue = this.getLatestValue("ICIMRevisionCollections", "CIMSpoolUIDs");
        if (!StringUtils.isEmpty(actualValue.toString())) {
            return actualValue.toString().split(",");
        }
        return new String[0];
    }

    @Override
    public void setCIMSpoolUIDs(String... CIMSpoolUIDs) throws Exception {
        String lstrUIDs = getUiDs(CIMSpoolUIDs);
        this.setPropertyValue("ICIMRevisionCollections", "CIMSpoolUIDs", lstrUIDs, null, true);
    }

    @Override
    public String[] getCIMPipeComponentUIDs() throws Exception {
        Object actualValue = this.getLatestValue("ICIMRevisionCollections", "CIMPipeComponentUIDs");
        if (!StringUtils.isEmpty(actualValue.toString())) {
            return actualValue.toString().split(",");
        }
        return new String[0];
    }

    @Override
    public void setCIMPipeComponentUIDs(String... CIMPipeComponentUIDs) throws Exception {
        String lstrUIDs = getUiDs(CIMPipeComponentUIDs);
        this.setPropertyValue("ICIMRevisionCollections", "CIMPipeComponentUIDs", lstrUIDs, null, true);

    }

    @Override
    public String[] getCIMPipeUIDs() throws Exception {
        Object actualValue = this.getLatestValue("ICIMRevisionCollections", "CIMPipeUIDs");
        if (!StringUtils.isEmpty(actualValue.toString())) {
            return actualValue.toString().split(",");
        }
        return new String[0];
    }

    @Override
    public void setCIMPipeUIDs(String... CIMPipeUIDs) throws Exception {
        String lstrUIDs = getUiDs(CIMPipeUIDs);
        this.setPropertyValue("ICIMRevisionCollections", "CIMPipeUIDs", lstrUIDs, null, true);
    }

    @Override
    public String[] getCCMPipeLineUIDs() throws Exception {
        Object actualValue = this.getLatestValue("ICIMRevisionCollections", "CCMPipeLineUIDs");
        if (!StringUtils.isEmpty(actualValue.toString())) {
            return actualValue.toString().split(",");
        }
        return new String[0];
    }

    @Override
    public void setCCMPipeLineUIDs(String... CCMPipeLineUIDs) throws Exception {
        String lstrUIDs = getUiDs(CCMPipeLineUIDs);
        this.setPropertyValue("ICIMRevisionCollections", "CCMPipeLineUIDs", lstrUIDs, null, true);
    }

    @Override
    public String[] getCCMBoltUIDs() throws Exception {
        Object actualValue = this.getLatestValue("ICIMRevisionCollections", "CCMBoltUIDs");
        if (!StringUtils.isEmpty(actualValue.toString())) {
            return actualValue.toString().split(",");
        }
        return new String[0];
    }

    @Override
    public void setCCMBoltUIDs(String... CCMBoltUIDs) throws Exception {
        String lstrUIDs = getUiDs(CCMBoltUIDs);
        this.setPropertyValue("ICIMRevisionCollections", "CCMBoltUIDs", lstrUIDs, null, true);

    }

    @Override
    public String[] getCCMGasketUIDs() throws Exception {
        Object actualValue = this.getLatestValue("ICIMRevisionCollections", "CCMGasketUIDs");
        if (!StringUtils.isEmpty(actualValue.toString())) {
            return actualValue.toString().split(",");
        }
        return new String[0];
    }

    @Override
    public void setCCMGasketUIDs(String... CCMGasketUIDs) throws Exception {
        String lstrUIDs = getUiDs(CCMGasketUIDs);
        this.setPropertyValue("ICIMRevisionCollections", "CCMGasketUIDs", lstrUIDs, null, true);
    }

    @Override
    public String[] getCCMEquipUIDs() throws Exception {
        Object actualValue = this.getLatestValue("ICIMRevisionCollections", "CCMEquipUIDs");
        if (!StringUtils.isEmpty(actualValue.toString())) {
            return actualValue.toString().split(",");
        }
        return new String[0];
    }

    @Override
    public void setCCMEquipUIDs(String... CCMEquipUIDs) throws Exception {
        String lstrUIDs = getUiDs(CCMEquipUIDs);
        this.setPropertyValue("ICIMRevisionCollections", "CCMEquipUIDs", lstrUIDs, null, true);

    }

    @Override
    public String[] getCCMSubEquipUIDs() throws Exception {
        Object actualValue = this.getLatestValue("ICIMRevisionCollections", "CCMSubEquipUIDs");
        if (!StringUtils.isEmpty(actualValue.toString())) {
            return actualValue.toString().split(",");
        }
        return new String[0];
    }

    @Override
    public void setCCMSubEquipUIDs(String... CCMSubEquipUIDs) throws Exception {
        String lstrUIDs = getUiDs(CCMSubEquipUIDs);
        this.setPropertyValue("ICIMRevisionCollections", "CCMSubEquipUIDs", lstrUIDs, null, true);
    }

    @Override
    public String[] getCCMCableTrayUIDs() throws Exception {
        Object actualValue = this.getLatestValue("ICIMRevisionCollections", "CCMCableTrayUIDs");
        if (!StringUtils.isEmpty(actualValue.toString())) {
            return actualValue.toString().split(",");
        }
        return new String[0];
    }

    @Override
    public void setCCMCableTrayUIDs(String... CCMCableTrayUIDs) throws Exception {
        String lstrUIDs = getUiDs(CCMCableTrayUIDs);
        this.setPropertyValue("ICIMRevisionCollections", "CCMCableTrayUIDs", lstrUIDs, null, true);

    }

    @Override
    public String[] getCCMCableTrayComponentUIDs() throws Exception {
        Object actualValue = this.getLatestValue("ICIMRevisionCollections", "CCMCableTrayComponentUIDs");
        if (!StringUtils.isEmpty(actualValue.toString())) {
            return actualValue.toString().split(",");
        }
        return new String[0];
    }

    @Override
    public void setCCMCableTrayComponentUIDs(String... CCMCableTrayComponentUIDs) throws Exception {
        String lstrUIDs = getUiDs(CCMCableTrayComponentUIDs);
        this.setPropertyValue("ICIMRevisionCollections", "CCMCableTrayComponentUIDs", lstrUIDs, null, true);

    }

    @Override
    public String[] getCCMCableUIDs() throws Exception {
        Object actualValue = this.getLatestValue("ICIMRevisionCollections", "CCMCableUIDs");
        if (!StringUtils.isEmpty(actualValue.toString())) {
            return actualValue.toString().split(",");
        }
        return new String[0];
    }

    @Override
    public void setCCMCableUIDs(String... CCMCableUIDs) throws Exception {
        String lstrUIDs = getUiDs(CCMCableUIDs);
        this.setPropertyValue("ICIMRevisionCollections", "CCMCableUIDs", lstrUIDs, null, true);
    }

    @Override
    public String[] getCCMInstrumentUIDs() throws Exception {
        Object actualValue = this.getLatestValue("ICIMRevisionCollections", "CCMInstrumentUIDs");
        if (!StringUtils.isEmpty(actualValue.toString())) {
            return actualValue.toString().split(",");
        }
        return new String[0];
    }

    @Override
    public void setCCMInstrumentUIDs(String... CCMInstrumentUIDs) throws Exception {
        String lstrUIDs = getUiDs(CCMInstrumentUIDs);
        this.setPropertyValue("ICIMRevisionCollections", "CCMInstrumentUIDs", lstrUIDs, null, true);

    }

    @Override
    public String[] getCCMDuctLineUIDs() throws Exception {
        Object actualValue = this.getLatestValue("ICIMRevisionCollections", "CCMDuctLineUIDs");
        if (!StringUtils.isEmpty(actualValue.toString())) {
            return actualValue.toString().split(",");
        }
        return new String[0];
    }

    @Override
    public void setCCMDuctLineUIDs(String... CCMDuctLineUIDs) throws Exception {
        String lstrUIDs = getUiDs(CCMDuctLineUIDs);
        this.setPropertyValue("ICIMRevisionCollections", "CCMDuctLineUIDs", lstrUIDs, null, true);

    }

    @Override
    public String[] getCCMDuctComponentUIDs() throws Exception {
        Object actualValue = this.getLatestValue("ICIMRevisionCollections", "CCMDuctComponentUIDs");
        if (!StringUtils.isEmpty(actualValue.toString())) {
            return actualValue.toString().split(",");
        }
        return new String[0];
    }

    @Override
    public void setCCMDuctComponentUIDs(String... CCMDuctComponentUIDs) throws Exception {
        String lstrUIDs = getUiDs(CCMDuctComponentUIDs);
        this.setPropertyValue("ICIMRevisionCollections", "CCMDuctComponentUIDs", lstrUIDs, null, true);

    }

    @Override
    public String[] getCCMJunctionBoxUIDs() throws Exception {
        Object actualValue = this.getLatestValue("ICIMRevisionCollections", "CCMJunctionBoxUIDs");
        if (!StringUtils.isEmpty(actualValue.toString())) {
            return actualValue.toString().split(",");
        }
        return new String[0];
    }

    @Override
    public void setCCMJunctionBoxUIDs(String... CCMJunctionBoxUIDs) throws Exception {
        String lstrUIDs = getUiDs(CCMJunctionBoxUIDs);
        this.setPropertyValue("ICIMRevisionCollections", "CCMJunctionBoxUIDs", lstrUIDs, null, true);

    }

    @Override
    public String[] getCCMSTPartUIDs() throws Exception {
        Object actualValue = this.getLatestValue("ICIMRevisionCollections", "CCMSTPartUIDs");
        if (!StringUtils.isEmpty(actualValue.toString())) {
            return actualValue.toString().split(",");
        }
        return new String[0];
    }

    @Override
    public void setCCMSTPartUIDs(String... CCMSTPartUIDs) throws Exception {
        String lstrUIDs = getUiDs(CCMSTPartUIDs);
        this.setPropertyValue("ICIMRevisionCollections", "CCMSTPartUIDs", lstrUIDs, null, true);

    }

    @Override
    public String[] getCCMSTComponentUIDs() throws Exception {
        Object actualValue = this.getLatestValue("ICIMRevisionCollections", "CCMSTComponentUIDs");
        if (!StringUtils.isEmpty(actualValue.toString())) {
            return actualValue.toString().split(",");
        }
        return new String[0];
    }

    @Override
    public void setCCMSTComponentUIDs(String... CCMSTComponentUIDs) throws Exception {
        String lstrUIDs = getUiDs(CCMSTComponentUIDs);
        this.setPropertyValue("ICIMRevisionCollections", "CCMSTComponentUIDs", lstrUIDs, null, true);

    }

    @Override
    public String[] getCCMSTBlockUIDs() throws Exception {
        Object actualValue = this.getLatestValue("ICIMRevisionCollections", "CCMSTBlockUIDs");
        if (!StringUtils.isEmpty(actualValue.toString())) {
            return actualValue.toString().split(",");
        }
        return new String[0];
    }

    @Override
    public void setCCMSTBlockUIDs(String... CCMSTBlockUIDs) throws Exception {
        String lstrUIDs = getUiDs(CCMSTBlockUIDs);
        this.setPropertyValue("ICIMRevisionCollections", "CCMSTBlockUIDs", lstrUIDs, null, true);
    }

    @NotNull
    private String getUiDs(String[] uids) {
        String lstrUIDs = "";
        if (uids != null && uids.length > 0) {
            lstrUIDs = String.join(",", uids);
        }
        return lstrUIDs;
    }
}
