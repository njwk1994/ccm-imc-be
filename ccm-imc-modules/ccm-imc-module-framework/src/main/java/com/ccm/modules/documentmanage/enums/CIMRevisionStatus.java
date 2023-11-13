package com.ccm.modules.documentmanage.enums;

/**
 * @description：TODO
 * @author： kekai.huang
 * @create： 2023/11/8 15:16
 */
public enum CIMRevisionStatus {
    CURRENT("ELT_Current_CIMRevisionStatus", "当前"),
    SUPERSEDED("ELT_Superseded_CIMRevisionStatus", "过期"),
    REVISED("ELT_Revised_CIMRevisionStatus", "升版"),
    MIGRATION("ELT_Migration_CIMRevisionStatus", "迁移"),
    NEW("ELT_New_CIMRevisionStatus", "新增"),
    WORKING("ELT_Working_CIMRevisionStatus", "工作中"),
    RESERVED("ELT_Reserved_CIMRevisionStatus", "预留"),
    ;
    private String code;
    private String name;

    CIMRevisionStatus(String code, String name){
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
