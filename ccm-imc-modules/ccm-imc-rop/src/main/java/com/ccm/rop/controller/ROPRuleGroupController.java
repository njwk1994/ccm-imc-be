package com.ccm.rop.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ccm.rop.service.IROPRuleGroupItemService;
import com.ccm.rop.service.IROPRuleGroupService;
import com.imc.common.core.domain.R;
import com.imc.common.core.model.parameters.ObjParam;
import com.imc.common.core.utils.ExceptionUtil;
import com.imc.common.core.utils.MessageUtils;
import com.imc.common.core.web.table.TableData;
import com.imc.common.security.annotation.InnerAuth;
import com.imc.schema.interfaces.IObject;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/rop/ruleGroupItem")
public class ROPRuleGroupController {


    private final IROPRuleGroupService ropRuleGroupService;

    public ROPRuleGroupController(IROPRuleGroupService ropRuleGroupService) {
        this.ropRuleGroupService = ropRuleGroupService;
    }


    @InnerAuth
    @ApiOperation("获取施工分类的规则组信息")
    @GetMapping("/find")
    public R<List<IObject>>getRuleGroupItem() {
        try {
            return R.ok(ropRuleGroupService.findRuleGroup());
        } catch (Exception ex) {
            log.error("-----获取施工分类的规则组信息失败!-----", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }



    @PostMapping("addOrUpdate")
    @ApiOperation("新建或更新规则组")
    public R<Boolean> creatRuleGroup(JSONObject param){
        try {
            this.ropRuleGroupService.addRuleGroup(param);
            return R.ok(true, MessageUtils.get("opt.success"));
        } catch (Exception ex) {
            log.error("-----新建或更新规则组失败!-----", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }


    @PostMapping("/remove")
    @ApiOperation("删除规则组")
    public R<Boolean> deleteRuleGroup(ObjParam param){
        try {
            this.ropRuleGroupService.deleteROPTemplateInfo(param);
            return R.ok(true, MessageUtils.get("opt.success"));
        } catch (Exception ex) {
            log.error("-----删除规则组失败!-----", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

}
