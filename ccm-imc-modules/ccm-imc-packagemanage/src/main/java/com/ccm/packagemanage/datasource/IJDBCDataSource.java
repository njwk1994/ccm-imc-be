package com.ccm.packagemanage.datasource;

import java.sql.SQLType;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
/**
 * @Author kekai.huang
 * @Date 2023/9/12 11:16
 */
public interface IJDBCDataSource<T extends IDataSourceConfiguration> extends IDataSource<T> {

    /**
     * 执行存储过程
     * @param call
     * @param params
     * @param result
     */
    boolean executeCall(String call, LinkedList<ProcedureParam> params, LinkedHashMap<String, SQLType> outTypes, Map<String, Object> result);
}
