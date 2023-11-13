package com.ccm.scheduler.service;

/**
 * @Author kekai.huang
 * @Date 2023/9/13 11:13
 * @PackageName:com.ccm.scheduler.service
 * @ClassName: ICCMP6HandleService
 * @Description: P6业务处理服务
 * @Version 1.0
 */
public interface ICCMP6HandleService {

    /**
     * 同步P6计划
     *
     */
    void syncSchedule() throws Exception;
}
