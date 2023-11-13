package com.ccm.rop.service.impl;
import com.alibaba.fastjson2.JSONObject;
import com.ccm.rop.service.IROPRuleGroupService;
import com.imc.common.core.enums.frame.PropertyDefinitions;
import com.imc.common.core.model.parameters.ObjParam;
import com.imc.common.core.model.parameters.UserEnvParameter;
import com.imc.common.core.utils.StringUtils;
import com.imc.common.core.utils.exception.HandlerExceptionUtils;
import com.imc.framework.collections.impl.ObjectCollection;
import com.imc.framework.context.Context;
import com.imc.framework.utils.GeneralUtil;
import com.imc.framework.utils.SchemaUtil;
import com.imc.schema.interfaces.ICIMHierarchyComposition;
import com.imc.schema.interfaces.IObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Slf4j
@Service
public class IROPRuleGroupServiceImp implements IROPRuleGroupService {
    @Override
    public List<IObject> findRuleGroup() {
        List<IObject> objectsByUIDsAndClassDefinitionUID = Context.Instance.getQueryHelper().getObjectsByUIDsAndClassDefinitionUID(new ArrayList<>(), "CIMROPRuleGroup", IObject.class);
        return objectsByUIDsAndClassDefinitionUID;
    }

    @Override
    public void addRuleGroup(JSONObject param) throws Exception {
        String uid = param.getString(PropertyDefinitions.uid.name());
        String name = param.getString(PropertyDefinitions.name.name());
        String desc = param.getString(PropertyDefinitions.description.name());
        String display = param.getString(PropertyDefinitions.displayName.name());
        ObjectCollection objectCollection = new ObjectCollection(GeneralUtil.getUsername());


        if (StringUtils.isEmpty(uid)){
            //新建
            IObject object = SchemaUtil.newIObject("CIMROPRuleGroup", name, desc, display);
            Objects.requireNonNull(object, HandlerExceptionUtils.createObjFailMsg("CIMROPRuleGroup"));
            object.setProperties(GeneralUtil.jsonObjectToMap(param));
            object.finishCreate(objectCollection);
        }else {
            //更新
            IObject toUpdate = Context.Instance.getQueryHelper().getObjectByUidAndDefinitionUid(uid, "CIMROPRuleGroup", IObject.class);
            Objects.requireNonNull(toUpdate, HandlerExceptionUtils.objNotFoundFailMsg(uid,"CIMROPRuleGroup"));
            toUpdate.BeginUpdate(objectCollection);
            toUpdate.setProperties(GeneralUtil.jsonObjectToMap(param));
            toUpdate.finishCreate(objectCollection);
            ICIMHierarchyComposition toInterface = toUpdate.toInterface(ICIMHierarchyComposition.class);
            Objects.requireNonNull(toInterface,HandlerExceptionUtils.convertInterfaceFailMsg(ICIMHierarchyComposition.class.getSimpleName()));
            toInterface.updateHierarchyRelationships(null,objectCollection,false);
        }
        objectCollection.commit();
    }

    @Override
    public void deleteROPTemplateInfo(ObjParam param) throws Exception {
        String objUid = param.getObjUid();
        String classDefUid = param.getClassDefUid();
//        if (StringUtils.isBlank(objUid)){
//            throw new Exception(MessageUtils.get(""));
//        }
        ObjectCollection objectCollection = new ObjectCollection(new UserEnvParameter(GeneralUtil.getUsername()));

       IObject object = Context.Instance.getQueryHelper().getObjectByUidAndDefinitionUid(objUid, classDefUid, IObject.class);
        ICIMHierarchyComposition icimHierarchyComposition = object.toInterface(ICIMHierarchyComposition.class);
        Objects.requireNonNull(icimHierarchyComposition,HandlerExceptionUtils.convertInterfaceFailMsg("ICIMROPRuleGroup"));
        icimHierarchyComposition.deleteAllChildrenNode(objectCollection,false);
        object.Delete(objectCollection);
        objectCollection.commit();

    }
}
