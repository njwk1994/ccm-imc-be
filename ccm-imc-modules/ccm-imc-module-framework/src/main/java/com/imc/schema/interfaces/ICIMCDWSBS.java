package com.imc.schema.interfaces;

/**
 * 系统分解结构
 */
public interface ICIMCDWSBS extends IObject{
    //子系统

    String  getCIMCDWSubSystem() throws  Exception;

    void  setCIMCDWSubSystem(String CIMCDWSubSystem) throws  Exception;

    // 系统
    String  getCIMCDWSystem() throws  Exception;

    void  setCIMCDWSystem(String CIMCDWSystem) throws  Exception;
}
