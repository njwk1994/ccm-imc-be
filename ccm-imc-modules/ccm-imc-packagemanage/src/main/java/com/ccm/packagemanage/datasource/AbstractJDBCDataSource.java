package com.ccm.packagemanage.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * @Author kekai.huang
 * @Date 2023/9/12 11:16
 */
@Slf4j
public abstract class AbstractJDBCDataSource<T extends AbstractJDBCDataSourceConfiguration> extends AbstractDataSource<T> implements IJDBCDataSource<T> {

    protected DruidDataSource dataSource;

    /**
     * 获取JDBC驱动名称
     * @return
     */
    protected abstract String getJDBCDriverName();

    protected abstract void dataSourceSetting(DruidDataSource dataSource);

    @Override
    protected void createDataSource() {
//        T defaultConf = (AbstractJDBCDataSourceConfiguration)config.defaultConfiguration();
        dataSource = new DruidDataSource();
        dataSource.setDriverClassName(getJDBCDriverName());
        dataSource.setUrl(config.getUrl());
        dataSource.setUsername(config.getUsername());
        dataSource.setPassword(config.getPassword());
        dataSource.setInitialSize(config.getInitialSize());
        dataSource.setMinIdle(config.getMinIdle());
        dataSource.setMaxActive(config.getMaxActive());
        dataSource.setMaxWait(config.getMaxWait());
        dataSource.setTimeBetweenConnectErrorMillis(config.getTimeBetweenEvictionRunsMillis());
        dataSource.setMinEvictableIdleTimeMillis(config.getMinEvictableIdleTimeMillis());
        dataSource.setMaxEvictableIdleTimeMillis(config.getMaxEvictableIdleTimeMillis());
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSourceSetting(dataSource);
    }

    @Override
    public boolean executeCall(String call, LinkedList<ProcedureParam> params, LinkedHashMap<String, SQLType> outTypes, Map<String, Object> result) {
        Connection connection = null;
        try {
            // 获取连接并处理
            connection = dataSource.getConnection();
            CallableStatement callableStatement = connection.prepareCall(call);
            AtomicInteger num = new AtomicInteger(1);

            // 传入参数
            for (ProcedureParam entry : params) {
                insertParam(num.get(), callableStatement, entry.getValue(), entry.getType());
                num.getAndIncrement();
            }

            // 传入返回类型
            LinkedHashMap<Integer,String> outNUm = new LinkedHashMap<>();
            for (Map.Entry<String, SQLType> entry : outTypes.entrySet()) {
                outNUm.put(num.get(), entry.getKey());
                callableStatement.registerOutParameter(num.get(), entry.getValue().getVendorTypeNumber());
                num.getAndIncrement();
            }

            // 执行
            callableStatement.execute();

            // 打包返回参数
            if (result == null) result = new HashMap<>();
            for (Map.Entry<Integer, String> entry : outNUm.entrySet()) {
                result.put(entry.getValue(), callableStatement.getObject(entry.getKey()));
            }

        } catch (Exception e) {
            log.error(e.toString());
            return false;
        } finally {
            try {
                assert connection != null;
                connection.close();
            } catch (Exception e) {
                log.error(e.toString());
            }

        }
        return true;
    }

    private void insertParam(int num, CallableStatement callableStatement, Object value, SQLType type) throws SQLException {
        callableStatement.setObject(num, value, type.getVendorTypeNumber());
    }

    @Override
    public boolean test() {
        try {
            Class.forName(getJDBCDriverName());
            DriverManager.getConnection(config.getUrl(),
                    config.getUsername(),
                    config.getPassword());

            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }
}
