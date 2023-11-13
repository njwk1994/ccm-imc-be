package com.imc.schema.interfaces;

import cn.hutool.json.JSON;
import com.alibaba.fastjson2.JSONObject;

/**
 * 标注快照
 */
public interface ICIMMarkupSnapshot extends IObject{
    //视图状态
    JSONObject getCIMViewState()throws  Exception;
    void   setCIMViewState(JSONObject CIMViewState) throws Exception;

}
