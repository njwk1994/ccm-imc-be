package com.imc.general.api;

import com.alibaba.fastjson2.JSONObject;
import com.imc.common.core.model.frame.LiteObject;
import com.imc.common.core.model.frame.MJSONObject;
import com.imc.common.core.utils.CollectionUtils;
import com.imc.common.core.utils.CommonUtils;
import com.imc.common.core.utils.StringUtils;
import com.imc.common.core.utils.exception.HandlerExceptionUtils;
import com.imc.common.core.web.tree.TreeNode;
import com.imc.framework.api.IServerApi;
import com.imc.framework.api.impl.ServerApiBase;
import com.imc.framework.context.Context;
import com.imc.framework.utils.GeneralUtil;
import com.imc.general.vo.QueryHierarchyObjectsVo;
import com.imc.schema.interfaces.ICIMHierarchyObject;
import com.imc.schema.interfaces.IObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class QueryHierarchyObjectsByClassDef extends ServerApiBase<List<TreeNode>> {

    private QueryHierarchyObjectsVo hierarchyObjectsVo;
    private List<TreeNode> treeNodeList;

    @Override
    public void onDeserialize(JSONObject jsonObject) {
        if (null != requestParam) {
            this.hierarchyObjectsVo = JSONObject.parseObject(requestParam.toJSONString(), QueryHierarchyObjectsVo.class);
        }
    }

    @SneakyThrows
    @Override
    public void onHandle() {
        if (null == hierarchyObjectsVo) {
            throw new RuntimeException(HandlerExceptionUtils.paramsInvalid("hierarchyObjectsVo"));
        }
        hierarchyObjectsVo.validateParam();
        List<LiteObject> objects = Context.Instance.getQueryHelper().getObjectsByDefinitionUid(hierarchyObjectsVo.getClassDefUid());
        if (!CollectionUtils.hasValue(objects)) {
            log.trace("not found objects");
            return;
        }
        List<LiteObject> hierarchyObjects = objects.stream().filter(r -> r.getInterfaces().contains(ICIMHierarchyObject.class.getSimpleName())).collect(Collectors.toList());
        if (!CollectionUtils.hasValue(hierarchyObjects)) {
            log.trace("not found hierarchy objects");
            return;
        }
        if (hierarchyObjectsVo.getLevel() == null) {
            treeNodeList = new ArrayList<>();
            GeneralUtil.generateHierarchyList(Context.Instance.getConvertHelper().getIObjectFromLiteObject(objects, IObject.class), treeNodeList);
        } else {
            List<ICIMHierarchyObject> hierarchyObjectList = Context.Instance.getConvertHelper().getIObjectFromLiteObject(objects, ICIMHierarchyObject.class).stream().filter(r -> Objects.equals(r.getLevel(), hierarchyObjectsVo.getLevel())).collect(Collectors.toList());
            if (!CollectionUtils.hasValue(hierarchyObjectList)) {
                log.trace("not found point level objects");
                return;
            }
            List<String> uids = hierarchyObjectList.stream().map(IObject::getUid).distinct().collect(Collectors.toList());
            List<LiteObject> pointObjects = objects.stream().filter(r -> uids.contains(r.getUid())).collect(Collectors.toList());
            List<MJSONObject> jsonObjects = Context.Instance.getConvertHelper().convertFromLiteObjectToJSONObject(pointObjects);
            treeNodeList = jsonObjects.stream().filter(r -> {
                if (StringUtils.isNotBlank(hierarchyObjectsVo.getName())) {
                    return r.getName().contains(hierarchyObjectsVo.getName());
                }
                return true;
            }).map(TreeNode::new).sorted(Comparator.comparing(TreeNode::getName)).collect(Collectors.toList());
        }
    }

    @Override
    public List<TreeNode> onSerialize() {
        return treeNodeList;
    }

    @Override
    public String getApiId() {
        return this.getClass().getSimpleName();
    }

    @Override
    public IServerApi<List<TreeNode>> nullInstance() {
        return new QueryHierarchyObjectsByClassDef();
    }
}
