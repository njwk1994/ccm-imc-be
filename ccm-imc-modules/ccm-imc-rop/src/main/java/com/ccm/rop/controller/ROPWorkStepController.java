package com.ccm.rop.controller;


import com.alibaba.fastjson2.JSONObject;
import com.ccm.rop.service.IROPWorkStepService;
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
@RequestMapping("/rop/workStep")
public class ROPWorkStepController {


    private final IROPWorkStepService ropWorkStepService;


    public ROPWorkStepController(IROPWorkStepService ropWorkStepService) {
        this.ropWorkStepService = ropWorkStepService;
    }



    @InnerAuth
    @ApiOperation("获取规则组工作步骤")
    @GetMapping("/getWorkStep")
    public R<TableData<JSONObject>>getRuleGroupItem() {
        try {
            return R.ok(ropWorkStepService.getRuleWorkStep());
        } catch (Exception ex) {
            log.error("-----获取规则组工作步骤失败!-----", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }


    @PostMapping("createOrUpdate")
    @ApiOperation("新建或更新工作步骤")
    public R<Boolean> creatRuleGroup(JSONObject param){
        try {
            this.ropWorkStepService.saveOrUpdateWorkStep(param);
            return R.ok(true, MessageUtils.get("opt.success"));
        } catch (Exception ex) {
            log.error("-----新建或更新工作步骤失败!-----", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }


    @PostMapping("delete")
    @ApiOperation("删除工作步骤")
    public R<Boolean> deleteRuleGroup(DeleteParam param){
        try {
            this.ropWorkStepService.deleteROPWorkStep(param);
            return R.ok(true, MessageUtils.get("opt.success"));
        } catch (Exception ex) {
            log.error("-----删除工作步骤失败!-----", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

}
