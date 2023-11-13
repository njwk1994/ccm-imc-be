package com.ccm.packagemanage.service.impl;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ccm.modules.packagemanage.CWAContext;
import com.ccm.modules.packagemanage.WBSContext;
import com.ccm.modules.packagemanage.constant.PackageRequestCommon;
import com.ccm.packagemanage.datasource.DataSourceConfiguration;
import com.ccm.packagemanage.datasource.IJDBCDataSource;
import com.ccm.packagemanage.datasource.JDBCDataSourceFactory;
import com.ccm.packagemanage.datasource.ProcedureParam;
import com.ccm.packagemanage.domain.PackageProcedureRequest;
import com.ccm.packagemanage.domain.ProcedureResult;
import com.ccm.modules.packagemanage.enums.DataSourceType;
import com.ccm.modules.packagemanage.enums.PackageType;
import com.ccm.packagemanage.helpers.IPackageQueryHelper;
import com.ccm.packagemanage.service.IPredictionReservationService;
import com.ccm.packagemanage.service.ITaskPackageService;
import com.ccm.packagemanage.service.IWorkPackageService;
import com.imc.common.core.exception.ServiceException;
import com.imc.common.core.utils.DateUtils;
import com.imc.common.core.utils.StringUtils;
import com.imc.schema.interfaces.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.ccm.modules.COMContext.*;
import static com.ccm.modules.packagemanage.constant.PackageRequestCommon.DRAWING_NUMBERS;

/**
 * @Author kekai.huang
 * @Date 2023/9/12 13:47
 * @PackageName:com.ccm.packagemanage.service.impl
 * @ClassName: PredictionReservationServiceImpl
 * @Description: TODO
 * @Version 1.0
 */
@Slf4j
@Service
public class PredictionReservationServiceImpl implements IPredictionReservationService {

    @Autowired
    IWorkPackageService workPackageService;

    @Autowired
    ITaskPackageService taskPackageService;

    @Autowired
    IPackageQueryHelper packageQueryHelper;

