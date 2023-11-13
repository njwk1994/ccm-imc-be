package com.imc.general.service;

import com.imc.general.vo.FBSTreeNodeVo;

import java.util.List;

public interface IFBSService {

    /**
     * 获取所有PAU目录树
     * @return
     * @throws Exception
     */
    List<FBSTreeNodeVo> getTreeList(String extraRelDefs, Boolean isActive) throws Exception;

    /**
     * 根据uid和类型定义获取PAU目录树（异步）
     * @param uid
     * @param classDefUid
     * @param extraRelDefs
     * @param isActive 是否创建作用域
     * @return
     * @throws Exception
     */
    List<FBSTreeNodeVo> getChildrenNodeList(String uid, String classDefUid, String extraRelDefs, Boolean isActive) throws Exception;
}
