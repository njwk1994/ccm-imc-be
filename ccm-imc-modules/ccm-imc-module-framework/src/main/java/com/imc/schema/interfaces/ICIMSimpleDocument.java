package com.imc.schema.interfaces;


/**
 * 文档
 */
public interface ICIMSimpleDocument extends IObject {
     //版本
     String  getCIMSimpleDocumentRev()throws  Exception;
     void  setCIMSimpleDocumentRev(String CIMSimpleDocumentRev) throws  Exception;

     //修订号
     String  getCIMSimpleDocumentVer() throws Exception;

     void  setCIMSimpleDocumentVer(String CIMSimpleDocumentVer) throws Exception;

     //说明
     String  getCIMSimpleDocumentRemark() throws Exception;

     void  setCIMSimpleDocumentRemark(String CIMSimpleDocumentRemark) throws Exception;

     //阶段
     String  getCIMSimpleDocumentDesignPhase() throws Exception;

     void  setCIMSimpleDocumentDesignPhase(String CIMSimpleDocumentDesignPhase) throws Exception;








}
