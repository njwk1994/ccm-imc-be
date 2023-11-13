package com.imc.general.api;

import com.imc.common.core.enums.frame.PropertyDefinitions;
import com.imc.common.core.utils.CommonUtils;
import com.imc.common.core.utils.StringUtils;
import com.imc.common.core.utils.exception.HandlerExceptionUtils;
import com.imc.framework.api.IServerApi;
import com.imc.framework.api.impl.ServerApiBase;
import com.imc.framework.collections.impl.ObjectCollection;
import com.imc.framework.constant.InterfaceDefConstant;
import com.imc.framework.constant.RelDefConstant;
import com.imc.framework.context.Context;
import com.imc.framework.utils.GeneralUtil;
import com.imc.framework.utils.SchemaUtil;
import com.imc.schema.interfaces.ICIMHierarchyObject;
import com.imc.schema.interfaces.IObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.imc.framework.utils.GeneralUtil.jsonObjectToMap;

@Service
@Slf4j
public class GeneralCreate extends ServerApiBase<Boolean> {

    @SneakyThrows
    @Override
    public void onHandle() {
        if (null == this.requestParam) {
            throw new RuntimeException("参数无效");
        }
        String lstrClassDefinitionUid = this.requestParam.getString(PropertyDefinitions.classDefinitionUid.name());
        if (StringUtils.isEmpty(lstrClassDefinitionUid)) {
            throw new RuntimeException("未获取类型定义信息!");
        }
        String lstrUid = this.requestParam.getString(PropertyDefinitions.uid.name());
        if (StringUtils.isNotEmpty(lstrUid)) {
            throw new RuntimeException("uid信息应为空值");
        }
        String username = GeneralUtil.getUsername();
        ObjectCollection lobjTransaction = new ObjectCollection(username);

        String lstrName = this.requestParam.getString(PropertyDefinitions.name.name());
        String lstrDescription = this.requestParam.getString(PropertyDefinitions.description.name());

        IObject lobjCreated = SchemaUtil.newIObject(lstrClassDefinitionUid, lstrName, lstrDescription, CommonUtils.generateDisplay(lstrName, lstrDescription));
        if (lobjCreated == null) {
            throw new Exception("创建对象失败!");
        }
        lobjCreated.setProperties(jsonObjectToMap(this.requestParam, username));
        lobjCreated.finishCreate(lobjTransaction);

        boolean isHierarchyObj = Context.Instance.getCacheHelper().validateClassDefIsHierarchyObj(lstrClassDefinitionUid);
        if (isHierarchyObj) {
            ICIMHierarchyObject lobjCreatedHierarchyObject = lobjCreated.toInterface(ICIMHierarchyObject.class);
            ICIMHierarchyObject lobjParent = null;
            //特殊处理层级
            String lstrParentUid = this.requestParam.getString("-" + RelDefConstant.HIERARCHY_ITEMS);
            if (StringUtils.isNotEmpty(lstrParentUid)) {
                lobjParent = Context.Instance.getQueryHelper().getObjectByUidAndDefinitionUid(lstrParentUid, InterfaceDefConstant.IMC_HIERARCHY_OBJ, ICIMHierarchyObject.class);
                Objects.requireNonNull(lobjParent, HandlerExceptionUtils.objNotFoundFailMsg(lstrParentUid, InterfaceDefConstant.IMC_HIERARCHY_OBJ));
            }
            lobjCreatedHierarchyObject.setHierarchyPropsWhenCreate(lobjParent);
            //处理关联
            lobjCreatedHierarchyObject.saveRelOfParent(lobjParent, lobjTransaction);
        }
        //处理关联关系相关的
        lobjCreated.processRels(this.requestParam, lobjTransaction);
        lobjTransaction.commit();
    }

    @Override
    public Boolean onSerialize() {
        return true;
    }

    @Override
    public IServerApi<Boolean> nullInstance() {
        return new GeneralCreate();
    }

    @Override
    public String getApiId() {
        return this.getClass().getSimpleName();
    }
}
