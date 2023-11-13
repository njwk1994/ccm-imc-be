package com.ccm.packagemanage.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 包添图纸
 */
@ApiModel
@Data
public class PackageRelDocumentsParamDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 包id
     */
    @ApiModelProperty(name = "id", value = "包uid", required = true)
    private String uid;

    /**
     * 文档uid
     */
    @ApiModelProperty(name = "docIds", value = "文档uid", required = true)
    private List<String> docIds;
}
