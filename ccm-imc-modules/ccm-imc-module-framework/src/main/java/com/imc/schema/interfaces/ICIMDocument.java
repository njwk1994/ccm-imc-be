package com.imc.schema.interfaces;

import com.imc.framework.entity.schema.EnumListType;

/**
 *文档接口 ICIMDocument
 */
public interface ICIMDocument extends IObject{

    //get文档分类
    String  getCIMDocCategory()  throws  Exception;

    //set文档分类
    void   setCIMDocCategory(String CIMDocCategory) throws Exception;

    //get文档状态
    String  getCIMDocState() throws Exception;
    //set文档状态
    void   setCIMDocState(String CIMDocState)  throws  Exception;

    //get文档标题
    String getCIMDocTitle() throws  Exception;

    //set文档标题
    void  setCIMDocTitle(String CIMDocTitle) throws  Exception;




}
