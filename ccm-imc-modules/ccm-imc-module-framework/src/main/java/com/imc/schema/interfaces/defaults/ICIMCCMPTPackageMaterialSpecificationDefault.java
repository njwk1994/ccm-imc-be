package com.imc.schema.interfaces.defaults;

import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.ccm.modules.packagemanage.TestPackageContext;
import com.imc.common.core.enums.frame.PropertyDefinitions;
import com.imc.common.core.enums.frame.RelDirection;
import com.imc.framework.context.Context;
import com.imc.framework.model.qe.QueryRequest;
import com.imc.schema.interfaces.ICIMCCMPTPackageMaterialTemplate;
import com.imc.schema.interfaces.bases.ICIMCCMPTPackageMaterialSpecificationBase;

import java.util.List;

public class ICIMCCMPTPackageMaterialSpecificationDefault extends ICIMCCMPTPackageMaterialSpecificationBase {

    public ICIMCCMPTPackageMaterialSpecificationDefault(boolean instantiateRequiredProperties) {
        super(instantiateRequiredProperties);
    }

    @Override
    public List<ICIMCCMPTPackageMaterialTemplate> getPTPMaterialTemplates() {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addClassDefForQuery(TestPackageContext.CLASS_CIM_CCM_PRESSURE_TEST_PACKAGE_MATERIAL_TEMPLATE);
        queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + TestPackageContext.REL_PACKAGE_MATERIAL_SPECIFICATION_TO_MATERIAL_TEMPLATE, PropertyDefinitions.name1.toString(), SqlKeyword.EQ, this.getPTPMaterialTemplates());
        return Context.Instance.getQueryHelper().query(queryRequest, ICIMCCMPTPackageMaterialTemplate.class);
    }
}
