package com.ccm.modules.packagemanage.enums;

/**
 * 工作步骤状态
 */
public enum WSStatusEnum {
    TYPICAL("ELT_Typical_WSStatus", "典型"),
    REVISED_DELETE("ELT_RevisedDelete_WSStatus", "图纸升版删除"),
    REVISED_RESERVE("ELT_RevisedReserve_WSStatus", "图纸升版遗留"),
    REVISED_TEMP_PROCESS("ELT_RevisedTempProcess_WSStatus", "图纸升版临时处理"),
    ROP_DELETE("ELT_ROPDelete_WSStatus", "图纸升版临时处理"),
    ;
    private String code;
    private String name;

    WSStatusEnum(String code, String name){
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
