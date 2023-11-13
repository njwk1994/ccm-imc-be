package com.ccm.document.action;

import com.alibaba.excel.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ccm.document.domain.DrawingLoaderPercentageDTO;
import com.ccm.document.service.ICCMDocumentBusinessService;
import com.ccm.modules.documentmanage.constant.DocumentCommon;
import com.imc.framework.handlers.loader.base.abstr.DataParsingAfterAction;
import com.imc.framework.handlers.loader.handler.parsing.StandardExcelParsingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @description：图纸解析后处理动作
 * @author： kekai.huang
 * @create： 2023/10/25 14:56
 */
@Slf4j
@Component
public class DrawingDataParsingAfterAction extends DataParsingAfterAction {

    @Autowired
    ICCMDocumentBusinessService documentBusinessService;

    @Override
    public JSONObject execute(MultipartFile[] files, JSONObject baseData, JSONObject parsingResult, JSONObject extraData) {
        log.debug(JSON.toJSONString(parsingResult));
        List<StandardExcelParsingHandler.ExcelStruct> parsedDatas = (List)parsingResult.get("parsedData");
        for (StandardExcelParsingHandler.ExcelStruct fileInfo : parsedDatas) {
            Map fileContext = (Map)fileInfo.getExcelSheetDataMap();
            if (fileContext == null) continue;
            List<Map> documents = (List)fileContext.get("图纸");
            if (documents == null) continue;
            for (Map document : documents) {
                String name = (String) document.get("名称");
                if (StringUtils.isEmpty(name)) continue;
                DrawingLoaderPercentageDTO drawingLoaderPercentageDTO = new DrawingLoaderPercentageDTO();
                drawingLoaderPercentageDTO.setDrawingName(name);
                drawingLoaderPercentageDTO.setPercentage(5.0);
                drawingLoaderPercentageDTO.setProcessingMsg("解析完成!");
                drawingLoaderPercentageDTO.setCreateUser(extraData.getString(DocumentCommon.USERNAME));
                documentBusinessService.insertOrUpdateDrawingLoaderPercentage(drawingLoaderPercentageDTO);
            }
        }
        parsingResult.put(DocumentCommon.USERNAME, extraData.getString(DocumentCommon.USERNAME));
        return parsingResult;
    }

    @Override
    public String getDescription() {
        return "图纸解析后处理动作";
    }

    @Override
    public String getUniqueId() {
        return DrawingDataParsingAfterAction.class.getName();
    }
}
