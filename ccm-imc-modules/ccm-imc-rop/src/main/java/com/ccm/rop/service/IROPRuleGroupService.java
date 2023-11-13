package com.ccm.rop.service;

import com.alibaba.fastjson2.JSONObject;
import com.imc.common.core.model.parameters.ObjParam;
import com.imc.schema.interfaces.IObject;

import java.util.List;

public interface IROPRuleGroupService {


    List<IObject> findRuleGroup();


    void addRuleGroup (JSONObject param)throws Exception;

    void deleteROPTemplateInfo (ObjParam param)throws Exception;
}
