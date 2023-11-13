package com.ccm.document.event;

import com.alibaba.fastjson2.JSONObject;
import com.ccm.document.domain.FileInfoDTO;
import com.ccm.document.notify.Event;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @description：图纸加载事件
 * @author： kekai.huang
 * @create： 2023/10/25 15:32
 */
@Data
public class DrawingLoaderEvent extends Event {
    private static final long serialVersionUID = 7308126651076668976L;

    private List<FileInfoDTO> files;

    private String loaderUid;
    private JSONObject baseData;
    private JSONObject extraData;

}
