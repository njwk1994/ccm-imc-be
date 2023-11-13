package com.imc.schema.interfaces;

public interface ICIMROPWorkStep {


    /**
     * 获取任务包施工阶段
     * @return
     */
    String getCIMROPWorkStepTPPhase() ;

    /**
     * 设置任务包施工阶段
     * @param CIMROPWorkStepTPPhase
     * @throws Exception
     */
    void setCIMCIMROPWorkStepTPPhase(String CIMROPWorkStepTPPhase) throws Exception;


    /**
     *  获取工作包施工阶段
     * @return
     */
    String getCIMROPWorkStepWPPhase() ;

    /**
     * 设置工作包施工阶段
     * @param CIMROPWorkStepWPPhase
     * @throws Exception
     */
    void setCIMROPWorkStepWPPhase(String CIMROPWorkStepWPPhase) throws Exception;


    /**
     * 获取工作步骤
     * @return
     */
    String getCIMROPWorkStepName() ;

    /**
     * 设置工作步骤
     * @param CIMROPWorkStepName
     * @throws Exception
     */
    void setCIMROPWorkStepName(String CIMROPWorkStepName) throws Exception;


    /**
     * 获取工作步骤类别
     * @return
     */
    String getCIMROPWorkStepType() ;

    /**
     * 设置工作步骤类别
     * @param CIMROPWorkStepType
     * @throws Exception
     */
    void setCIMROPWorkStepType(String CIMROPWorkStepType) throws Exception;

    /**
     * 获取阶段放行步骤
     * @return
     */
    Boolean getCIMROPWorkStepAllowInd() ;

    /**
     * 设置阶段放行步骤
     * @param CIMROPWorkStepAllowInd
     * @throws Exception
     */
    void setCIMROPWorkStepAllowInd(Boolean CIMROPWorkStepAllowInd) throws Exception;


    /**
     * 获取消耗材料
     * @return
     */
    Boolean getCIMROPWorkStepConsumeMaterialInd() ;

    /**
     * 设置消耗材料
     * @param CIMROPWorkStepConsumeMaterialInd
     * @throws Exception
     */
    void setCIMROPWorkStepConsumeMaterialInd(Boolean CIMROPWorkStepConsumeMaterialInd) throws Exception;


    /**
     * 获取用于计算权重的属性项
     * @return
     */
    String getCIMROPWorkStepWeightCalculateProperty() ;

    /**
     * 设置用于计算权重的属性项
     * @param CIMROPWorkStepWeightCalculateProperty
     * @throws Exception
     */
    void setCIMROPWorkStepWeightCalculateProperty(String CIMROPWorkStepWeightCalculateProperty) throws Exception;


    /**
     * 获取模板（模板设置的权重）
     * @return
     */
    Double getCIMROPWorkStepBaseWeight() ;

    /**
     * 设置模板（模板设置的权重）
     * @param CIMROPWorkStepBaseWeight
     * @throws Exception
     */
    void setCIMROPWorkStepBaseWeight(Double CIMROPWorkStepBaseWeight) throws Exception;

    /**
     * 获取顺序
     * @return
     */
    Integer getCIMROPWorkStepOrderValue() ;

    /**
     * 设置顺序
     * @param CIMROPWorkStepOrderValue
     * @throws Exception
     */
    void setCIMROPWorkStepOrderValue(Integer CIMROPWorkStepOrderValue) throws Exception;

    /**
     * 获取材料下发说明
     * @return
     */
    String getCIMROPWorkStepMaterialIssue() ;

    /**
     * 设置材料下发说明
     * @param CIMROPWorkStepMaterialIssue
     * @throws Exception
     */
    void setCIMROPWorkStepMaterialIssue(String CIMROPWorkStepMaterialIssue) throws Exception;


    /**
     * 获取步骤生产方式
     * @return
     */
    String getCIMROPWorkStepGenerateMode() ;

    /**
     * 设置步骤生产方式
     * @param CIMROPWorkStepGenerateMod
     * @throws Exception
     */
    void setCIMROPWorkStepGenerateMode(String CIMROPWorkStepGenerateMod) throws Exception;


}
