package com.imc.schema.interfaces;

/**
 * 设计工具对象基本数据
 */
public interface ICIMCDWDesignToolsData extends IObject{


    //设计对象类型
    String  getCIMCDWDesignToolsClassType() throws  Exception;

    void setCIMCDWDesignToolsClassType(String CIMCDWDesignToolsClassType) throws Exception;
    //父对象设计工具ID
    String getCIMCDWPUCI()throws Exception;

    void setCIMCDWPUCI(String CIMCDWPUCI) throws  Exception;
    //设计工具ID
    String getCIMCDWUCI()throws Exception;

    void setCIMCDWUCI(String CIMCDWUCI) throws  Exception;
    //子类型
    String getCIMCDWSubType()throws Exception;

    void setCIMCDWSubType(String CIMCDWSubType) throws  Exception;




}
