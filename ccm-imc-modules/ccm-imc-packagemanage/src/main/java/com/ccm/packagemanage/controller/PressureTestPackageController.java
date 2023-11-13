package com.ccm.packagemanage.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ccm.packagemanage.domain.*;
import com.ccm.packagemanage.service.IPressureTestPackageService;
import com.imc.common.core.domain.R;
import com.imc.common.core.utils.ExceptionUtil;
import com.imc.common.core.web.table.TableData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(tags = "试压包管理")
@RequestMapping("/ccm/pressureTestPackageManagement")
public class PressureTestPackageController {

    @Autowired
    IPressureTestPackageService pressureTestPackageService;

    @ApiOperation("获取试压包可选择添加的图纸")
    @RequestMapping(value = "/selectableDocumentsForPressureTestPackage", method = RequestMethod.POST)
    public R<TableData<JSONObject>> selectableDocumentsForPressureTestPackage(@RequestBody QueryByPackageParamDTO documentsQueryByTask) {
        try {
            return R.ok(pressureTestPackageService.selectableDocumentsForPressureTestPackage(documentsQueryByTask));
        } catch (Exception ex) {
            log.error(ExceptionUtil.getRootErrorMessage(ex));
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("根据试压包和图纸获得可选择添加的设计数据")
    @RequestMapping(value = "/selectableComponentsForPressureTestPackage", method = RequestMethod.POST)
    public R<TableData<JSONObject>> selectableComponentsForPressureTestPackage(@RequestBody DesignsQueryByDocumentAndPackageParamDTODTO designsQueryByDocumentAndPackageParamDTO) {
        try {
            return R.ok(pressureTestPackageService.selectableComponentsForPressureTestPackage(designsQueryByDocumentAndPackageParamDTO));
        } catch (Exception ex) {
            log.error(ExceptionUtil.getRootErrorMessage(ex));
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("添加图纸和设计数据到试压包")
    @RequestMapping(value = "/assignDocumentsIntoTestPackage", method = RequestMethod.POST)
    public R<String> assignDocumentsIntoWorkPackage(@RequestBody TestRelDocsAndDesignsParamDTO testRelDocsAndDesignsParamDTO) {
        try {
            pressureTestPackageService.assignDocumentsAndDesignsToPressureTestPackage(testRelDocsAndDesignsParamDTO);
            return R.ok();
        } catch (Exception ex) {
            log.error(ExceptionUtil.getRootErrorMessage(ex));
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("删除图纸和设计数据到试压包")
    @RequestMapping(value = "/removeDocumentsFromPressureTestPackage", method = RequestMethod.POST)
    public R<String> removeDocumentsFromPressureTestPackage(@RequestBody PackageRelDocumentsParamDTO packageRelDocumentsParamDTO) {
        try {
            pressureTestPackageService.removeDocumentsFromPressureTestPackage(packageRelDocumentsParamDTO);
            return R.ok();
        } catch (Exception ex) {
            log.error(ExceptionUtil.getRootErrorMessage(ex));
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("获得试压包下可以添加得材料")
    @RequestMapping(value = "/getPTPMaterialTemplatesByPTPackage", method = RequestMethod.POST)
    public R<TableData<JSONObject>> getPTPMaterialTemplatesByPTPackage(@RequestBody QueryByPackageParamDTO query) {
        try {
            pressureTestPackageService.getPTPMaterialTemplatesByPTPackage(query);
            return R.ok();
        } catch (Exception ex) {
            log.error(ExceptionUtil.getRootErrorMessage(ex));
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("移除试压包下材料")
    @RequestMapping(value = "/deletePTPMaterials", method = RequestMethod.POST)
    public R<String> deletePTPMaterials(@RequestBody PTPackageMaterialsParamDTO ptPackageMaterialsParamDTO) {
        try {
            pressureTestPackageService.deletePTPMaterials(ptPackageMaterialsParamDTO);
            return R.ok();
        } catch (Exception ex) {
            log.error(ExceptionUtil.getRootErrorMessage(ex));
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("为试压包添加材料")
    @RequestMapping(value = "/createPTPackageMaterials", method = RequestMethod.POST)
    public R<String> createPTPackageMaterials(@RequestBody PTPackageMaterialsParamDTO ptPackageMaterialsParamDTO) {
        try {
            pressureTestPackageService.createPTPackageMaterials(ptPackageMaterialsParamDTO);
            return R.ok();
        } catch (Exception ex) {
            log.error(ExceptionUtil.getRootErrorMessage(ex));
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("删除试压包下设计数据")
    @RequestMapping(value = "/removeComponentsFromPackage", method = RequestMethod.POST)
    public R<String> removeComponentsFromPackage(@RequestBody TestRelDocsAndDesignsParamDTO testRelDocsAndDesignsParamDTO) {
        try {
            pressureTestPackageService.removeComponentsFromPackage(testRelDocsAndDesignsParamDTO);
            return R.ok();
        } catch (Exception ex) {
            log.error(ExceptionUtil.getRootErrorMessage(ex));
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }
}
