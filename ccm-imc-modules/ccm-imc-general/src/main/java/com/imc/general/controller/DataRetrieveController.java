package com.imc.general.controller;

import com.alibaba.fastjson2.JSONObject;
import com.imc.common.core.domain.R;
import com.imc.common.core.utils.ExceptionUtil;
import com.imc.common.core.utils.MessageUtils;
import com.imc.general.service.IDataRetrieveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequestMapping("/retrieveData")
@Api(tags = "接受数据相关")
public class DataRetrieveController {

    private final IDataRetrieveService dataRetrieveService;

    public DataRetrieveController(IDataRetrieveService dataRetrieveService) {
        this.dataRetrieveService = dataRetrieveService;
    }

    @ApiOperation("导入Excel文件数据")
    @PostMapping("/importExcelFileData")
    public R<Boolean> importExcelData(@RequestPart(name = "file") MultipartFile file) {
        try {
            this.dataRetrieveService.importExcelData(file);
            return R.ok(true, MessageUtils.get("import.success"));
        } catch (Exception ex) {
            log.error("----------导入Excel文件数据失败!----------", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }


    @ApiOperation("导入xml文件数据")
    @PostMapping("/importXmlFileData")
    public R<Boolean> importXmlData(@RequestPart(name = "files") MultipartFile[] files) {
        try {
            this.dataRetrieveService.importXmlData(files);
            return R.ok(true, MessageUtils.get("import.success"));
        } catch (Exception ex) {
            log.error("----------导入xml文件数据失败!----------", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("导入Json文件数据")
    @PostMapping("/importJsonFileData")
    public R<Boolean> importJsonData(@RequestPart(name = "files") MultipartFile[] files) {
        try {
            this.dataRetrieveService.importJsonData(files);
            return R.ok(true, MessageUtils.get("import.success"));
        } catch (Exception ex) {
            log.error("----------导入Json文件数据失败!----------", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }


    @ApiOperation("接受发布的Json数据")
    @PostMapping("/retrievePublishJsonData")
    public R<Boolean> retrievePublishJsonData(@RequestBody JSONObject jsonObject) {
        try {
            this.dataRetrieveService.retrievePublishJsonData(jsonObject);
            return R.ok(true, MessageUtils.get("receive.success"));
        } catch (Exception ex) {
            log.error("----------接受发布的Json数据失败!----------", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }


}
