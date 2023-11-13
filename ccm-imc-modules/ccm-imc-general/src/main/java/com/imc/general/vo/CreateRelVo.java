package com.imc.general.vo;

import com.imc.common.core.utils.StringUtils;
import com.imc.common.core.utils.exception.HandlerExceptionUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class CreateRelVo implements Serializable {

    private static final long serializableId = 1L;

    @ApiModelProperty(value = "1端对象唯一标识,多个以,拼接")
    private String uid1;
    @ApiModelProperty(value = "2端对象唯一标识,多个以,拼接")
    private String uid2;
    @ApiModelProperty(value = "1端对象类型定义")
    private String classDefinitionUid1;
    @ApiModelProperty(value = "2端对象类型定义")
    private String classDefinitionUid2;
    @ApiModelProperty(value = "关联关系定义,带方向+|-")
    private String relDefUid;

    public void validateParam() {
        if (StringUtils.isEmpty(this.getRelDefUid()) || StringUtils.isEmpty(this.getUid1()) || StringUtils.isEmpty(this.getUid2()) || StringUtils.isEmpty(this.getClassDefinitionUid2()) || StringUtils.isEmpty(this.getClassDefinitionUid1())) {
            throw new RuntimeException(HandlerExceptionUtils.paramsInvalid());
        }
    }
}
