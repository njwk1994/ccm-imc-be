package com.ccm.document.action;

import com.alibaba.excel.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ccm.document.domain.DrawingLoaderPercentageDTO;
import com.ccm.document.service.ICCMDocumentBusinessService;
import com.ccm.modules.documentmanage.constant.DocumentCommon;
import com.imc.framework.handlers.loader.base.abstr.DataConvertBeforeAction;
import com.imc.framework.handlers.loader.handler.parsing.StandardExcelParsingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @description：图纸转换前动作
 * @author： kekai.huang
 * @create： 2023/10/25 14:58
 */
@Slf4j
@Component
public class DrawingDataConvertBeforeAction extends DataConvertBeforeAction {

    @Autowired
    ICCMDocumentBusinessService documentBusinessService;

    @Override
    public void execute(JSONObject jsonObject) {
        log.debug(JSON.toJSONString(jsonObject));
        List<StandardExcelParsingHandler.ExcelStruct> parsedDatas = (List)jsonObject.get("parsedData");
        for (StandardExcelParsingHandler.ExcelStruct fileInfo : parsedDatas) {
            Map fileContext = fileInfo.getExcelSheetDataMap();
            if (fileContext == null) continue;
            List<Map> documents = (List)fileContext.get("图纸");
            if (documents == null) continue;
            for (Map document : documents) {
                String name = (String) document.get("名称");
                if (StringUtils.isEmpty(name)) continue;
                DrawingLoaderPercentageDTO drawingLoaderPercentageDTO = documentBusinessService.getProgressDrawingLoaderPercentageByDrawingName(name, jsonObject.getString("username"));
                if (drawingLoaderPercentageDTO == null) continue;
                drawingLoaderPercentageDTO.setPercentage(10.0);
                drawingLoaderPercentageDTO.setCreateUser(jsonObject.getString(DocumentCommon.USERNAME));
                drawingLoaderPercentageDTO.setProcessingMsg("开始转换中...");
                documentBusinessService.insertOrUpdateDrawingLoaderPercentage(drawingLoaderPercentageDTO);
            }
        }
    }

    @Override
    public String getDescription() {
        return "图纸转换前动作";
    }

    @Override
    public String getUniqueId() {
        return DrawingDataConvertBeforeAction.class.getName();
    }
}
