package com.ccm.packagemanage.executor;


import com.ccm.packagemanage.domain.CompWeight;
import com.ccm.packagemanage.domain.DocumentPriorityWeightDTO;
import com.imc.schema.interfaces.ICIMCCMDesignObj;
import com.imc.schema.interfaces.ICIMCCMPriority;
import com.imc.schema.interfaces.ICIMCCMPriorityItem;
import com.imc.schema.interfaces.IObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@Slf4j
public class PriorityWeightCalculationExecutor implements Callable<DocumentPriorityWeightDTO> {
    private IObject document;
    private List<ICIMCCMDesignObj> components;
    private ICIMCCMPriority priority;

    public PriorityWeightCalculationExecutor(IObject document, List<ICIMCCMDesignObj> components, ICIMCCMPriority priority) {
        this.document = document;
        this.components = components;
        this.priority = priority;
        if (this.document == null || this.priority == null)
            throw new IllegalArgumentException("invalid document or priority is null");
    }

    @Override
    public DocumentPriorityWeightDTO call() throws Exception {
        Map<String, ICIMCCMPriorityItem> cachedPriorityItems = new HashMap<>();
        Map<String, List<CompWeight>> cachedCompWeightBasedOnPriorityItem = new HashMap<>();
        for (IObject comp: components) {
            log.trace("enter to calculate weight for " + comp.getName());
            List<Map.Entry<ICIMCCMPriorityItem, Double>> weights = this.priority.calculateWeight(comp);
            if (weights != null && weights.size() > 0) {
                for (Map.Entry<ICIMCCMPriorityItem, Double> weight : weights) {
                    ICIMCCMPriorityItem priorityItem = weight.getKey();
                    Double weightValue = weight.getValue();
                    cachedPriorityItems.putIfAbsent(priorityItem.getUid(), priorityItem);
                    List<CompWeight> based = cachedCompWeightBasedOnPriorityItem.getOrDefault(priorityItem.getUid(), new ArrayList<>());
                    based.add(new CompWeight(comp.getUid(), weightValue));
                    if (based.size() == 1)
                        cachedCompWeightBasedOnPriorityItem.put(priorityItem.getUid(), based);
                    else
                        cachedCompWeightBasedOnPriorityItem.replace(priorityItem.getUid(), based);
                }
            }
        }

        log.info("cached comps weight based on priority item quantity:" + cachedCompWeightBasedOnPriorityItem.size());
        Double currentWeight = 0.0;
        Map<String, List<Double>> grpOfProperty = new HashMap<>();
        Map<String, String> mapCalculateType = new HashMap<>();
        for (Map.Entry<String, List<CompWeight>> entry : cachedCompWeightBasedOnPriorityItem.entrySet()) {
            ICIMCCMPriorityItem priorityItem = cachedPriorityItems.getOrDefault(entry.getKey(), null);
            if (priorityItem == null)
                throw new Exception("cannot found priority item with " + entry.getKey() + " for further operation");

            String targetPropertyDef = priorityItem.getPriorityTargetProperty();
            String calculateType = priorityItem.getPriorityCalculateType();
            mapCalculateType.putIfAbsent(targetPropertyDef, calculateType);
            List<Double> doubleList = grpOfProperty.getOrDefault(targetPropertyDef, new ArrayList<>());
            doubleList.addAll(entry.getValue().stream().map(CompWeight::getWeight).collect(Collectors.toList()));
            if (grpOfProperty.containsKey(targetPropertyDef))
                grpOfProperty.replace(targetPropertyDef, doubleList);
            else
                grpOfProperty.put(targetPropertyDef, doubleList);
        }
        log.info("group by property size quantity:" + grpOfProperty.size());
        if (grpOfProperty.size() > 0) {
            for (Map.Entry<String, List<Double>> listEntry : grpOfProperty.entrySet()) {
                String targetProperty = listEntry.getKey();
                String calculateType = mapCalculateType.get(targetProperty);
                if (calculateType == null || StringUtils.isEmpty(calculateType))
                    throw new Exception("invalid calculation type as it is NULL during priority progress");

                if (calculateType.equalsIgnoreCase("ELT_Accumulation_PriorityCalculateType")) {
                    for (Double doubleEntry : listEntry.getValue()) {
                        currentWeight += doubleEntry;
                    }
                } else if (calculateType.equalsIgnoreCase("ELT_MaxValue_PriorityCalculateType")) {
                    currentWeight += Collections.max(listEntry.getValue());
                }
            }
        }
        return new DocumentPriorityWeightDTO(this.document, currentWeight);
    }
}
