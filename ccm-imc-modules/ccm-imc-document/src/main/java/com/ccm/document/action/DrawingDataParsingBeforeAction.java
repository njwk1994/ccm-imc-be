package com.ccm.document.action;

import com.alibaba.fastjson2.JSONObject;
import com.imc.framework.handlers.loader.base.abstr.DataParsingBeforeAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
public class DrawingDataParsingBeforeAction extends DataParsingBeforeAction {
    @Override
    public void execute(MultipartFile[] multipartFiles, JSONObject baseData, JSONObject extraData) {
        log.debug(multipartFiles.toString());
    }

    @Override
    public String getDescription() {
        return "图纸导入解析前处理";
    }

    @Override
    public String getUniqueId() {
        return DrawingDataParsingBeforeAction.class.getName();
    }
}
