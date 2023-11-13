package com.ccm.rop.service;

import com.alibaba.fastjson2.JSONObject;
import com.imc.common.core.model.parameters.DeleteParam;
import com.imc.common.core.web.table.TableData;

public interface IROPWorkStepService {

    TableData<JSONObject> getRuleWorkStep() throws Exception;
    void saveOrUpdateWorkStep(JSONObject param) throws Exception;

    void deleteROPWorkStep (DeleteParam param)throws Exception;
}
