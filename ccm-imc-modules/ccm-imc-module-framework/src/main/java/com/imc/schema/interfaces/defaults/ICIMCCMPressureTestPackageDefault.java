package com.imc.schema.interfaces.defaults;

import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.ccm.modules.documentmanage.enums.CIMRevisionStatus;
import com.ccm.modules.packagemanage.TestPackageContext;
import com.imc.common.core.enums.frame.PropertyDefinitions;
import com.imc.common.core.enums.frame.RelDirection;
import com.imc.framework.collections.impl.ObjectCollection;
import com.imc.framework.context.Context;
import com.imc.framework.model.qe.QueryRequest;
import com.imc.schema.interfaces.*;
import com.imc.schema.interfaces.bases.ICIMCCMPressureTestPackageBase;

import java.util.List;

import static com.ccm.modules.COMContext.*;

public class ICIMCCMPressureTestPackageDefault extends ICIMCCMPressureTestPackageBase {

    public ICIMCCMPressureTestPackageDefault(boolean instantiateRequiredProperties) {
        super(instantiateRequiredProperties);
    }

    @Override
    public List<ICIMWorkStep> getWorkSteps() {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addClassDefForQuery(CLASS_CIM_CCM_WORK_STEP);
        queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + TestPackageContext.REL_PRESSURE_TEST_PACKAGE_TO_WORK_STEP, PropertyDefinitions.uid1.toString(), SqlKeyword.EQ, this.getUid());
        return Context.Instance.getQueryHelper().query(queryRequest, ICIMWorkStep.class);
    }

    @Override
    public List<ICIMCCMDesignObj> getDesignOBJs() {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addInterfaceDefForQuery(INTERFACE_CIM_DESIGN_OBJ);
        queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + TestPackageContext.REL_PRESSURE_TEST_PACKAGE_TO_DESIGN_OBJ, PropertyDefinitions.uid1.toString(), SqlKeyword.EQ, this.getUid());
        return Context.Instance.getQueryHelper().query(queryRequest, ICIMCCMDesignObj.class);
    }

    @Override
    public List<ICIMDocumentMaster> getDocuments() {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addClassDefForQuery(CLASS_CIM_CCM_DOCUMENT_MASTER);
        queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + TestPackageContext.REL_PRESSURE_TEST_PACKAGE_TO_DOCUMENT, PropertyDefinitions.uid1.toString(), SqlKeyword.EQ, this.getUid());
        return Context.Instance.getQueryHelper().query(queryRequest, ICIMDocumentMaster.class);
    }

    @Override
    public List<ICIMCCMPTPackageMaterialTemplate> getPTPackageMaterials() {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addClassDefForQuery(TestPackageContext.CLASS_CIM_CCM_PRESSURE_TEST_PACKAGE_MATERIAL);
        queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + TestPackageContext.REL_PRESSURE_TEST_PACKAGE_TO_MATERIAL, PropertyDefinitions.uid1.toString(), SqlKeyword.EQ, this.getUid());
        return Context.Instance.getQueryHelper().query(queryRequest, ICIMCCMPTPackageMaterialTemplate.class);
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
