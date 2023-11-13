package com.imc.schema.interfaces.bases;



import cn.hutool.json.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMMarkupSnapshot;

public abstract class ICIMMarkupSnapshotBase extends InterfaceDefault implements ICIMMarkupSnapshot {


    public ICIMMarkupSnapshotBase(boolean instantiateRequiredProperties) {
        super(ICIMMarkupSnapshot.class.getSimpleName(),instantiateRequiredProperties);
    }


    @Override
    public JSONObject getCIMViewState() throws Exception {
        Object actualValue = this.getLatestValue("ICIMDocumentRevision", "CIMRevState");
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
    public void setCIMViewState(JSONObject CIMViewState) throws Exception {
        this.setPropertyValue("ICIMMarkupSnapshot", "CIMViewState", CIMViewState, null, true);

    }



}
