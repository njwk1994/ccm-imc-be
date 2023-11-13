package com.ccm.rop.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.ccm.rop.service.IROPRuleGroupItemService;
import com.imc.common.core.enums.frame.PropertyDefinitions;
import com.imc.common.core.model.parameters.DeleteParam;
import com.imc.common.core.model.parameters.UserEnvParameter;
import com.imc.common.core.utils.CollectionUtils;
import com.imc.common.core.utils.StringUtils;
import com.imc.common.core.utils.exception.HandlerExceptionUtils;
import com.imc.common.core.web.table.TableData;
import com.imc.framework.collections.impl.ObjectCollection;
import com.imc.framework.context.Context;
import com.imc.framework.model.qe.QueryRequest;
import com.imc.framework.utils.GeneralUtil;
import com.imc.framework.utils.SchemaUtil;
import com.imc.schema.interfaces.ICIMHierarchyComposition;
import com.imc.schema.interfaces.ICIMHierarchyObject;
import com.imc.schema.interfaces.IObject;
import com.imc.schema.interfaces.IRel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class IROPRuleGroupItemServiceImpl implements IROPRuleGroupItemService {


    @Override
    public TableData<JSONObject> getRuleGroupItem() throws Exception {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addClassDefForQuery("CIMROPRuleGroupItem");
        List<IObject> query = Context.Instance.getQueryHelper().query(queryRequest, IObject.class);
        return GeneralUtil.generateTableData(query, GeneralUtil.getUsername(), "CIMROPRuleGroupItem");
    }

    @Override
    public void addOrUpdate(JSONObject param) throws Exception {

        String uid = param.getString(PropertyDefinitions.uid.name());
        String name = param.getString(PropertyDefinitions.name.name());
        String desc = param.getString(PropertyDefinitions.description.name());
        String display = param.getString(PropertyDefinitions.displayName.name());
        ObjectCollection objectCollection = new ObjectCollection(GeneralUtil.getUsername());
        String username = GeneralUtil.getUsername();
        String userCreateScope = GeneralUtil.getUserCreateScope(username);
        ObjectCollection lobjTransaction = new ObjectCollection(new UserEnvParameter(username,userCreateScope));
        //获取父对象信息
        String parentObjUid = param.getString("-" + "CIMROPRuleGroup2Item");
        IObject parent =null;
        if (StringUtils.isNotBlank(parentObjUid)){
            parent = Context.Instance.getQueryHelper().getObjectByUidAndDefinitionUid(parentObjUid,"CIMROPRuleGroupItem",IObject.class);
            Objects.requireNonNull(parent,HandlerExceptionUtils.objNotFoundFailMsg(parentObjUid,"CIMROPRuleGroupItem"));
        }
        ICIMHierarchyObject icimHierarchyObject = null;
        if (parent !=null){
             icimHierarchyObject = parent.toInterface(ICIMHierarchyObject.class);
        }

        if (StringUtils.isEmpty(uid)){
            //新建
            IObject creatObject = SchemaUtil.newIObject("CIMROPRuleGroupItem", name, desc, display);
            Objects.requireNonNull(creatObject, HandlerExceptionUtils.createObjFailMsg("CIMROPRuleGroupItem"));
            creatObject.setProperties(GeneralUtil.jsonObjectToMap(param));
            creatObject.finishCreate(objectCollection);

            //处理层级信息
            ICIMHierarchyObject hierarchyObject = creatObject.toInterface(ICIMHierarchyObject.class);
            hierarchyObject.setHierarchyPropsWhenCreate(icimHierarchyObject);
            //创建关联关系
            if(parent != null){
                IRel rel = SchemaUtil.newRelationship("CIMROPRuleGroup2Item", parent, creatObject);
                rel.finishCreate(lobjTransaction);

            }

        }else {
            //更新
            IObject toUpdate = Context.Instance.getQueryHelper().getObjectByUidAndDefinitionUid(uid, "CIMROPRuleGroup", IObject.class);
            Objects.requireNonNull(toUpdate, HandlerExceptionUtils.objNotFoundFailMsg(uid,"CIMROPRuleGroup"));
            toUpdate.BeginUpdate(objectCollection);
            toUpdate.setProperties(GeneralUtil.jsonObjectToMap(param));
            toUpdate.finishCreate(objectCollection);
            ICIMHierarchyComposition hierarchyComposition = toUpdate.toInterface(ICIMHierarchyComposition.class);
            Objects.requireNonNull(hierarchyComposition,HandlerExceptionUtils.convertInterfaceFailMsg(ICIMHierarchyComposition.class.getSimpleName()));
            hierarchyComposition.updateHierarchyRelationships(icimHierarchyObject,lobjTransaction,false);
        }
        lobjTransaction.commit();
    }

    @Override
    public void deleteROPRuleGroupItem(@NotNull DeleteParam param) throws Exception {
        String classDefUid = param.getClassDefUid();
        String relDefUid = param.getRelDefUid();
        List<String> uids = param.getUids();
        if (!CollectionUtils.hasValue(uids)){
            throw new Exception("请给出需要删除对象的uid信息");
        }

        if (StringUtils.isEmpty(relDefUid)&&StringUtils.isEmpty(classDefUid)){
            throw new Exception("请给出类型定义或关联定义信息");
        }

        if (StringUtils.isNotEmpty(relDefUid)&& StringUtils.isNotEmpty(classDefUid)){
            throw new Exception("只能同时删除一种类型的对象");
        }

        ObjectCollection objectCollection = new ObjectCollection(new UserEnvParameter(GeneralUtil.getUsername()));
        if (StringUtils.isNotEmpty(classDefUid)){
          List <IObject> objects = Context.Instance.getQueryHelper().getObjectsByUIDsAndClassDefinitionUID(uids,classDefUid,IObject.class);
          if (CollectionUtils.hasValue(objects)){
              HandlerExceptionUtils.throwObjNotFoundException(classDefUid,String.join(",",uids));
          }

            for (IObject object : objects) {
                if (object.getClassBase().hasInterface("ICIMROPRuleGroupItem")){
                    ICIMHierarchyComposition icimHierarchyComposition = object.toInterface(ICIMHierarchyComposition.class);
                    Objects.requireNonNull(icimHierarchyComposition,HandlerExceptionUtils.convertInterfaceFailMsg("ICIMROPRuleGroupItem"));
                    icimHierarchyComposition.deleteAllChildrenNode(objectCollection,false);
                }else {
                    object.Delete(objectCollection);
                }
            }
        }else {
            objectCollection.commit();
        }

    }


}
