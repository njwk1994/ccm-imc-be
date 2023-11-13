package com.imc.schema.interfaces.enums;

/**
 *  枚举   文档状态
 */
public enum DocStatus {

    EL_Official_DSIssued("Official","正式"),
    EL_Invalid_DSCancelled("Invalid","作废"),
    EL_Reserved_DSReserved("Reserved","预留");

    private  final   String DocStatusName;

    private  String  description;

    DocStatus(String docStatusName, String description) {
        DocStatusName = docStatusName;
        this.description = description;
    }





}
