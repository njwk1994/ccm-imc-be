package com.imc.general.api;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.ibm.icu.text.UFormat;
import com.imc.common.core.model.frame.LiteObject;
import com.imc.common.core.model.method.ClientApiVO;
import com.imc.common.core.model.parameters.ObjParam;
import com.imc.common.core.utils.CollectionUtils;
import com.imc.common.core.utils.exception.HandlerExceptionUtils;
import com.imc.framework.api.IServerApi;
import com.imc.framework.api.impl.ServerApiBase;
import com.imc.framework.constant.ClassDefConstant;
import com.imc.framework.constant.PropertyDefConstant;
import com.imc.framework.context.Context;
import com.imc.framework.model.qe.QueryRequest;
import com.imc.framework.utils.GeneralUtil;
import com.imc.schema.interfaces.ICIMMethod;
import com.imc.schema.interfaces.ICIMWorkflowMethod;
import com.imc.schema.interfaces.IObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GetObjectsMethods extends ServerApiBase<List<ClientApiVO>> {
    private List<ICIMMethod> returnMethodList;
    private ObjParam objParam;

    @Override
    public String getApiId() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void onDeserialize(JSONObject jsonObject) {
        if (null != requestParam) {
            this.objParam = JSONObject.parseObject(requestParam.toJSONString(), ObjParam.class);
        }

    }

    @SneakyThrows
    @Override
    public void onHandle() {
        Objects.requireNonNull(objParam, HandlerExceptionUtils.paramsInvalid("objParam"));
        objParam.validateParam();
        String[] uids = objParam.getObjUid().split(",");
        List<IObject> objList = Context.Instance.getQueryHelper().getObjectsByUIDsAndClassDefinitionUID(Arrays.asList(uids), objParam.getClassDefUid(), IObject.class);
        if (!CollectionUtils.hasValue(objList)) {
            HandlerExceptionUtils.throwObjNotFoundException(objParam.getClassDefUid(), objParam.getObjUid());
        }
        List<ICIMMethod> methodList = Context.Instance.getAccessHelper().getAllMethods(GeneralUtil.getUsername());
        if (CollectionUtils.hasValue(methodList)) {
            this.returnMethodList = methodList.stream().filter(r -> r.isEffectOn(objList)).collect(Collectors.toList());
        }
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
        return new GetObjectsMethods();
    }
}
