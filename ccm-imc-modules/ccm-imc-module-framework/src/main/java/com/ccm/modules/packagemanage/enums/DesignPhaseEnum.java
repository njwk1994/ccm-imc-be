package com.ccm.modules.packagemanage.enums;

/**
 * 设计阶段枚举
 */
public enum DesignPhaseEnum {
    EN_SHOP_DESIGN("ELT_ShopDesign_DesignPhase", "加设"),
    EN_DETAIL_DESIGN("ELT_DetailDesign_DesignPhase", "详设");
    private String code;
    private String name;

    DesignPhaseEnum(String code, String name){
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
