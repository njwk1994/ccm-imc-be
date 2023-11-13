package com.imc.general.api;

import com.imc.common.core.enums.frame.PropertyDefinitions;
import com.imc.common.core.utils.StringUtils;
import com.imc.framework.api.IServerApi;
import com.imc.framework.api.impl.ServerApiBase;
import com.imc.framework.collections.impl.ObjectCollection;
import com.imc.framework.context.Context;
import com.imc.framework.utils.GeneralUtil;
import com.imc.schema.interfaces.IObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.imc.framework.utils.GeneralUtil.jsonObjectToMap;

@Service
@Slf4j
public class GeneralUpdate extends ServerApiBase<Boolean> {
    @SneakyThrows
    @Override
    public void onHandle() {
        if (this.requestParam == null) {
            throw new RuntimeException("参数无效");
        }
        String lstrClassDef = this.requestParam.getString(PropertyDefinitions.classDefinitionUid.name());
        if (StringUtils.isEmpty(lstrClassDef)) {
            throw new RuntimeException("未获取类型定义信息!");
        }

        String lstrName = this.requestParam.getString(PropertyDefinitions.name.name());
        if (StringUtils.isEmpty(lstrName)) {
            throw new RuntimeException("未找到对象的Name信息!");
        }
        String lstrUid = this.requestParam.getString(PropertyDefinitions.uid.name());
        if (StringUtils.isEmpty(lstrUid)) throw new RuntimeException("未解析到UID信息!");

        IObject lobjExist = Context.Instance.getQueryHelper().getObjectByUidAndDefinitionUid(lstrUid, lstrClassDef, IObject.class);
        if (lobjExist == null) throw new RuntimeException("未找到指定对象,UID:" + lstrUid);

        String username = GeneralUtil.getUsername();
        ObjectCollection lcolContainer = new ObjectCollection(username);

        lobjExist.BeginUpdate(lcolContainer);
        lobjExist.setProperties(jsonObjectToMap(this.requestParam, username));
        lobjExist.FinishUpdate(lcolContainer);
        //处理关联关系
        lobjExist.processRels(this.requestParam, lcolContainer);
        lcolContainer.commit();
    }

    @Override
    public Boolean onSerialize() {
        return true;
    }

    @Override
    public IServerApi<Boolean> nullInstance() {
        return new GeneralUpdate();
    }

    @Override
    public String getApiId() {
        return this.getClass().getSimpleName();
    }
}
