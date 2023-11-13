package com.imc.schema.interfaces;

/**
 * 可跟踪对象
 */
public interface ICIMTracingItem  extends IObject{

    //跟踪状态
    String getCIMTracingStatus()throws  Exception;

    void  setCIMTracingStatus(String CIMTracingStatus) throws Exception;


}
