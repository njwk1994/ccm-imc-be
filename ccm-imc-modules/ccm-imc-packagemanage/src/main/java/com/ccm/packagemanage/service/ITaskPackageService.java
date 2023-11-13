package com.ccm.packagemanage.service;

import com.alibaba.fastjson2.JSONObject;
import com.ccm.packagemanage.domain.*;
import com.imc.common.core.web.table.TableData;
import com.imc.schema.interfaces.IObject;

import java.util.List;

public interface ITaskPackageService {
    /**
     * 为当前任务包推荐设计图
     * @param documentsQueryByTask
     * @return
     */
    TableData<JSONObject> selectDocumentsForTaskPackage(QueryByPackageParamDTO documentsQueryByTask) throws Exception;

    /**
     * 添加图纸到任务包
     * @param taskAddDocumentsParam
     * @throws Exception
     */
    void assignDocumentsIntoTaskPackage(PackageRelDocumentsParamDTO taskAddDocumentsParam) throws Exception;

    /**
     * 添加任务包关联的图纸
     * @param taskAddDocumentsParam
     * @throws Exception
     */
    void removeDocumentsFromTaskPackage(PackageRelDocumentsParamDTO taskAddDocumentsParam) throws Exception;

    /**
     * 根据物料类型查询材料
     * @param materialQueryParamDTO
     * @return
     * @throws Exception
     */
    TableData<JSONObject> selectDesignDataByPurposeAndConsumeMaterial(MaterialQueryParamDTO materialQueryParamDTO, boolean needConsumeMaterial) throws Exception;

    /**
     * 获取任务包同施工阶段并且有材料消耗的工作步骤
     * @param taskPackage
     * @return
     */
    List<IObject> getWorkStepsWithSamePurposeAndConsumeMaterial(IObject taskPackage) throws Exception;

    /**
     * 根据优先级计算出推荐设计图
     * @param calculateByPriorityParamDTO
     * @return
     * @throws Exception
     */
    TableData<JSONObject> calculatePriorityForTaskPackage(CalculateByPriorityParamDTO calculateByPriorityParamDTO) throws Exception;

    /**
     * 刷新任务包进度
     * @param packageUid
     */
    Double refreshPackageProgress(String packageUid) throws Exception;

    /**
     * 刷新任务包计划权重
     */
    Double refreshPackagePlanWeight(String packageUid) throws Exception;
}
