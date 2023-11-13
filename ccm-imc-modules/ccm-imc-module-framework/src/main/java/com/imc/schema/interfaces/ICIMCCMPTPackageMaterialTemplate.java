package com.imc.schema.interfaces;

import cn.hutool.json.JSONObject;

/**
 * 试压包材料模板对象
 */
public interface ICIMCCMPTPackageMaterialTemplate extends IObject {

    //材料类型
    String getCCMPTPMDesignToolsClassType() throws Exception;

    void setCCMPTPMDesignToolsClassType(String CCMPTPMDesignToolsClassType) throws Exception;

    //壁厚
    String getCCMPTPMPSize2() throws Exception;

    void setCCMPTPMPSize2(String CCMPTPMPSize2) throws Exception;

    //尺寸
    String getCCMPTPMPSize1() throws Exception;

    void setCCMPTPMPSize1(String CCMPTPMPSize1) throws Exception;

    //油料编码
    String getCCMPTPMMaterialCode() throws Exception;

    void setCCMPTPMMaterialCode(String CCMPTPMMaterialCode) throws Exception;

    //描述
    String getCCMPTPMDescription() throws Exception;

    void setCCMPTPMDescription(String CCMPTPMDescription) throws Exception;

    //材料规格
    String getCCMPTPMBelongsMS() throws Exception;

    void setCCMPTPMBelongsMS(String CCMPTPMBelongsMS) throws Exception;

    boolean sameAsOtherTemplate(ICIMCCMPTPackageMaterialTemplate materialTemplate) throws Exception;

    JSONObject generateTemplateInfoJSONObj() throws Exception;
}
