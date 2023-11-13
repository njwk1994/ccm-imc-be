package com.ccm.modules.documentmanage.enums;

/**
 * @description：TODO
 * @author： kekai.huang
 * @create： 2023/11/8 17:05
 */
public enum PackageRevisionType {
    DELETE("批量删除工作步骤"),
    UPDATE("批量关联工作步骤"),
    REFRESH("批量删除并关联工作步骤");

    PackageRevisionType(String name) {
    }
}
