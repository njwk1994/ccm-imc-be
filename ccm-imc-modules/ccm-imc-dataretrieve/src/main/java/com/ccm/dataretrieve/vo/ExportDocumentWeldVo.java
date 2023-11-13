package com.ccm.dataretrieve.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 导出图纸焊口数据 参数
 */
@Data
public class ExportDocumentWeldVo {

    @ApiModelProperty(value = "模版类型(DD-详设,SD-加设)")
    private  String  type;
    @ApiModelProperty(value = "焊口数据")
    private  String  filters;

}
