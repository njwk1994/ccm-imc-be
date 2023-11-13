package com.ccm.scheduler.controller;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson.JSONObject;
import com.ccm.scheduler.service.ICCMP6HandleService;
import com.imc.common.core.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author kekai.huang
 * @Date 2023/9/13 10:25
 * @PackageName:com.ccm.scheduler.controller
 * @ClassName: CCMP6HandleController
 * @Description: TODO
 * @Version 1.0
 */
@Slf4j
@RestController
@Api(tags = "P6接口相关")
@RequestMapping("/ccm/P6")
public class CCMP6HandleController {

    @Autowired
    ICCMP6HandleService iccmp6HandleService;

    @ApiOperation(value = "同步P6计划", notes = "同步P6计划")
    @PostMapping("/syncSchedule")
    public R<Object> syncSchedule(@RequestBody JSONObject requestBody) {
        try {
            iccmp6HandleService.syncSchedule();
            return R.ok();
        } catch (Exception exception) {
            return R.fail("获取计划管理表单失败," + ExceptionUtil.getSimpleMessage(exception));
        }
    }
}
