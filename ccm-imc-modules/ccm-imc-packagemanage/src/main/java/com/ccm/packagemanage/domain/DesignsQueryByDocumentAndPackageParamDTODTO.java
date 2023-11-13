package com.ccm.packagemanage.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 设计数据查询
 */
@Data
@ApiModel
public class DesignsQueryByDocumentAndPackageParamDTODTO extends QueryByPackageParamDTO {
    /**
     * 图纸UID
     */
    @ApiModelProperty(name = "documentOBID", value = "图纸UID", required = true)
    private String documentOBID;
}
