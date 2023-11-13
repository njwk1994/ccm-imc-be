package com.ccm.modules.packagemanage.enums;

/**
 * 设计数据操作状态
 */
public enum DesignObjOperateStatusEnum {
    CREATED("Created", "新建"),
    UPDATE("Updated", "更新"),
    NONE("None", "未操作"),
    DELETE("Deleted", "删除")
    ;
    private String code;
    private String name;

    DesignObjOperateStatusEnum(String code, String name){
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
