package com.imc.general.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class FBSChildrenQueryVo implements Serializable {

    @ApiModelProperty(value = "类型唯一标识", required = true)
    @NotBlank(message = "classDefinitionUID is required")
    private String classDefinitionUID;

    @ApiModelProperty(value = "对象唯一标识", required = true)
    @NotBlank(message = "uid is required")
    private String uid;

    @ApiModelProperty(name="extraRelDefs", value = "附属对象关联定义", example = "-CIMFacility2SAConfig")
    private String extraRelDefs;

    @ApiModelProperty(name="isActive", value = "是否创建作用域，默认为false", example = "false")
    private Boolean isActive;
}
