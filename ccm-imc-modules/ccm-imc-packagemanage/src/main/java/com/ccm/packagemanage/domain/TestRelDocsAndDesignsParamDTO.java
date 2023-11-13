package com.ccm.packagemanage.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class TestRelDocsAndDesignsParamDTO {

    /**
     * 包id
     */
    @ApiModelProperty(name = "id", value = "包uid", required = true)
    private String uid;

    /**
     * 要关联的图纸UID
     */
    @ApiModelProperty(name = "documentUID", value = "图纸UID", required = true)
    private String documentUID;

    /**
     * 要关联的设计数据
     */
    @ApiModelProperty(name = "designUIDS", value = "设计数据UID列表", required = true)
    private List<String> designUIDS;
}
