package com.ccm.packagemanage.datasource;

import lombok.Data;
/**
 * @Author kekai.huang
 * @Date 2023/9/12 11:16
 */
@Data
public abstract class AbstractJDBCDataSourceConfiguration<T extends AbstractJDBCDataSourceConfiguration> implements IDataSourceConfiguration<T> {
    /**
     * 连接地址
     */
    private String url;

    /**
     * 账户
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 初始连接数
     */
    private Integer initialSize;

    /**
     * 最小连接池数量
     */
    private Integer minIdle;

    /**
     * 最大连接池数量
     */
    private Integer maxActive;

    /**
     * 配置获取连接等待超时的时间
     */
    private Integer maxWait;

    /**
     * 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
     */
    private Integer timeBetweenEvictionRunsMillis;

    /**
     * 配置一个连接在池中最小生存的时间，单位是毫秒
     */
    private Integer minEvictableIdleTimeMillis;

    /**
     * 配置一个连接在池中最大生存的时间，单位是毫秒
     */
    private Integer maxEvictableIdleTimeMillis;
}
