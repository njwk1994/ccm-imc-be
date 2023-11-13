package com.imc.general.api;

import com.alibaba.fastjson2.JSONObject;
import com.imc.common.core.enums.frame.PropertyDefinitions;
import com.imc.common.core.model.frame.MJSONObject;
import com.imc.common.core.model.method.ClientApiVO;
import com.imc.common.core.utils.CollectionUtils;
import com.imc.common.core.utils.StringUtils;
import com.imc.common.core.utils.exception.HandlerExceptionUtils;
import com.imc.framework.api.IServerApi;
import com.imc.framework.api.impl.ServerApiBase;
import com.imc.framework.constant.ClassDefConstant;
import com.imc.framework.context.Context;
import com.imc.framework.utils.GeneralUtil;
import com.imc.schema.interfaces.ICIMMethod;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GetMethodsByClassDef extends ServerApiBase<List<ClientApiVO>> {

    private String classDef;

    private List<ICIMMethod> returnMethodList;

    @Override
    public String getApiId() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void onDeserialize(JSONObject jsonObject) {
        if (null != requestParam) {
            this.classDef = requestParam.getString(PropertyDefinitions.classDefinitionUid.name());
        }
    }

    @SneakyThrows
    @Override
    public void onHandle() {
        if (StringUtils.isBlank(this.classDef)) {
            throw new RuntimeException(HandlerExceptionUtils.paramsInvalid(PropertyDefinitions.classDefinitionUid.name()));
        }
        List<ICIMMethod> methodList = Context.Instance.getAccessHelper().getAllMethods(GeneralUtil.getUsername());
        if (!CollectionUtils.hasValue(methodList)) {
            return;
        }
        List<MJSONObject> requiredInterfaceDefsForClassDef = Context.Instance.getCacheHelper().getRequiredInterfaceDefsForClassDef(this.classDef);
        this.returnMethodList = methodList.stream().filter(r -> r.isEffectOnByInterface(requiredInterfaceDefsForClassDef.stream().map(MJSONObject::getUid).toArray(String[]::new))).collect(Collectors.toList());
    }

    @Override
    public List<ClientApiVO> onSerialize() {
        if (CollectionUtils.hasValue(this.returnMethodList)) {
            return this.returnMethodList.stream().map(ICIMMethod::toClientApiVO).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public IServerApi<List<ClientApiVO>> nullInstance() {
        return new GetMethodsByClassDef();
    }
}
