package com.ccm.packagemanage.datasource.jdbc;

import com.ccm.packagemanage.datasource.AbstractJDBCDataSourceConfiguration;
import com.ccm.packagemanage.datasource.IDataSourceConfiguration;
/**
 * @Author kekai.huang
 * @Date 2023/9/12 11:16
 */
public class MysqlDataSourceConfiguration extends AbstractJDBCDataSourceConfiguration<MysqlDataSourceConfiguration> {
    @Override
    public MysqlDataSourceConfiguration defaultConfiguration() {
        MysqlDataSourceConfiguration configuration = new MysqlDataSourceConfiguration();
        configuration.setInitialSize(5);
        configuration.setMaxActive(20);
        configuration.setMinIdle(10);
        configuration.setMaxWait(60000);
        configuration.setTimeBetweenEvictionRunsMillis(60000);
        configuration.setMinEvictableIdleTimeMillis(300000);
        configuration.setMaxEvictableIdleTimeMillis(900000);
        return configuration;
    }
}
