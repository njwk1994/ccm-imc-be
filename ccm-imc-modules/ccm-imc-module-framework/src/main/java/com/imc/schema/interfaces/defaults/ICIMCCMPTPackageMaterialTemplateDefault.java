package com.imc.schema.interfaces.defaults;

import cn.hutool.json.JSONObject;
import com.imc.schema.interfaces.ICIMCCMPTPackageMaterialTemplate;
import com.imc.schema.interfaces.bases.ICIMCCMPTPackageMaterialTemplateBase;

public class ICIMCCMPTPackageMaterialTemplateDefault extends ICIMCCMPTPackageMaterialTemplateBase {

    public ICIMCCMPTPackageMaterialTemplateDefault(boolean instantiateRequiredProperties) {
        super(instantiateRequiredProperties);
    }

    @Override
    public boolean sameAsOtherTemplate(ICIMCCMPTPackageMaterialTemplate materialTemplate) throws Exception {
        if (materialTemplate != null) {
            return materialTemplate.generateTemplateInfoJSONObj().equals(this.generateTemplateInfoJSONObj());
        }
        return false;
    }

    @Override
    public JSONObject generateTemplateInfoJSONObj() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("CCMPTPMPSize1", this.getCCMPTPMPSize1());
        jsonObject.put("CCMPTPMDescription", this.getCCMPTPMDescription());
        jsonObject.put("CCMPTPMPSize2", this.getCCMPTPMPSize2());
        jsonObject.put("CCMPTPMDesignToolsClassType", this.getCCMPTPMDesignToolsClassType());
        jsonObject.put("CCMPTPMMaterialCode", this.getCCMPTPMMaterialCode());
        jsonObject.put("CCMPTPMBelongsMS", this.getCCMPTPMBelongsMS());
        return jsonObject;
    }
}
