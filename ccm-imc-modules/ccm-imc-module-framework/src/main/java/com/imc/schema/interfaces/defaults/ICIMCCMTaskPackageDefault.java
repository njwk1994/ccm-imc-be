package com.imc.schema.interfaces.defaults;

import com.ccm.modules.documentmanage.enums.CIMRevisionStatus;
import com.ccm.modules.packagemanage.BasicPackageContext;
import com.ccm.modules.packagemanage.WBSContext;
import com.imc.framework.collections.impl.ObjectCollection;
import com.imc.framework.utils.GeneralUtil;
import com.imc.schema.interfaces.ICIMRevisionItem;
import com.imc.schema.interfaces.ICIMWorkStep;
import com.imc.schema.interfaces.bases.ICIMCCMTaskPackageBase;

import java.util.List;
import java.util.stream.Collectors;

public class ICIMCCMTaskPackageDefault extends ICIMCCMTaskPackageBase {
    public ICIMCCMTaskPackageDefault(boolean instantiateRequiredProperties) {
        super(instantiateRequiredProperties);
    }

    @Override
    public List<ICIMWorkStep> getWorkStepsWithSamePurpose() throws Exception {
        String purpose = String.valueOf(this.getLatestValue(WBSContext.INTERFACE_CIM_CCM_ICIMCCMWBS, WBSContext.PROPERTY_PURPOSE));
        List<ICIMWorkStep> workSteps = this.getWorkStepsWithoutDeletedList();
        return workSteps.stream().filter(w -> {
            try {
                return purpose.equals(w.getWSTPProcessPhase());
            } catch (Exception e) {
                return false;
            }
        }).collect(Collectors.toList());
    }

    @Override
    public void updateProgress(Double progress, boolean withTransaction) throws Exception {
        ObjectCollection objectCollection = new ObjectCollection(GeneralUtil.getUsername());
        this.BeginUpdate(objectCollection);
        this.setValue(BasicPackageContext.INTERFACE_BASIC_PLAN_PACKAGE_OBJ, BasicPackageContext.PROPERTY_PROGRESS, progress, null);
        this.FinishUpdate(objectCollection);
        objectCollection.commit();
    }

    @Override
    public void setObjectRevised() throws Exception {
        ObjectCollection objectCollection = new ObjectCollection();
        ICIMRevisionItem icimRevisionItem = this.toInterface(ICIMRevisionItem.class);
        icimRevisionItem.BeginUpdate(objectCollection);
        icimRevisionItem.setCIMRevisionItemRevState(CIMRevisionStatus.REVISED.getCode());
        icimRevisionItem.FinishUpdate(objectCollection);
        objectCollection.commit();
    }
}
