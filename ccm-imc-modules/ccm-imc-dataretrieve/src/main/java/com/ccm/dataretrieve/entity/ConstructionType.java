package com.ccm.dataretrieve.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 施工分类
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ConstructionType {

    @ApiModelProperty(value = "页签的classdefuid")
    private  String  targetClassDefUid;
    @ApiModelProperty(value = "页签的名称")
    private  String  label;

}
