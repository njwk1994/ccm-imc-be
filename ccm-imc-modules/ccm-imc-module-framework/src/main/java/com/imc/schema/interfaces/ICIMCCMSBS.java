package com.imc.schema.interfaces;

/**
 * 系统分解结构
 */
public interface ICIMCCMSBS extends IObject{
    //子系统
    String getSubSystem() throws Exception;

    void setSubSystem(String SubSystem) throws Exception;


    //系统
    String  getSystem() throws Exception;

    void  setSystem(String System)throws  Exception;




}
