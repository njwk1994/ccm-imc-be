package com.imc.schema.interfaces.bases;

import cn.hutool.core.date.DateTime;
import com.ccm.modules.COMContext;
import com.imc.common.core.utils.NumberUtils;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCCMWorkPackageResources;
import java.time.DateTimeException;

public abstract class ICIMCCMWorkPackageResourcesBase extends InterfaceDefault implements ICIMCCMWorkPackageResources {

    public ICIMCCMWorkPackageResourcesBase(boolean instantiateRequiredProperties) {
        super(COMContext.INTERFACE_CIM_CCM_WORK_PACKAGE_RESOURCES, instantiateRequiredProperties);
    }

    @Override
    public DateTime getEndDate() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMWorkPackageResources", "EndDate");
        if (actualValue != null && actualValue instanceof DateTime) { return (DateTime) actualValue; } else { throw new DateTimeException("Failed to retrieve the CIM revision issue date."); }

    }

    @Override
    public void setEndDate(DateTime EndDate) throws Exception {
        this.setPropertyValue("ICIMCCMWorkPackageResources", "EndDate", EndDate, null, true);

    }

    @Override
    public Boolean getHad() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMWorkPackageResources", "Had");
        return actualValue.equals(actualValue);
    }

    @Override
    public void setHad(Boolean Had) throws Exception {
        this.setPropertyValue("ICIMCCMWorkPackageResources", "Had", Had, null, true);

    }

    @Override
    public Integer getQuantity() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMWorkPackageResources", "Quantity");
        return actualValue != null ? NumberUtils.toInteger(actualValue): 0;
    }

    @Override
    public void setQuantity(Integer Quantity) throws Exception {
        this.setPropertyValue("ICIMCCMWorkPackageResources", "Quantity", Quantity, null, true);

    }

    @Override
    public String getResourcesType() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMWorkPackageResources", "ResourcesType");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setResourcesType(String ResourcesType) throws Exception {
        this.setPropertyValue("ICIMCCMWorkPackageResources", "ResourcesType", ResourcesType, null, true);

    }

    @Override
    public DateTime getStartDate() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMWorkPackageResources", "StartDate");
        if (actualValue != null && actualValue instanceof DateTime) { return (DateTime) actualValue; } else { throw new DateTimeException("Failed to retrieve the CIM revision issue date."); }

    }

    @Override
    public void setStartDate(DateTime StartDate) throws Exception {
        this.setPropertyValue("ICIMCCMWorkPackageResources", "StartDate", StartDate, null, true);

    }
}
