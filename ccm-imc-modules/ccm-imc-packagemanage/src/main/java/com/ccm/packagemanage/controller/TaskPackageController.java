package com.ccm.packagemanage.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ccm.modules.packagemanage.ProjectConfigContext;
import com.ccm.packagemanage.domain.*;
import com.ccm.modules.packagemanage.enums.PackageType;
import com.ccm.modules.packagemanage.enums.ProcedureType;
import com.ccm.packagemanage.handler.IPackageProcedureHandler;
import com.ccm.packagemanage.service.IPackageService;
import com.ccm.packagemanage.service.ITaskPackageService;
import com.imc.common.core.domain.R;
import com.imc.common.core.utils.ExceptionUtil;
import com.imc.common.core.utils.StringUtils;
import com.imc.common.core.web.table.TableData;
import com.imc.framework.context.Context;
import com.imc.framework.model.qe.QueryRequest;
import com.imc.framework.utils.SpringBeanUtil;
import com.imc.schema.interfaces.ICIMCCMProjectConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author kekai.huang
 * @Date 2023/9/12 13:18
 * @PackageName:com.ccm.packagemanage.controller
 * @ClassName: TaskPackageController
 * @Description: TODO
 * @Version 1.0
 */
@Slf4j
@RestController
@Api(tags = "任务包管理")
@RequestMapping("/ccm/taskpackagemanagement")
public class TaskPackageController {

    @Autowired
    ITaskPackageService taskPackageService;

    @Autowired
    IPackageService packageService;

    @ApiOperation(value = "任务包部分预测/预留并获取预测结果", notes = "检测图纸是否存在,不存在则提示SPM不存在此图纸,存在的进行预测预留操作")
    @PostMapping("existAndCreatePartialStatusRequestForTP")
    public R<TableData<JSONObject>> existAndCreatePartialStatusRequestForTP(@RequestBody PackageProcedureRequest request) {
        try {

            // 获取项目配置
            QueryRequest queryRequest = new QueryRequest();
            queryRequest.addClassDefForQuery(ProjectConfigContext.CLASS_CIM_CCM_PROJECT_CONFIG);
            List<ICIMCCMProjectConfig> projectConfigs = Context.Instance.getQueryHelper().query(queryRequest, ICIMCCMProjectConfig.class);
            if (projectConfigs.size() <= 0) {
                throw new RuntimeException("获取项目配置失败!");
            }
            String projectId = projectConfigs.get(0).getSPMProject();
            if (StringUtils.isEmpty(projectId)) {
                throw new RuntimeException("获取项目配置的项目号失败!");
            }

            // 存储过程类型
            String value = projectConfigs.get(0).getProcedureType();
            if (StringUtils.isEmpty(value)){
                throw new RuntimeException("存储过程类型配置值获取失败!请检查项目配置!");
            }
            String procedureTypeStr = value.toString();
            ProcedureType procedureType = ProcedureType.valueOf(procedureTypeStr);

            // 调用存储过程
            Map<String, IPackageProcedureHandler> handlers = SpringBeanUtil.getApplicationContext().getBeansOfType(IPackageProcedureHandler.class);
            for (Map.Entry<String, IPackageProcedureHandler> entry : handlers.entrySet()) {
               if (entry.getValue().switchHandle(PackageType.TP, procedureType)) {
                   request.setPackageType(PackageType.TP);
                   request.setProcedureType(procedureType);
                   return R.ok(packageService.getPartialStatusRequestByObjects(entry.getValue().predictionReservation(request)));
               }
            }
            return R.fail();
        } catch (Exception exception) {
            log.error("任务包部分预测/预留并获取预测结果失败!{}{}", exception.getMessage(), exception.getMessage());
            return R.fail("任务包部分预测/预留并获取预测结果失败!" + exception.getMessage());
        }
    }

    @ApiOperation("获取任务包可选择添加的图纸")
    @RequestMapping(value = "/selectDocumentsForTaskPackage", method = RequestMethod.POST)
    public R<TableData<JSONObject>> selectDocumentsForTaskPackage(@RequestBody QueryByPackageParamDTO documentsQueryByTask) {
        try {
            return R.ok(taskPackageService.selectDocumentsForTaskPackage(documentsQueryByTask));
        } catch (Exception ex) {
            log.error(ExceptionUtil.getRootErrorMessage(ex));
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("添加图纸到任务包")
    @RequestMapping(value = "/assignDocumentsIntoTaskPackage", method = RequestMethod.POST)
    public R<String> assignDocumentsIntoTaskPackage(@RequestBody PackageRelDocumentsParamDTO documentDTOs) {
        try {
            taskPackageService.assignDocumentsIntoTaskPackage(documentDTOs);
            return R.ok();
        } catch (Exception ex) {
            log.error(ExceptionUtil.getRootErrorMessage(ex));
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("移除任务包下图纸")
    @RequestMapping(value = "/removeDocumentsFromTaskPackage", method = RequestMethod.POST)
    public R<String> removeDocumentsFromTaskPackage(@RequestBody PackageRelDocumentsParamDTO documentDTOs) {
        try {
            taskPackageService.removeDocumentsFromTaskPackage(documentDTOs);
            return R.ok();
        } catch (Exception ex) {
            log.error(ExceptionUtil.getRootErrorMessage(ex));
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("获取和任务包相同阶段并且有材料消耗的设计数据")
    @RequestMapping(value = "/selectDesignDataByPurposeAndConsumeMaterial", method = RequestMethod.POST)
    public R<TableData<JSONObject>> selectDesignDataByPurposeAndConsumeMaterial(@RequestBody MaterialQueryParamDTO materialQueryParamDTO) {
        try {
            return R.ok(taskPackageService.selectDesignDataByPurposeAndConsumeMaterial(materialQueryParamDTO, true));
        } catch (Exception ex) {
            log.error(ExceptionUtil.getRootErrorMessage(ex));
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("根据优先级计算图纸权重")
    @RequestMapping(value = "/executePriorityForTaskPackage", method = RequestMethod.POST)
    public R<TableData<JSONObject>> executePriorityForTaskPackage(@RequestBody CalculateByPriorityParamDTO calculateByPriorityParamDTO) throws Exception {
        try {
            return R.ok(taskPackageService.calculatePriorityForTaskPackage(calculateByPriorityParamDTO));
        } catch (Exception ex) {
            log.error(ExceptionUtil.getRootErrorMessage(ex));
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("刷新任务包计划权重")
    @RequestMapping(value = "/refreshPackagePlanWeight", method = RequestMethod.GET)
    public R<Double> refreshPackagePlanWeight(@RequestParam String uid) {
        try {
            return R.ok(taskPackageService.refreshPackagePlanWeight(uid));
        } catch (Exception ex) {
            log.error(ExceptionUtil.getRootErrorMessage(ex));
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("刷新任务包进度")
    @RequestMapping(value = "/refreshPackageProgress", method = RequestMethod.GET)
    public R<Double> refreshPackageProgress(@RequestParam String uid) {
        try {
            return R.ok(taskPackageService.refreshPackageProgress(uid));
        } catch (Exception ex) {
            log.error(ExceptionUtil.getRootErrorMessage(ex));
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }
}
