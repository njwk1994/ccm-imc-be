package com.imc.schema.interfaces;

/**
 * @Author kekai.huang
 * @Date 2023/9/13 8:45
 * @PackageName:com.imc.schema.interfaces
 * @ClassName: ICIMCCMProjectConfig
 * @Description: TODO
 * @Version 1.0
 */
public interface ICIMCCMProjectConfig extends IObject {
    /**
     * 获取SPM数据库地址
     *
     * @return
     * @throws Exception
     */
    String getSPMDBHost();

    /**
     * 设置SPM数据库地址
     *
     * @return
     * @throws Exception
     */
    void setSPMDBHost(String value) throws Exception;

    /**
     * 获取SPM数据库端口
     *
     * @return
     * @throws Exception
     */
    String getSPMDBPort();

    /**
     * 设置SPM数据库端口
     *
     * @return
     * @throws Exception
     */
    void setSPMDBPort(String value) throws Exception;

    /**
     * 获取SPM数据库实例名称
     *
     * @return
     * @
     */
    String getSPMDatabaseName();

    /**
     * 设置SPM数据库实例名称
     *
     * @return
     * @throws Exception
     */
    void setSPMDatabaseName(String value) throws Exception;

    /**
     * 获取SPM数据库用户名
     *
     * @return
     * @throws Exception
     */
    String getSPMDBUsername();

    /**
     * 设置SPM数据库用户名
     *
     * @return
     * @throws Exception
     */
    void setSPMDBUsername(String value) throws Exception;

    /**
     * 获取SPM数据库密码
     *
     * @return
     * @throws Exception
     */
    String getSPMDBPassword();

    /**
     * 设置SPM数据库密码
     *
     * @return
     * @throws Exception
     */
    void setSPMDBPassword(String value) throws Exception;

    /**
     * 获取SPM项目名称
     *
     * @return
     * @throws Exception
     */
    String getSPMProject();

    /**
     * 设置SPM项目名称
     *
     * @return
     * @throws Exception
     */
    void setSPMProject(String value) throws Exception;

    /**
     * 获取存储过程类型
     *
     * @return
     * @throws Exception
     */
    String getProcedureType();

    /**
     * 设置存储过程类型
     *
     * @return
     * @throws Exception
     */
    void setProcedureType(String value) throws Exception;

    /**
     * 获取P6的Webservice请求地址
     *
     * @return
     * @throws Exception
     */
    String getP6WebserviceURL();

    /**
     * 设置P6的Webservice请求地址
     *
     * @return
     * @throws Exception
     */
    void setP6WebserviceURL(String value) throws Exception;

    /**
     * 获取P6项目名
     *
     * @return
     * @throws Exception
     */
    String getP6ProjectName();

    /**
     * 设置P6项目名
     *
     * @return
     * @throws Exception
     */
    void setP6ProjectName(String value) throws Exception;

    /**
     * 获取P6项目用户名
     *
     * @return
     * @throws Exception
     */
    String getP6ProjectLoginName();

    /**
     * 设置P6项目用户名
     *
     * @return
     * @throws Exception
     */
    void setP6ProjectLoginName(String value) throws Exception;

    /**
     * 获取P6项目密码
     *
     * @return
     * @throws Exception
     */
    String getP6ProjectPassword();

    /**
     * 设置P6项目密码
     *
     * @return
     * @throws Exception
     */
    void setP6ProjectPassword(String value) throws Exception;

    /**
     * 获取指定字符串类型值
     * @return
     * @throws Exception
     */
    String getStringPropertyValue(String propertyName)throws Exception;
}
