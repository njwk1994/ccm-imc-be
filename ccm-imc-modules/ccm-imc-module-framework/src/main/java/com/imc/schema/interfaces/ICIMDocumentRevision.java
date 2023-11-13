package com.imc.schema.interfaces;

import cn.hutool.core.date.DateTime;

/**
 * 版本
 */
public interface ICIMDocumentRevision extends IObject{


         // 获取和设置完整版本
         String  getCIMExternalRevision() throws  Exception;
         void  setCIMExternalRevision(String CIMExternalRevision) throws  Exception;

         //获取和设置主版本
         String getCIMMajorRevision() throws  Exception;

         void  setCIMMajorRevision(String CIMMajorRevision) throws  Exception;

         //获取和设置次版本
         String  getCIMMinorRevision() throws  Exception;

         void  setCIMMinorRevision(String CIMMinorRevision) throws  Exception;
         //获取和设置版本规则
         String  getCIMRevisionSchema() throws  Exception;

         void  setCIMRevisionSchema(String CIMRevisionSchema) throws  Exception;

         //获取和设置签结日期
         DateTime getCIMRevIssueDate() throws  Exception;
         void  setCIMRevIssueDate(DateTime CIMRevIssueDate) throws  Exception;

         //获取和设置版本状态
         String getCIMRevState() throws  Exception;
         void  setCIMRevState(String CIMRevState) throws  Exception;
         //获取和设置签结内容
         String getCIMSignOffComments() throws  Exception;
         void  setCIMSignOffComments(String CIMSignOffComments) throws  Exception;









}
