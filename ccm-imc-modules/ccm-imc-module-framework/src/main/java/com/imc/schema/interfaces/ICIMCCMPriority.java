package com.imc.schema.interfaces;

import java.util.List;
import java.util.Map;

/**
 * 施工优先级策略
 */
public interface ICIMCCMPriority extends IObject{
    /**
     * 获得优先级策略获得优先级选项
     * @return
     * @throws Exception
     */
    Map<String, List<ICIMCCMPriorityItem>> getPriorityItemPerTargetProperty() throws Exception;

    /**
     * 获得优先级策略选项
     * @return
     * @throws Exception
     */
    List<ICIMCCMPriorityItem> getPriorityItems() throws Exception;

    /**
     * 计算优先级权重
     * @param object
     * @return
     * @throws Exception
     */
    List<Map.Entry<ICIMCCMPriorityItem, Double>> calculateWeight(IObject object) throws Exception;
}
