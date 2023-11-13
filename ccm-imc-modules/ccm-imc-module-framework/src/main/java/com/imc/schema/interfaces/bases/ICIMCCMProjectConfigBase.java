package com.imc.schema.interfaces.bases;

import com.ccm.modules.packagemanage.ProjectConfigContext;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICIMCCMProjectConfig;

/**
 * @Author kekai.huang
 * @Date 2023/9/13 8:46
 * @PackageName:com.imc.schema.interfaces.bases
 * @ClassName: ICIMCCMProjectConfigBase
 * @Description: TODO
 * @Version 1.0
 */
public class ICIMCCMProjectConfigBase extends InterfaceDefault implements ICIMCCMProjectConfig {

    public ICIMCCMProjectConfigBase(boolean instantiateRequiredProperties) {
        super(ProjectConfigContext.INTERFACE_CIM_CCM_PROJECT_CONFIG, instantiateRequiredProperties);
    }

    @Override
    public String getSPMDBHost() {
        Object actualValue = this.getLatestValue(ProjectConfigContext.INTERFACE_CIM_CCM_PROJECT_CONFIG, ProjectConfigContext.PROPERTY_SPM_DB_HOST);
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setSPMDBHost(String value) throws Exception {
        this.setPropertyValue(ProjectConfigContext.INTERFACE_CIM_CCM_PROJECT_CONFIG, ProjectConfigContext.PROPERTY_SPM_DB_HOST, value, null, true);
    }

    @Override
    public String getSPMDBPort() {
        Object actualValue = this.getLatestValue(ProjectConfigContext.INTERFACE_CIM_CCM_PROJECT_CONFIG, ProjectConfigContext.PROPERTY_SPM_DB_PORT);
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setSPMDBPort(String value) throws Exception {
        this.setPropertyValue(ProjectConfigContext.INTERFACE_CIM_CCM_PROJECT_CONFIG, ProjectConfigContext.PROPERTY_SPM_DB_PORT, value, null, true);
    }

    @Override
    public String getSPMDatabaseName() {
        Object actualValue = this.getLatestValue(ProjectConfigContext.INTERFACE_CIM_CCM_PROJECT_CONFIG, ProjectConfigContext.PROPERTY_SPM_DATABASE_NAME);
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setSPMDatabaseName(String value) throws Exception {
        this.setPropertyValue(ProjectConfigContext.INTERFACE_CIM_CCM_PROJECT_CONFIG, ProjectConfigContext.PROPERTY_SPM_DATABASE_NAME, value, null, true);
    }

    @Override
    public String getSPMDBUsername() {
        Object actualValue = this.getLatestValue(ProjectConfigContext.INTERFACE_CIM_CCM_PROJECT_CONFIG, ProjectConfigContext.PROPERTY_SPM_DB_USERNAME);
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setSPMDBUsername(String value) throws Exception {
        this.setPropertyValue(ProjectConfigContext.INTERFACE_CIM_CCM_PROJECT_CONFIG, ProjectConfigContext.PROPERTY_SPM_DB_USERNAME, value, null, true);
    }

    @Override
    public String getSPMDBPassword() {
        Object actualValue = this.getLatestValue(ProjectConfigContext.INTERFACE_CIM_CCM_PROJECT_CONFIG, ProjectConfigContext.PROPERTY_SPM_DB_PASSWORD);
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setSPMDBPassword(String value) throws Exception {
        this.setPropertyValue(ProjectConfigContext.INTERFACE_CIM_CCM_PROJECT_CONFIG, ProjectConfigContext.PROPERTY_SPM_DB_PASSWORD, value, null, true);
    }

    @Override
    public String getSPMProject() {
        Object actualValue = this.getLatestValue(ProjectConfigContext.INTERFACE_CIM_CCM_PROJECT_CONFIG, ProjectConfigContext.PROPERTY_SPM_PROJECT);
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setSPMProject(String value) throws Exception {
        this.setPropertyValue(ProjectConfigContext.INTERFACE_CIM_CCM_PROJECT_CONFIG, ProjectConfigContext.PROPERTY_SPM_PROJECT, value, null, true);
    }

    @Override
    public String getProcedureType() {
        Object actualValue = this.getLatestValue(ProjectConfigContext.INTERFACE_CIM_CCM_PROJECT_CONFIG, ProjectConfigContext.PROPERTY_PROCEDURE_TYPE);
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setProcedureType(String value) throws Exception {
        this.setPropertyValue(ProjectConfigContext.INTERFACE_CIM_CCM_PROJECT_CONFIG, ProjectConfigContext.PROPERTY_PROCEDURE_TYPE, value, null, true);
    }

    @Override
    public String getP6WebserviceURL() {
        Object actualValue = this.getLatestValue(ProjectConfigContext.INTERFACE_CIM_CCM_PROJECT_CONFIG, ProjectConfigContext.PROPERTY_P6_WEBSERVICE_URL);
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setP6WebserviceURL(String value) throws Exception {
        this.setPropertyValue(ProjectConfigContext.INTERFACE_CIM_CCM_PROJECT_CONFIG, ProjectConfigContext.PROPERTY_P6_WEBSERVICE_URL, value, null, true);
    }

    @Override
    public String getP6ProjectName() {
        Object actualValue = this.getLatestValue(ProjectConfigContext.INTERFACE_CIM_CCM_PROJECT_CONFIG, ProjectConfigContext.PROPERTY_P6_PROJECT_NAME);
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setP6ProjectName(String value) throws Exception {
        this.setPropertyValue(ProjectConfigContext.INTERFACE_CIM_CCM_PROJECT_CONFIG, ProjectConfigContext.PROPERTY_P6_PROJECT_NAME, value, null, true);
    }

    @Override
    public String getP6ProjectLoginName() {
        Object actualValue = this.getLatestValue(ProjectConfigContext.INTERFACE_CIM_CCM_PROJECT_CONFIG, ProjectConfigContext.PROPERTY_P6_PROJECT_LOGIN_NAME);
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setP6ProjectLoginName(String value) throws Exception {
        this.setPropertyValue(ProjectConfigContext.INTERFACE_CIM_CCM_PROJECT_CONFIG, ProjectConfigContext.PROPERTY_P6_PROJECT_LOGIN_NAME, value, null, true);
    }

    @Override
    public String getP6ProjectPassword() {
        Object actualValue = this.getLatestValue(ProjectConfigContext.INTERFACE_CIM_CCM_PROJECT_CONFIG, ProjectConfigContext.PROPERTY_P6_PROJECT_PASSWORD);
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setP6ProjectPassword(String value) throws Exception {
        this.setPropertyValue(ProjectConfigContext.INTERFACE_CIM_CCM_PROJECT_CONFIG, ProjectConfigContext.PROPERTY_P6_PROJECT_PASSWORD, value, null, true);
    }

    @Override
    public String getStringPropertyValue(String propertyName) throws Exception {
        Object actualValue = this.getLatestValue(ProjectConfigContext.INTERFACE_CIM_CCM_PROJECT_CONFIG, propertyName);
        return actualValue != null ? actualValue.toString() : "";
    }
}
