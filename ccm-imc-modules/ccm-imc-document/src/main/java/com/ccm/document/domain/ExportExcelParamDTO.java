package com.ccm.document.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description：TODO
 * @author： kekai.huang
 * @create： 2023/10/31 13:12
 */
@Data
public class ExportExcelParamDTO {
    /**
     * 设计阶段
     */
    @ApiModelProperty(name = "designPhase", value = "设计阶段", required = true)
    private String designPhase;

    /**
     * 图纸uid
     */
    @ApiModelProperty(name = "documentUid", value = "图纸uid", required = false)
    private String documentUid;
}
