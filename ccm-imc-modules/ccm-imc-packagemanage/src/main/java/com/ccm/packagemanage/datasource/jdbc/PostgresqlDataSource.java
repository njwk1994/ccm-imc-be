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
        type = DataSourceType.POSTGRESQL,
        configClazz = PostgresqlDataSourceConfiguration.class
)
public class PostgresqlDataSource extends AbstractJDBCDataSource<PostgresqlDataSourceConfiguration> {
    @Override
    protected String getJDBCDriverName() {
        return "org.postgresql.Driver";
    }

    @Override
    protected void dataSourceSetting(DruidDataSource dataSource) {
        dataSource.setValidationQuery("SELECT VERSION()");
    }
}
