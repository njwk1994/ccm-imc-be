package com.ccm.scheduler.helpers;

import com.imc.schema.interfaces.*;

import java.util.List;

/**
 * @description：计划查询
 * @author： kekai.huang
 * @create： 2023/10/27 15:33
 */
public interface ISchedulerQueryHelper {

    /**
     * 根据uid获得标段信息
     * @param uid
     * @return
     */
    ICIMCCMBidSection getBidSectionByUid(String uid);

    /**
     * 根据uid获得计划
     * @param uid
     * @return
     */
    ICIMCCMSchedule getScheduleByUid(String uid);

    /**
     * 获得标段下关联的施工区域
     * @param uid
     * @return
     */
    List<ICIMCCMCWA> getCWAsByUid(String uid);
}
