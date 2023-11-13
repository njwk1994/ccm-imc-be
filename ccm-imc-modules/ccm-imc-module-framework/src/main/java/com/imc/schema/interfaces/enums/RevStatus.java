package com.imc.schema.interfaces.enums;

import lombok.Getter;

/**
 * 枚举  版本状态
 *
 */
@Getter
public enum RevStatus {

    EL_Invalid_RSCancelled("Invalid","作废"),
    EL_Working_RSWorking("Working","工作中"),
    EL_Expire_RSSuperseded("Expire","过期"),
    EL_Reserved_RSReserved("Reserved","预留"),
    EL_Formal_RSCurrent("Formal","正式");

    private  final  String   RevStatusName;
    private  String  description;

    RevStatus(String revStatusName, String description) {
        RevStatusName = revStatusName;
        this.description = description;
    }
}
