package com.imc.general.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class FBSTreeQueryVo implements Serializable {

    @ApiModelProperty(name="extraRelDefs", value = "附属对象关联定义", example = "-CIMFacility2SAConfig")
    private String extraRelDefs;

    @ApiModelProperty(name="isActive", value = "是否创建作用域，默认为false", example = "false")
    private Boolean isActive;
}
