package com.ccm.scheduler.domain;

import lombok.Data;

/**
 * @description：TODO
 * @author： kekai.huang
 * @create： 2023/10/27 15:25
 */
@Data
public class RelCWATOSectionDTO {
    /**
     * 施工区域
     */
    private String cwa;

    /**
     * 施工区域描述
     */
    private String cwaDescription;

    /**
     * 标段uid
     */
    private String sectionUid;
}
