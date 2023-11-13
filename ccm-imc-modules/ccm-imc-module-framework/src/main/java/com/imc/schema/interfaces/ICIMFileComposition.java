package com.imc.schema.interfaces;

/***
 * 文件集合
 */
public interface ICIMFileComposition extends  IObject{


    //文件数量
    Integer  getCIMFileCount()throws  Exception;

    void  setCIMFileCount(Integer CIMFileCount) throws  Exception;




}
