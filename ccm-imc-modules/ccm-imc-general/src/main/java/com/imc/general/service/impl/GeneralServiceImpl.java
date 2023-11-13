package com.imc.general.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.imc.common.core.domain.R;
import com.imc.common.core.enums.form.FormPurpose;
import com.imc.common.core.enums.frame.PropertyDefinitions;
import com.imc.common.core.model.method.ClientApiVO;
import com.imc.common.core.model.parameters.*;
import com.imc.common.core.utils.CollectionUtils;
import com.imc.common.core.utils.StringUtils;
import com.imc.common.core.web.comp.OptionItem;
import com.imc.common.core.web.form.FormDataInfo;
import com.imc.common.core.web.option.SelectionOption;
import com.imc.common.core.web.table.TableData;
import com.imc.common.core.web.table.TableFieldColumn;
import com.imc.common.core.web.tree.TreeNode;
import com.imc.framework.api.impl.ServerApiBase;
import com.imc.framework.constant.PropertyDefConstant;
import com.imc.framework.context.Context;
import com.imc.framework.excel.entity.ExcelDetail;
import com.imc.framework.excel.entity.SheetDetail;
import com.imc.framework.model.api.ApiProcessParam;
import com.imc.framework.utils.GeneralUtil;
import com.imc.general.api.AttachFiles;
import com.imc.general.service.IGeneralService;
import com.imc.general.vo.CreateRelVo;
import com.imc.general.vo.QueryHierarchyObjectsVo;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
public class GeneralServiceImpl implements IGeneralService {


    /**
     * 加载Schema文件
     *
     * @param files 文件
     */
    @Override
    public void loadSchemaFiles(MultipartFile[] files) {
        ApiProcessParam apiProcessParam = ServerApiBase.generateApiProcessParam(null, "LoadSchema", null, null, null, null);
        apiProcessParam.setFiles(files);
        Context.Instance.getDynamicApiEngine().execute(apiProcessParam);
    }

    /**
     * 获得表单
     * 2022/09/05 09:08:13
     * Chen  Jing
     *
     * @param pobjFormParam 请求参数
     * @return {@code FormDataInfo }
     */
    @Override
    public FormDataInfo getForm(QueryFormParam pobjFormParam) {
        ApiProcessParam apiProcessParam = ServerApiBase.generateApiProcessParam(JSONObject.from(pobjFormParam), "GetForm", null, null, null, null);
        R<FormDataInfo> execute = Context.Instance.getDynamicApiEngine().execute(apiProcessParam);
        if (execute.success()) {
            return execute.getData();
        }
        throw new RuntimeException(execute.getMsg());
    }

    /**
     * 删除对象
     * 2022/09/05 09:10:08
     * Chen  Jing
     *
     * @param pobjDeleteParam 删除参数
     * @return {@code Boolean }
     * @throws Exception 异常
     */
    @Override
    public Boolean deleteObjects(@NotNull DeleteParam pobjDeleteParam) throws Exception {
        String lstrClassDefinitionUid = pobjDeleteParam.getClassDefUid();
        String lstrRelDefUid = pobjDeleteParam.getRelDefUid();
        List<String> lcolUids = pobjDeleteParam.getUids();
        if (!CollectionUtils.hasValue(lcolUids)) {
            throw new Exception("请给出需要删除对象的uid信息");
        }
        if (StringUtils.isEmpty(lstrRelDefUid) && StringUtils.isEmpty(lstrClassDefinitionUid)) {
            throw new Exception("请给出类型定义或关联定义信息");
        }
        if (StringUtils.isNotEmpty(lstrRelDefUid) && StringUtils.isNotEmpty(lstrClassDefinitionUid)) {
            throw new Exception("只能同时删除一种类型的对象");
        }
        JSONArray objs = new JSONArray();
        for (String uid : lcolUids) {
            JSONObject obj = new JSONObject();
            obj.put(PropertyDefinitions.uid.name(), uid);
            obj.put(PropertyDefinitions.classDefinitionUid.name(), StringUtils.hasText(lstrClassDefinitionUid) ? lstrClassDefinitionUid : lstrRelDefUid);
            objs.add(obj);
        }
        ApiProcessParam apiProcessParam = ServerApiBase.generateApiProcessParam(null, "GeneralDelete", null, objs, null, null);
        R<Boolean> execute = Context.Instance.getDynamicApiEngine().execute(apiProcessParam);
        if (execute.success()) {
            return execute.getData();
        }
        throw new RuntimeException(execute.getMsg());
    }


