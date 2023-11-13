package com.ccm.packagemanage.datasource.jdbc;

import com.ccm.packagemanage.datasource.AbstractJDBCDataSourceConfiguration;
/**
 * @Author kekai.huang
 * @Date 2023/9/12 11:16
 */
public class OracleDataSourceConfiguration extends AbstractJDBCDataSourceConfiguration<OracleDataSourceConfiguration> {
    @Override
    public OracleDataSourceConfiguration defaultConfiguration() {
        OracleDataSourceConfiguration configuration = new OracleDataSourceConfiguration();
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
