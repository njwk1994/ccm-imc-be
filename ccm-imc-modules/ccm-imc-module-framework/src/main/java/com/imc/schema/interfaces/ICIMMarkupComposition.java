package com.imc.schema.interfaces;

/**
 *
 */
public interface ICIMMarkupComposition  extends IObject{

    //外部文件Id
    String  getCIMExternalFileId()throws  Exception;
    void  setCIMExternalFileId(String CIMExternalFileId)throws  Exception;
}
