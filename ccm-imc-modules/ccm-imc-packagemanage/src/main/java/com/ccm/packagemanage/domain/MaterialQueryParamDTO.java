package com.ccm.packagemanage.domain;

import com.imc.common.core.model.parameters.GeneralQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 物料查询
 */
@Data
@ApiModel
public class MaterialQueryParamDTO extends GeneralQueryParam {
    /**
     * 包uid
     */
    @ApiModelProperty(name = "id", value = "包uid", required = true)
    private String uid;

    /**
     * 材料类
     */
    @ApiModelProperty(name = "classDefinitionUID", value = "材料类", required = true)
    private String classDefinitionUID;
}
