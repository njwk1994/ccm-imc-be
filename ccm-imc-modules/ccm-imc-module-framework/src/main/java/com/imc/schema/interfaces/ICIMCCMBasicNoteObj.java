package com.imc.schema.interfaces;

/**
 * 基础备注对象
 */
public interface ICIMCCMBasicNoteObj extends IObject{

    //备注
    String getNotes() throws Exception;

    void setNotes(String Notes) throws Exception;

}
