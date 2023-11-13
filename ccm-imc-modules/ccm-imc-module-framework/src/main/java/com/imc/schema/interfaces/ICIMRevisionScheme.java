package com.imc.schema.interfaces;

/**
 * 版本规则
 */
public interface ICIMRevisionScheme  extends  IObject{

    // major系列
    String  getCIMMajorSequence() throws  Exception;

    void setCIMMajorSequence(String CIMMajorSequence) throws  Exception;

    // major最小长度
    Integer  getCIMMajorSequenceMinLength() throws  Exception;

    void setCIMMajorSequenceMinLength(Integer CIMMajorSequenceMinLength) throws  Exception;
    //major连接符
    String    getCIMMajorSequencePadChar() throws  Exception;
    void  setCIMMajorSequencePadChar(String CIMMajorSequencePadChar ) throws  Exception;
    //major系列类型
    String  getCIMMajorSequenceType() throws  Exception;
    void  setCIMMajorSequenceType(String CIMMajorSequenceType)throws  Exception;
    //minor系列
    String  getCIMMinorSequence() throws  Exception;
    void  setCIMMinorSequence(String CIMMinorSequence) throws  Exception;
    //minor最小长度
    Integer  getCIMMinorSequenceMinLength() throws  Exception;

    void   setCIMMinorSequenceMinLength(Integer CIMMinorSequenceMinLength) throws  Exception;
    //minor连接符
    String  getCIMMinorSequencePadChar() throws  Exception;
    void     setCIMMinorSequencePadChar(String CIMMinorSequencePadChar) throws  Exception;
       //minor系列类型
    String  getCIMMinorSequenceType() throws  Exception;
    void     setCIMMinorSequenceType  (String CIMMinorSequenceType) throws  Exception;



}
