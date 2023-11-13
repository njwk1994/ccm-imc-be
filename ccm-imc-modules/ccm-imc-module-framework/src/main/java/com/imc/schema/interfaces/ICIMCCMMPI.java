package com.imc.schema.interfaces;

/**
 *材料采购信息
 */
public interface ICIMCCMMPI extends IObject{
     //物料编号
    String  getMaterialCode() throws  Exception;

    void setMaterialCode(String MaterialCode) throws  Exception;

    //采购数量
    Double getPQuantity() throws  Exception;

    void setPQuantity(Double PQuantity) throws  Exception;

    //采购尺寸1
    String  getPSize1() throws  Exception;

    void  setPSize1(String  PSize1) throws  Exception;

    //采购尺寸2
    String getPSize2() throws  Exception;

    void  setgetPSize2(String getPSize2) throws  Exception;



}
