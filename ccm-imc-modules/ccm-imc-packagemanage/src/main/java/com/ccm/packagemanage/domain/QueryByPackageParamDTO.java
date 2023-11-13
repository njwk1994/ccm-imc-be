package com.ccm.packagemanage.domain;

import com.imc.common.core.model.parameters.GeneralQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 根据包Id查询可以使用的任务包
 */
@Data
@ApiModel
public class QueryByPackageParamDTO extends GeneralQueryParam {

    /**
     * 包id
     */
    @ApiModelProperty(name = "id", value = "包uid", required = true)
    private String uid;
}
