package com.imc.general.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * FBS树形节点DTO
 */
@Data
public class FBSTreeNodeVo implements Serializable {
    private static final long serializableId = 1L;

    @ApiModelProperty("对象唯一标识")
    private String obid;

    @ApiModelProperty("唯一标识")
    private String uid;

    @ApiModelProperty("Class定义唯一标识")
    private String classDefinitionUID;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("显示名称")
    private String displayName;

    @ApiModelProperty("是否叶子节点")
    private Boolean isLeaf;

    @ApiModelProperty("子节点集合")
    private List<FBSTreeNodeVo> children;

    @ApiModelProperty("附属对象集合")
    private List<Object> extraObjs;
}
