package com.ccm.packagemanage.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class PackageDeleteRelWokStepDTO {
    /**
     * 包uid
     */
    @ApiModelProperty(name = "id", value = "包uid", required = true)
    private String uid;

    /**
     * 工作步骤
     */
    @ApiModelProperty(name = "id", value = "工作步骤UID列表", required = true)
    private List<String> workSteps;
}
