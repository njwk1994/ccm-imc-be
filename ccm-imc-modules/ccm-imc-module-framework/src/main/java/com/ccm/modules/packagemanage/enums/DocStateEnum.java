package com.ccm.modules.packagemanage.enums;

/**
 * 文件类型
 */
public enum DocStateEnum {
    EN_IFC("ELT_IFC_CIMDocState", "IFC");
    private String code;
    private String name;

    DocStateEnum(String code, String name){
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
