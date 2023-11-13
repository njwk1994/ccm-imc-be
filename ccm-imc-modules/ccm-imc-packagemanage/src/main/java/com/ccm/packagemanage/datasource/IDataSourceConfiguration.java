package com.ccm.packagemanage.datasource;

/**
 * @Author kekai.huang
 * @Date 2023/9/12 11:16
 */
public interface IDataSourceConfiguration<T extends IDataSourceConfiguration> {
    T defaultConfiguration();
}
