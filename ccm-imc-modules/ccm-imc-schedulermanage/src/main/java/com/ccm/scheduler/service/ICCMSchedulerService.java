package com.ccm.scheduler.service;

import com.alibaba.fastjson2.JSONObject;
import com.ccm.scheduler.domain.CWAInfoDTO;
import com.ccm.scheduler.domain.RelCWATOSectionDTO;
import com.imc.common.core.web.table.TableData;
import netscape.javascript.JSObject;

/**
 * @description：计划服务
 * @author： kekai.huang
 * @create： 2023/10/27 15:20
 */
public interface ICCMSchedulerService {
    /**
     * 获得当前施工标段可关联的施工区域
     * @param sectionUid
     * @return
     */
    TableData<CWAInfoDTO> getSelectableCWA(String sectionUid) throws Exception;

    /**
     * 保存关联施工区域
     * @param relCWATOSectionDTO
     */
    void saveCWATOSection(RelCWATOSectionDTO relCWATOSectionDTO) throws Exception;

    /**
     * 数据归集
     * @param scheduleUID
     */
    void dataCollectionByScheduleUID(String scheduleUID) throws Exception;
}
