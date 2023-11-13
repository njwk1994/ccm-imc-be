package com.imc.schema.interfaces;

/**
 * 移交文件
 */
public interface ICIMCCMHandoverFile extends IObject{
    //完工文件页数
    String  getHandoverFilePage() throws Exception;

    void  setHandoverFilePage(String HandoverFilePage) throws Exception;
    //完工文件状态
    String  getHandoverFileStatus() throws Exception;

    void  setHandoverFileStatus(String HandoverFileStatus) throws Exception;

}
