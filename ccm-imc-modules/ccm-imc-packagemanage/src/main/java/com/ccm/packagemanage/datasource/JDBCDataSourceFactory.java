package com.ccm.packagemanage.datasource;

import com.alibaba.fastjson.JSONObject;
import com.ccm.modules.packagemanage.enums.DataSourceType;
import com.imc.common.core.exception.ServiceException;
import com.imc.common.core.utils.SpringUtils;
import com.imc.common.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
/**
 * @Author kekai.huang
 * @Date 2023/9/12 11:16
 */
@Slf4j
public class JDBCDataSourceFactory {
    private static ConcurrentHashMap<String, IJDBCDataSource> jdbcDataSources = new ConcurrentHashMap<>();

    /**
     * 根据id获取数据源
     * @param id
     * @return
     */
    public static IJDBCDataSource getJdbcDataSourceById(String id) {
        return jdbcDataSources.get(id);
    }

    /**
     * 初始化数据源
     * @param dataSourceConfiguration
     * @param dataSourceType
     * @param id
     */
    public static boolean initJDBCDataSource(DataSourceConfiguration dataSourceConfiguration, DataSourceType dataSourceType, String id) {
        IDataSourceDiscoveryService dataSourceDiscoveryService = SpringUtils.getBean(IDataSourceDiscoveryService.class);
        if (dataSourceDiscoveryService == null) return false;
        Optional<DataSourceDescriptor> dataSourceDescriptor = dataSourceDiscoveryService.getDataSourceByType(dataSourceType);
        if (!dataSourceDescriptor.isPresent()) return false;
        try {
            JSONObject defaultConfig = dataSourceDescriptor.get().getDefaultConfiguration();
            defaultConfig.putAll(dataSourceConfiguration.getData());
            dataSourceConfiguration.setData(defaultConfig);
            IJDBCDataSource ijdbcDataSource = (IJDBCDataSource)dataSourceDescriptor.get().getDataSourceClass().newInstance();
            if (!ijdbcDataSource.testConnect(dataSourceConfiguration)) return false;
            ijdbcDataSource.init(dataSourceConfiguration);
            jdbcDataSources.put(id, ijdbcDataSource);
            return true;
        } catch (InstantiationException e) {
            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    public static String getUrl(String host, String port, String databaseName, DataSourceType dataSourceType) {
        if (StringUtils.isBlank(host) || StringUtils.isBlank(port) || StringUtils.isBlank(databaseName)) {
            throw new ServiceException("数据库配置信息不全,请检查配置!地址:" + host + ",端口:" + port + ",数据库名称:" + databaseName);
        }
        StringBuilder builder = new StringBuilder();
        if (dataSourceType == DataSourceType.SQLSERVER) {
            builder.append("jdbc:sqlserver://")
                    .append(host).append(":").append(port)
                    .append(";DatabaseName=").append(databaseName);
        }
        if (dataSourceType == DataSourceType.ORACLE) {
            builder.append("jdbc:oracle:thin:@")
                    .append(host).append(":").append(port)
                    .append(":").append(databaseName);
        }
        if (dataSourceType == DataSourceType.MYSQL) {
            builder.append("jdbc:mysql://")
                    .append(host).append(":")
                    .append(port).append("/")
                    .append(databaseName)
                    .append("?useUnicode=true&characterEncoding=utf8&autoReconnect=true&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai");
        }
       return builder.toString();
    }
}
