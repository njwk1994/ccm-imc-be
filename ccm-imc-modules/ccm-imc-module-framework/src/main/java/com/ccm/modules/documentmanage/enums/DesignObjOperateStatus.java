package com.ccm.modules.documentmanage.enums;

/**
 * @description：TODO
 * @author： kekai.huang
 * @create： 2023/11/8 10:44
 */
public enum DesignObjOperateStatus {
    CREATE("ELT_Created_DesignObjOperateStatus", "新建"),
    UPDATE("ELT_Updated_DesignObjOperateStatus", "更新"),
    NONE("ELT_None_DesignObjOperateStatus", "未操作"),
    DELETE("ELT_Deleted_DesignObjOperateStatus", "删除");
    private String code;
    private String name;

    DesignObjOperateStatus(String code, String name){
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
