package com.ccm.packagemanage.datasource.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import com.ccm.packagemanage.datasource.AbstractJDBCDataSource;
import com.ccm.packagemanage.datasource.DataSource;
import com.ccm.modules.packagemanage.enums.DataSourceType;

/**
 * @Author kekai.huang
 * @Date 2023/9/12 11:16
 */
@DataSource(
        type = DataSourceType.SQLSERVER,
        configClazz = SqlServerDataSourceConfiguration.class
)
public class SqlServerDataSource extends AbstractJDBCDataSource<SqlServerDataSourceConfiguration> {
    @Override
    protected String getJDBCDriverName() {
        return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    }

    @Override
    protected void dataSourceSetting(DruidDataSource dataSource) {
        dataSource.setValidationQuery("SELECT 'x'");
    }
}
