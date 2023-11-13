package com.imc.schema.interfaces.bases;

import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.ccm.modules.COMContext;
import com.ccm.modules.packagemanage.TaskPackageContext;
import com.ccm.modules.packagemanage.enums.WSStatusEnum;
import com.imc.common.core.enums.frame.PropertyDefinitions;
import com.imc.common.core.enums.frame.RelDirection;
import com.imc.framework.context.Context;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.framework.model.qe.QueryRequest;
import com.imc.schema.interfaces.*;

import java.util.ArrayList;
import java.util.List;

import static com.ccm.modules.COMContext.*;

public abstract class ICIMCCMTaskPackageBase extends InterfaceDefault implements ICIMCCMTaskPackage {

    public ICIMCCMTaskPackageBase(boolean instantiateRequiredProperties) {
        super(TaskPackageContext.INTERFACE_CIM_CCM_TASK_PACKAGE, instantiateRequiredProperties);
    }

    @Override
    public String getTTPStatus() {
        Object actualValue = this.getLatestValue(TaskPackageContext.INTERFACE_CIM_CCM_TASK_PACKAGE, TaskPackageContext.PROPERTY_TTP_STATUS);
        return actualValue != null ? actualValue.toString() : "";
    }

    @Override
    public void setTTPStatus(String TTPStatus) throws Exception {
        this.setPropertyValue(TaskPackageContext.INTERFACE_CIM_CCM_TASK_PACKAGE, TaskPackageContext.PROPERTY_TTP_STATUS, TTPStatus, null, true);
    }

    @Override
    public List<ICIMDocumentMaster> getDocumentList() throws Exception {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addClassDefForQuery(CLASS_CIM_DESIGN_DOC_MASTER);
        queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + TaskPackageContext.REL_TASK_PACKAGE_TO_DOCUMENT, PropertyDefinitions.uid1.toString(), SqlKeyword.EQ, this.getUid());
        return Context.Instance.getQueryHelper().query(queryRequest, ICIMDocumentMaster.class);
    }

    @Override
    public List<ICIMCCMDesignObj> getDesignDataList() throws Exception {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addInterfaceDefForQuery(INTERFACE_CIM_DESIGN_OBJ);
        queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + TaskPackageContext.REL_TASK_PACKAGE_TO_DESIGN_OBJ, PropertyDefinitions.uid1.toString(), SqlKeyword.EQ, this.getUid());
        return Context.Instance.getQueryHelper().query(queryRequest, ICIMCCMDesignObj.class);
    }

    @Override
    public List<ICIMWorkStep> getWorkStepsWithoutDeletedList() throws Exception {
        List<ICIMCCMDesignObj> designDataList = getDesignDataList();
        List<ICIMWorkStep> workStepCollection = new ArrayList<>();
        for (IObject designData : designDataList) {
            QueryRequest queryRequest = new QueryRequest();
            queryRequest.addClassDefForQuery(CLASS_CIM_CCM_WORK_STEP);
            queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + COMContext.REL_DESIGN_OBJ_TO_WORK_STEP, PropertyDefinitions.uid1.toString(), SqlKeyword.EQ, designData.getUid());
            List<ICIMWorkStep> workSteps = Context.Instance.getQueryHelper().query(queryRequest, ICIMWorkStep.class);
            for (ICIMWorkStep workStep : workSteps) {
                // 过滤删除状态工作步骤
                String wsStatus = workStep.getWSStatus();
                if (!wsStatus.equalsIgnoreCase(WSStatusEnum.REVISED_DELETE.getCode()) && !wsStatus.equalsIgnoreCase(WSStatusEnum.ROP_DELETE.getCode())) {
                    workStepCollection.add(workStep);
                }
            }

        }
        return workStepCollection;
    }
}
