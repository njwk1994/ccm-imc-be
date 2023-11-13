package com.imc.general.service;


import com.alibaba.fastjson2.JSONObject;
import com.imc.common.core.model.render.TreeObj;
import com.imc.common.core.web.option.SelectionOption;

/**
 * @author HuangTao
 * @version 1.0
 * @since 2022/10/25 22:50
 */
public interface ITreeService {

    /**
     * 获取用于配置树的类型
     *
     * @param classDefName
     * @return
     */
    SelectionOption getClassDefs(String classDefName);

    /**
     * 根据类型获取可用于配置树的属性
     *
     * @param classDefinitionUid 类型UID
     * @return
     */
    SelectionOption getPropertiesForConfigItem(String classDefinitionUid, String propertyName);

    /**
     * 根据ClassDefUid获取对应类型可选择的树配置
     *
     * @param classDefinitionUid 类型UID
     * @return
     */
    SelectionOption getTreeConfigSelectionOption(String classDefinitionUid, String configName);

    /**
     * 根据树配置的UID生成树结构
     *
     * @param uid
     * @return
     */
    TreeObj getTreeByConfigUID(String uid);


    /**
     * 创建树配置和项
     *
     * @param args args
     * @return {@link String}
     */
    String createTreeConfigAndItems(JSONObject args);
}
