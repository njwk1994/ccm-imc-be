package com.ccm.modules.documentmanage.enums;

/**
 * @description：TODO
 * @author： kekai.huang
 * @create： 2023/11/7 16:25
 */
public enum RevStateEnum {
    RS_CANCELLED("EL_RSCancelled", "作废"),
    RS_CURRENT("EL_RSCurrent", "正式"),
    RS_SUPERSEDED("EL_RSSuperseded", "过期"),
    RS_RESERVED("EL_RSReserved", "预留"),
    RS_WORKING("EL_RSWorking", "工作中");
    private String code;
    private String name;

    RevStateEnum(String code, String name){
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
