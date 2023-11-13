package com.ccm.scheduler.service.impl;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.http.webservice.SoapClient;
import cn.hutool.core.codec.Base64;
import com.ccm.modules.packagemanage.ProjectConfigContext;
import com.ccm.scheduler.service.ICCMP6APIService;
import com.ccm.scheduler.webservice.p6.SoapClientUtils;
import com.imc.common.core.exception.ServiceException;
import com.imc.common.core.utils.StringUtils;
import com.imc.framework.context.Context;
import com.imc.framework.model.qe.QueryRequest;
import com.imc.schema.interfaces.ICIMCCMProjectConfig;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Service;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static com.ccm.scheduler.constant.P6Common.*;

/**
 * @Author kekai.huang
 * @Date 2023/9/13 10:36
 * @PackageName:com.ccm.scheduler.service.impl
 * @ClassName: CCMP6APIServiceImpl
 * @Description: TODO
 * @Version 1.0
 */
@Slf4j
@Service
public class CCMP6APIServiceImpl implements ICCMP6APIService {
    @Override
    public boolean isServiceAvailable() {
        try {
            // 获取项目配置
            QueryRequest queryRequest = new QueryRequest();
            queryRequest.addClassDefForQuery(ProjectConfigContext.CLASS_CIM_CCM_PROJECT_CONFIG);
            List<ICIMCCMProjectConfig> projectConfigs = Context.Instance.getQueryHelper().query(queryRequest, ICIMCCMProjectConfig.class);
            if (projectConfigs.size() <= 0) {
                throw new ServiceException("获取项目配置失败!");
            }

            String p6WSUrl = projectConfigs.get(0).getP6WebserviceURL();
            String p6ProjectName = projectConfigs.get(0).getP6ProjectName();
            String p6ProjectLoginName = projectConfigs.get(0).getP6ProjectLoginName();
            String p6ProjectPassword = projectConfigs.get(0).getP6ProjectPassword();

            if (StringUtils.isBlank(p6WSUrl) || StringUtils.isBlank(p6ProjectName) || StringUtils.isBlank(p6ProjectLoginName) || StringUtils.isBlank(p6ProjectPassword)) {
                throw new ServiceException("未能获取项目配置中P6配置失败,P6地址:" + p6WSUrl +
                        ",P6项目名称:" + p6ProjectName +
                        ",P6项目登录用户名:" + p6ProjectLoginName +
                        ",P6项目登录密码:" + p6ProjectPassword +
                        ",请检查配置!");
            }
            HttpURLConnection connectionProject = (HttpURLConnection) new URL(SoapClientUtils.checkURLPath(p6WSUrl) + P6_PROJECT_SERVICE_PATH).openConnection();
            HttpURLConnection connectionExport = (HttpURLConnection) new URL(SoapClientUtils.checkURLPath(p6WSUrl) + P6_EXPORT_SERVICE_PATH).openConnection();
            if ((HttpURLConnection.HTTP_OK == connectionProject.getResponseCode()) && (HttpURLConnection.HTTP_OK == connectionExport.getResponseCode())) {
                return true;
            } else {
                throw new ServiceException("P6接口连接失败,获取服务:" + connectionProject.getResponseCode() + ",导出服务:" + connectionExport.getResponseCode() + ".");
            }

        } catch (Exception e) {
            log.error("检查P6接口服务失败!{}", ExceptionUtil.getSimpleMessage(e), ExceptionUtil.getRootCause(e));
            throw new ServiceException("检查P6接口服务失败!" + ExceptionUtil.getSimpleMessage(e));
        }
    }

