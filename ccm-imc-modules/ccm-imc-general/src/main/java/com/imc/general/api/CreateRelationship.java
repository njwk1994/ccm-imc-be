package com.imc.general.api;

import com.alibaba.fastjson2.JSONObject;
import com.imc.common.core.enums.frame.ClassDefinitions;
import com.imc.common.core.utils.CollectionUtils;
import com.imc.common.core.utils.CommonUtils;
import com.imc.common.core.utils.exception.HandlerExceptionUtils;
import com.imc.framework.api.IServerApi;
import com.imc.framework.api.impl.ServerApiBase;
import com.imc.framework.collections.IRelCollection;
import com.imc.framework.collections.impl.ObjectCollection;
import com.imc.framework.context.Context;
import com.imc.framework.utils.GeneralUtil;
import com.imc.framework.utils.SchemaUtil;
import com.imc.general.vo.CreateRelVo;
import com.imc.schema.interfaces.IObject;
import com.imc.schema.interfaces.IRel;
import com.imc.schema.interfaces.IRelDef;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class CreateRelationship extends ServerApiBase<Boolean> {


    private CreateRelVo createRelVo;

    @Override
    public String getApiId() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void onDeserialize(JSONObject jsonObject) {
        if (null != requestParam) {
            this.createRelVo = JSONObject.parseObject(requestParam.toJSONString(), CreateRelVo.class);
        }
    }

    @SneakyThrows
    @Override
    public void onHandle() {
        Objects.requireNonNull(this.createRelVo, HandlerExceptionUtils.paramsInvalid("createRelVo"));
        createRelVo.validateParam();
        ObjectCollection lobjTransaction = new ObjectCollection(GeneralUtil.getUsername());
        String lstrRelDirection = createRelVo.getRelDefUid();
        String lstrRelDef = lstrRelDirection.substring(1);
        IRelDef lobjRelDef = Context.Instance.getCacheHelper().getSchema(lstrRelDef, IRelDef.class);
        Objects.requireNonNull(lobjRelDef, HandlerExceptionUtils.objNotFoundFailMsg(lstrRelDef, ClassDefinitions.RelDef.name()));
        String lstrUid1 = createRelVo.getUid1();
        String lstrUid2 = createRelVo.getUid2();
        if (lstrRelDirection.startsWith("+")) {
            IObject lobjEnd1 = Context.Instance.getQueryHelper().getObjectByUidAndDefinitionUid(lstrUid1, createRelVo.getClassDefinitionUid1(), IObject.class);
            Objects.requireNonNull(lobjEnd1, HandlerExceptionUtils.objNotFoundFailMsg(lstrUid1, createRelVo.getClassDefinitionUid1()));
            List<String> lcolUid2s = Arrays.asList(lstrUid2.split(","));
            List<IObject> lcolEnd2s = Context.Instance.getQueryHelper().getObjectsByUIDsAndClassDefinitionUID(lcolUid2s, createRelVo.getClassDefinitionUid2(), IObject.class);
            if (!CollectionUtils.hasValue(lcolEnd2s)) {
                HandlerExceptionUtils.throwObjNotFoundException(createRelVo.getClassDefinitionUid2(), lstrUid2);
            }
            IRelCollection lobjRels = lobjEnd1.getEnd1Relationships().getRels(lstrRelDef, false);
            if (CommonUtils.hasValue(lobjRels)) {
                List<IRel> lcolRels = lobjRels.get(lstrRelDef);
                for (IObject lobjEnd2 : lcolEnd2s) {
                    if (lcolRels.stream().noneMatch(r -> r.getUid2().equalsIgnoreCase(lobjEnd2.getUid()))) {
                        IRel lobjRel = SchemaUtil.newRelationship(lstrRelDef, lobjEnd1, lobjEnd2);
                        lobjRel.finishCreate(lobjTransaction);
                    }
                }
            } else {
                SchemaUtil.createRelationships1To2(lstrRelDef, lobjEnd1, lobjTransaction, lcolEnd2s);
            }
        } else {
            IObject lobjEnd2 = Context.Instance.getQueryHelper().getObjectByUidAndDefinitionUid(lstrUid2, createRelVo.getClassDefinitionUid2(), IObject.class);
            Objects.requireNonNull(lobjEnd2, HandlerExceptionUtils.objNotFoundFailMsg(lstrUid2, createRelVo.getClassDefinitionUid2()));
            List<String> lcolUid1s = Arrays.asList(lstrUid1.split(","));
            List<IObject> lcolEnd1s = Context.Instance.getQueryHelper().getObjectsByUIDsAndClassDefinitionUID(lcolUid1s, createRelVo.getClassDefinitionUid1(), IObject.class);
            if (!CollectionUtils.hasValue(lcolEnd1s)) {
                HandlerExceptionUtils.throwObjNotFoundException(createRelVo.getClassDefinitionUid1(), lstrUid1);
            }
            IRelCollection lobjRels = lobjEnd2.getEnd2Relationships().getRels(lstrRelDef, false);
            if (CommonUtils.hasValue(lobjRels)) {
                List<IRel> lcolRels = lobjRels.get(lstrRelDef);
                for (IObject lobjEnd1 : lcolEnd1s) {
                    if (lcolRels.stream().noneMatch(r -> r.getUid1().equalsIgnoreCase(lobjEnd1.getUid()))) {
                        IRel lobjRel = SchemaUtil.newRelationship(lstrRelDef, lobjEnd1, lobjEnd2);
                        lobjRel.finishCreate(lobjTransaction);
                    }
                }
            } else {
                SchemaUtil.createRelationships2To1(lstrRelDef, lobjEnd2, lobjTransaction, lcolEnd1s);
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
        return new CreateRelationship();
    }
}