    /**
     * 一般查询
     * 2022/09/05 09:10:26
     * Chen  Jing
     *
     * @param queryParam 查询参数
     * @return {@code TableData }
     */
    @Override
    public TableData<JSONObject> generalQuery(@NotNull GeneralQueryParam queryParam) {
        ApiProcessParam apiProcessParam = ServerApiBase.generateApiProcessParam(JSONObject.from(queryParam), "GeneralQuery", null, null, null, null);
        R<TableData<JSONObject>> execute = Context.Instance.getDynamicApiEngine().execute(apiProcessParam);
        if (execute.success()) {
            return execute.getData();
        }
        throw new RuntimeException(execute.getMsg());
    }

    /**
     * 一般创建
     * 2022/09/11 07:14:37
     * Chen  Jing
     *
     * @param pobjFormModel 数据模型
     * @return {@code Boolean }
     */
    @Override
    public Boolean generalCreate(JSONObject pobjFormModel) {
        ApiProcessParam apiProcessParam = ServerApiBase.generateApiProcessParam(pobjFormModel, "GeneralCreate", null, null, null, null);
        R<Boolean> execute = Context.Instance.getDynamicApiEngine().execute(apiProcessParam);
        if (execute.success()) {
            return execute.getData();
        }
        throw new RuntimeException(execute.getMsg());
    }


    /**
     * 一般更新
     * 2022/09/11 07:14:48
     * Chen  Jing
     *
     * @param pobjModel pobj模型
     * @return {@code Boolean }
     */
    @Override
    public Boolean generalUpdate(JSONObject pobjModel) {
        ApiProcessParam apiProcessParam = ServerApiBase.generateApiProcessParam(pobjModel, "GeneralUpdate", null, null, null, null);
        R<Boolean> execute = Context.Instance.getDynamicApiEngine().execute(apiProcessParam);
        if (execute.success()) {
            return execute.getData();
        }
        throw new RuntimeException(execute.getMsg());
    }

    /**
     * 得到枚举列表条目
     * 2022/09/29 02:05:12
     * Chen  Jing
     *
     * @param uid 枚举Uid
     * @return {@code SelectionOption }
     */
    @Override
    public SelectionOption getEnumListEntries(@NotNull String uid) throws Exception {
        if (StringUtils.isEmpty(uid)) throw new Exception("未提供有效的Uid信息");
        ApiProcessParam apiProcessParam = ServerApiBase.generateApiProcessParam(new JSONObject() {{
            put(PropertyDefinitions.uid.name(), uid);
        }}, "GetEnumListEntries", null, null, null, null);
        R<SelectionOption> execute = Context.Instance.getDynamicApiEngine().execute(apiProcessParam);
        if (execute.success()) {
            return execute.getData();
        }
        throw new RuntimeException(execute.getMsg());
    }


    /**
     * 创造关系
     * Chen  Jing
     *
     * @param createRelVo 创建rel签证官
     * @return {@code Boolean }
     */
    @Override
    public Boolean createRelationship(@NotNull CreateRelVo createRelVo) {
        ApiProcessParam apiProcessParam = ServerApiBase.generateApiProcessParam(JSONObject.from(createRelVo), "CreateRelationship", null, null, null, null);
        R<Boolean> execute = Context.Instance.getDynamicApiEngine().execute(apiProcessParam);
        if (execute.success()) {
            return execute.getData();
        }
        throw new RuntimeException(execute.getMsg());
    }

    /**
     * 查询对象所有关系
     * Chen  Jing
     *
     * @param param 参数
     * @return {@code TableData<JSONObject> }
     */
    @Override
    public TableData<JSONObject> queryObjAllRelationships(@NotNull ObjParam param) {
        ApiProcessParam apiProcessParam = ServerApiBase.generateApiProcessParam(JSONObject.from(param), "QueryObjAllRelationships", null, null, null, null);
        R<TableData<JSONObject>> execute = Context.Instance.getDynamicApiEngine().execute(apiProcessParam);
        if (execute.success()) {
            return execute.getData();
        }
        throw new RuntimeException(execute.getMsg());
    }

    public static final String SHEET_TITLE = "Title";
    public static final String SHEET_DATA = "Data";

