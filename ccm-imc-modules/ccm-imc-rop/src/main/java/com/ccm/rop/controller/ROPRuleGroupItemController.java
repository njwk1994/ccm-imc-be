package com.ccm.rop.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ccm.rop.service.IROPRuleGroupItemService;
import com.imc.common.core.domain.R;
import com.imc.common.core.model.parameters.DeleteParam;
import com.imc.common.core.utils.ExceptionUtil;
import com.imc.common.core.utils.MessageUtils;
import com.imc.common.core.web.table.TableData;
import com.imc.common.security.annotation.InnerAuth;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/rop/groupItem")
public class ROPRuleGroupItemController {


    private final IROPRuleGroupItemService ropRuleGroupItemService;

    public ROPRuleGroupItemController(IROPRuleGroupItemService ropRuleGroupItemService) {
        this.ropRuleGroupItemService = ropRuleGroupItemService;
    }

    @InnerAuth
    @ApiOperation("获取规则条目")
    @GetMapping("/select")
    public R<TableData<JSONObject>>getRuleGroupItem() {
        try {
            return R.ok(ropRuleGroupItemService.getRuleGroupItem());
        } catch (Exception ex) {
            log.error("-----获取规则条目失败!-----", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @PostMapping("saveOrExit")
    @ApiOperation("新建或更新规则组条目")
    public R<Boolean> creatRuleGroup(JSONObject param){
        try {
            this.ropRuleGroupItemService.addOrUpdate(param);
            return R.ok(true, MessageUtils.get("opt.success"));
        } catch (Exception ex) {
            log.error("-----新建或更新规则组条目失败!-----", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @PostMapping("delItem")
    @ApiOperation("删除规则组条目")
    public R<Boolean> deleteRuleGroup(DeleteParam param){
        try {
            this.ropRuleGroupItemService.deleteROPRuleGroupItem(param);
            return R.ok(true, MessageUtils.get("opt.success"));
        } catch (Exception ex) {
            log.error("-----删除规则组条目失败!-----", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }


}
