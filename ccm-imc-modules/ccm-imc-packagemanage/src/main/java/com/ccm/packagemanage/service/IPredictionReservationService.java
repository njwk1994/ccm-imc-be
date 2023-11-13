package com.ccm.packagemanage.service;

import com.ccm.packagemanage.domain.PackageProcedureRequest;
import com.ccm.packagemanage.domain.ProcedureResult;
import com.imc.schema.interfaces.IObject;

import java.util.Map;

/**
 * @Author kekai.huang
 * @Date 2023/9/12 13:45
 * @PackageName:com.ccm.packagemanage.service
 * @ClassName: IPredictionReservationService
 * @Description: 预测预留相关操作
 * @Version 1.0
 */
public interface IPredictionReservationService {
    /**
     * 任务包 检测图纸 并预测/预留 获取预测数据
     * @param request
     * @return
     */
    Map<String, Object>  taskExistAndCreateNewStatusRequest(PackageProcedureRequest request);

    /**
     * 任务包 检测图纸 并部分预测/预留 获取预测数据
     * @return
     */
    Map<String, Object> taskExistAndCreatePartialStatusRequest(PackageProcedureRequest request);

    /**
     * 工作包 检测图纸 并预测/预留 获取预测数据
     * @param request
     * @return
     */
    Map<String, Object> workExistAndCreateNewStatusRequest(PackageProcedureRequest request);

    /**
     * 工作包 检测图纸 并部分预测/预留 获取预测数据
     * @return
     */
    Map<String, Object> workExistAndCreatePartialStatusRequest(PackageProcedureRequest request);

    /**
     * 检测图纸 并预测/预留 获取预测数据
     * @param request
     * @return
     */
    Map<String, Object> materialExistAndCreateNewStatusRequest(PackageProcedureRequest request);

    /**
     * 检测图纸 并部分预测/预留 获取预测数据
     * @param request
     * @return
     */
    Map<String, Object> materialExistAndCreatePartialStatusRequest(PackageProcedureRequest request, String commodityCodes, String size1s, String size2s);

    /**
     * 检测图纸 并部分预测/预留 获取预测数据 33
     * @param request
     * @return
     */
    Map<String, Object> materialExistAndCreatePartialStatusRequest33(PackageProcedureRequest request, String identCode);

    /**
     * 获取包用于预测预留的参数
     * @param request
     * @param needCCOrIC
     * @return
     */
    Map<String, String> getRequestParams(PackageProcedureRequest request, IObject packageOBJ, boolean needCCOrIC) throws Exception;

    /**
     * 取消预测预留
     *
     * @param projectId   项目ID
     * @param requestName TWP编号
     * @param requestType FR是预测，RR是预留
     * @return
     * @throws Exception
     */
    ProcedureResult undoMatStatusRequests(String projectId, String requestName, String requestType) throws Exception;
}
