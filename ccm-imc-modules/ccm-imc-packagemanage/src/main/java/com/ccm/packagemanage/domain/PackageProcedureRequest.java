package com.ccm.packagemanage.domain;

import com.ccm.modules.packagemanage.enums.PackageType;
import com.ccm.modules.packagemanage.enums.ProcedureType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author kekai.huang
 * @Date 2023/9/12 13:05
 * @PackageName:com.ccm.packagemanage.domain
 * @ClassName: PackageProcedureRequest
 * @Description: TODO
 * @Version 1.0
 */
@ApiModel
@Data
public class PackageProcedureRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 包类型(TP/WP 任务包/工作包)
     */
    @ApiModelProperty(name = "packageType", value = "任务包/工作包", example = "TP/WP", required = true)
    private PackageType packageType;
    /**
     * 包id
     */
    @ApiModelProperty(name = "packageId", value = "任务包/工作包的OBID", example = "OBID", required = true)
    private String packageId;
    /**
     * 项目id
     * 如未传入则从项目配置中获取
     */
    @ApiModelProperty(name = "projectId", value = "项目id", example = "如未传入则从项目配置中获取", required = true)
    private String projectId;
    /**
     * 预测单号
     */
    @ApiModelProperty(name = "requestName", value = "预测单号", required = true)
    private String requestName;
    /**
     * FR是预测,RR是预留
     */
    @ApiModelProperty(name = "requestType", value = "FR是预测,RR是预留", required = true)
    private String requestType;
    /**
     * 仓库
     */
    @ApiModelProperty(name = "warehouses", value = "仓库", required = true)
    private String warehouses;
    /**
     * 用","拼接的图纸OBID
     * 例子：docOBID01,docOBID02
     */
    @ApiModelProperty(name = "drawingNumbers", value = "用\",\"拼接的图纸OBID", example = "docOBID01,docOBID02", required = true)
    private String drawingNumbers;

    /**
     * 查询字段
     */
    @ApiModelProperty(name = "searchColumn", value = "查询字段")
    private String searchColumn;
    /**
     * 查询值
     */
    @ApiModelProperty(name = "searchValue", value = "查询值")
    private String searchValue;

    /**
     * 存储过程类型
     * 例子：EN_DefaultDoc
     */
    @ApiModelProperty(name = "procedureType", value = "存储过程类型", example = "EN_DefaultDoc", required = true)
    private ProcedureType procedureType;
}
