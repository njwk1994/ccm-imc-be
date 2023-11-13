package com.ccm.rop.service;

import com.alibaba.fastjson2.JSONObject;
import com.imc.common.core.model.parameters.DeleteParam;
import com.imc.common.core.model.parameters.ObjParam;
import com.imc.common.core.web.table.TableData;

public interface IROPRuleGroupItemService {

    TableData<JSONObject> getRuleGroupItem() throws Exception;

    void addOrUpdate(JSONObject param) throws Exception;

    void deleteROPRuleGroupItem (DeleteParam param)throws Exception;
}
