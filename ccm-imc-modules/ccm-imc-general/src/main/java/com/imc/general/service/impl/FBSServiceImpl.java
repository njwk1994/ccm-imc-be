package com.imc.general.service.impl;

import com.baomidou.mybatisplus.core.enums.SqlKeyword;

import com.imc.common.core.enums.frame.PropertyDefinitions;
import com.imc.common.core.model.frame.MJSONObject;
import com.imc.common.core.model.frame.LiteObject;
import com.imc.common.core.utils.CollectionUtils;
import com.imc.common.core.utils.StringUtils;
import com.imc.framework.cache.base.ICacheService;
import com.imc.framework.constant.ClassDefConstant;
import com.imc.framework.constant.PropertyDefConstant;
import com.imc.framework.constant.RelDefConstant;
import com.imc.framework.context.Context;
import com.imc.framework.enginees.IQueryEngine;
import com.imc.framework.model.qe.QueryRequest;
import com.imc.framework.utils.GeneralUtil;
import com.imc.general.service.IFBSService;
import com.imc.general.vo.FBSTreeNodeVo;
import com.imc.schema.interfaces.ICIMAssignment;
import com.imc.schema.interfaces.IRelDef;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author llu
 * @description FBS结构树
 * @since 2022/12/7
 */
@Slf4j
@Service("FBSService")
public class FBSServiceImpl implements IFBSService {

    /**
     * get children list
     *
     * @param uid
     * @param classDefUid
     */
    private List<LiteObject> getChildrenList(String uid, String classDefUid, String relName) throws Exception {
        QueryRequest queryRequest = new QueryRequest();
        if (StringUtils.isNotBlank(relName) && StringUtils.isNotBlank(uid)) {
            String relDef = relName;
            String propertyDefUid = "uid2";
            if (relName.startsWith("-")) {
                propertyDefUid = "uid1";
                relDef = relName.substring(1);
            } else if (relName.startsWith("+")) {
                propertyDefUid = "uid2";
                relDef = relName.substring(1);
            }
            if (StringUtils.isEmpty(classDefUid)) {
                IRelDef relDefObj = Context.Instance.getCacheHelper().getSchema(relDef, IRelDef.class);
                String interfaceDef = "uid1".equals(propertyDefUid) ? relDefObj.getInterfaceDefUid1() : relDefObj.getInterfaceDefUid2();
                List<String> classDefs = Context.Instance.getCacheHelper().getRealizedClassDefsForInterfaceDef(new ArrayList<String>() {{
                    add(interfaceDef);
                }}).stream().map(MJSONObject::getUid).collect(Collectors.toList());
                classDefUid = String.join(",", classDefs);
            }
            queryRequest.addClassDefForQuery(classDefUid);
            queryRequest.addQueryCriteria(relName, propertyDefUid, SqlKeyword.EQ, uid);
        }
        return Context.Instance.getQueryHelper().query(queryRequest);
    }

    /**
     * expand rel
     *
     * @param uid
     * @param classDefUid
     * @return
     * @throws Exception
     */
    private List<LiteObject> expandRel(String uid, String classDefUid, String relName) throws Exception {
        QueryRequest queryRequest = new QueryRequest();
        if (StringUtils.isNotBlank(relName) && StringUtils.isNotBlank(uid)) {
            String relDef = relName;
            String propertyDefUid = "uid2";
            if (relName.startsWith("-")) {
                propertyDefUid = "uid1";
                relDef = relName.substring(1);
            } else if (relName.startsWith("+")) {
                propertyDefUid = "uid2";
                relDef = relName.substring(1);
            }
            if (StringUtils.isEmpty(classDefUid)) {
                IRelDef relDefObj = Context.Instance.getCacheHelper().getSchema(relDef, IRelDef.class);
                String interfaceDef = "uid2".equals(propertyDefUid) ? relDefObj.getInterfaceDefUid1() : relDefObj.getInterfaceDefUid2();
                List<String> classDefs = Context.Instance.getCacheHelper().getRealizedClassDefsForInterfaceDef(new ArrayList<String>() {{
                    add(interfaceDef);
                }}).stream().map(MJSONObject::getUid).collect(Collectors.toList());
                classDefUid = String.join(",", classDefs);
            }
            queryRequest.addClassDefForQuery(classDefUid);
            queryRequest.addQueryCriteria(relName, propertyDefUid, SqlKeyword.EQ, uid);
        }
        return Context.Instance.getQueryHelper().query(queryRequest);
    }

