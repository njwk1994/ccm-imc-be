package com.ccm.packagemanage.datasource;

import lombok.Data;

import java.sql.SQLType;
/**
 * @Author kekai.huang
 * @Date 2023/9/12 11:16
 */
@Data
public class ProcedureParam {

    private final SQLType type;
    private final Object value;
}
