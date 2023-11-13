package com.ccm.packagemanage.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ccm.packagemanage.domain.ConstructionTypesQueryParamDTO;
import com.ccm.packagemanage.service.IPackageService;
import com.imc.common.core.domain.R;
import com.imc.common.core.utils.ExceptionUtil;
import com.imc.common.core.web.table.TableData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "通用包管理")
@RequestMapping("/ccm/packages")
public class PackageController {

    @Autowired
    IPackageService packageService;

    @ApiOperation("获取包对应的施工数据分类")
    @PostMapping("/selectPackageConstructionTypes")
    public R<TableData<JSONObject>> selectPackageConstructionTypes(@RequestBody ConstructionTypesQueryParamDTO constructionTypesQueryParamDTO) {
        try {
            return R.ok(packageService.selectPackageConstructionTypes(constructionTypesQueryParamDTO));
        } catch (Exception ex) {
            log.error(ExceptionUtil.getRootErrorMessage(ex));
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("获取图纸对应的施工数据分类")
    @GetMapping("/selectDocumentConstructionTypes")
    public R<TableData<JSONObject>> selectDocumentConstructionTypes(@RequestParam String documentUID) {
        try {
            return R.ok(packageService.selectDocumentConstructionTypes(documentUID, false));
        } catch (Exception ex) {
            log.error(ExceptionUtil.getRootErrorMessage(ex));
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }
}
