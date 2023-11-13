package com.imc.schema.interfaces.defaults;


import com.ccm.modules.documentmanage.enums.CIMRevisionStatus;
import com.ccm.modules.documentmanage.enums.DesignObjOperateStatus;
import com.imc.framework.collections.impl.ObjectCollection;
import com.imc.schema.interfaces.bases.ICIMRevisionItemBase;

public class ICIMRevisionItemDefault extends ICIMRevisionItemBase {
    public ICIMRevisionItemDefault(boolean instantiateRequiredProperties) {
        super(instantiateRequiredProperties);
    }

    @Override
    public void setObjectDelete() throws Exception {
        ObjectCollection objectCollection = new ObjectCollection();
        this.BeginUpdate(objectCollection);
        this.setCIMRevisionItemOperationState(DesignObjOperateStatus.DELETE.getCode());
        this.setCIMRevisionItemRevState(CIMRevisionStatus.SUPERSEDED.getCode());
        this.FinishUpdate(objectCollection);
        objectCollection.commit();
    }
}
