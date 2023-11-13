package com.ccm.document.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description：图纸加载进度信息
 * @author： kekai.huang
 * @create： 2023/10/25 15:48
 */
@Data
public class DrawingLoaderPercentageDTO {
    /**
     * 图纸名称
     */
    @ApiModelProperty(name = "drawingName", value = "图纸名称")
    private String drawingName;

    /**
     * 对象数量
     */
    @ApiModelProperty(name = "designObjectsCount", value = "对象数量")
    private Integer designObjectsCount;

    /**
     * 创建用户
     */
    @ApiModelProperty(name = "createUser", value = "创建用户")
    private String createUser;

    /**
     * 项目
     */
    @ApiModelProperty(name = "createUser", value = "创建用户")
    private String project;

    /**
     * 开始时间
     */
    @ApiModelProperty(name = "startTime", value = "开始时间")
    private String startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(name = "endTime", value = "结束时间")
    private String endTime;

    /**
     * 耗时
     */
    @ApiModelProperty(name = "timeSpan", value = "耗时")
    private String timeSpan;

    /**
     * 过程信息
     */
    @ApiModelProperty(name = "processingMsg", value = "过程信息")
    private String processingMsg;

    /**
     * 进度
     */
    @ApiModelProperty(name = "processingMsg", value = "进度")
    private Double percentage = 0.0;
}
