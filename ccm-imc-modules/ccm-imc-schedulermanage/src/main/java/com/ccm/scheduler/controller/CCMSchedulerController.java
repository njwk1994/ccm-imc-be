package com.ccm.scheduler.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ccm.scheduler.domain.CWAInfoDTO;
import com.ccm.scheduler.domain.RelCWATOSectionDTO;
import com.ccm.scheduler.service.ICCMSchedulerService;
import com.imc.common.core.domain.R;
import com.imc.common.core.utils.ExceptionUtil;
import com.imc.common.core.web.table.TableData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description：计划接口相关
 * @author： kekai.huang
 * @create： 2023/10/27 15:19
 */
@Slf4j
@RestController
@Api(tags = "计划接口相关")
@RequestMapping("/ccm/scheduler")
public class CCMSchedulerController {

    @Autowired
    ICCMSchedulerService schedulerService;

    @ApiOperation("获取包对应的施工数据分类")
    @PostMapping("/getSelectableCWA")
    public R<TableData<CWAInfoDTO>> selectPackageConstructionTypes(@RequestBody RelCWATOSectionDTO relCWATOSectionDTO) {
        try {
            return R.ok(schedulerService.getSelectableCWA(relCWATOSectionDTO.getSectionUid()));
        } catch (Exception ex) {
            log.error(ExceptionUtil.getRootErrorMessage(ex));
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("保存关联施工区域")
    @PostMapping("/saveCWATOSection")
    public R<String> saveCWATOSection(@RequestBody RelCWATOSectionDTO relCWATOSectionDTO) {
        try {
            schedulerService.saveCWATOSection(relCWATOSectionDTO);
            return R.ok();
        } catch (Exception ex) {
            log.error(ExceptionUtil.getRootErrorMessage(ex));
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("设计数据归集")
    @GetMapping("/dataCollectionByScheduleUID")
    public R<String> dataCollectionByScheduleUID(@RequestParam String scheduleUID) {
        try {
            schedulerService.dataCollectionByScheduleUID(scheduleUID);
            return R.ok();
        } catch (Exception ex) {
            log.error(ExceptionUtil.getRootErrorMessage(ex));
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }
}
