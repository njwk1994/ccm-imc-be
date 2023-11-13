package com.imc.schema.interfaces;

/**
 * 标段
 */
public interface ICIMCCMBidSection extends  IObject{
    //大项目编码
    String getPCode() throws Exception;

    void  setPCode(String PCode) throws Exception;
}
