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
        type = DataSourceType.MYSQL,
        configClazz = MysqlDataSourceConfiguration.class
)
public class MysqlDataSource extends AbstractJDBCDataSource<MysqlDataSourceConfiguration> {
    @Override
    protected String getJDBCDriverName() {
        return "com.mysql.cj.jdbc.Driver";
    }

    @Override
    protected void dataSourceSetting(DruidDataSource dataSource) {
        dataSource.setValidationQuery("SELECT 1 FROM DUAL");
    }
}
