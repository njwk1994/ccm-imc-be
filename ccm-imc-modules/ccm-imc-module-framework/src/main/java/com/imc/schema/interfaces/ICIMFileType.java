package com.imc.schema.interfaces;

/**
 * 文件类型
 */
public interface ICIMFileType extends IObject{


    //文件可编辑
    Boolean   getCIMFileEditable()  throws  Exception;

    void   setCIMFileEditable(Boolean CIMFileEditable) throws  Exception;

    //文件可查看
    Boolean    getCIMFileViewable() throws  Exception;
    void  setCIMFileViewable(Boolean  CIMFileViewable) throws  Exception;






}
