package com.imc.schema.interfaces.bases;

import com.ccm.modules.COMContext;
import com.imc.common.core.utils.NumberUtils;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCCMPressureTestPackageFile;

public abstract class ICIMCCMPressureTestPackageFileBase extends InterfaceDefault implements ICIMCCMPressureTestPackageFile {


    public ICIMCCMPressureTestPackageFileBase(boolean instantiateRequiredProperties) {
        super(COMContext.INTERFACE_CIM_CCM_PRESSURE_TEST_PACKAGE_FILE, instantiateRequiredProperties);
    }

    @Override
    public String getFilePath() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPressureTestPackageFile", "FilePath");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setFilePath(String FilePath) throws Exception {
        this.setPropertyValue("ICIMCCMPressureTestPackageFile", "FilePath", FilePath, null, true);

    }

    @Override
    public Integer getPageCount() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPressureTestPackageFile", "PageCount");
        return actualValue != null ? NumberUtils.toInteger(actualValue): 0;
    }

    @Override
    public void setPageCount(Integer PageCount) throws Exception {
        this.setPropertyValue("ICIMCCMPressureTestPackageFile", "PageCount", PageCount, null, true);

    }

    @Override
    public String getVersion() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMPressureTestPackageFile", "Version");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setVersion(String Version) throws Exception {
        this.setPropertyValue("ICIMCCMPressureTestPackageFile", "Version", Version, null, true);

    }
}
