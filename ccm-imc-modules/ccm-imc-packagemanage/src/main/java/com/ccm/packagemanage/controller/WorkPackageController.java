package com.ccm.packagemanage.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ccm.modules.packagemanage.ProjectConfigContext;
import com.ccm.packagemanage.domain.*;
import com.ccm.modules.packagemanage.enums.PackageType;
import com.ccm.modules.packagemanage.enums.ProcedureType;
import com.ccm.packagemanage.handler.IPackageProcedureHandler;
import com.ccm.packagemanage.service.IPackageService;
import com.ccm.packagemanage.service.IWorkPackageService;
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
 * @Date 2023/9/12 13:37
 * @PackageName:com.ccm.packagemanage.controller
 * @ClassName: WorkPackageController
 * @Description: TODO
 * @Version 1.0
 */
@Slf4j
@RestController
@Api(tags = "工作包管理")
@RequestMapping("/ccm/workPackageManagement")
public class WorkPackageController {

    @Autowired
    private IWorkPackageService workPackageService;

    @Autowired
    IPackageService packageService;

    @ApiOperation(value = "工作包部分预测/预留并获取预测结果", notes = "检测图纸是否存在,不存在则提示SPM不存在此图纸,存在的进行预测预留操作")
    @PostMapping("existAndCreatePartialStatusRequestForWP")
    public R<TableData<JSONObject>> existAndCreatePartialStatusRequestForWP(@RequestBody PackageProcedureRequest request) {
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
                if (entry.getValue().switchHandle(PackageType.WP, procedureType)) {
                    request.setPackageType(PackageType.WP);
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

    @ApiOperation("获取工作包可选择添加的图纸(根据关联任务包过滤)")
    @RequestMapping(value = "/selectDocumentsForWorkPackage", method = RequestMethod.POST)
    public R<TableData<JSONObject>> selectDocumentsForWorkPackage(@RequestBody QueryByPackageParamDTO documentsQueryByTask) {
        try {
            return R.ok(workPackageService.selectDocumentsForWorkPackage(documentsQueryByTask));
        } catch (Exception ex) {
            log.error(ExceptionUtil.getRootErrorMessage(ex));
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("添加图纸到工作包")
    @RequestMapping(value = "/assignDocumentsIntoWorkPackage", method = RequestMethod.POST)
    public R<String> assignDocumentsIntoWorkPackage(@RequestBody PackageRelDocumentsParamDTO documentDTOs) {
        try {
            workPackageService.assignDocumentsIntoWorkPackage(documentDTOs);
            return R.ok();
        } catch (Exception ex) {
            log.error(ExceptionUtil.getRootErrorMessage(ex));
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("移除工作包下图纸")
    @RequestMapping(value = "/removeDocumentsFromWorkPackage", method = RequestMethod.POST)
    public R<String> removeDocumentsFromWorkPackage(@RequestBody PackageRelDocumentsParamDTO documentDTOs) {
        try {
            workPackageService.removeDocumentsFromWorkPackage(documentDTOs);
            return R.ok();
        } catch (Exception ex) {
            log.error(ExceptionUtil.getRootErrorMessage(ex));
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("根据优先级计算图纸权重")
    @RequestMapping(value = "/calculatePriorityForWorkPackage", method = RequestMethod.POST)
    public R<TableData<JSONObject>> calculatePriorityForWorkPackage(@RequestBody CalculateByPriorityParamDTO calculateByPriorityParamDTO) throws Exception {
        try {
            return R.ok(workPackageService.calculatePriorityForWorkPackage(calculateByPriorityParamDTO));
        } catch (Exception ex) {
            log.error(ExceptionUtil.getRootErrorMessage(ex));
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("获取和工作包相同阶段并且有材料消耗的设计数据")
    @RequestMapping(value = "/selectDesignDataByPurposeAndConsumeMaterialForWP", method = RequestMethod.POST)
    public R<TableData<JSONObject>> selectDesignDataByPurposeAndConsumeMaterialForWP(@RequestBody MaterialQueryParamDTO materialQueryParamDTO) {
        try {
            return R.ok(workPackageService.selectDesignDataByPurposeAndConsumeMaterialForWP(materialQueryParamDTO, true));
        } catch (Exception ex) {
            log.error(ExceptionUtil.getRootErrorMessage(ex));
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("刷新工作包计划权重")
    @RequestMapping(value = "/refreshPackagePlanWeight", method = RequestMethod.GET)
    public R<Double> refreshPackagePlanWeight(@RequestParam String uid) {
        try {
            return R.ok(workPackageService.refreshPackagePlanWeight(uid));
        } catch (Exception ex) {
            log.error(ExceptionUtil.getRootErrorMessage(ex));
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("刷新工作包进度")
    @RequestMapping(value = "/refreshPackageProgress", method = RequestMethod.GET)
    public R<Double> refreshPackageProgress(@RequestParam String uid) {
        try {
            return R.ok(workPackageService.refreshPackageProgress(uid));
        } catch (Exception ex) {
            log.error(ExceptionUtil.getRootErrorMessage(ex));
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("移除工作包下工作步骤")
    @RequestMapping(value = "/removeWorkStepUnderWorkPackage", method = RequestMethod.POST)
    public R<String> removeWorkStepUnderWorkPackage(@RequestBody PackageDeleteRelWokStepDTO packageDeleteRelWokStepDTO) {
        try {
            workPackageService.removeWorkStepUnderWorkPackage(packageDeleteRelWokStepDTO);
            return R.ok();
        } catch (Exception ex) {
            log.error(ExceptionUtil.getRootErrorMessage(ex));
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("移除工作包下设计数据")
    @RequestMapping(value = "/removeComponentsUnderWorkPackage", method = RequestMethod.POST)
    public R<String> removeComponentsUnderWorkPackage(@RequestBody PackageDeleteRelDesignDTO packageDeleteRelDesignDTO) {
        try {
            workPackageService.removeComponentsUnderWorkPackage(packageDeleteRelDesignDTO);
            return R.ok();
        } catch (Exception ex) {
            log.error(ExceptionUtil.getRootErrorMessage(ex));
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }
}