    @Override
    public ExcelDetail generalExportExcel(@NotNull GeneralQueryParam queryRequest) {
        ExcelDetail result = new ExcelDetail();
        // 查询数据
        TableData<JSONObject> tableData;
        try {
            queryRequest.setPurpose(FormPurpose.export.name());
            tableData = generalQuery(queryRequest);
        } catch (Exception e) {
            throw new RuntimeException("查询待导出数据失败!", e);
        }
        List<TableFieldColumn> tableColumns = tableData.getTableColumns();
        SheetDetail sheetDetail = new SheetDetail();
        sheetDetail.setSheetName(queryRequest.getClassDefUid());
        List<String> titles = tableColumns.stream().filter(c -> StringUtils.isNotBlank(c.getProp()) && c.getShow()).
                map(TableFieldColumn::getLabel).collect(Collectors.toList());
        for (String title : titles) {
            List<String> head = new ArrayList<>();
            head.add(title);
            sheetDetail.addHeader(head);
        }

        List<JSONObject> queryData = tableData.getTableData().getData();
        for (JSONObject queryDatum : queryData) {
            List<Object> datum = new ArrayList<>();
            for (TableFieldColumn tableFieldColumn : tableColumns) {
                String prop = tableFieldColumn.getProp();
                if (StringUtils.isNotBlank(prop) && tableFieldColumn.getShow()) {
                    // if file content is included in table fields, image will be exported into excel
                    // TODO need to check file type to determine if it is image.
                    if (prop.endsWith(PropertyDefConstant.PROPERTY_DEF_PHYSICAL_FILE_SAVED_INFO) && queryDatum.containsKey(prop)) {
                        String fileSavedInfoStr = queryDatum.getString(prop);
                        ArrayList<JSONObject> fileSavedInfos = null;
                        if (StringUtils.isNotEmpty(fileSavedInfoStr)) {
                            if (fileSavedInfoStr.startsWith("[")) {
                                JSONArray files = JSONArray.parse(fileSavedInfoStr);
                                if (!files.isEmpty()) {
                                    fileSavedInfos = new ArrayList<>();
                                    for (Object file : files
                                    ) {
                                        fileSavedInfos.add((JSONObject) file);
                                    }
                                }
                            } else {
                                fileSavedInfos = new ArrayList<>();
                                fileSavedInfos.add(JSONObject.parse(fileSavedInfoStr));
                            }
                        }
                        InputStream inputStream = null;
                        if (null != fileSavedInfos) {
                            try {
                                inputStream = Context.Instance.getFileOperateHelper().downloadPhysicalFiles(fileSavedInfos);
                                if (null != inputStream) {
                                    byte[] buffer = new byte[1024 * 8];
                                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                                    int count;
                                    while ((count = inputStream.read(buffer)) != -1) {
                                        outputStream.write(buffer, 0, count);
                                    }
                                    outputStream.flush();
                                    byte[] fileBytes = outputStream.toByteArray();
                                    // easyExcel has internal converter to write bytes as image
                                    datum.add(fileBytes);

                                }
                            } catch (Exception e) {
                                log.error("download from Minio error", e);
                                throw new RuntimeException(e);
                            } finally {
                                if (null != inputStream) {
                                    try {
                                        inputStream.close();
                                    } catch (Exception ex) {
                                        log.error("---------close inputStream error", ex);
                                    }
                                }
                            }
                        } else {
                            datum.add(queryDatum.get(prop));
                        }
                    } else {
                        datum.add(queryDatum.get(prop));
                    }
                }
            }
            sheetDetail.addDatum(datum);
        }
        result.addSheetDetail(sheetDetail);
        return result;
    }

    /**
     * CHEN JING
     * 2023/06/21 10:59:27
     * 查询对象下拉列表
     *
     * @param interfaceDefUid 接口定义UID
     * @return {@link SelectionOption }
     */
    @Override
    public SelectionOption querySelectOptionByInterfaceDefUid(String interfaceDefUid) {
        JSONObject param = new JSONObject();
        param.put("interfaceDefUid", interfaceDefUid);
        ApiProcessParam apiProcessParam = ServerApiBase.generateApiProcessParam(param, "QuerySelectOptionByInterfaceDefUid", null, null, null, null);
        R<SelectionOption> execute = Context.Instance.getDynamicApiEngine().execute(apiProcessParam);
        if (execute.success()) {
            return execute.getData();
        }
        throw new RuntimeException(execute.getMsg());
    }


    /**
     * CHEN JING
     * 2023/07/04 20:54:29
     * 根据对象类型查询层级对象信息
     *
     * @param hierarchyObjectsVo 查询层次对象参数
     * @return {@link List }<{@link TreeNode }>
     */
    @Override
    public List<TreeNode> queryHierarchyObjectByClassDefUid(@NotNull QueryHierarchyObjectsVo hierarchyObjectsVo) {
        ApiProcessParam apiProcessParam = ServerApiBase.generateApiProcessParam(JSONObject.from(hierarchyObjectsVo), "QueryHierarchyObjectsByClassDef", null, null, null, null);
        R<List<TreeNode>> execute = Context.Instance.getDynamicApiEngine().execute(apiProcessParam);
        if (execute.success()) {
            return execute.getData();
        }
        throw new RuntimeException(execute.getMsg());
    }

