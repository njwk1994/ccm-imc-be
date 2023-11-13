package com.imc.general.api;

import com.alibaba.fastjson2.JSONObject;
import com.imc.common.core.utils.CollectionUtils;
import com.imc.common.core.utils.StringUtils;
import com.imc.common.core.utils.exception.HandlerExceptionUtils;
import com.imc.common.core.web.comp.OptionItem;
import com.imc.common.core.web.option.SelectionOption;
import com.imc.framework.api.IServerApi;
import com.imc.framework.api.impl.ServerApiBase;
import com.imc.framework.context.Context;
import com.imc.schema.interfaces.IObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class QuerySelectOptionByInterfaceDefUid extends ServerApiBase<SelectionOption> {

    private String interfaceDefUid;
    private final SelectionOption selectionOption = new SelectionOption();

    @Override
    public void onDeserialize(JSONObject jsonObject) {
        if (null != this.requestParam) {
            this.interfaceDefUid = this.requestParam.getString("interfaceDefUid");
        }
    }

    @Override
    public void onHandle() {
        if (StringUtils.isBlank(interfaceDefUid)) {
            throw new RuntimeException(HandlerExceptionUtils.paramsInvalid("interfaceDefUid"));
        }
        List<IObject> objects = Context.Instance.getQueryHelper().getObjectsByDefinitionUid(interfaceDefUid, IObject.class);
        if (!CollectionUtils.hasValue(objects)) {
            return;
        }
        selectionOption.setOptionItems(objects.stream().map(r -> new OptionItem(r.getDescription(), r.getUid(), r.getClassDefinitionUid())).sorted(Comparator.comparing(OptionItem::getLabel)).collect(Collectors.toList()));
    }

    @Override
    public SelectionOption onSerialize() {
        return selectionOption;
    }

    @Override
    public IServerApi<SelectionOption> nullInstance() {
        return new QuerySelectOptionByInterfaceDefUid();
    }

    @Override
    public String getApiId() {
        return this.getClass().getSimpleName();
    }
}
