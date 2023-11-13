package com.imc.schema.interfaces;

/**
 * 文件接口
 */
public interface ICIMFile  extends IObject{

    //  扩展名
    String  getCIMFileExt()  throws  Exception;

    void    setCIMFileExt(String  CIMFileExt) throws  Exception;
    // 文件内容(内存)

    String  getCIMFileContent()  throws  Exception;

    void    setCIMFileContent(String  CIMFileContent) throws  Exception;

    // 文件名
    String  getCIMFileName()  throws  Exception;


    void    setCIMFileName(String  CIMFileName) throws  Exception;


}
