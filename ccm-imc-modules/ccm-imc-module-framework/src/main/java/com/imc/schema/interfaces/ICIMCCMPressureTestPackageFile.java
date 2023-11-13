package com.imc.schema.interfaces;

/**
 * 试压包文件
 */
public interface ICIMCCMPressureTestPackageFile extends IObject{

    //文件地址
    String getFilePath() throws Exception;

    void  setFilePath(String FilePath) throws Exception;
    //总页数
    Integer getPageCount() throws Exception;

    void  setPageCount(Integer PageCount) throws Exception;
    //版本
    String getVersion() throws Exception;

    void  setVersion(String Version) throws Exception;


}
