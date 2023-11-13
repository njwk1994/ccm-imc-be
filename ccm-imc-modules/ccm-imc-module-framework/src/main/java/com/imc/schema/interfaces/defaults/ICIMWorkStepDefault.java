package com.imc.schema.interfaces.defaults;

import com.alibaba.druid.util.StringUtils;
import com.ccm.modules.packagemanage.enums.WSStatusEnum;
import com.imc.schema.interfaces.bases.ICIMWorkStepBase;

public class ICIMWorkStepDefault extends ICIMWorkStepBase {
    public ICIMWorkStepDefault(boolean instantiateRequiredProperties) {
        super(instantiateRequiredProperties);
    }

    @Override
    public boolean hasActualCompletedDate() {
        Object actualEnd = this.getLatestValue("ICIMCCMBasicPlanPackageObj","ActualEnd");
        if (actualEnd == null) return false;
        if (StringUtils.isEmpty(actualEnd.toString())) return false;
        return true;
    }

    @Override
    public boolean isDeleteStatus() throws Exception {
        String status = getWSStatus();
        return WSStatusEnum.ROP_DELETE.getCode().equalsIgnoreCase(status) || WSStatusEnum.REVISED_DELETE.getCode().equalsIgnoreCase(status);
    }
}
