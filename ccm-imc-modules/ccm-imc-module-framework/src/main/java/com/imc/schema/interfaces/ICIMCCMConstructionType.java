package com.imc.schema.interfaces;

/**
 * 设计对象的施工分类
 */
public interface ICIMCCMConstructionType extends IObject{

    //是否管理对象
    Boolean getManaged() throws Exception;


    void setManaged(Boolean Managed)throws Exception;

}
