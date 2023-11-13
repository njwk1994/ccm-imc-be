package com.ccm.document.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @description：清除所有进度信息参数
 * @author： kekai.huang
 * @create： 2023/10/26 16:45
 */
@Data
public class ClearAllDrawingParamDTO {
    /**
     * 开始日期
     */
    @ApiModelProperty(name = "startDate", value = "开始日期", required = true)
    private String startDate;
}
