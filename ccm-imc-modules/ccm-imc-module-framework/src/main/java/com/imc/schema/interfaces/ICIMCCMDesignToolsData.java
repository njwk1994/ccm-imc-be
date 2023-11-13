package com.imc.schema.interfaces;

/**
 * 设计工具对象基本数据
 */
public interface ICIMCCMDesignToolsData extends  IObject{

    //设计对象类型
    String getDesignToolsClassType() throws  Exception;

    void  setDesignToolsClassType(String DesignToolsClassType) throws  Exception;

    //父对象设计工具ID
    String  getPUCI() throws  Exception;

    void  setPUCI(String PUCI) throws  Exception;

    //设计工具ID
    String getUCI() throws  Exception;

    void setUCI(String UCI) throws  Exception;

    //子类型
    String getSubType() throws  Exception;

    void  setSubType(String SubType) throws  Exception;
}
