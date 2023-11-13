package com.imc.schema.interfaces;

/**
 * 设计数据类型
 */
public interface ICIMCCMComponentCategory extends IObject{
    //设计对象类型
    String getCCClassType() throws Exception;

    void  setCCClassType(String CCClassType ) throws Exception;

    //设计工具
    String getCCDesignTool() throws Exception;

    void setCCDesignTool(String CCDesignTool) throws Exception;



}
