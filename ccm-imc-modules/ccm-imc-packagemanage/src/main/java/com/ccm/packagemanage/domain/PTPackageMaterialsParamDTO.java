package com.ccm.packagemanage.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PTPackageMaterialsParamDTO {
    /**
     * 包id
     */
    @ApiModelProperty(name = "id", value = "包uid", required = true)
    private String uid;

    /**
     * 材料模板UID集合
     */
    @ApiModelProperty(name = "id", value = "材料模板UID集合", required = true)
    private List<String> materialTemplateUIDs;

    /**
     * 材料数量集合，和UID集合对应
     */
    @ApiModelProperty(name = "id", value = "材料数量集合，和UID集合对应", required = true)
    private List<Integer> counts;
}
