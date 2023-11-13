package com.imc.schema.interfaces;

/**
 * 工厂分解结构
 */
public interface ICIMCDWPBS extends IObject{
    // 区域
    String getCIMCDWArea()throws  Exception;
    void  setCIMCDWArea(String CIMCDWArea)throws Exception;
    //装置
    String getCIMCDWUnit()throws  Exception;
    void  setCIMCDWUnit(String CIMCDWUnit) throws  Exception;

}
