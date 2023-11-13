package com.imc.general.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.imc.common.core.domain.R;
import com.imc.common.core.model.render.TreeObj;
import com.imc.common.core.utils.ExceptionUtil;
import com.imc.common.core.web.option.SelectionOption;
import com.imc.general.service.ITreeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HuangTao
 * @version 1.0
 * @since 2022/10/26 10:35
 */
@Slf4j
@RestController
@Api(tags = "目录树配置相关")
@RequestMapping("/tree")
public class TreeController {

    private final ITreeService treeService;

    public TreeController(ITreeService treeService) {
        this.treeService = treeService;
    }

    @ApiOperation("获取配置树的类型")
    @PostMapping("/getClassDefs")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "类型UID或者描述", value = "classDefName", dataTypeClass = String.class, paramType = "body", required = false)
    })
    public R<SelectionOption> getClassDefs(@RequestBody JSONObject body) {
        try {
            SelectionOption selectionOption = this.treeService.getClassDefs(body.getString("classDefName"));
            return R.ok(selectionOption);
        } catch (Exception ex) {
            log.error("---------获取配置树的类型失败!------", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("获取对应类型可选择的树配置项目标属性")
    @PostMapping("/getPropertiesForConfigItem")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "类型UID", value = "classDefinitionUid", dataTypeClass = String.class, paramType = "body", required = true),
            @ApiImplicitParam(name = "属性名称", value = "propertyName", dataTypeClass = String.class, paramType = "body", required = false)
    })
    public R<SelectionOption> getPropertiesForConfigItem(@RequestBody JSONObject body) {
        try {
            SelectionOption selectionOption = this.treeService.getPropertiesForConfigItem(body.getString("classDefinitionUid"), body.getString("propertyName"));
            return R.ok(selectionOption);
        } catch (Exception ex) {
            log.error("---------根据ClassDefUid获取对应类型可选择的树配置项目标属性失败!------", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("获取对应类型可选择的树配置")
    @PostMapping("/getTreeConfigSelectionOption")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "类型UID", value = "classDefinitionUid", dataTypeClass = String.class, paramType = "body", required = true),
            @ApiImplicitParam(name = "配置名称", value = "configName", dataTypeClass = String.class, paramType = "body", required = false)
    })
    public R<SelectionOption> getTreeConfigSelectionOption(@RequestBody JSONObject body) {
        try {
            SelectionOption selectionOption = this.treeService.getTreeConfigSelectionOption(body.getString("classDefinitionUid"), body.getString("configName"));
            return R.ok(selectionOption);
        } catch (Exception ex) {
            log.error("---------根据ClassDefUid获取对应类型可选择的树配置失败!------", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("根据树配置生成树")
    @PostMapping("/getTreeByConfigUID")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "树配置UID", value = "uid", dataTypeClass = String.class, paramType = "body", required = true)
    })
    public R<TreeObj> getTreeByConfigUID(@RequestBody JSONObject body) {
        try {
            TreeObj treeObj = this.treeService.getTreeByConfigUID(body.getString("uid"));
            return R.ok(treeObj);
        } catch (Exception ex) {
            log.error("---------根据树配置生成树失败!------", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("一次性生成树配置和配置项")
    @PostMapping("/createTreeConfigAndItems")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "treeConf", value = "treeConf", dataTypeClass = JSONObject.class, paramType = "body", required = true),
            @ApiImplicitParam(name = "treeConfItems", value = "treeConfItems", dataTypeClass = JSONArray.class, paramType = "body", required = true)
    })
    public R<String> createTreeConfigAndItems(@RequestBody JSONObject body) {
        try {
            String treeId = this.treeService.createTreeConfigAndItems(body);
            return R.ok(treeId);
        } catch (Exception ex) {
            log.error("---------一次性生成树配置和配置项失败!------", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }
}
