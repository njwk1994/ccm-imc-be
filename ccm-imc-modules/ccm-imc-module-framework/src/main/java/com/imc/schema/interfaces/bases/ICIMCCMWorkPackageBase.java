package com.imc.schema.interfaces.bases;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.ccm.modules.packagemanage.TaskPackageContext;
import com.ccm.modules.packagemanage.WorkPackageContext;
import com.imc.common.core.enums.frame.PropertyDefinitions;
import com.imc.common.core.enums.frame.RelDirection;
import com.imc.framework.context.Context;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.framework.model.qe.QueryRequest;
import com.imc.schema.interfaces.*;

import java.time.DateTimeException;
import java.util.List;

import static com.ccm.modules.COMContext.*;

public abstract class ICIMCCMWorkPackageBase extends InterfaceDefault implements ICIMCCMWorkPackage {

    public ICIMCCMWorkPackageBase(boolean instantiateRequiredProperties) {
        super(WorkPackageContext.INTERFACE_CIM_CCM_WORK_PACKAGE, instantiateRequiredProperties);
    }

    @Override
    public DateTime getTWPStatus() throws Exception {
        Object actualValue = this.getLatestValue(WorkPackageContext.INTERFACE_CIM_CCM_WORK_PACKAGE, WorkPackageContext.PROPERTY_TTP_STATUS);
        if (actualValue != null && actualValue instanceof DateTime) { return (DateTime) actualValue; } else { throw new DateTimeException("Failed to retrieve the CIM revision issue date."); }

    }

    @Override
    public void setTWPStatus(DateTime TWPStatus) throws Exception {
        this.setPropertyValue(WorkPackageContext.INTERFACE_CIM_CCM_WORK_PACKAGE, WorkPackageContext.PROPERTY_TTP_STATUS, TWPStatus, null, true);
    }

    @Override
    public List<ICIMDocumentMaster> getDocumentList() throws Exception {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addClassDefForQuery(CLASS_CIM_CCM_DOCUMENT_MASTER);
        queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + WorkPackageContext.REL_WORK_PACKAGE_TO_DOCUMENT, PropertyDefinitions.uid1.toString(), SqlKeyword.EQ, this.getUid());
        return Context.Instance.getQueryHelper().query(queryRequest, ICIMDocumentMaster.class);
    }

    @Override
    public List<ICIMCCMDesignObj> getDesignDataList() throws Exception {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addInterfaceDefForQuery(INTERFACE_CIM_DESIGN_OBJ);
        queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + WorkPackageContext.REL_WORK_PACKAGE_TO_DESIGN, PropertyDefinitions.uid1.toString(), SqlKeyword.EQ, this.getUid());
        return Context.Instance.getQueryHelper().query(queryRequest, ICIMCCMDesignObj.class);
    }

    @Override
    public List<ICIMCCMTaskPackage> getTaskPackageList() {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addClassDefForQuery(TaskPackageContext.CLASS_CIM_CCM_TASK_PACKAGE);
        queryRequest.addQueryCriteria(RelDirection.From1To2.getPrefix() + TaskPackageContext.REL_TASK_PACKAGE_TO_WORK_PACKAGE, PropertyDefinitions.uid2.toString(), SqlKeyword.EQ, this.getUid());
        return Context.Instance.getQueryHelper().query(queryRequest, ICIMCCMTaskPackage.class);
    }

    @Override
    public List<ICIMWorkStep> getWorkStepsWithoutDeleted() {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addClassDefForQuery(CLASS_CIM_CCM_WORK_STEP);
        queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + REL_WORK_PACKAGE_2_WORK_STEP, PropertyDefinitions.uid1.toString(), SqlKeyword.EQ, this.getUid());
        return Context.Instance.getQueryHelper().query(queryRequest, ICIMWorkStep.class);
    }
}
