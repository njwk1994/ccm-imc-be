package com.ccm.document.event;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson2.JSONObject;
import com.ccm.document.domain.FileInfoDTO;
import com.ccm.document.notify.Event;
import com.ccm.document.notify.NotifyCenter;
import com.ccm.document.notify.listener.Subscriber;
import com.imc.framework.context.Context;
import com.imc.framework.entity.loader.LoadResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

/**
 * @description：异步处理图纸导入
 * @author： kekai.huang
 * @create： 2023/10/25 15:32
 */
@Slf4j
@Service
public class DrawingLoaderEventManage extends Subscriber<DrawingLoaderEvent> {

    private static final String LOADER_UID = "DrawingExcelLoader";

    @PostConstruct
    public void init() {
        NotifyCenter.registerToPublisher(DrawingLoaderEvent.class, 100);
        NotifyCenter.registerSubscriber(this);
    }

    @Override
    public void onEvent(DrawingLoaderEvent event) {
        try {
            Context.Instance.getDataLoaderExporterHelper().loadData(LOADER_UID, fileInfoToMultipartFile(event.getFiles()), event.getBaseData(), event.getExtraData());
        } catch (Exception e) {
            log.error(ExceptionUtil.getMessage(e));
        }
    }

    private MultipartFile[] fileInfoToMultipartFile(List<FileInfoDTO> fileInfoDTOS) throws IOException {
        MultipartFile[] multipartFiles = new MultipartFile[fileInfoDTOS.size()];
        for (int i = 0; i < fileInfoDTOS.size(); i++) {
            multipartFiles[i] = new MockMultipartFile(fileInfoDTOS.get(i).getFilename(), fileInfoDTOS.get(i).getOriginalFilename(),
                    fileInfoDTOS.get(i).getContentType(), fileInfoDTOS.get(i).getContentStream());
        }
        return multipartFiles;
    }

    @Override
    public Class<? extends Event> subscribeType() {
        return DrawingLoaderEvent.class;
    }
}
