package com.ccm.packagemanage.datasource;

import com.alibaba.fastjson.JSONObject;
import com.ccm.modules.packagemanage.enums.DataSourceType;
import lombok.Data;
/**
 * @Author kekai.huang
 * @Date 2023/9/12 11:16
 */
@Data
public class DataSourceDescriptor {
    /**
     * 数据源类
     */
    private Class dataSourceClass;

    /**
     * 配置类
     */
    private Class configurationClass;

    /**
     * 数据源类型
     */
    private DataSourceType dataSourceType;

    /**
     * 默认配置
     */
    private JSONObject defaultConfiguration;
}
