package com.ccm.scheduler.service;

/**
 * @Author kekai.huang
 * @Date 2023/9/13 10:36
 * @PackageName:com.ccm.scheduler.service
 * @ClassName: ICCMP6APIService
 * @Description: P6API调用服务
 * @Version 1.0
 */
public interface ICCMP6APIService {

    /**
     * 检查P6的webservice服务是否可以访问
     * @return
     */
    boolean isServiceAvailable();

    /**
     * 检查P6的webservice服务是否可以访问
     * @param p6WSUrl
     * @param p6ProjectName
     * @param p6ProjectLoginName
     * @param p6ProjectPassword
     * @return
     */
    boolean isServiceAvailable(String p6WSUrl, String p6ProjectName, String p6ProjectLoginName, String p6ProjectPassword);

    /**
     * 获取项目
     * @return
     */
    String readProjects();

    /**
     * 获取项目
     * @param p6WebservicePath
     * @param p6ProjectName
     * @param p6ProjectLoginName
     * @param p6ProjectPassword
     * @return
     */
    String readProjects(String p6WebservicePath, String p6ProjectName, String p6ProjectLoginName, String p6ProjectPassword);

    /**
     * 导出项目
     * @param objectId
     * @return
     */
    String exportProject(String objectId);

    /**
     * 导出项目
     * @param objectId
     * @param p6WebservicePath
     * @param p6ProjectName
     * @param p6ProjectLoginName
     * @param p6ProjectPassword
     * @return
     */
    String exportProject(String objectId, String p6WebservicePath, String p6ProjectName, String p6ProjectLoginName, String p6ProjectPassword);
}
