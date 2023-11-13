package com.imc.schema.interfaces;

/**
 * 试压包材料
 */
public interface ICIMCCMPTPackageMaterial extends  IObject{

    //数量
   Integer  getCCMPTPMaterialLength() throws Exception;

   void  setCCMPTPMaterialLength(Integer CCMPTPMaterialLength) throws Exception;
}
