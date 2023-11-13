package com.ccm.document.controller;

import com.ccm.document.domain.DrawingLoaderPercentageDTO;
import com.ccm.document.domain.PackageRevisionParamDTO;
import com.ccm.document.service.IRevisedService;
import com.imc.common.core.domain.R;
import com.imc.common.core.model.parameters.GeneralQueryParam;
import com.imc.common.core.utils.ExceptionUtil;
import com.imc.common.core.web.table.TableData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description：TODO
 * @author： kekai.huang
 * @create： 2023/11/9 11:19
 */
@Slf4j
@RestController
@Api(tags = "升版管理")
@RequestMapping("/ccm/revisedService")
public class CCMRevisedServiceController {

    @Autowired
    IRevisedService revisedService;

    @ApiOperation("包升版操作")
    @PostMapping("/packageRevisionHandler")
    public R<String> packageRevisionHandler(@RequestBody PackageRevisionParamDTO packageRevisionParamDTO) {
        try {
            revisedService.packageRevisionHandler(packageRevisionParamDTO);
            return R.ok();
        } catch (Exception ex) {
            log.error(ExceptionUtil.getRootErrorMessage(ex));
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("包升版确认")
    @PostMapping("/packageConfirmRevision")
    public R<String> packageConfirmRevision(@RequestBody PackageRevisionParamDTO packageRevisionParamDTO) {
        try {
            revisedService.packageConfirmRevision(packageRevisionParamDTO);
            return R.ok();
        } catch (Exception ex) {
            log.error(ExceptionUtil.getRootErrorMessage(ex));
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("图纸升版确认")
    @GetMapping("/documentConfirmRevision")
    public R<String> documentConfirmRevision(@RequestParam String documentUID) {
        try {
            revisedService.documentConfirmRevision(documentUID);
            return R.ok();
        } catch (Exception ex) {
            log.error(ExceptionUtil.getRootErrorMessage(ex));
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }
}
