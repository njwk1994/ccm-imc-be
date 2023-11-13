package com.imc.schema.interfaces;

/**
 * 用于关联标段
 */
public interface ICIMCCMToBidSection extends IObject{

    //关联标段UID
    String getToBidSectionUID() throws Exception;

    void  setToBidSectionUID(String ToBidSectionUID) throws Exception;
    //关联标段name
    String getToBidSectionName() throws Exception;

    void  setToBidSectionName(String ToBidSectionName) throws Exception;
}
