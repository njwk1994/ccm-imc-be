package com.imc.schema.interfaces.enums;

import lombok.Getter;

/**
 * 跟踪状态枚举类
 */
@Getter
public enum TracingStatus {

    EL_Open_TracingStatus("Open","开启"),
    EL_Close_TracingStatus("Close","关闭"),
    EL_Cancelled_TracingStatus("Cancelled","取消");
    private  final  String  TracingStatusName;
    private  String  description;
    TracingStatus(String tracingStatusName, String description) {
        TracingStatusName = tracingStatusName;
        this.description = description;
    }
}
