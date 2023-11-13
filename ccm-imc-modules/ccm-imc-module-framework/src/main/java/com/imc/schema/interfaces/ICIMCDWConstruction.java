package com.imc.schema.interfaces;

/**
 * 施工信息
 */
public interface ICIMCDWConstruction extends IObject{
    //安装时前一对象
    String getCIMCDWNextObj() throws  Exception;
    void setCIMCDWNextObj(String CIMCDWNextObj) throws Exception;
    //安装时后一对象
    String getCIMCDWPreObj() throws  Exception;
    void setCIMCDWPreObj(String CIMCDWPreObj) throws Exception;
    //重做代码
    String getCIMCDWReworkCode() throws  Exception;
    void setCIMCDWReworkCode(String CIMCDWReworkCode) throws Exception;

}
