package com.imc.schema.interfaces;

/**
 * 工厂分解结构
 */
public interface ICIMCCMPBS extends IObject{
    //区域
    String getArea() throws Exception;


    void   setArea(String  Area)throws  Exception;
    //装置
    String getUnit() throws  Exception;

    void setUnit(String Unit) throws Exception;



}
