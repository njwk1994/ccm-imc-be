package com.imc.general.api;

import com.imc.common.core.model.parameters.UserEnvParameter;
import com.imc.common.core.utils.exception.HandlerExceptionUtils;
import com.imc.framework.api.IServerApi;
import com.imc.framework.api.impl.ServerApiBase;
import com.imc.framework.collections.impl.ObjectCollection;
import com.imc.framework.constant.InterfaceDefConstant;
import com.imc.framework.utils.GeneralUtil;
import com.imc.schema.interfaces.ICIMHierarchyComposition;
import com.imc.schema.interfaces.IObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class GeneralDelete extends ServerApiBase<Boolean> {
    @SneakyThrows
    @Override
    public void onHandle() {
        if (this.contextObjs.isEmpty()) {
            return;
        }
        ObjectCollection lobjTransaction = new ObjectCollection(new UserEnvParameter(GeneralUtil.getUsername()));
        for (IObject lobj : this.contextObjs) {
            if (lobj.getClassBase().hasInterface(InterfaceDefConstant.IMC_HIERARCHY_OBJ_COMPOSITION)) {
                ICIMHierarchyComposition lobjHierarchyComposition = lobj.toInterface(ICIMHierarchyComposition.class);
                Objects.requireNonNull(lobjHierarchyComposition, HandlerExceptionUtils.convertInterfaceFailMsg(InterfaceDefConstant.IMC_HIERARCHY_OBJ_COMPOSITION));
                lobjHierarchyComposition.deleteAllChildrenNode(lobjTransaction, false);
            } else {
                lobj.Delete(lobjTransaction);
            }
        }
        lobjTransaction.commit();
    }

    @Override
    public Boolean onSerialize() {
        return true;
    }

    @Override
    public IServerApi<Boolean> nullInstance() {
        return new GeneralDelete();
    }

    @Override
    public String getApiId() {
        return this.getClass().getSimpleName();
    }
}
