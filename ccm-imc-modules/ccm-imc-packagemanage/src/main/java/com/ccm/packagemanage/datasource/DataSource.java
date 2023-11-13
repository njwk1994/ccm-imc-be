package com.ccm.packagemanage.datasource;

import com.ccm.modules.packagemanage.enums.DataSourceType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * @Author kekai.huang
 * @Date 2023/9/12 11:16
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DataSource {
    DataSourceType type();

    Class<? extends IDataSourceConfiguration> configClazz();
}