    @Override
    public Map<String, Object> taskExistAndCreateNewStatusRequest(PackageProcedureRequest request) {
        try {
            // 根据名称获取任务包
            ICIMCCMTaskPackage iccmTaskPackage = packageQueryHelper.getTaskPackageByName(request.getRequestName());

            // 获得当前任务包下的所有设计图
            List<ICIMDocumentMaster> documents = iccmTaskPackage.getDocumentList();
            List<String> drawingNumberList = new ArrayList<String>();
            documents.forEach(document -> {
                drawingNumberList.add(document.getName());
            });
            String drawingNumbers = String.join(",", drawingNumberList);
            request.setDrawingNumbers(drawingNumbers);

            // 执行事务
            return this.materialExistAndCreateNewStatusRequest(request);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Map<String, Object> taskExistAndCreatePartialStatusRequest(PackageProcedureRequest request) {
        try {
            IObject taskPackage = packageQueryHelper.getTaskPackageByUid(request.getPackageId());
            Map<String, String> requestParams = this.getRequestParams(request, taskPackage, true);
            request.setDrawingNumbers(requestParams.get(DRAWING_NUMBERS));
            return this.materialExistAndCreatePartialStatusRequest(request,requestParams.get(PackageRequestCommon.COMMODITY_CODES),
                    requestParams.get(PackageRequestCommon.P_SIZE1S),
                    requestParams.get(PackageRequestCommon.P_SIZE2S));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Map<String, Object> workExistAndCreateNewStatusRequest(PackageProcedureRequest request) {
        ICIMCCMWorkPackage iccmWorkPackage = packageQueryHelper.getWorkPackageByName(request.getRequestName());
        // 获得当前工作包下的所有设计图
        List<ICIMDocumentMaster> documents = null;
        try {
            documents = iccmWorkPackage.getDocumentList();
            List<String> drawingNumberList = new ArrayList<String>();
            documents.forEach(document -> {
                drawingNumberList.add(document.getName());
            });
            String drawingNumbers = String.join(",", drawingNumberList);
            request.setDrawingNumbers(drawingNumbers);
            return this.materialExistAndCreateNewStatusRequest(request);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Map<String, Object> workExistAndCreatePartialStatusRequest(PackageProcedureRequest request) {
        try {
            IObject workPackage = packageQueryHelper.getWorkPackageByUid(request.getPackageId());
            Map<String, String> requestParams = this.getRequestParams(request, workPackage, true);
            request.setDrawingNumbers(requestParams.get(DRAWING_NUMBERS));
            return this.materialExistAndCreatePartialStatusRequest(request,requestParams.get(PackageRequestCommon.COMMODITY_CODES),
                    requestParams.get(PackageRequestCommon.P_SIZE1S),
                    requestParams.get(PackageRequestCommon.P_SIZE2S));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * 检测图纸 并预测/预留 获取预测数据
     * @param request
     * @return
     */
    @Override
    public Map<String, Object> materialExistAndCreateNewStatusRequest(PackageProcedureRequest request) {
        if (StringUtils.isBlank(request.getProjectId()) || StringUtils.isBlank(request.getRequestName()) || StringUtils.isBlank(request.getRequestType()) || StringUtils.isBlank(request.getDrawingNumbers())) {
            throw new ServiceException("存在为空参数,请检查参数!项目号:" + request.getProjectId() + " 任务包名称:" + request.getRequestName() + " 预测/预留:" + request.getRequestType() + " 图纸集合:" + request.getDrawingNumbers());
        }
        Map<String, Object> resultMap = new HashMap<>();
        List<ProcedureResult> procedureResults = new ArrayList<>();
        String[] split = request.getDrawingNumbers().split(",");
        try {
            // 检测图纸是否存在
            for (String drawingNumber : split) {
                ProcedureResult result = new ProcedureResult();
                result.setObjectName(drawingNumber);
                int i = doesDrawingExist(request.getProjectId(), drawingNumber);
                result.setExist(i);
                if (!result.isExist()) {
                    result.setMessage("SPM不存在此图纸");
                }
                procedureResults.add(result);
            }
            resultMap.put("drawingInfo", procedureResults);
        } catch (Exception e) {
            log.error(ExceptionUtil.getMessage(e), e);
            throw new ServiceException("检测图纸是否存在时异常! 异常信息:" + ExceptionUtil.getMessage(e));
        }
        // 获取存在的图纸集合
        List<String> existedDrawingNumbers = procedureResults.stream()
                .filter(ProcedureResult::isExist)
                .map(ProcedureResult::getObjectName)
                .collect(Collectors.toList());
        String existedDrawingNumbersStr = StringUtils.join(existedDrawingNumbers, ",");
        ProcedureResult newStatusRequest = null;
        try {
            // 创建预测预留
            if (StringUtils.isNotBlank(existedDrawingNumbersStr)) {
                ProcedureResult result = createNewStatusRequestDnStr(request.getProjectId(),
                        request.getRequestName(), request.getRequestType(), existedDrawingNumbersStr);
                newStatusRequest = result;
                procedureResults.forEach(p -> {
                    if (p.isExist()) {
                        p.setRequestId(result.getRequestId());
                        p.setRequestDate(result.getRequestDate());
                        p.setMessage(result.getMessage());
                    }
                });
                if (null != newStatusRequest.getRequestId() && 0 < newStatusRequest.getRequestId()) {
                    resultMap.put("requestId", newStatusRequest.getRequestId());
                    resultMap.put("requestDate", newStatusRequest.getRequestDate());
                } else {
                    throw new ServiceException("创建预测预留异常! 异常信息: requestId:" + newStatusRequest.getRequestId() + " requestDate:" + newStatusRequest.getRequestDate());
                }
            }
        } catch (Exception e) {
            log.error(ExceptionUtil.getMessage(e), e);
            throw new ServiceException("创建预测预留异常! 异常信息:" + ExceptionUtil.getMessage(e));
        }
        // 获取预测结果
        JSONArray materialStatusResults = null;
        try {
            if (newStatusRequest != null) {
                materialStatusResults = getMaterialStatusResults(request.getProjectId(), newStatusRequest.getRequestId(), request.getSearchColumn(), request.getSearchColumn());
            }
        } catch (Exception e) {
            log.error(ExceptionUtil.getMessage(e), e);
            throw new ServiceException("获取预测结果异常! 异常信息:" + ExceptionUtil.getMessage(e));
        }
        resultMap.put("data", materialStatusResults);
        return resultMap;
    }

    /**
     * 检测图纸 并部分预测/预留 获取预测数据
     * @param request
     * @param commodityCodes
     * @param size1s
     * @param size2s
     * @return
     */
    @Override
    public Map<String, Object> materialExistAndCreatePartialStatusRequest(PackageProcedureRequest request, String commodityCodes, String size1s, String size2s) {
        String params = "项目号:[" + request.getProjectId()
                + "], 预测单号:[" + request.getRequestName()
                + "], 预测/预留:[" + request.getRequestType()
                + "], 存在材料消耗图纸集合:[" + request.getDrawingNumbers()
                + "], 材料编码集合:[" + commodityCodes
                + "], PSize1集合:[" + size1s
                + "], PSize2集合:[" + size2s
                + "]";
        if (StringUtils.isBlank(request.getProjectId()) || StringUtils.isBlank(request.getRequestName()) || StringUtils.isBlank(request.getRequestType()) || StringUtils.isBlank(request.getDrawingNumbers())) {
            throw new ServiceException("存在为空参数,请检查参数!" + params);
        }

        Map<String, Object> resultMap = new HashMap<>();

        List<ProcedureResult> procedureResults = new ArrayList<>();
        String[] split = request.getDrawingNumbers().split(",");
        try {
            // 检测图纸是否存在
            for (String drawingNumber : split) {
                ProcedureResult result = new ProcedureResult();
                result.setObjectName(drawingNumber);
                int i = doesDrawingExist(request.getProjectId(), drawingNumber);
                result.setExist(i);
                if (!result.isExist()) {
                    result.setMessage("SPM不存在此图纸");
                }
                procedureResults.add(result);
            }
            resultMap.put("drawingInfo", procedureResults);
        } catch (Exception e) {
            log.error("检测图纸是否存在时异常!" + params + " 异常信息:{}{}", ExceptionUtil.getMessage(e), ExceptionUtil.getRootCause(e));
            throw new ServiceException("检测图纸是否存在时异常!" + params + " 异常信息:" + ExceptionUtil.getMessage(e));
        }
        // 获取存在的图纸集合
        List<String> existedDrawingNumbers = procedureResults.stream()
                .filter(ProcedureResult::isExist)
                .map(ProcedureResult::getObjectName)
                .collect(Collectors.toList());
        String existedDrawingNumbersStr = StringUtils.join(existedDrawingNumbers, ",");
        ProcedureResult newStatusRequest = null;
        try {
            // 创建预测预留
            if (StringUtils.isNotBlank(existedDrawingNumbersStr)) {
                ProcedureResult result = createPartialStatusRequestStr(request.getProjectId(),
                        request.getRequestName(), request.getRequestType(),
                        existedDrawingNumbersStr, commodityCodes,
                        size1s, size2s);
                newStatusRequest = result;
                procedureResults.forEach(p -> {
                    if (p.isExist()) {
                        p.setRequestId(result.getRequestId());
                        p.setRequestDate(result.getRequestDate());
                        p.setMessage(result.getMessage());
                    }
                });
                if (null != newStatusRequest.getRequestId() && 0 < newStatusRequest.getRequestId()) {
                    resultMap.put("requestId", newStatusRequest.getRequestId());
                    resultMap.put("requestDate", newStatusRequest.getRequestDate());
                } else {
                    throw new Exception("存储过程信息: requestId:" + newStatusRequest.getRequestId()
                            + " requestDate:" + newStatusRequest.getRequestDate()
                            + " requestMessage:" + newStatusRequest.getMessage());
                }
            }
        } catch (Exception e) {
            log.error("创建部分预测预留异常!" + params + " 异常信息:{}{}", ExceptionUtil.getMessage(e), ExceptionUtil.getRootCause(e));
            throw new ServiceException("创建部分预测预留异常! " + params + " 异常信息:" + ExceptionUtil.getMessage(e));
        }
        // 获取预测结果
        JSONArray materialStatusResults = null;
        try {
            if (newStatusRequest != null) {
                materialStatusResults = getMaterialStatusResults(request.getProjectId(), newStatusRequest.getRequestId(), request.getSearchColumn(), request.getSearchValue());
            }
        } catch (Exception e) {
            log.error("获取预测结果异常! 异常信息:{}{}", ExceptionUtil.getMessage(e), ExceptionUtil.getRootCause(e));
            throw new ServiceException("获取预测结果异常!" + params + " 异常信息:" + ExceptionUtil.getMessage(e));
        }
        resultMap.put("data", materialStatusResults);
        resultMap.put("param", params);
        return resultMap;
    }

    /**
     * 检测图纸 并部分预测/预留 获取预测数据 33
     * @param request
     * @param identCode
     * @return
     */
    @Override
    public Map<String, Object> materialExistAndCreatePartialStatusRequest33(PackageProcedureRequest request, String identCode) {
        String params = "项目号:[" + request.getProjectId()
                + "], 预测单号:[" + request.getRequestName()
                + "], 预测/预留:[" + request.getRequestType()
                + "], 存在材料消耗图纸集合:[" + request.getDrawingNumbers()
                + "], identCode集合:[" + identCode
                + "], 仓库集合:[" + request.getWarehouses()
                + "]";
        if (StringUtils.isBlank(request.getProjectId()) || StringUtils.isBlank(request.getRequestName()) || StringUtils.isBlank(request.getRequestType()) || StringUtils.isBlank(request.getDrawingNumbers())) {
            throw new ServiceException("存在为空参数,请检查参数!" + params);
        }

        Map<String, Object> resultMap = new HashMap<>();

        List<ProcedureResult> procedureResults = new ArrayList<>();
        String[] split = request.getDrawingNumbers().split(",");
        try {
            // 检测图纸是否存在
            for (String drawingNumber : split) {
                ProcedureResult result = new ProcedureResult();
                result.setObjectName(drawingNumber);
                int i = doesDrawingExist(request.getProjectId(), drawingNumber);
                result.setExist(i);
                if (!result.isExist()) {
                    result.setMessage("SPM不存在此图纸");
                }
                procedureResults.add(result);
            }
            resultMap.put("drawingInfo", procedureResults);
        } catch (Exception e) {
            log.error("检测图纸是否存在时异常!" + params + " 异常信息:{}{}", ExceptionUtil.getMessage(e), ExceptionUtil.getRootCause(e));
            throw new ServiceException("检测图纸是否存在时异常!" + params + " 异常信息:" + ExceptionUtil.getMessage(e));
        }
        // 获取存在的图纸集合
        List<String> existedDrawingNumbers = procedureResults.stream()
                .filter(ProcedureResult::isExist)
                .map(ProcedureResult::getObjectName)
                .collect(Collectors.toList());
        String existedDrawingNumbersStr = StringUtils.join(existedDrawingNumbers, ",");
        ProcedureResult newStatusRequest = null;
        try {
            // 创建预测预留
            if (StringUtils.isNotBlank(existedDrawingNumbersStr)) {
                ProcedureResult result = createPartialStatusRequest33Str(request.getProjectId(),
                        request.getRequestName(), request.getRequestType(), request.getWarehouses(),
                        existedDrawingNumbersStr, identCode);
                newStatusRequest = result;
                procedureResults.forEach(p -> {
                    if (p.isExist()) {
                        p.setRequestId(result.getRequestId());
                        p.setRequestDate(result.getRequestDate());
                        p.setMessage(result.getMessage());
                    }
                });
                if (null != newStatusRequest.getRequestId() && 0 < newStatusRequest.getRequestId()) {
                    resultMap.put("requestId", newStatusRequest.getRequestId());
                    resultMap.put("requestDate", newStatusRequest.getRequestDate());
                } else {
                    throw new Exception("存储过程信息: requestId:" + newStatusRequest.getRequestId()
                            + " requestDate:" + newStatusRequest.getRequestDate()
                            + " requestMessage:" + newStatusRequest.getMessage());
                }
            }
        } catch (Exception e) {
            log.error("创建部分预测预留异常!" + params + " 异常信息:{}{}", ExceptionUtil.getMessage(e), ExceptionUtil.getRootCause(e));
            throw new ServiceException("创建部分预测预留异常! " + params + " 异常信息:" + ExceptionUtil.getMessage(e));
        }
        // 获取预测结果
        JSONArray materialStatusResults = new JSONArray();
        try {
            if (newStatusRequest != null) {
                materialStatusResults = getMaterialStatusResults(request.getProjectId(), newStatusRequest.getRequestId(), request.getSearchColumn(), request.getSearchValue());
            }
        } catch (Exception e) {
            log.error("获取预测结果异常! 异常信息:{}{}", ExceptionUtil.getMessage(e), ExceptionUtil.getRootCause(e));
            throw new ServiceException("获取预测结果异常!" + params + " 异常信息:" + ExceptionUtil.getMessage(e));
        }
        resultMap.put("data", materialStatusResults);
        resultMap.put("param", params);
        return resultMap;
    }

    @Override
    public ProcedureResult undoMatStatusRequests(String projectId, String requestName, String requestType) throws Exception {

        ProcedureResult procedureResult = new ProcedureResult();
        // 获得连接
        IJDBCDataSource ijdbcDataSource = getIJDBCDataSourceByProjectId(projectId);

        // 调用存储过程
        String call = "{ call M_API_SITE_SPC2.UndoMatStatusRequests(?,?,?,?,?) }";
        LinkedList<ProcedureParam> params = new LinkedList<>();
        params.add(new ProcedureParam(JDBCType.VARCHAR, projectId));
        params.add(new ProcedureParam(JDBCType.VARCHAR, requestName));
        params.add(new ProcedureParam(JDBCType.VARCHAR, requestType));

        LinkedHashMap<String, SQLType> outTypes = new LinkedHashMap<>();
        outTypes.put("r_result", JDBCType.INTEGER);
        outTypes.put("r_message", JDBCType.VARCHAR);

        HashMap<String, Object> result = new HashMap<>();
        if (ijdbcDataSource.executeCall(call, params, outTypes, result)) {
            procedureResult.setRequestId(Integer.valueOf(result.getOrDefault("r_result", "0").toString()));
            procedureResult.setRequestDate(result.getOrDefault("r_message","").toString());
            return procedureResult;
        }
        throw new ServiceException("M_API_SITE_SPC2.CreatePartialStatusRequestStr事务执行失败!");
    }

    @Override
    public Map<String, String> getRequestParams(PackageProcedureRequest request, IObject packageOBJ, boolean needCCOrIC) throws Exception {
        // 获取施工阶段
        String packagePurpose = String.valueOf(packageOBJ.getLatestValue(WBSContext.INTERFACE_CIM_CCM_ICIMCCMWBS, WBSContext.PROPERTY_PURPOSE));

        // 获取施工区域
        String packageCWA = String.valueOf(packageOBJ.getLatestValue(CWAContext.INTERFACE_CIM_CCM_ICIMCCMCWA, CWAContext.PROPERTY_CWA));

        Set<String> drawingNumberList = new HashSet<String>();
        List<String> materialCodeList = new ArrayList<String>();
        List<String> pSize1List = new ArrayList<>();
        List<String> pSize2List = new ArrayList<String>();

        //  MaterialCode+PSize1+PSize2 唯一
        List<String> filters = new ArrayList<String>();
        // 添加选择图纸支持
        if (!StringUtils.isEmpty(request.getDrawingNumbers())) {
            // 手动选择图纸时 根据包消耗材料进行预测预留
            List<ICIMDocumentMaster> docs = packageQueryHelper.getDocumentsByUIDs(Arrays.asList(request.getDrawingNumbers().split(",")));
            for (IObject doc: docs) {
                // 获取图纸下设计数据
                List<ICIMCCMDesignObj> designOBJs = packageQueryHelper.getDesignsByDocumentUIDAndCWA(doc.getUid(), packageCWA);

                for (IObject designOBJ: designOBJs) {
                    Object materialCode = designOBJ.getLatestValue(INTERFACE_CIM_CCM_MPI, PROPERTY_MATERIAL_CODE);

                    // 材料编码为空的跳过
                    if (materialCode == null || StringUtils.isEmpty(materialCode.toString())) {
                        continue;
                    }

                    Object pSize1 = designOBJ.getLatestValue(INTERFACE_CIM_CCM_MPI,"PSize1");
                    pSize1 = pSize1 == null ? "0" : pSize1;
                    Object pSize2 = designOBJ.getLatestValue(INTERFACE_CIM_CCM_MPI,"PSize2");
                    pSize2 = pSize2 == null ? "0" : pSize2;

                    // 通过唯一标识判断过滤
                    String filter = materialCode.toString() + pSize1 + pSize2;
                    if (filters.contains(filter)) {
                        continue;
                    }
                    filters.add(filter);

                    // 查询设计数据下工作步骤
                    List<ICIMWorkStep> workSteps = packageQueryHelper.getWorkStepsByDesignUidAndRopWorkStepPhaseAndConsumeMaterial(designOBJ.getUid(), request.getPackageType(), packagePurpose, false);
                    for (IObject workStep : workSteps) {
                        ICIMWorkStep iWorkStep = workStep.toInterface(ICIMWorkStep.class);
                        // 判断是否耗材
                        if (!iWorkStep.getWSConsumeMaterial()) {
                            continue;
                        }

                        switch (request.getPackageType()) {
                            case TP:
                                break;
                            case WP:
                            default:
                                // 筛选为当前工作包的任务
                                IRel wp2wsRel = workStep.getEnd1Relationships().getRel(REL_WORK_PACKAGE_2_WORK_STEP, false);
                                if (null != wp2wsRel) {
                                    if (!packageOBJ.getUid().equalsIgnoreCase(wp2wsRel.getUid1())) {
                                        continue;
                                    }
                                }
                                break;
                        }

                        if (needCCOrIC) {
                            materialCodeList.add(materialCode.toString());
                            pSize1List.add(pSize1.toString());
                            pSize2List.add(pSize2.toString());
                        }
                    }
                }
            }
        } else {
            List<IObject> workStepsWithSamePurposeAndConsumeMaterial = new ArrayList<>();
            if (PackageType.TP == request.getPackageType()) {
                workStepsWithSamePurposeAndConsumeMaterial = taskPackageService.getWorkStepsWithSamePurposeAndConsumeMaterial(packageOBJ);
            } else {
                ICIMCCMWorkPackage iccmWorkPackage = packageOBJ.toInterface(ICIMCCMWorkPackage.class);
                List<ICIMWorkStep> workSteps = iccmWorkPackage.getWorkStepsWithoutDeleted();
                workStepsWithSamePurposeAndConsumeMaterial = workSteps.stream().filter(ICIMWorkStep::getWSConsumeMaterial).collect(Collectors.toList());
            }

            for (IObject workStepObj : workStepsWithSamePurposeAndConsumeMaterial) {
                // 获取材料编码
                IRel rel = workStepObj.getEnd1Relationships().getRel(REL_DESIGN_OBJ_TO_WORK_STEP, false);
                IObject designDataObj = packageQueryHelper.getDesignByUid(rel.getUid1());
//                IObject designDataObj = workStepObj.getEnd2Relationships().getRel(REL_DESIGN_OBJ_TO_WORK_STEP, false).getTemporaryEnd1();
                // 设计数据 版本状态
                String designRevisionItemOperationState = String.valueOf(designDataObj.getLatestValue(INTERFACE_CIM_REVISION_ITEM, "CIMRevisionItemOperationState"));
                // 过滤删除状态的设计数据
                if ("EN_Deleted".equalsIgnoreCase(designRevisionItemOperationState)) {
                    continue;
                }
                IRel relDesign = designDataObj.getEnd1Relationships().getRel(REL_DOCUMENT_TO_DESIGN_OBJ, false);
                IObject documentObj = packageQueryHelper.getDocumentByUID(rel.getUid1());
//                IObject documentObj = designDataObj.getEnd2Relationships().getRel(REL_DOCUMENT_TO_DESIGN_OBJ,false).getTemporaryEnd1();
                if (needCCOrIC) {
                    Object materialCode = designDataObj.getLatestValue(INTERFACE_CIM_CCM_MPI, PROPERTY_MATERIAL_CODE);

                    // 材料编码为空的跳过
                    if (materialCode == null || StringUtils.isEmpty(materialCode.toString())) {
                        continue;
                    }

                    Object pSize1 = designDataObj.getLatestValue(INTERFACE_CIM_CCM_MPI,"PSize1");
                    pSize1 = pSize1 == null ? "0" : pSize1;
                    Object pSize2 = designDataObj.getLatestValue(INTERFACE_CIM_CCM_MPI,"PSize2");
                    pSize2 = pSize2 == null ? "0" : pSize2;

                    // 通过唯一标识判断过滤
                    String filter = materialCode.toString() + pSize1 + pSize2;
                    if (filters.contains(filter)) {
                        continue;
                    }
                    filters.add(filter);

                    materialCodeList.add(materialCode.toString());
                    pSize1List.add(pSize1.toString());
                    pSize2List.add(pSize2.toString());
                }
                drawingNumberList.add(documentObj.getName());
            }
        }
        String drawingNumbers = String.join(",", drawingNumberList);
        String commodityCodes = String.join(",", materialCodeList);
        String pSize1s = String.join(",", pSize1List);
        String pSize2s = String.join(",", pSize2List);
        String lpAttrValue = packagePurpose.toString().replace("EN_", "").trim();

        Map<String, String> resultParams = new HashMap<>();
        resultParams.put(PackageRequestCommon.DRAWING_NUMBERS, drawingNumbers);
        resultParams.put(PackageRequestCommon.COMMODITY_CODES, commodityCodes);
        resultParams.put(PackageRequestCommon.P_SIZE1S, pSize1s);
        resultParams.put(PackageRequestCommon.P_SIZE2S, pSize2s);
        resultParams.put(PackageRequestCommon.LP_ATTR_VALUE, lpAttrValue);

        return resultParams;
    }

    /**
     * 根据项目id获得数据源连接
     * @param projectId
     * @return
     */
    private IJDBCDataSource getIJDBCDataSourceByProjectId(String projectId) {
        // 获得连接
        IJDBCDataSource ijdbcDataSource = JDBCDataSourceFactory.getJdbcDataSourceById(projectId);
        if (ijdbcDataSource == null) {
            // TODO: 获取项目配置信息
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("url", JDBCDataSourceFactory.getUrl("localhost", "3306", "externaldb", DataSourceType.MYSQL));
            jsonObject.put("username", "root");
            jsonObject.put("password", "password");
            DataSourceConfiguration dataSourceConfiguration = new DataSourceConfiguration();
            dataSourceConfiguration.setData(jsonObject);
            if (!JDBCDataSourceFactory.initJDBCDataSource(dataSourceConfiguration, DataSourceType.MYSQL, projectId)) {
                throw new ServiceException("项目数据库连接失败!");
            }
            ijdbcDataSource = JDBCDataSourceFactory.getJdbcDataSourceById(projectId);
        }

        return ijdbcDataSource;
    }

    /**
     * 判断图纸是否存在
     * @param projectId
     * @param drawingNumber
     * @return
     */
    private int doesDrawingExist(String projectId, String drawingNumber) {
        // 获得连接
        IJDBCDataSource ijdbcDataSource = getIJDBCDataSourceByProjectId(projectId);

        // 调用存储过程
        String call = "{ call M_API_SITE_SPC2.DoesDrawingExist(?,?,?) }";
        LinkedList<ProcedureParam> params = new LinkedList<>();
        params.add(new ProcedureParam(JDBCType.VARCHAR, projectId));
        params.add(new ProcedureParam(JDBCType.VARCHAR, drawingNumber));
        LinkedHashMap<String, SQLType> outTypes = new LinkedHashMap<>();
        outTypes.put("message", JDBCType.INTEGER);
        HashMap<String, Object> result = new HashMap<>();
        if (ijdbcDataSource.executeCall(call, params, outTypes, result)) {
            return result.get("message") == null ? 0 : Integer.valueOf(result.get("message").toString());
        }
        throw new ServiceException("M_API_SITE_SPC2.DoesDrawingExist事务执行失败!");
    }

    /**
     * 创建预测预留
     * @param projectId      项目ID
     * @param requestName    TWP编号
     * @param requestType    FR是预测，RR是预留
     * @param drawingNumbers 图纸号集合数组
     * @return
     */
    private ProcedureResult createNewStatusRequestDnStr(String projectId,
                                                String requestName,
                                                String requestType,
                                                String drawingNumbers
    ) {
        ProcedureResult procedureResult = new ProcedureResult();
        // 获得连接
        IJDBCDataSource ijdbcDataSource = getIJDBCDataSourceByProjectId(projectId);

        // 调用存储过程
        String call = "{ call M_API_SITE_SPC2.CreateNewStatusRequestDnStr(?,?,?,?,?,?,?) }";
        LinkedList<ProcedureParam> params = new LinkedList<>();
        params.add(new ProcedureParam(JDBCType.VARCHAR, projectId));
        params.add(new ProcedureParam(JDBCType.VARCHAR, requestName));
        params.add(new ProcedureParam(JDBCType.VARCHAR, requestType));
        params.add(new ProcedureParam(JDBCType.VARCHAR, drawingNumbers));

        LinkedHashMap<String, SQLType> outTypes = new LinkedHashMap<>();
        outTypes.put("requestId", JDBCType.INTEGER);
        outTypes.put("requestDate", JDBCType.VARCHAR);
        outTypes.put("message", JDBCType.VARCHAR);

        HashMap<String, Object> result = new HashMap<>();
        if (ijdbcDataSource.executeCall(call, params, outTypes, result)) {
            procedureResult.setRequestId(Integer.valueOf(result.getOrDefault("requestId", "0").toString()));
            procedureResult.setRequestDate(result.getOrDefault("requestDate","").toString());
            procedureResult.setMessage(result.getOrDefault("message","").toString());
            return procedureResult;
        }
        throw new ServiceException("M_API_SITE_SPC2.CreateNewStatusRequestDnStr事务执行失败!");
    }

    /**
     * 创建部分预测预留
     * @param projectId      项目ID
     * @param requestName    TWP编号
     * @param requestType    FR是预测，RR是预留
     * @param drawingNumbers 图纸号集合数组
     * @param commodityCodes
     * @param size1s
     * @param size2s
     * @return
     */
    private ProcedureResult createPartialStatusRequestStr(String projectId, String requestName, String requestType,
                                                         String drawingNumbers, String commodityCodes,
                                                         String size1s, String size2s) {
        ProcedureResult procedureResult = new ProcedureResult();
        // 获得连接
        IJDBCDataSource ijdbcDataSource = getIJDBCDataSourceByProjectId(projectId);

        // 调用存储过程
        String call = "{ call M_API_SITE_SPC2.CreatePartialStatusRequestStr(?,?,?,?,?,?,?,?,?,?) }";
        LinkedList<ProcedureParam> params = new LinkedList<>();
        params.add(new ProcedureParam(JDBCType.VARCHAR, projectId));
        params.add(new ProcedureParam(JDBCType.VARCHAR, requestName));
        params.add(new ProcedureParam(JDBCType.VARCHAR, requestType));
        params.add(new ProcedureParam(JDBCType.VARCHAR, drawingNumbers));
        params.add(new ProcedureParam(JDBCType.VARCHAR, commodityCodes));
        params.add(new ProcedureParam(JDBCType.VARCHAR, size1s));
        params.add(new ProcedureParam(JDBCType.VARCHAR, size2s));

        LinkedHashMap<String, SQLType> outTypes = new LinkedHashMap<>();
        outTypes.put("requestId", JDBCType.INTEGER);
        outTypes.put("requestDate", JDBCType.VARCHAR);
        outTypes.put("message", JDBCType.VARCHAR);

        HashMap<String, Object> result = new HashMap<>();
        if (ijdbcDataSource.executeCall(call, params, outTypes, result)) {
            procedureResult.setRequestId(Integer.valueOf(result.getOrDefault("requestId", "0").toString()));
            procedureResult.setRequestDate(result.getOrDefault("requestDate","").toString());
            procedureResult.setMessage(result.getOrDefault("message","").toString());
            return procedureResult;
        }
        throw new ServiceException("M_API_SITE_SPC2.CreatePartialStatusRequestStr事务执行失败!");
    }

    /**
     * 创建部分预测预留33
     * @param projectId      项目ID
     * @param requestName    TWP编号
     * @param requestType    FR是预测，RR是预留
     * @param warehouses     仓库
     * @param drawingNumbers 图纸号集合数组
     * @return
     */
    public ProcedureResult createPartialStatusRequest33Str(String projectId, String requestName, String requestType,
                                                           String warehouses, String drawingNumbers, String identCode) {
        ProcedureResult procedureResult = new ProcedureResult();
        // 获得连接
        IJDBCDataSource ijdbcDataSource = getIJDBCDataSourceByProjectId(projectId);

        // 调用存储过程
        String call = "{ call M_API_SITE_SPC2.CreatePartialStatusRequest33Str(?,?,?,?,?,?,?,?,?) }";
        LinkedList<ProcedureParam> params = new LinkedList<>();
        params.add(new ProcedureParam(JDBCType.VARCHAR, projectId));
        params.add(new ProcedureParam(JDBCType.VARCHAR, requestName));
        params.add(new ProcedureParam(JDBCType.VARCHAR, requestType));
        params.add(new ProcedureParam(JDBCType.VARCHAR, warehouses));
        params.add(new ProcedureParam(JDBCType.VARCHAR, drawingNumbers));
        params.add(new ProcedureParam(JDBCType.VARCHAR, identCode));

        LinkedHashMap<String, SQLType> outTypes = new LinkedHashMap<>();
        outTypes.put("requestId", JDBCType.INTEGER);
        outTypes.put("requestDate", JDBCType.VARCHAR);
        outTypes.put("message", JDBCType.VARCHAR);

        HashMap<String, Object> result = new HashMap<>();
        if (ijdbcDataSource.executeCall(call, params, outTypes, result)) {
            procedureResult.setRequestId(Integer.valueOf(result.getOrDefault("requestId", "0").toString()));
            procedureResult.setRequestDate(result.getOrDefault("requestDate","").toString());
            procedureResult.setMessage(result.getOrDefault("message","").toString());
            return procedureResult;
        }
        throw new ServiceException("M_API_SITE_SPC2.CreatePartialStatusRequestStr事务执行失败!");
    }

    private JSONArray getMaterialStatusResults(String projectId, int requestId, String searchColumn, String searchValue) {
        JSONArray results = new JSONArray();

        // 获得连接
        IJDBCDataSource ijdbcDataSource = getIJDBCDataSourceByProjectId(projectId);
        String call = "{ call M_API_SITE_SPC2.GetMaterialStatusResults(?,?,?,?) }";
        LinkedList<ProcedureParam> params = new LinkedList<>();
        params.add(new ProcedureParam(JDBCType.VARCHAR, requestId));
        params.add(new ProcedureParam(JDBCType.VARCHAR, searchColumn));
        params.add(new ProcedureParam(JDBCType.VARCHAR, searchValue));

        LinkedHashMap<String, SQLType> outTypes = new LinkedHashMap<>();
        outTypes.put("request_data_cursor", JDBCType.REF_CURSOR);

        HashMap<String, Object> result = new HashMap<>();
        if (ijdbcDataSource.executeCall(call, params, outTypes, result)) {
            try (ResultSet rs = (ResultSet) result.get("request_data_cursor")) {
                results = extractJSONArray(rs);
                return results;
            } catch (Exception e) {
                throw new ServiceException("get request_data_cursor error");
            }
        }
        throw new ServiceException("M_API_SITE_SPC2.GetMaterialStatusResults事务执行失败!");
    }

    private JSONArray extractJSONArray(ResultSet rs) throws SQLException, ParseException {
        ResultSetMetaData md = rs.getMetaData();
        int num = md.getColumnCount();
        JSONArray array = new JSONArray();
        while (rs.next()) {
            JSONObject mapOfColValues = new JSONObject();
            Object o = new Object();
            for (int i = 1; i <= num; i++) {
                if (md.getColumnName(i).equals("ETA_DATE") || md.getColumnName(i).equals("REQ_SITE_DATE")) {
                    if (null != rs.getObject(i)) {
                        String s = rs.getObject(i).toString();
                        java.util.Date date = DateUtils.parseDate(s, "yyyy-MM-dd HH:mm:ss");
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                        o = simpleDateFormat.format(date);
                    } else {
                        o = "";
                    }
                } else {
                    o = rs.getObject(i);
                }
                mapOfColValues.put(md.getColumnName(i), o);
            }
            array.add(mapOfColValues);
        }
        return array;
    }
}
