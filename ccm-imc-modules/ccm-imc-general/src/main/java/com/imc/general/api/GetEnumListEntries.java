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
import com.imc.schema.interfaces.IEnumListType;
import com.imc.schema.interfaces.IObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GetEnumListEntries extends ServerApiBase<SelectionOption> {

    private SelectionOption selectionOption;

    private String uid;

    @Override
    public String getApiId() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void onDeserialize(JSONObject jsonObject) {
        if (null != requestParam) {
            this.uid = requestParam.getString("uid");
        }
    }

    @SneakyThrows
    @Override
    public void onHandle() {
        if (StringUtils.isBlank(this.uid)) {
            throw new RuntimeException(HandlerExceptionUtils.paramsInvalid("uid"));
        }
        IEnumListType enumListType = Context.Instance.getCacheHelper().getSchema(uid, IEnumListType.class);
        if (enumListType == null)
            HandlerExceptionUtils.throwObjNotFoundException("EnumListType", uid);
        List<IObject> entries = enumListType.getEntries();
        selectionOption = new SelectionOption();
        if (CollectionUtils.hasValue(entries)) {
            selectionOption.setOptionItems(entries.stream().map(r -> new OptionItem(r.getDisplayName(), r.getUid())).collect(Collectors.toList()));
        }
    }

    @Override
    public SelectionOption onSerialize() {
        return this.selectionOption;
    }

    @Override
    public IServerApi<SelectionOption> nullInstance() {
        return new GetEnumListEntries();
    }
}
