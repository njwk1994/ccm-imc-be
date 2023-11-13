package com.imc.general.api;

import com.alibaba.fastjson2.JSONObject;
import com.imc.common.core.model.parameters.ObjParam;
import com.imc.common.core.utils.CommonUtils;
import com.imc.common.core.utils.exception.HandlerExceptionUtils;
import com.imc.common.core.web.comp.OptionItem;
import com.imc.framework.api.IServerApi;
import com.imc.framework.api.impl.ServerApiBase;
import com.imc.framework.collections.IObjectCollection;
import com.imc.framework.context.Context;
import com.imc.schema.interfaces.ICIMHierarchyComposition;
import com.imc.schema.interfaces.ICIMOrderObject;
import com.imc.schema.interfaces.IObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class QuerySelectTreeNodeChildrenByUidAndClassDef extends ServerApiBase<List<OptionItem>> {

    private ObjParam objParam;

    private List<OptionItem> optionItems;


    @SneakyThrows
    @Override
    public void onHandle() {
        Objects.requireNonNull(objParam, HandlerExceptionUtils.paramsInvalid("objParam"));
        objParam.validateParam();
        ICIMHierarchyComposition hierarchyComposition = Context.Instance.getQueryHelper().getObjectByUidAndDefinitionUid(objParam.getObjUid(), objParam.getClassDefUid(), ICIMHierarchyComposition.class);
        Objects.requireNonNull(hierarchyComposition, HandlerExceptionUtils.objNotFoundFailMsg(objParam.getObjUid(), objParam.getClassDefUid()));
        IObjectCollection<IObject> children = hierarchyComposition.getChildren();
        if (!CommonUtils.hasValue(children)) {
            return;
        }
        this.optionItems = children.getList().stream().sorted(Comparator.comparingInt(r -> r.toInterface(ICIMOrderObject.class).getOrderValue())).map(r -> r.toOptionItem(true)).collect(Collectors.toList());
    }

    @Override
    public List<OptionItem> onSerialize() {
        return optionItems;
    }

    @Override
    public IServerApi<List<OptionItem>> nullInstance() {
        return new QuerySelectTreeNodeChildrenByUidAndClassDef();
    }

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
}
