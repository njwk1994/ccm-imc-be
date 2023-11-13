package com.ccm.packagemanage.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class PackageDeleteRelDesignDTO {
    /**
     * 包uid
     */
    @ApiModelProperty(name = "id", value = "包uid", required = true)
    private String uid;

    /**
     * 设计数据
     */
    @ApiModelProperty(name = "id", value = "设计数据UID列表", required = true)
    private List<String> designs;
}
