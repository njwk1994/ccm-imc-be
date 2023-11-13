package com.imc.schema.interfaces;

/**
 * 版本管理接口
 */
public interface ICIMRevisionItem extends IObject {
    //版本管理对象的Major
    String getCIMRevisionItemMajorRevision() throws Exception;

    void setCIMRevisionItemMajorRevision(String CIMRevisionItemMajorRevision) throws Exception;

    //版本管理对象的Minor
    String getCIMRevisionItemMinorRevision() throws Exception;

    void setCIMRevisionItemMinorRevision(String CIMRevisionItemMinorRevision) throws Exception;

    //操作状态
    String getCIMRevisionItemOperationState() throws Exception;

    void setCIMRevisionItemOperationState(String CIMRevisionItemOperationState) throws Exception;

    // 版本状态
    String getCIMRevisionItemRevState() throws Exception;

    void setCIMRevisionItemRevState(String CIMRevisionItemRevState) throws Exception;

    void setObjectDelete() throws Exception;
}
