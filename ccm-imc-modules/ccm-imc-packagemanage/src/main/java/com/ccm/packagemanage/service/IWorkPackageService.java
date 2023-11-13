package com.ccm.packagemanage.service;

import com.alibaba.fastjson2.JSONObject;
import com.ccm.packagemanage.domain.*;
import com.imc.common.core.web.table.TableData;

public interface IWorkPackageService {

    /**
     * 获取工作包可选择添加的图纸
     * @param documentsQueryByPackageParam
     * @return
     */
    TableData<JSONObject> selectDocumentsForWorkPackage(QueryByPackageParamDTO documentsQueryByPackageParam) throws Exception;

    /**
     * 添加图纸到任务包
     * @param taskAddDocumentsParam
     * @throws Exception
     */
    void assignDocumentsIntoWorkPackage(PackageRelDocumentsParamDTO taskAddDocumentsParam) throws Exception;

    /**
     * 添加任务包关联的图纸
     * @param taskAddDocumentsParam
     * @throws Exception
     */
    void removeDocumentsFromWorkPackage(PackageRelDocumentsParamDTO taskAddDocumentsParam) throws Exception;

    /**
     * 根据优先级计算出推荐设计图
     * @param calculateByPriorityParamDTO
     * @return
     * @throws Exception
     */
    TableData<JSONObject> calculatePriorityForWorkPackage(CalculateByPriorityParamDTO calculateByPriorityParamDTO) throws Exception;

    /**
     *
     * @param materialQueryParamDTO
     * @param needConsumeMaterial
     * @return
     * @throws Exception
     */
    TableData<JSONObject> selectDesignDataByPurposeAndConsumeMaterialForWP(MaterialQueryParamDTO materialQueryParamDTO, boolean needConsumeMaterial) throws Exception;

    /**
     * 刷新任务包进度
     * @param packageUid
     */
    Double refreshPackageProgress(String packageUid) throws Exception;

    /**
     * 刷新任务包计划权重
     */
    Double refreshPackagePlanWeight(String packageUid) throws Exception;

    /**
     * 移除工作包下工作步骤
     */
    void removeWorkStepUnderWorkPackage(PackageDeleteRelWokStepDTO packageDeleteRelWokStepDTO) throws Exception;

    /**
     * 移除工作包下设计数据
     */
    void removeComponentsUnderWorkPackage(PackageDeleteRelDesignDTO packageDeleteRelDesignDTO) throws Exception;
}