    /**
     * 获取用户项目信息
     *
     * @param pstrUsername 用户名
     * @param isActive 是否通过创建作用域过滤
     * @return {@code List<IIMCUserConfigurationItemDetail> }
     * CHEN JING
     */
    public List<ICIMAssignment> getUserAssignmentInfos(String pstrUsername, Boolean isActive) throws Exception {
        QueryRequest lobjQueryRequest = new QueryRequest();
        lobjQueryRequest.addClassDefForQuery(ClassDefConstant.CIM_ASSIGNMENT);
        lobjQueryRequest.addQueryCriteria(null, PropertyDefinitions.name.name(), SqlKeyword.EQ, pstrUsername);
        //filter by query scope
        lobjQueryRequest.addQueryCriteria(null, PropertyDefConstant.USER_QUERY_SCOPE, SqlKeyword.EQ, true);
        if (isActive) {
            lobjQueryRequest.addQueryCriteria(null, PropertyDefConstant.IS_ACTIVE, SqlKeyword.EQ, true);
        }
        IQueryEngine lobjQueryEngine = Context.Instance.getQueryEngine();
        lobjQueryEngine.execute(lobjQueryRequest);
        List<LiteObject> lcolLiteObjects = lobjQueryRequest.getLiteObjects();
        if (CollectionUtils.hasValue(lcolLiteObjects)) {
            return Context.Instance.getConvertHelper().getIObjectFromLiteObject(lcolLiteObjects, ICIMAssignment.class);
        }
        return null;
    }

    /**
     * get node model by recursion
     *
     * @param nodeDTO
     * @return
     * @throws Exception
     */
    private FBSTreeNodeVo getNodeByRecursion(FBSTreeNodeVo nodeDTO, String extraRelDefs) throws Exception {
        List<FBSTreeNodeVo> children = this.getChildrenNodeList(nodeDTO.getUid(), nodeDTO.getClassDefinitionUID(), extraRelDefs, null);
        if (children.size() > 0) {
            for (FBSTreeNodeVo dto : children) {
                this.getNodeByRecursion(dto, extraRelDefs);
            }
        }
        nodeDTO.setChildren(children);
        nodeDTO.setIsLeaf(children.size() == 0);
        return nodeDTO;
    }

    private void expandExtraRelDefs(FBSTreeNodeVo nodeDTO, String extraRelDefs) throws Exception {
        if (StringUtils.isNotEmpty(extraRelDefs)) {
            List<Object> extraObjs = new ArrayList<>();
            for (String relDef : extraRelDefs.split(",")) {
                List<LiteObject> relObjs = expandRel(nodeDTO.getUid(), null, relDef);
                for (LiteObject relObj : relObjs) {
                    Map<String, Object> map = Context.Instance.getObjectConverterService().
                            convertFromLiteObjectToIObject(relObj).toTableRowMap();
                    map.put("relDef", relDef);
                    extraObjs.add(map);
                }
            }
            nodeDTO.setExtraObjs(extraObjs);
        }
    }

    /**
     * get object data list by uid
     *
     * @param classDefinitionUID
     * @param uid
     * @return
     * @throws Exception
     */
    private List<LiteObject> getListByUid(String classDefinitionUID, String uid) throws Exception {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addClassDefForQuery(classDefinitionUID);
        if (StringUtils.isNotBlank(uid)) {
            queryRequest.addQueryCriteria(null, PropertyDefinitions.uid.toString(), SqlKeyword.EQ, uid);
        }
        return Context.Instance.getQueryHelper().query(queryRequest);
    }

