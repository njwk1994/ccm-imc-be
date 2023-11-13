package com.imc.schema.interfaces;

/**
 * 基础目标对象
 */
public interface ICIMCCMBasicTargetObj extends IObject{
    //目标实体
    String getTargetClassDef() throws Exception;

    void  setTargetClassDef(String TargetClassDef) throws Exception;

    //目标属性
   String getTargetProperty() throws Exception;

   void  setTargetProperty(String TargetProperty) throws Exception;

   //目标值
    String getTargetValue() throws Exception;

    void  setTargetValue(String TargetValue) throws Exception;
}