    /**
     * CHEN JING
     * 2023/06/29 12:04:43
     * 根据类定义和层级查询层次对象
     *
     * @param hierarchyObjectsVo 参数
     * @return {@link List }<{@link TreeNode }>
     */
    @Override
    public List<TreeNode> queryHierarchyObjectsByClassDefAndLevel(@NotNull QueryHierarchyObjectsVo hierarchyObjectsVo) {
        ApiProcessParam apiProcessParam = ServerApiBase.generateApiProcessParam(JSONObject.from(hierarchyObjectsVo), "QueryHierarchyObjectsByClassDef", null, null, null, null);
        R<List<TreeNode>> execute = Context.Instance.getDynamicApiEngine().execute(apiProcessParam);
        if (execute.success()) {
            return execute.getData();
        }
        throw new RuntimeException(execute.getMsg());
    }


    /**
     * CHEN JING
     * 2023/08/17 10:18:20
     * 查询指定下拉树节点的子集
     *
     * @param objParam obj参数
     * @return {@link List }<{@link OptionItem }>
     */
    @Override
    public List<OptionItem> querySelectTreeNodeChildrenByUidAndClassDef(@NotNull ObjParam objParam) {
        ApiProcessParam apiProcessParam = ServerApiBase.generateApiProcessParam(JSONObject.from(objParam), "QuerySelectTreeNodeChildrenByUidAndClassDef", null, null, null, null);
        R<List<OptionItem>> execute = Context.Instance.getDynamicApiEngine().execute(apiProcessParam);
        if (execute.success()) {
            return execute.getData();
        }
        throw new RuntimeException(execute.getMsg());
    }

    /**
     * CHEN JING
     * 2023/08/17 14:09:59
     * 获得对象方法
     *
     * @param objParam obj参数
     * @return {@link List }<{@link JSONObject }>
     */
    @Override
    public List<ClientApiVO> getObjectsMethods(@NotNull ObjParam objParam) {
        ApiProcessParam apiProcessParam = ServerApiBase.generateApiProcessParam(JSONObject.from(objParam), "GetObjectsMethods", null, null, null, null);
        R<List<ClientApiVO>> execute = Context.Instance.getDynamicApiEngine().execute(apiProcessParam);
        if (execute.success()) {
            return execute.getData();
        }
        throw new RuntimeException(execute.getMsg());
    }

    /**
     * CHEN JING
     * 2023/08/23 20:26:00
     * 附加文件
     *
     * @param attachFileParam 附件参数
     * @return {@link Boolean }
     */
    @Override
    public Boolean attachFiles(@NotNull AttachFileParam attachFileParam) throws Exception {
        attachFileParam.validateParam();
        if (!CollectionUtils.hasValue(attachFileParam.getAttachFiles())) {
            throw new RuntimeException("未获取到需要挂载的附件信息");
        }
        JSONObject contextObj = new JSONObject();
        contextObj.put(PropertyDefinitions.uid.name(), attachFileParam.getObjUid());
        contextObj.put(PropertyDefinitions.classDefinitionUid.name(), attachFileParam.getClassDef());
        JSONObject param = new JSONObject();
        param.put(AttachFiles.ATTACH_FILE_KEY, attachFileParam.getAttachFiles());
        param.put(AttachFiles.COVER_SAME_NAME_FILE, attachFileParam.getCoverSameNameFile());
        ApiProcessParam apiProcessParam = ServerApiBase.generateApiProcessParam(param, "AttachFiles", contextObj, null, null, null);
        R<Boolean> execute = Context.Instance.getDynamicApiEngine().execute(apiProcessParam);
        if (execute.success()) {
            return execute.getData();
        }
        throw new RuntimeException(execute.getMsg());
    }

    /**
     * CHEN JING
     * 2023/09/20 23:44:42
     * 根据类型获取方法列表 供前段渲染
     *
     * @param param 参数 类型定义 {"classDefinitionUid":""}
     * @return {@link List }<{@link ClientApiVO }>
     */
    @Override
    public List<ClientApiVO> getMethodsByClassDef(JSONObject param) {
        ApiProcessParam apiProcessParam = ServerApiBase.generateApiProcessParam(param, "GetMethodsByClassDef", null, null, null, null);
        R<List<ClientApiVO>> execute = Context.Instance.getDynamicApiEngine().execute(apiProcessParam);
        if (execute.success()) {
            return execute.getData();
        }
        throw new RuntimeException(execute.getMsg());
    }
}

