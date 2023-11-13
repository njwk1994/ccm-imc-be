package com.imc.general.controller;

import com.alibaba.fastjson2.JSONObject;
import com.imc.common.core.domain.R;
import com.imc.common.core.utils.ExceptionUtil;
import com.imc.framework.context.Context;
import com.imc.framework.entity.loader.LoadResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author HuangTao
 * @version 1.0
 * @since 2023/7/20 18:33
 */
@RestController
@Api(tags = "IMC数据写入")
@Slf4j
@RequestMapping("/loader")
public class LoaderController {

    @ApiOperation("导入数据")
    @PostMapping("/general/load")
    public R<List<LoadResult>> loadSchemaFiles(@RequestPart(value = "files") MultipartFile[] files, String loaderUid, String baseData, String extraData) {
        try {
            List<LoadResult> result = Context.Instance.getDataLoaderExporterHelper().loadData(loaderUid, files, JSONObject.parseObject(baseData), JSONObject.parseObject(extraData));
            return R.ok(result);
        } catch (Exception ex) {
            log.error("-------------导入数据失败----------", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

}