    @Override
    public boolean isServiceAvailable(String p6WSUrl, String p6ProjectName, String p6ProjectLoginName, String p6ProjectPassword) {
        try {
            if (StringUtils.isBlank(p6WSUrl) || StringUtils.isBlank(p6ProjectName) || StringUtils.isBlank(p6ProjectLoginName) || StringUtils.isBlank(p6ProjectPassword)) {
                throw new ServiceException("未能获取项目配置中P6配置失败,P6地址:" + p6WSUrl +
                        ",P6项目名称:" + p6ProjectName +
                        ",P6项目登录用户名:" + p6ProjectLoginName +
                        ",P6项目登录密码:" + p6ProjectPassword +
                        ",请检查配置!");
            }
            HttpURLConnection connectionProject;
            HttpURLConnection connectionExport;
            try {
                connectionProject = (HttpURLConnection) new URL(SoapClientUtils.checkURLPath(p6WSUrl) + P6_PROJECT_SERVICE_PATH).openConnection();
                connectionExport = (HttpURLConnection) new URL(SoapClientUtils.checkURLPath(p6WSUrl) + P6_EXPORT_SERVICE_PATH).openConnection();
            } catch (Exception e) {
                throw new ServiceException("P6接口连接异常!" + ExceptionUtil.getSimpleMessage(e));
            }
            if ((HttpURLConnection.HTTP_OK == connectionProject.getResponseCode()) && (HttpURLConnection.HTTP_OK == connectionExport.getResponseCode())) {
                return true;
            } else {
                throw new ServiceException("P6接口连接失败,获取服务:" + connectionProject.getResponseCode() + ",导出服务:" + connectionExport.getResponseCode() + ".");
            }
        } catch (Exception e) {
            log.error("检查P6接口服务失败!{}", ExceptionUtil.getSimpleMessage(e), ExceptionUtil.getRootCause(e));
            throw new ServiceException("检查P6接口服务失败!" + ExceptionUtil.getSimpleMessage(e));
        }
    }

    @Override
    public String readProjects() {
        // 获取项目配置
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addClassDefForQuery(ProjectConfigContext.CLASS_CIM_CCM_PROJECT_CONFIG);
        List<ICIMCCMProjectConfig> projectConfigs = Context.Instance.getQueryHelper().query(queryRequest, ICIMCCMProjectConfig.class);
        if (projectConfigs.size() <= 0) {
            throw new ServiceException("获取项目配置失败!");
        }

        // 参数
        String p6WSUrl = projectConfigs.get(0).getP6WebserviceURL();
        String p6ProjectName = projectConfigs.get(0).getP6ProjectName();
        String p6ProjectLoginName = projectConfigs.get(0).getP6ProjectLoginName();
        String p6ProjectPassword = projectConfigs.get(0).getP6ProjectPassword();

        if (StringUtils.isEmpty(p6WSUrl) || StringUtils.isEmpty(p6ProjectName) || StringUtils.isEmpty(p6ProjectLoginName) || StringUtils.isEmpty(p6ProjectPassword)) {
            log.error("获取P6项目时获取P6项目配置失败!WS地址:{},项目名称:{},登录用户名:{},登录密码:{}", p6WSUrl, p6ProjectName, p6ProjectLoginName, p6ProjectPassword);
            throw new ServiceException("获取P6项目时获取P6项目配置失败,请检查项目配置!WS地址:" + p6WSUrl + ",项目名称:" + p6ProjectName + ",登录用户名:" + p6ProjectLoginName + ",登录密码:" + p6ProjectPassword);
        }
        return readProjects(p6WSUrl, p6ProjectName, p6ProjectLoginName, p6ProjectPassword);
    }

