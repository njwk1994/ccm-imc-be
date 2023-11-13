package com.ccm.packagemanage.domain;

import com.ccm.modules.packagemanage.enums.PackageType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class ConstructionTypesQueryParamDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 任务包uid
     */
    @ApiModelProperty(name = "id", value = "任务包uid", required = true)
    private String uid;

    /**
     * 是否需要验证材料消耗
     */
    @ApiModelProperty(name = "needConsumeMaterial", value = "是否需要验证材料消耗", required = true)
    private Boolean needConsumeMaterial;

    /**
     * 包类
     */
    @ApiModelProperty(name = "packageType", value = "包类", required = true)
    private PackageType packageType;
}
