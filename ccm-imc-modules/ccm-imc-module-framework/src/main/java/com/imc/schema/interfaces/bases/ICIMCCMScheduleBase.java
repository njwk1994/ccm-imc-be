package com.imc.schema.interfaces.bases;

import cn.hutool.core.date.DateTime;
import com.ccm.modules.COMContext;
import com.imc.common.core.utils.NumberUtils;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCCMSchedule;

import java.time.DateTimeException;

public abstract class ICIMCCMScheduleBase extends InterfaceDefault implements ICIMCCMSchedule {


    public ICIMCCMScheduleBase(boolean instantiateRequiredProperties) {
        super(COMContext.INTERFACE_CIM_CCM_SCHEDULE, instantiateRequiredProperties);
    }

    @Override
    public Double getBudgetedLabor() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMSchedule", "BudgetedLabor");
        return actualValue != null ? NumberUtils.toDouble(actualValue) : 0.0;
    }

    @Override
    public void setBudgetedLabor(Double BudgetedLabor) throws Exception {
        this.setPropertyValue("ICIMCCMSchedule", "BudgetedLabor", BudgetedLabor, null, true);

    }

    @Override
    public String getContractDescription() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMSchedule", "ContractDescription");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setContractDescription(String ContractDescription) throws Exception {
        this.setPropertyValue("ICIMCCMSchedule", "ContractDescription", ContractDescription, null, true);

    }

    @Override
    public String getContractorDescription() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMSchedule", "ContractorDescription");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setContractorDescription(String ContractorDescription) throws Exception {
        this.setPropertyValue("ICIMCCMSchedule", "ContractorDescription", ContractorDescription, null, true);

    }

    @Override
    public String getCWP() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMSchedule", "CWP");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCWP(String CWP) throws Exception {
        this.setPropertyValue("ICIMCCMSchedule", "CWP", CWP, null, true);

    }

    @Override
    public String getCWPArea() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMSchedule", "CWPArea");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCWPArea(String CWPArea) throws Exception {
        this.setPropertyValue("ICIMCCMSchedule", "CWPArea", CWPArea, null, true);

    }

    @Override
    public String getCWPDescription() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMSchedule", "CWPDescription");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCWPDescription(String CWPDescription) throws Exception {
        this.setPropertyValue("ICIMCCMSchedule", "CWPDescription", CWPDescription, null, true);

    }

    @Override
    public String getCWPDiscipline() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMSchedule", "CWPDiscipline");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCWPDiscipline(String  CWPDiscipline) throws Exception {
        this.setPropertyValue("ICIMCCMSchedule", "CWPDiscipline", CWPDiscipline, null, true);

    }

    @Override
    public String getCWPEWP() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMSchedule", "CWPEWP");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCWPEWP(String CWPEWP) throws Exception {
        this.setPropertyValue("ICIMCCMSchedule", "CWPEWP", CWPEWP, null, true);

    }

    @Override
    public DateTime getEarlyEnd() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMSchedule", "EarlyEnd");
        if (actualValue != null && actualValue instanceof DateTime) { return (DateTime) actualValue; } else { throw new DateTimeException("Failed to retrieve the CIM revision issue date."); }

    }

    @Override
    public void setEarlyEnd(DateTime EarlyEnd) throws Exception {
        this.setPropertyValue("ICIMCCMSchedule", "EarlyEnd", EarlyEnd, null, true);

    }

    @Override
    public DateTime getEarlyStart() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMSchedule", "EarlyStart");
        if (actualValue != null && actualValue instanceof DateTime) { return (DateTime) actualValue; } else { throw new DateTimeException("Failed to retrieve the CIM revision issue date."); }

    }

    @Override
    public void setEarlyStart(DateTime EarlyStart) throws Exception {
        this.setPropertyValue("ICIMCCMSchedule", "EarlyStart", EarlyStart, null, true);

    }

    @Override
    public DateTime getLateEnd() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMSchedule", "LateEnd");
        if (actualValue != null && actualValue instanceof DateTime) { return (DateTime) actualValue; } else { throw new DateTimeException("Failed to retrieve the CIM revision issue date."); }

    }

    @Override
    public void setLateEnd(DateTime LateEnd) throws Exception {
        this.setPropertyValue("ICIMCCMSchedule", "LateEnd", LateEnd, null, true);

    }

    @Override
    public DateTime getLateStart() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMSchedule", "LateStart");
        if (actualValue != null && actualValue instanceof DateTime) { return (DateTime) actualValue; } else { throw new DateTimeException("Failed to retrieve the CIM revision issue date."); }

    }

    @Override
    public void setLateStart(DateTime LateStart) throws Exception {
        this.setPropertyValue("ICIMCCMSchedule", "LateStart", LateStart, null, true);

    }

    @Override
    public String getWBSPath() throws Exception {
        Object actualValue = this.getLatestValue("ICIMCCMSchedule", "WBSPath");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setWBSPath(String WBSPath) throws Exception {
        this.setPropertyValue("ICIMCCMSchedule", "WBSPath", WBSPath, null, true);

    }
}