    @Override
    public String readProjects(String p6WebservicePath, String p6ProjectName, String p6ProjectLoginName, String p6ProjectPassword) {
        String objectId;
        SoapClient clientWithWsseNoCache = SoapClientUtils.createClientWithWsseNoCache(SoapClientUtils.checkURLPath(p6WebservicePath) + P6_PROJECT_SERVICE_PATH, p6ProjectLoginName, p6ProjectPassword);
        clientWithWsseNoCache.setMethod("v2:ReadProjects", P6_PROJECT_V2_NAMESPACE_URL)
                .setParam("Field", "Name")
                .setParam("Filter", "Name='" + p6ProjectName + "'");
        String ret = clientWithWsseNoCache.send();
        Document document = null;
        try {
            document = DocumentHelper.parseText(ret);
        } catch (DocumentException e) {
            throw new ServiceException("P6返回数据转换失败：" + ExceptionUtil.getSimpleMessage(e));
        }
        Element rootElement = document.getRootElement();
        Element envBody = rootElement.element("Body");
        Element readProjectsResponse = envBody.element("ReadProjectsResponse");
        Element project = readProjectsResponse.element("Project");
        if (org.springframework.util.StringUtils.isEmpty(project)) {
            throw new ServiceException("未从P6获取到对应项目名称为\"" + p6ProjectName + "\"的项目信息!");
        } else {
            objectId = project.elementText("ObjectId");
            if (org.springframework.util.StringUtils.isEmpty(objectId)) {
                throw new ServiceException("未从P6获取到对应项目名称为\"" + p6ProjectName + "\"的项目ID!");
            }
        }
        return objectId;
    }

    @Override
    public String exportProject(String objectId) {
        // 获取项目配置
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addClassDefForQuery(ProjectConfigContext.CLASS_CIM_CCM_PROJECT_CONFIG);
        List<ICIMCCMProjectConfig> projectConfigs = Context.Instance.getQueryHelper().query(queryRequest, ICIMCCMProjectConfig.class);
        if (projectConfigs.size() <= 0) {
            throw new ServiceException("获取项目配置失败!");
        }

        // 参数
        String p6WSUrl = projectConfigs.get(0).getP6WebserviceURL();
        String p6ProjectName = projectConfigs.get(0).getP6ProjectName();
        String p6ProjectLoginName = projectConfigs.get(0).getP6ProjectLoginName();
        String p6ProjectPassword = projectConfigs.get(0).getP6ProjectPassword();
        if (StringUtils.isEmpty(p6WSUrl) || StringUtils.isEmpty(p6ProjectName) || StringUtils.isEmpty(p6ProjectLoginName) || StringUtils.isEmpty(p6ProjectPassword)) {
            log.error("获取P6项目时获取P6项目配置失败!WS地址:{},项目名称:{},登录用户名:{},登录密码:{}", p6WSUrl, p6ProjectName, p6ProjectLoginName, p6ProjectPassword);
            throw new ServiceException("获取P6项目时获取P6项目配置失败,请检查项目配置!WS地址:" + p6WSUrl + ",项目名称:" + p6ProjectName + ",登录用户名:" + p6ProjectLoginName + ",登录密码:" + p6ProjectPassword);
        }

        return exportProject(objectId, p6WSUrl, p6ProjectName, p6ProjectLoginName, p6ProjectPassword);
    }

    @Override
    public String exportProject(String objectId, String p6WebservicePath, String p6ProjectName, String p6ProjectLoginName, String p6ProjectPassword) {
        SoapClient clientWithWsseNoCache = SoapClientUtils.createClientWithWsseNoCache(SoapClientUtils.checkURLPath(p6WebservicePath) + P6_EXPORT_SERVICE_PATH, p6ProjectLoginName, p6ProjectPassword);
        clientWithWsseNoCache.setMethod("v2:ExportProject", P6_EXPORT_V2_NAMESPACE_URL)
                .setParam("ProjectObjectId", objectId);
        String send = clientWithWsseNoCache.send();
        Document document = null;
        try {
            document = DocumentHelper.parseText(send);
        } catch (DocumentException e) {
            throw new ServiceException("P6返回数据转换失败：" + ExceptionUtil.getSimpleMessage(e));
        }
        Element rootElement = document.getRootElement();
        Element envBody = rootElement.element("Body");
        Element exportProjectResponse = envBody.element("ExportProjectResponse");
        String projectData = exportProjectResponse.elementText("ProjectData");
        if (StringUtils.isEmpty(projectData)) {
            throw new ServiceException("未从P6获取到对应项目名称为\"" + p6ProjectName + "\"的项目数据!");
        }
        // 获取到的数据为base64加密数据,返回解密后的XML字符串
        return Base64.decodeStr(projectData);
    }
}
