package com.ccm.scheduler.domain;

import lombok.Data;

/**
 * @description：TODO
 * @author： kekai.huang
 * @create： 2023/10/27 16:06
 */
@Data
public class CWAInfoDTO {
    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String description;
    /**
     * 编码
     */
    private String uid;
    /**
     * 顺序
     */
    private Integer order;
}
