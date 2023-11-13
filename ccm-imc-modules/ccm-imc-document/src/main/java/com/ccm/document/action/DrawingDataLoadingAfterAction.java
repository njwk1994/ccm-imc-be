package com.ccm.document.action;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson.JSON;
import com.ccm.document.domain.DrawingLoaderPercentageDTO;
import com.ccm.document.service.ICCMDocumentBusinessService;
import com.ccm.document.service.IRevisedService;
import com.ccm.modules.documentmanage.constant.DocumentCommon;
import com.imc.framework.collections.impl.ObjectCollection;
import com.imc.framework.entity.loader.LoadResult;
import com.imc.framework.entity.loader.struct.LoadClassDefStruct;
import com.imc.framework.entity.loader.struct.LoadDataStruct;
import com.imc.framework.handlers.loader.base.abstr.DataLoadingAfterAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.ccm.modules.COMContext.CLASS_CIM_CCM_DOCUMENT_MASTER;

@Slf4j
@Component
public class DrawingDataLoadingAfterAction extends DataLoadingAfterAction {

    @Autowired
    IRevisedService revisedService;

    @Autowired
    ICCMDocumentBusinessService documentBusinessService;

    @Override
    public List<LoadResult> execute(List<LoadDataStruct> loadDataStruct, List<LoadResult> loadingResult) {
        log.debug(JSON.toJSONString(loadingResult));
        // 创建图纸升版记录
        for (int i = 0; i < loadDataStruct.size(); i++) {
            String username = loadDataStruct.get(i).getExtraDataStruct().getString(DocumentCommon.CURRENT_USERNAME);
            List<LoadClassDefStruct> loadClassDefStructs = loadDataStruct.get(i).getStandardClassDefStructs();
            LoadResult loadResult = loadingResult.get(i);
            ObjectCollection objectCollection = new ObjectCollection(username);
            if (loadClassDefStructs == null || loadClassDefStructs.isEmpty()) continue;

            // 设计数据
            List<LoadClassDefStruct> designs = loadClassDefStructs.stream().filter(x -> !x.getClassDefUid().equalsIgnoreCase(CLASS_CIM_CCM_DOCUMENT_MASTER)).collect(Collectors.toList());

            // 图纸
            List<LoadClassDefStruct> documents = loadClassDefStructs.stream().filter(x -> x.getClassDefUid().equalsIgnoreCase(CLASS_CIM_CCM_DOCUMENT_MASTER)).collect(Collectors.toList());

            // 遍历图纸
            for (LoadClassDefStruct document : documents) {
                // 进度
                DrawingLoaderPercentageDTO drawingLoaderPercentageDTO = documentBusinessService.getProgressDrawingLoaderPercentageByDrawingName(document.getName(), username);
                drawingLoaderPercentageDTO.setPercentage(60.0);
                drawingLoaderPercentageDTO.setProcessingMsg("图纸加载完成！");
                drawingLoaderPercentageDTO.setCreateUser(username);
                documentBusinessService.insertOrUpdateDrawingLoaderPercentage(drawingLoaderPercentageDTO);

                // 获得新版本的所有设计数据集合
                List<LoadClassDefStruct> docDesigns = revisedService.getDesignsByDocumentUid(document.getUid(), designs, loadDataStruct.get(i).getStandardRelStructs());

                try {
                    revisedService.createDocumentRevisionByDocumentUid(document.getUid(), docDesigns, objectCollection);
                    drawingLoaderPercentageDTO.setPercentage(70.0);
                    drawingLoaderPercentageDTO.setProcessingMsg("图纸添加升版信息...");
                    drawingLoaderPercentageDTO.setCreateUser(username);
                    documentBusinessService.insertOrUpdateDrawingLoaderPercentage(drawingLoaderPercentageDTO);
                } catch (Exception e) {
                    drawingLoaderPercentageDTO.setPercentage(70.0);
                    drawingLoaderPercentageDTO.setProcessingMsg(ExceptionUtil.getMessage(e));
                    drawingLoaderPercentageDTO.setCreateUser(username);
                    documentBusinessService.insertOrUpdateDrawingLoaderPercentage(drawingLoaderPercentageDTO);
                }

                // TODO 生成ROP工序

                // TODO 包升版操作

                try {
                    drawingLoaderPercentageDTO.setPercentage(70.0);
                    drawingLoaderPercentageDTO.setProcessingMsg("包升版操作...");
                    drawingLoaderPercentageDTO.setCreateUser(username);
                    documentBusinessService.insertOrUpdateDrawingLoaderPercentage(drawingLoaderPercentageDTO);
                    revisedService.handlePackageAndRelRevised(document, docDesigns);
                } catch (Exception e) {
                    drawingLoaderPercentageDTO.setPercentage(80.0);
                    drawingLoaderPercentageDTO.setProcessingMsg(ExceptionUtil.getMessage(e));
                    drawingLoaderPercentageDTO.setCreateUser(username);
                    documentBusinessService.insertOrUpdateDrawingLoaderPercentage(drawingLoaderPercentageDTO);
                }
                String msg = "接受发布完成!";
                if (!loadResult.isSuccess()) {
                    msg = loadResult.getMsg();
                }
                documentBusinessService.completeDrawingLoader(document.getName(), msg, username);
            }

            try {
                objectCollection.commit();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return loadingResult;
    }

    @Override
    public String getDescription() {
        return "图纸导入后动作";
    }

    @Override
    public String getUniqueId() {
        return DrawingDataLoadingAfterAction.class.getName();
    }
}
