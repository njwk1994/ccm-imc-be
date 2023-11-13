package com.imc.schema.interfaces;

/**
 * 工作分解结构
 */
public interface ICIMCDWWBS extends IObject{


    //设计专业
    String getCIMCDWDesignDiscipline() throws  Exception;

    void  setCIMCDWDesignDiscipline(String CIMCDWDesignDiscipline) throws  Exception;


}
