package com.imc.schema.interfaces.defaults;


import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.imc.common.core.enums.frame.PropertyDefinitions;
import com.imc.common.core.enums.frame.RelDirection;
import com.imc.framework.context.Context;
import com.imc.framework.model.qe.QueryRequest;
import com.imc.schema.interfaces.ICIMCCMPriorityItem;
import com.imc.schema.interfaces.IObject;
import com.imc.schema.interfaces.bases.ICIMCCMPriorityBase;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static com.ccm.modules.COMContext.*;

@Slf4j
public class ICIMCCMPriorityDefault extends ICIMCCMPriorityBase {
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    protected Map<String, List<ICIMCCMPriorityItem>> priorityItemsPerTargetProperty = null;

    public ICIMCCMPriorityDefault(boolean instantiateRequiredProperties) {
        super(instantiateRequiredProperties);
    }

    @Override
    public Map<String, List<ICIMCCMPriorityItem>> getPriorityItemPerTargetProperty() throws Exception {
        if (this.priorityItemsPerTargetProperty != null && this.priorityItemsPerTargetProperty.size() > 0) {
            log.trace("get priority setting(s) from cache");
            return this.priorityItemsPerTargetProperty;
        }
        Exception exception = null;
        try {
            lock.writeLock().lock();
            log.trace("enter to get priority by target property");
            List<ICIMCCMPriorityItem> priorityItems = this.getPriorityItems();
            this.priorityItemsPerTargetProperty = new HashMap<>();
            for (ICIMCCMPriorityItem icimccmPriorityItem: priorityItems) {
                String targetProperty = icimccmPriorityItem.getPriorityTargetProperty();
                List<ICIMCCMPriorityItem> items = this.priorityItemsPerTargetProperty.getOrDefault(targetProperty, new ArrayList<>());
                items.add(icimccmPriorityItem);
                if (this.priorityItemsPerTargetProperty.containsKey(targetProperty)) {
                    this.priorityItemsPerTargetProperty.replace(targetProperty, items);
                } else {
                    this.priorityItemsPerTargetProperty.put(targetProperty, items);
                }
            }
            log.trace("complete to get priority by target property " );
        } catch (Exception e) {
            log.error("get priority item per target property failed", e);
            exception = e;
        } finally {
            lock.writeLock().unlock();
        }
        if (exception != null)
            throw exception;
        return this.priorityItemsPerTargetProperty;
    }

    @Override
    public List<ICIMCCMPriorityItem> getPriorityItems() throws Exception {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addClassDefForQuery(CLASS_CIM_CCM_PRIORITY_ITEM);
        queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + REL_PRIORITY_TO_ITEM, PropertyDefinitions.uid1.toString(), SqlKeyword.EQ, this.getUid());
        return Context.Instance.getQueryHelper().query(queryRequest, ICIMCCMPriorityItem.class);
    }

    @Override
    public List<Entry<ICIMCCMPriorityItem, Double>> calculateWeight(IObject object) throws Exception {
        if (object != null) {
            return this.OnCalculateWeight(object);
        }
        return null;
    }

    protected List<Map.Entry<ICIMCCMPriorityItem, Double>> OnCalculateWeight(IObject object) throws Exception {
        log.trace("enter to on calculate weight");
        List<Map.Entry<ICIMCCMPriorityItem, Double>> result = new ArrayList<>();
        Map<String, List<ICIMCCMPriorityItem>> priorityItemPerTargetProperty = this.getPriorityItemPerTargetProperty();
        if (priorityItemPerTargetProperty != null) {
            for (Map.Entry<String, List<ICIMCCMPriorityItem>> entry : priorityItemPerTargetProperty.entrySet()) {
                for (ICIMCCMPriorityItem priorityItem : entry.getValue()) {
                    if (priorityItem.isHint(object)) {
                        double currentWeight = priorityItem.getPriorityWeight();
                        result.add(new AbstractMap.SimpleEntry<>(priorityItem, currentWeight));
                    }
                }
            }
        }
        log.trace("complete to do calculation for object ");
        return result;
    }
}
