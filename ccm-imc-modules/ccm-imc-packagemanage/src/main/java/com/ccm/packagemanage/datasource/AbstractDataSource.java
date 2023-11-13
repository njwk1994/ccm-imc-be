package com.ccm.packagemanage.datasource;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @Author kekai.huang
 * @Date 2023/9/12 11:16
 */
public abstract class AbstractDataSource<T extends IDataSourceConfiguration> implements IDataSource<T> {
    protected T config;

    protected Class<T> configClassType;

    /**
     * 创建数据源
     */
    protected abstract void createDataSource();

    private boolean init = false;

    /**
     * 测试链接
     * @return
     */
    protected abstract boolean test();

    @Override
    public void init(DataSourceConfiguration configuration) {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        configClassType = (Class<T>) params[0];
        config = JSON.parseObject(JSON.toJSONString(configuration.getData()), configClassType);
        this.createDataSource();
        init = true;
    }

    @Override
    public boolean isInit() {
        return init;
    }

    @Override
    public boolean testConnect(DataSourceConfiguration configuration) {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        configClassType = (Class<T>) params[0];
        config = JSON.parseObject(JSON.toJSONString(configuration.getData()), configClassType);
        return test();
    }
}
