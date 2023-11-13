package com.ccm.packagemanage.datasource;

/**
 * @Author kekai.huang
 * @Date 2023/9/12 11:16
 */
public interface IDataSource<T extends IDataSourceConfiguration> {

    /**
     * 初始化
     * @param configuration
     */
    void init(DataSourceConfiguration configuration);

    /**
     * 测试连接
     * @return
     */
    boolean testConnect(DataSourceConfiguration configuration);

    /**
     * 判断是否初始化
     * @return
     */
    boolean isInit();
}
