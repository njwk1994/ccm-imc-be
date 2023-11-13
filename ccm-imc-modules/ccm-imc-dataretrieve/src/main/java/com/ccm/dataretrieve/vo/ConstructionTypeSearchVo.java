package com.ccm.dataretrieve.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 设计数据的uid
 */
@Data
public class ConstructionTypeSearchVo {

    @ApiModelProperty(value = "圖紙uid")
    private String uid;

    @ApiModelProperty(value = "是否包含已刪除圖紙")
    private boolean withDeleted = true;



}