    /**
     * 根据uid和类型定义获取PAU目录树
     *
     * @param uid
     * @param classDefUid
     * @param extraRelDefs
     * @return
     * @throws Exception
     */
    @Override
    public List<FBSTreeNodeVo> getChildrenNodeList(String uid, String classDefUid, String extraRelDefs, Boolean isActive) throws Exception {
        List<FBSTreeNodeVo> nodeList = new ArrayList<>();
        if (StringUtils.isBlank(uid)) {
            getPlantTreeNode(nodeList, extraRelDefs, isActive);
        } else {
            if (StringUtils.isBlank(classDefUid)) {
                throw new Exception("classDefinitionUID cannot be empty.");
            }
            if (classDefUid.equals(ClassDefConstant.CIM_PLANT)) {
                List<LiteObject> list = this.getChildrenList(uid, ClassDefConstant.CIM_AREA, "-" + RelDefConstant.PLANT_AREAS);
                convertToTreeNode(list, nodeList);
            }
            if (classDefUid.equals(ClassDefConstant.CIM_AREA)) {
                List<LiteObject> list = this.getChildrenList(uid, ClassDefConstant.CIM_UNIT, "-" + RelDefConstant.AREA_UNITS);
                convertToTreeNode(list, nodeList);
            }
//            if (classDefUid.equals(ClassDefConstant.CIM_UNIT)) {
//                List<LiteObject> list = this.getChidrenList(uid, "CIMSAConfig", "-CIMFacility2SAConfig");
//                convertToTreeNode(list, nodeList);
//            }
            for (FBSTreeNodeVo node : nodeList) {
                expandExtraRelDefs(node, extraRelDefs);
            }
        }
        return nodeList;
    }

    private static void convertToTreeNode(List<LiteObject> list, List<FBSTreeNodeVo> nodeList) {
        if (CollectionUtils.hasValue(list) && nodeList != null) {
            for (LiteObject map : list) {
                FBSTreeNodeVo dto = new FBSTreeNodeVo();
                dto.setName(map.get("name").toString());
                dto.setClassDefinitionUID(map.get("class_definition_uid").toString());
                dto.setUid(map.get("uid").toString());
                dto.setObid(map.get("uid").toString());
                dto.setDisplayName(map.getDisplayName());
                dto.setIsLeaf(true);
                nodeList.add(dto);
            }
        }
    }


    /**
     * 获取PAU结构树
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<FBSTreeNodeVo> getTreeList(String extraRelDefs, Boolean isActive) throws Exception {
        List<FBSTreeNodeVo> nodeList = new ArrayList<>();
        getPlantTreeNode(nodeList, extraRelDefs, isActive);
        for (FBSTreeNodeVo node : nodeList) {
            this.getNodeByRecursion(node, extraRelDefs);
        }
        return nodeList;
    }

    private void getPlantTreeNode(List<FBSTreeNodeVo> nodeList, String extraRelDefs, Boolean isActive) throws Exception {
        List<ICIMAssignment> assignments = getUserAssignmentInfos(GeneralUtil.getUsername(), isActive);
        if (CollectionUtils.hasValue(assignments)) {
            ICacheService cacheService = Context.Instance.getCacheBroker().getCacheService(ClassDefConstant.CIM_PLANT);
            List<MJSONObject> rootList = assignments.stream().map(x -> {
                return cacheService.getObjectByKey(x.getCurrentPlant());
            }).collect(Collectors.toList());
            for (MJSONObject map : rootList) {
                FBSTreeNodeVo dto = new FBSTreeNodeVo();
                dto.setName(map.getName());
                dto.setDisplayName(map.getDisplayName());
                dto.setClassDefinitionUID(map.getClassDefinitionUid());
                dto.setUid(map.getUid());
                dto.setObid(map.getUid());
                expandExtraRelDefs(dto, extraRelDefs);
                nodeList.add(dto);
            }
        }
    }
}
