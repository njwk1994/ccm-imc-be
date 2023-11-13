package com.ccm.packagemanage.controller;

import com.ccm.modules.packagemanage.ProjectConfigContext;
import com.ccm.packagemanage.domain.PackageProcedureRequest;
import com.ccm.packagemanage.domain.ProcedureResult;
import com.ccm.packagemanage.service.IPredictionReservationService;
import com.imc.common.core.domain.R;
import com.imc.common.core.utils.ExceptionUtil;
import com.imc.common.core.utils.StringUtils;
import com.imc.framework.context.Context;
import com.imc.framework.model.qe.QueryRequest;
import com.imc.schema.interfaces.ICIMCCMProjectConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author kekai.huang
 * @Date 2023/9/19 13:09
 * @PackageName:com.ccm.packagemanage.controller
 * @ClassName: CCMMaterialsController
 * @Description: TODO
 * @Version 1.0
 */
@RestController
@RequestMapping("/procedure")
@Api(tags = "预测预留接口")
@Slf4j
public class CCMMaterialsController {

    @Autowired
    IPredictionReservationService iPredictionReservationService;

    @ApiOperation(value = "取消预测预留", notes = "取消预测预留")
    @PostMapping("undoMatStatusRequests")
    public R<ProcedureResult> undoMatStatusRequests(@RequestBody PackageProcedureRequest packageProcedureRequest) {
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

            ProcedureResult requestInfo = iPredictionReservationService.undoMatStatusRequests(projectId,
                    packageProcedureRequest.getRequestName(), packageProcedureRequest.getRequestType());
            return R.ok(requestInfo);
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            return R.fail(ExceptionUtil.getExceptionMessage(exception));
        }
    }
}
