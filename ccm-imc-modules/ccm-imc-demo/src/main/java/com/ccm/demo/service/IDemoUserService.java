package com.ccm.demo.service;

import com.alibaba.fastjson2.JSONObject;
import com.imc.common.core.web.table.TableData;

/**
 * @author HuangTao
 * @version 1.0
 * @since 2023/4/4 6:05
 */
public interface IDemoUserService {

    TableData<JSONObject> getUsers() throws Exception;


}
