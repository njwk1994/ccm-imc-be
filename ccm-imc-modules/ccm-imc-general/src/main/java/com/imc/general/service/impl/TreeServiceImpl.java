package com.imc.general.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.imc.common.core.domain.R;
import com.imc.common.core.enums.frame.ClassDefinitions;
import com.imc.common.core.enums.frame.PropertyDefinitions;
import com.imc.common.core.enums.frame.PropertyValueTypes;
import com.imc.common.core.model.frame.LiteObject;
import com.imc.common.core.model.frame.MJSONObject;
import com.imc.common.core.model.render.TreeObj;
import com.imc.common.core.utils.StringUtils;
import com.imc.common.core.web.comp.OptionItem;
import com.imc.common.core.web.option.SelectionOption;
import com.imc.framework.api.impl.ServerApiBase;
import com.imc.framework.context.Context;
import com.imc.framework.enginees.IQueryEngine;
import com.imc.framework.model.api.ApiProcessParam;
import com.imc.framework.model.qe.QueryRequest;
import com.imc.general.service.ITreeService;
import com.imc.schema.interfaces.ICIMTreeConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author HuangTao
 * @version 1.0
 * @since 2022/10/25 22:54
 */
@Slf4j
@Service
public class TreeServiceImpl implements ITreeService {


    /**
     * 获取用于配置树的类型
     *
     * @param classDefName
     * @return
     */
    @Override
    public SelectionOption getClassDefs(String classDefName) {
        List<LiteObject> objectsByClassDefinitionUid;
        try {
            objectsByClassDefinitionUid = Context.Instance.getQueryHelper().getObjectsByDefinitionUid(ClassDefinitions.ClassDef.name());
        } catch (Exception e) {
            throw new RuntimeException("获取配置树的类型!" + e);
        }
        SelectionOption selectionOption = new SelectionOption();
        List<OptionItem> optionItems = selectionOption.getOptionItems();
        for (LiteObject liteObject : objectsByClassDefinitionUid) {
            String uid = liteObject.getUid();
            String description = liteObject.getDescription();
            if (StringUtils.isNotBlank(classDefName)) {
                if (description.contains(classDefName) || uid.contains(classDefName)) {
                    OptionItem optionItem = new OptionItem();
                    optionItem.setLabel(description + "(" + uid + ")");
                    optionItem.setValue(uid);
                    optionItems.add(optionItem);
                }
            } else {
                OptionItem optionItem = new OptionItem();
                optionItem.setLabel(description + "(" + uid + ")");
                optionItem.setValue(uid);
                optionItems.add(optionItem);
            }
        }
        return selectionOption;
    }

    /**
     * 根据类型获取可用于配置树的属性
     *
     * @param classDefinitionUid
     * @return
     */
    @Override
    public SelectionOption getPropertiesForConfigItem(String classDefinitionUid, String propertyName) {
        if (StringUtils.isEmpty(classDefinitionUid)) {
            throw new RuntimeException("类型UID不可为空!");
        }
        Map<Boolean, List<MJSONObject>> propertyDefinitions = Context.Instance.getCacheHelper().getPropertyDefinitions(classDefinitionUid);
        List<MJSONObject> MJSONObjects = propertyDefinitions.get(true);
        if (null == MJSONObjects || MJSONObjects.isEmpty()) {
            throw new RuntimeException("未获取到" + classDefinitionUid + "对应的属性,请检查配置!");
        }
        SelectionOption selectionOption = new SelectionOption();
        List<OptionItem> optionItems = selectionOption.getOptionItems();
        for (MJSONObject MJSONObject : MJSONObjects) {
            PropertyValueTypes propertyValueType = MJSONObject.getPropertyValueType();
            if (PropertyValueTypes.EnumList.equals(propertyValueType)) {
                String uid = MJSONObject.getUid();
                String displayName = MJSONObject.getDisplayName();
                if (StringUtils.isNotBlank(propertyName)) {
                    if (displayName.contains(propertyName)) {
                        OptionItem optionItem = new OptionItem();
                        optionItem.setLabel(displayName);
                        optionItem.setValue(uid);
                        optionItems.add(optionItem);
                    }
                } else {
                    OptionItem optionItem = new OptionItem();
                    optionItem.setLabel(displayName);
                    optionItem.setValue(uid);
                    optionItems.add(optionItem);
                }

            }
        }
        return selectionOption;
    }

    /**
     * 根据ClassDefUid获取对应类型可选择的树配置
     *
     * @param classDefinitionUid 类型UID
     * @return
     */
    @Override
    public SelectionOption getTreeConfigSelectionOption(String classDefinitionUid, String configName) {
        if (StringUtils.isEmpty(classDefinitionUid)) {
            throw new RuntimeException("类型不可为空!");
        }
        IQueryEngine queryEngine = Context.Instance.getQueryEngine();
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addClassDefForQuery("CIMTreeConfig");
        queryRequest.addQueryCriteria(null, "CIMTreeTargetClassDefUid", SqlKeyword.EQ, classDefinitionUid);
        if (StringUtils.isNotBlank(configName)) {
            queryRequest.addQueryCriteria(null, PropertyDefinitions.name.name(), SqlKeyword.LIKE, configName);
        }
        try {
            queryEngine.execute(queryRequest);
        } catch (Exception e) {
            throw new RuntimeException("查询配置对象失败!");
        }
        SelectionOption result = new SelectionOption();
        List<OptionItem> optionItems = result.getOptionItems();
        List<LiteObject> request = queryRequest.getLiteObjects();
        for (LiteObject liteObject : request) {
            OptionItem optionItem = new OptionItem();
            optionItem.setValue(liteObject.getUid());
            optionItem.setLabel(liteObject.getName());
            optionItems.add(optionItem);
        }

        return result;
    }

    /**
     * 根据树配置的UID生成树结构
     *
     * @param uid
     * @return
     */
    @Override
    public TreeObj getTreeByConfigUID(String uid) {
        ICIMTreeConfig cimTreeConfig;
        try {
            cimTreeConfig = Context.Instance.getQueryHelper().getObjectByUidAndDefinitionUid(uid, "CIMTreeConfig", ICIMTreeConfig.class);
        } catch (Exception e) {
            throw new RuntimeException("根据UID获取树配置对象失败!");
        }
        return cimTreeConfig.generateTree();
    }



    /**
     * 创建树配置和项
     *
     * @param args args
     * @return {@link String}
     */
    @Override
    public String createTreeConfigAndItems(JSONObject args) {
        ApiProcessParam apiProcessParam = ServerApiBase.generateApiProcessParam(args, "CreateTreeConfigAndItems", null, null, null, null);
        R<String> execute = Context.Instance.getDynamicApiEngine().execute(apiProcessParam);
        if (execute.success()) {
            return execute.getData();
        }
        throw new RuntimeException(execute.getMsg());
    }
}
