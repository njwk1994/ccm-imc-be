package com.ccm.packagemanage.datasource;

import com.ccm.modules.packagemanage.enums.DataSourceType;

import java.util.Optional;
/**
 * @Author kekai.huang
 * @Date 2023/9/12 11:16
 */
public interface IDataSourceDiscoveryService {

    /**
     * 根据数据源类型获得对应的数据源相关类
     * @param dataSourceType
     * @return
     */
    Optional<DataSourceDescriptor> getDataSourceByType(DataSourceType dataSourceType);
}
