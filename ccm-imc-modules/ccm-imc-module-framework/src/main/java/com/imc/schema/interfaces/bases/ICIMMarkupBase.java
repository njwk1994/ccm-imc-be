package com.imc.schema.interfaces.bases;

import com.alibaba.fastjson2.JSONObject;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMMarkup;

public abstract class ICIMMarkupBase extends InterfaceDefault implements ICIMMarkup {


    public ICIMMarkupBase(boolean instantiateRequiredProperties) {
        super(ICIMMarkup.class.getSimpleName(),instantiateRequiredProperties);
    }



    @Override
    public String getCIMMarkupSharedStatus() throws Exception {
        Object actualValue = this.getLatestValue("ICIMMarkup", "CIMMarkupSharedStatus");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMMarkupSharedStatus(String CIMMarkupSharedStatus) throws Exception {
        this.setPropertyValue("ICIMMarkup", "CIMMarkupSharedStatus", CIMMarkupSharedStatus, null, true);

    }

    @Override
    public String getCIMMarkupLabels() throws Exception {
        Object actualValue = this.getLatestValue("ICIMMarkup", "CIMMarkupLabels");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMMarkupLabels(String CIMMarkupLabels) throws Exception {
        this.setPropertyValue("ICIMMarkup", "CIMMarkupLabels", CIMMarkupLabels, null, true);

    }

    @Override
    public String getCIMMarkupRemark() throws Exception {
        Object actualValue = this.getLatestValue("ICIMMarkup", "CIMMarkupRemark");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMMarkupRemark(String CIMMarkupRemark) throws Exception {
        this.setPropertyValue("ICIMMarkup", "CIMMarkupRemark", CIMMarkupRemark, null, true);

    }

    @Override
    public Boolean getCIMIsMarkupLocked() throws Exception {
        Object actualValue = this.getLatestValue("ICIMMarkup", "CIMMarkupRemark");
        return  actualValue.equals(actualValue);
    }

    @Override
    public void setCIMIsMarkupLocked(Boolean CIMIsMarkupLocked) throws Exception {
        this.setPropertyValue("ICIMMarkup", "CIMIsMarkupLocked", CIMIsMarkupLocked, null, true);

    }

    @Override
    public JSONObject getCIMMarkupContent() throws Exception {
        Object actualValue = this.getLatestValue("ICIMMarkup", "CIMMarkupType");
        if (actualValue != null) {
            if (actualValue instanceof JSONObject) {
                return (JSONObject) actualValue;
            } else {
                // Handle the case when actualValue is not a valid JSONObject
                // Return an appropriate default JSONObject or throw an exception
                return new JSONObject(); // Replace with appropriate default JSONObject
            }
        } else {
            // Handle the case when actualValue is null
            // Return an appropriate default JSONObject or throw an exception
            return new JSONObject(); // Replace with appropriate default JSONObject
        }
    }

    @Override
    public void setCIMMarkupContent(JSONObject CIMMarkupContent) throws Exception {
        this.setPropertyValue("ICIMMarkup", "CIMMarkupContent", CIMMarkupContent, null, true);

    }

    @Override
    public String getCIMMarkupType() throws Exception {
        Object actualValue = this.getLatestValue("ICIMMarkup", "CIMMarkupType");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMMarkupType(String CIMMarkupType) throws Exception {
        this.setPropertyValue("ICIMMarkup", "CIMMarkupType", CIMMarkupType, null, true);

    }

    @Override
    public String getCIMMarkupConsolidatedStatus() throws Exception {
        Object actualValue = this.getLatestValue("ICIMMarkup", "CIMMarkupConsolidatedStatus");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMMarkupConsolidatedStatus(String CIMMarkupConsolidatedStatus) throws Exception {
        this.setPropertyValue("ICIMMarkup", "CIMMarkupConsolidatedStatus", CIMMarkupConsolidatedStatus, null, true);

    }

    @Override
    public String getCIMMarkupCheckedoutStatus() throws Exception {
        Object actualValue = this.getLatestValue("ICIMMarkup", "CIMMarkupCheckedoutStatus");
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setCIMMarkupCheckedoutStatus(String CIMMarkupCheckedoutStatus) throws Exception {
        this.setPropertyValue("ICIMMarkup", "CIMMarkupCheckedoutStatus", CIMMarkupCheckedoutStatus, null, true);

    }



}
