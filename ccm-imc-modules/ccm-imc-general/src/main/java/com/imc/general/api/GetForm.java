package com.imc.general.api;

import com.alibaba.fastjson2.JSONObject;
import com.imc.common.core.model.parameters.QueryFormParam;
import com.imc.common.core.utils.exception.HandlerExceptionUtils;
import com.imc.common.core.web.form.FormDataInfo;
import com.imc.framework.api.IServerApi;
import com.imc.framework.api.impl.ServerApiBase;
import com.imc.framework.utils.GeneralUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class GetForm extends ServerApiBase<FormDataInfo> {

    private FormDataInfo formDataInfo;

    private QueryFormParam queryFormParam;


    @SneakyThrows
    @Override
    public void onHandle() {
        Objects.requireNonNull(this.queryFormParam, HandlerExceptionUtils.paramsInvalid("queryFormParam"));
        this.formDataInfo = GeneralUtil.getForm(queryFormParam);
    }

    @Override
    public FormDataInfo onSerialize() {
        return this.formDataInfo;
    }

    @Override
    public IServerApi<FormDataInfo> nullInstance() {
        return new GetForm();
    }

    @Override
    public String getApiId() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void onDeserialize(JSONObject jsonObject) {
        if (null != requestParam) {
            this.queryFormParam = JSONObject.parseObject(requestParam.toJSONString(), QueryFormParam.class);
        }
    }
}
