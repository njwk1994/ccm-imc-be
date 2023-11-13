package com.ccm.document.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ccm.document.domain.DrawingLoaderPercentageDTO;
import com.ccm.document.service.ICCMDocumentBusinessService;
import com.ccm.modules.documentmanage.constant.DocumentCommon;
import com.imc.framework.entity.loader.struct.LoadClassDefStruct;
import com.imc.framework.entity.loader.struct.LoadDataStruct;
import com.imc.framework.handlers.loader.base.abstr.DataConvertAfterAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.ccm.modules.COMContext.CLASS_CIM_CCM_DOCUMENT_MASTER;

/**
 * @description：图纸转换后动作
 * @author： kekai.huang
 * @create： 2023/10/25 14:59
 */
@Slf4j
@Component
public class DrawingDataConvertAfterAction extends DataConvertAfterAction {

    @Autowired
    ICCMDocumentBusinessService documentBusinessService;

    @Override
    public List<LoadDataStruct> execute(JSONObject sourceData, List<LoadDataStruct> converted) {
        log.debug(JSON.toJSONString(converted));
        for (LoadDataStruct dataStruct : converted) {
            // 对象
            List<LoadClassDefStruct> loadClassDefStructs = dataStruct.getStandardClassDefStructs();
            if (loadClassDefStructs == null || loadClassDefStructs.isEmpty()) continue;
            List<LoadClassDefStruct> documents = loadClassDefStructs.stream().filter(x -> x.getClassDefUid().equalsIgnoreCase(CLASS_CIM_CCM_DOCUMENT_MASTER)).collect(Collectors.toList());
            for (LoadClassDefStruct document : documents) {

                // 文档下设计数据
                Integer count = (int)dataStruct.getStandardRelStructs().stream().filter(x -> x.getUid1().equalsIgnoreCase(document.getUid())).count();
                DrawingLoaderPercentageDTO drawingLoaderPercentageDTO = documentBusinessService.getProgressDrawingLoaderPercentageByDrawingName(document.getName(), sourceData.getString("username"));
                if (drawingLoaderPercentageDTO == null) continue;
                drawingLoaderPercentageDTO.setPercentage(15.0);
                drawingLoaderPercentageDTO.setCreateUser(sourceData.getString(DocumentCommon.USERNAME));
                drawingLoaderPercentageDTO.setProcessingMsg("转换完成！");
                drawingLoaderPercentageDTO.setDesignObjectsCount(count);
                documentBusinessService.insertOrUpdateDrawingLoaderPercentage(drawingLoaderPercentageDTO);
            }
        }
        return converted;
    }

    @Override
    public String getDescription() {
        return "图纸转换后动作";
    }

    @Override
    public String getUniqueId() {
        return DrawingDataConvertAfterAction.class.getName();
    }
}
