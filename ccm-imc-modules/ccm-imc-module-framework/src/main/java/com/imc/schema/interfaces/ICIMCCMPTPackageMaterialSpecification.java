package com.imc.schema.interfaces;

import java.util.List;

/**
 * 试压包材料规格
 */
public interface ICIMCCMPTPackageMaterialSpecification extends IObject{

    //试压包材料规格分类
    String getCCMPTPMSCategory() throws Exception;

    void setCCMPTPMSCategory(String CCMPTPMSCategory) throws Exception;

    List<ICIMCCMPTPackageMaterialTemplate> getPTPMaterialTemplates();
}
