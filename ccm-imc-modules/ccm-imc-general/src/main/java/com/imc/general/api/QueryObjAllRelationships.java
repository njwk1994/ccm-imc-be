package com.imc.general.api;

import com.alibaba.fastjson2.JSONObject;
import com.imc.common.core.enums.frame.RelDirection;
import com.imc.common.core.model.parameters.ObjParam;
import com.imc.common.core.utils.CollectionUtils;
import com.imc.common.core.utils.CommonUtils;
import com.imc.common.core.utils.StringUtils;
import com.imc.common.core.utils.exception.HandlerExceptionUtils;
import com.imc.common.core.web.table.TableData;
import com.imc.framework.api.IServerApi;
import com.imc.framework.api.impl.ServerApiBase;
import com.imc.framework.collections.IRelCollection;
import com.imc.framework.context.Context;
import com.imc.framework.utils.GeneralUtil;
import com.imc.schema.interfaces.IObject;
import com.imc.schema.interfaces.IRel;
import com.imc.schema.interfaces.IRelDef;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class QueryObjAllRelationships extends ServerApiBase<TableData<JSONObject>> {

    private TableData<JSONObject> tableData;
    private ObjParam objParam;

    @Override
    public String getApiId() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void onDeserialize(JSONObject jsonObject) {
        if (null != requestParam) {
            this.objParam = JSONObject.parseObject(this.requestParam.toJSONString(), ObjParam.class);
        }
    }

    @SneakyThrows
    @Override
    public void onHandle() {
        Objects.requireNonNull(this.objParam, HandlerExceptionUtils.paramsInvalid("objParam"));
        if (StringUtils.isEmpty(objParam.getObjUid())) {
            throw new Exception("无效的对象的标识信息");
        }
        if (StringUtils.isEmpty(objParam.getClassDefUid())) {
            throw new Exception("无效的类型定义信息");
        }
        IObject lobj = Context.Instance.getQueryHelper().getObjectByUidAndDefinitionUid(objParam.getObjUid(), objParam.getClassDefUid(), IObject.class);
        Objects.requireNonNull(lobj, HandlerExceptionUtils.objNotFoundFailMsg(objParam.getObjUid(), objParam.getClassDefUid()));
        List<IRel> lcolRels = new ArrayList<>();
        Map<RelDirection, List<IRelDef>> lcolLinkedRelDefsForClassDef = Context.Instance.getCacheHelper().getLinkedRelDefsForClassDef(objParam.getClassDefUid());
        if (CommonUtils.hasValue(lcolLinkedRelDefsForClassDef)) {
            for (Map.Entry<RelDirection, List<IRelDef>> relDirectionListEntry : lcolLinkedRelDefsForClassDef.entrySet()) {
                List<IRelDef> lcolRelDefs = relDirectionListEntry.getValue();
                String lstrRelDef = lcolRelDefs.stream().map(IObject::getUid).collect(Collectors.joining(","));
                IRelCollection rels;
                if (relDirectionListEntry.getKey().equals(RelDirection.From1To2)) {
                    rels = lobj.getEnd1Relationships().getRels(lstrRelDef, false);
                } else {
                    rels = lobj.getEnd2Relationships().getRels(lstrRelDef, false);
                }
                if (CommonUtils.hasValue(rels)) {
                    for (Map.Entry<String, List<IRel>> entry : rels.entrySet()) {
                        lcolRels.addAll(entry.getValue());
                    }
                }
            }
        }
        if (CollectionUtils.hasValue(lcolRels)) {
            lcolRels = lcolRels.stream().distinct().collect(Collectors.toList());
        }
        tableData = GeneralUtil.generateRelTableData(lcolRels);
    }

    @Override
    public TableData<JSONObject> onSerialize() {
        return tableData;
    }

    @Override
    public IServerApi<TableData<JSONObject>> nullInstance() {
        return new QueryObjAllRelationships();
    }
}
