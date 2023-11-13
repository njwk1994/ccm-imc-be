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
        type = DataSourceType.ORACLE,
        configClazz = OracleDataSourceConfiguration.class
)
public class OracleDataSource extends AbstractJDBCDataSource<OracleDataSourceConfiguration> {
    @Override
    protected String getJDBCDriverName() {
        return "oracle.jdbc.OracleDriver";
    }

    @Override
    protected void dataSourceSetting(DruidDataSource dataSource) {
        dataSource.setValidationQuery("SELECT 1 FROM DUAL");
    }
}
