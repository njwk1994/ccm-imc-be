package com.imc.schema.interfaces.defaults;

import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.ccm.modules.schedulermanage.ScheduleContext;
import com.imc.common.core.enums.frame.PropertyDefinitions;
import com.imc.common.core.enums.frame.RelDirection;
import com.imc.framework.context.Context;
import com.imc.framework.model.qe.QueryRequest;
import com.imc.schema.interfaces.ICIMCCMPriorityItem;
import com.imc.schema.interfaces.bases.ICIMCCMScheduleBase;

import java.util.List;

public class ICIMCCMScheduleDefault extends ICIMCCMScheduleBase {
    public ICIMCCMScheduleDefault(boolean instantiateRequiredProperties) {
        super(instantiateRequiredProperties);
    }

    @Override
    public List<ICIMCCMPriorityItem> getSchedulePolicyItems() {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addClassDefForQuery(ScheduleContext.CLASS_CIM_CCM_BID_SCHEDULE_POLICY_ITEM);
        queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + ScheduleContext.REL_SCHEDULE_TO_SCHEDULE_POLICY_ITEM, PropertyDefinitions.uid1.toString(), SqlKeyword.EQ, this.getUid());
        return Context.Instance.getQueryHelper().query(queryRequest, ICIMCCMPriorityItem.class);
    }
}
