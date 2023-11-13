package com.imc.schema.interfaces;

/**
 * 施工信息
 */
public interface ICIMCCMConstruction extends IObject {

    //安装时前一对象
   String  getNextObj() throws Exception;

   void  setNextObj(String NextObj) throws Exception;

   //安装时后一对象
   String getPreObj() throws  Exception;

   void setPreObj(String PreObj)throws  Exception;

   //重做代码
   String  getReworkCode() throws Exception;

   void   setReworkCode(String ReworkCode) throws Exception;

}
