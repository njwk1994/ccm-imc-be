package com.ccm.packagemanage.domain;

import cn.hutool.core.util.NumberUtil;
import com.ccm.modules.packagemanage.BasicPackageContext;
import com.ccm.packagemanage.service.IWorkPackageService;
import com.imc.schema.interfaces.ICIMCCMWorkPackage;
import com.imc.schema.interfaces.ICIMWorkStep;
import com.imc.schema.interfaces.IObject;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 工作包信息类(进度计算用)
 *
 * @author HuangTao
 * @version 1.0
 * @since 2022/9/8 10:11
 */
@Data
@Component
public class WorkPackageData {

    @Getter(value = AccessLevel.NONE)
    @Setter(value = AccessLevel.NONE)
    @Autowired
    private IWorkPackageService iWorkPackageService;
    private static IWorkPackageService workPackageService;

    @PostConstruct
    public void init() {
        workPackageService = iWorkPackageService;
    }

    /**
     * 工作包关联的工作步骤OBID
     */
    private List<String> innerWorkStepOBIDs = new ArrayList<>();
    /**
     * 工作包计划权重
     */
    private BigDecimal planWeight;
    /**
     * 工作包修正进度
     */
    private BigDecimal estimatedProgress;
    /**
     * 工作包下已完成的工作步骤权重统计
     */
    private BigDecimal completedWeightCount = new BigDecimal("0");

    public WorkPackageData() {
    }

    public WorkPackageData(ICIMCCMWorkPackage iccmWorkPackage) throws Exception {
        // 赋值修正进度
        String estimatedProgressStr = String.valueOf(iccmWorkPackage.getLatestValue(BasicPackageContext.INTERFACE_BASIC_PLAN_PACKAGE_OBJ, BasicPackageContext.PROPERTY_PROGRESS));
        if (StringUtils.isNotBlank(estimatedProgressStr)) {
            this.estimatedProgress = new BigDecimal(estimatedProgressStr);
        }
        // 计算计划权重
        Double refreshed = workPackageService.refreshPackagePlanWeight(iccmWorkPackage.getUid());

        this.planWeight = new BigDecimal(refreshed);
        // 添加工作步骤OBID
        List<ICIMWorkStep> workSteps = iccmWorkPackage.getWorkStepsWithoutDeleted();
        this.innerWorkStepOBIDs.addAll(workSteps.stream().map(IObject::getUid).collect(Collectors.toList()));

        // 统计已完成工作步骤权重
        for (ICIMWorkStep workStep : workSteps) {
             if (!workStep.hasActualCompletedDate()) continue;
            double toAddWeight = workStep.getWSWeight();
            BigDecimal toAddWeightBigDecimal = new BigDecimal(toAddWeight);
            this.completedWeightCount = NumberUtil.add(this.completedWeightCount, toAddWeightBigDecimal);
        }
    }

    public void hasNull() throws Exception {
        if (null == this.planWeight) {
            throw new Exception("工作包计划权重统计失败!");
        }
        /*if (null == this.estimatedProgress) {
            throw new Exception("工作包修正进度统计失败!");
        }*/
        if (NumberUtil.equals(this.completedWeightCount, new BigDecimal("0"))) {
            throw new Exception("工作包下已完成的工作步骤权重统计失败,统计权重为0!");
        }
    }
}
