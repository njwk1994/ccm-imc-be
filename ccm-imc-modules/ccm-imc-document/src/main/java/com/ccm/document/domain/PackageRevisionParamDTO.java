package com.ccm.document.domain;

import com.ccm.modules.documentmanage.enums.PackageRevisionType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description：TODO
 * @author： kekai.huang
 * @create： 2023/11/8 17:02
 */
@Data
public class PackageRevisionParamDTO {

    @ApiModelProperty(name = "packageUid", value = "包UID", required = true)
    private String packageUid;

    /**
     * 包类型
     */
    @ApiModelProperty(name = "classDefinitionUid", value = "包类型", required = true)
    private String classDefinitionUid;

    /**
     * 升版类型
     */
    @ApiModelProperty(name = "packageRevisionType", value = "升版类型",  example = "DELETE,UPDATE,REFRESH",required = true)
    private PackageRevisionType packageRevisionType;
}
