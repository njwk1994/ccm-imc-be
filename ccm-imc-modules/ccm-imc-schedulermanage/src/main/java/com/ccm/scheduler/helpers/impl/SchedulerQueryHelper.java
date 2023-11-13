package com.ccm.scheduler.helpers.impl;

import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.ccm.modules.schedulermanage.BidSectionContext;
import com.ccm.scheduler.helpers.ISchedulerQueryHelper;
import com.imc.common.core.enums.frame.PropertyDefinitions;
import com.imc.common.core.enums.frame.RelDirection;
import com.imc.framework.helpers.query.impl.QueryHelper;
import com.imc.framework.model.qe.QueryRequest;
import com.imc.schema.interfaces.*;
import org.springframework.stereotype.Service;
import com.ccm.modules.schedulermanage.ScheduleContext;

import java.util.List;

/**
 * @description：TODO
 * @author： kekai.huang
 * @create： 2023/10/27 15:34
 */
@Service
public class SchedulerQueryHelper extends QueryHelper implements ISchedulerQueryHelper {
    @Override
    public ICIMCCMBidSection getBidSectionByUid(String uid) {
        return this.getObjectByUidAndDefinitionUid(uid, BidSectionContext.CLASS_CIM_CCM_BID_SECTION, ICIMCCMBidSection.class);
    }

    @Override
    public ICIMCCMSchedule getScheduleByUid(String uid) {
        return this.getObjectByUidAndDefinitionUid(uid, ScheduleContext.CLASS_CIM_CCM_SCHEDULE, ICIMCCMSchedule.class);
    }

    @Override
    public List<ICIMCCMCWA> getCWAsByUid(String uid) {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addClassDefForQuery(BidSectionContext.CLASS_CIM_CCM_BID_SECTION_CWA);
        queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + BidSectionContext.REL_BID_SECTION_TO_CWA, PropertyDefinitions.uid1.toString(), SqlKeyword.EQ, uid);
        return this.query(queryRequest, ICIMCCMCWA.class);
    }
}
