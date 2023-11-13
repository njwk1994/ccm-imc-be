package com.imc.general.service;

import com.alibaba.fastjson2.JSONObject;
import com.imc.common.core.model.method.ClientApiVO;
import com.imc.common.core.model.parameters.*;
import com.imc.common.core.web.comp.OptionItem;
import com.imc.common.core.web.form.FormDataInfo;
import com.imc.common.core.web.option.SelectionOption;
import com.imc.common.core.web.table.TableData;
import com.imc.common.core.web.tree.TreeNode;
import com.imc.framework.excel.entity.ExcelDetail;
import com.imc.general.vo.CreateRelVo;
import com.imc.general.vo.QueryHierarchyObjectsVo;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface IGeneralService {

    /**
     * @param files schema文件
     *              加载schema
     * @author Chen Jing
     */
    void loadSchemaFiles(MultipartFile[] files) throws Exception;


    /**
     * @param formParam 查询表单参数
     * @return FormDataInfo 表单信息
     * 获取查询, 新建, 更新的表单信息
     * @author Chen Jing
     */
    FormDataInfo getForm(QueryFormParam formParam) throws Exception;


    /**
     * @param deleteParam 删除请求的参数
     * @return Boolean 是否删除成功
     * 删除对象
     * @author Chen Jing
     */
    Boolean deleteObjects(@NotNull DeleteParam deleteParam) throws Exception;


    /**
     * @param queryRequest 查询参数
     * @return TableData 表格数据
     * 通用查询
     * @author Chen Jing
     */
    TableData<JSONObject> generalQuery(@NotNull GeneralQueryParam queryRequest) throws Exception;


    /**
     * 一般创建
     *
     * @param jsonObject json对象
     * @return {@code Boolean }
     * @author CHEN JING
     */
    Boolean generalCreate(JSONObject jsonObject) throws Exception;


    /**
     * 一般更新
     *
     * @param jsonObject json对象
     * @return {@code Boolean }
     * @author CHEN JING
     */
    Boolean generalUpdate(JSONObject jsonObject) throws Exception;

    /**
     * 得到枚举列表条目
     * Chen  Jing
     *
     * @param pstrUid 枚举Uid
     * @return {@code SelectionOption }
     */
    SelectionOption getEnumListEntries(@NotNull String pstrUid) throws Exception;


    /**
     * 创造关系
     * Chen  Jing
     *
     * @param createRelVo 创建rel签证官
     * @return {@code Boolean }
     */
    Boolean createRelationship(@NotNull CreateRelVo createRelVo) throws Exception;

    /**
     * 查询对象所有关系
     * Chen  Jing
     *
     * @param param 参数
     * @return {@code TableData<JSONObject> }
     */
    TableData<JSONObject> queryObjAllRelationships(@NotNull ObjParam param) throws Exception;

    /**
     * 通用查询导出Excel
     *
     * @param queryRequest 查询参数
     * @return TableData 表格数据
     * @author Huangtao
     */
    ExcelDetail generalExportExcel(@NotNull GeneralQueryParam queryRequest);

    /**
     * CHEN JING
     * 2023/06/21 10:59:27
     * 查询对象下拉列表
     *
     * @param interfaceDefUid 接口定义UID
     * @return {@link SelectionOption }
     */
    SelectionOption querySelectOptionByInterfaceDefUid(String interfaceDefUid);

    /**
     * CHEN JING
     * 2023/06/29 12:04:43
     * 根据类定义和层级查询层次对象
     *
     * @param hierarchyObjectsVo 参数
     * @return {@link List }<{@link TreeNode }>
     */
    List<TreeNode> queryHierarchyObjectsByClassDefAndLevel(@NotNull QueryHierarchyObjectsVo hierarchyObjectsVo) throws Exception;

    /**
     * CHEN JING
     * 2023/07/04 20:54:29
     * 根据对象类型查询层级对象信息
     *
     * @param hierarchyObjectsVo 查询层次对象参数
     * @return {@link List }<{@link TreeNode }>
     */
    List<TreeNode> queryHierarchyObjectByClassDefUid(@NotNull QueryHierarchyObjectsVo hierarchyObjectsVo) throws Exception;

    /**
     * CHEN JING
     * 2023/08/17 10:18:20
     * 查询指定下拉树节点的子集
     *
     * @param objParam obj参数
     * @return {@link List }<{@link OptionItem }>
     */
    List<OptionItem> querySelectTreeNodeChildrenByUidAndClassDef(@NotNull ObjParam objParam) throws Exception;

    /**
     * CHEN JING
     * 2023/08/17 14:09:59
     * 获得对象方法
     *
     * @param objParam obj参数
     * @return {@link List }<{@link JSONObject }>
     */
    List<ClientApiVO> getObjectsMethods(@NotNull ObjParam objParam) throws Exception;

    /**
     * CHEN JING
     * 2023/08/23 20:26:00
     * 附加文件
     *
     * @param attachFileParam 附件参数
     * @return {@link Boolean }
     */
    Boolean attachFiles(@NotNull AttachFileParam attachFileParam) throws Exception;


    /**
     * CHEN JING
     * 2023/09/20 23:44:42
     * 根据类型获取方法列表 供前段渲染
     * @param param 参数 类型定义 {"classDefinitionUid":""}
     * @return {@link List }<{@link ClientApiVO }>
     */
    List<ClientApiVO> getMethodsByClassDef(JSONObject param);
}
