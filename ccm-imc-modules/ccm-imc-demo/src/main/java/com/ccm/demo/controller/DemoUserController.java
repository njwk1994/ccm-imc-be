package com.ccm.demo.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ccm.demo.service.IDemoUserService;
import com.imc.common.core.domain.R;
import com.imc.common.core.utils.ExceptionUtil;
import com.imc.common.core.web.table.TableData;
import com.imc.common.security.annotation.InnerAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author HuangTao
 * @version 1.0
 * @since 2023/4/4 6:31
 */
@RestController
@Slf4j
@RequestMapping("/demo/user")
@Api(tags = "示例写法展示")
public class DemoUserController {

    private final IDemoUserService demoUserService;

    public DemoUserController(IDemoUserService demoUserService) {
        this.demoUserService = demoUserService;
    }

    @InnerAuth
    @ApiOperation("获取用户")
    @GetMapping("/getUsers")
    public R<TableData<JSONObject>> getUsers() {
        try {
            return R.ok(demoUserService.getUsers());
        } catch (Exception ex) {
            log.error("-----获取用户失败!-----", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }
}
