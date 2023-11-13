package com.ccm.packagemanage.domain;


import com.imc.common.core.model.parameters.GeneralQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 根据优先级计算权重
 */
@Data
@ApiModel
public class CalculateByPriorityParamDTO extends GeneralQueryParam {
    /**
     * 包uid
     */
    @ApiModelProperty(name = "id", value = "包uid", required = true)
    private String uid;

    /**
     * 优先级uid
     */
    @ApiModelProperty(name = "id", value = "优先级uid", required = true)
    private String priorityId;

    /**
     * 是否通过缓存获得
     */
    @ApiModelProperty(name = "fromCache", value = "是否通过缓存获得", required = true)
    private Boolean fromCache;

    /**
     * 当前用户令牌
     */
    @ApiModelProperty(name = "token", value = "当前用户令牌", required = true)
    private String token;
}
