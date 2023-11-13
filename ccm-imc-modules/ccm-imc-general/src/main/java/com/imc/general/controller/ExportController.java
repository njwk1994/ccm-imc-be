package com.imc.general.controller;

import com.alibaba.fastjson2.JSONObject;
import com.imc.common.core.domain.R;
import com.imc.common.core.utils.ExceptionUtil;
import com.imc.framework.api.impl.ServerApiBase;
import com.imc.framework.context.Context;
import com.imc.framework.model.api.ApiProcessParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HuangTao
 * @version 1.0
 * @since 2023/9/22 13:13
 */
@RestController
@Api(tags = "IMC数据导出")
@Slf4j
@RequestMapping("/export")
public class ExportController {

    @ApiOperation(value = "导出数据", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @PostMapping("/general/export")
    public R<Void> loadSchemaFiles(@RequestBody JSONObject jsonObject) {
        try {
            /*String exportUid = jsonObject.getString(ExportData.EXPORTER_UID);
            if (!StringUtils.hasText(exportUid)) {
                throw new RuntimeException("导出器UID不可为空!");
            }
            JSONObject extraData = null != jsonObject.getJSONObject(ExportData.EXTRA_DATA) ? jsonObject.getJSONObject(ExportData.EXTRA_DATA) : new JSONObject();
            Context.Instance.getDataLoaderExporterHelper().exportData(exportUid, new ArrayList<>(), extraData);*/

            ApiProcessParam apiProcessParam = ServerApiBase.generateApiProcessParam(jsonObject, "ExportData", null, null, null, null);
            R<Void> execute = Context.Instance.getDynamicApiEngine().execute(apiProcessParam);
            if (execute.success()) {
                execute.getData();
                return null;
            } else {
                throw new RuntimeException(execute.getMsg());
            }
        } catch (Exception ex) {
            log.error("-------------导出数据失败----------", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }
}
