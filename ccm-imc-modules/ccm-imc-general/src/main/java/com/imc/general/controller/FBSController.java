package com.imc.general.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.imc.common.core.domain.R;
import com.imc.general.service.IFBSService;
import com.imc.general.vo.FBSChildrenQueryVo;
import com.imc.general.vo.FBSTreeNodeVo;
import com.imc.general.vo.FBSTreeQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = "FBS目录树")
@RestController
@RequestMapping("/fbs")
public class FBSController {
    @Autowired
    private IFBSService ifbsService;

    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "获取FBS结构树", notes = "获取FBS结构树")
    @PostMapping(value = "/getTreeList")
    public R<List<FBSTreeNodeVo>> getTreeList(@RequestBody FBSTreeQueryVo model) throws Exception {
        List<FBSTreeNodeVo> list = ifbsService.getTreeList(model.getExtraRelDefs(), model.getIsActive());
        return R.ok(list);
    }

    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "获取FBS结构树（懒加载）", notes = "根据uid和类型定义获取FBS结构树")
    @PostMapping(value = "/getChildrenList")
    public R<List<FBSTreeNodeVo>> getChildrenList(@Validated @RequestBody FBSChildrenQueryVo model) throws Exception {
        List<FBSTreeNodeVo> list = ifbsService.getChildrenNodeList(model.getUid(), model.getClassDefinitionUID(), model.getExtraRelDefs(), model.getIsActive());
        return R.ok(list);
    }
}
