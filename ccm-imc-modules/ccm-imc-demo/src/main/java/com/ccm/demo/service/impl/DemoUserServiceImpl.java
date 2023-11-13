package com.ccm.demo.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.ccm.demo.service.IDemoUserService;
import com.ccm.modules.COMContext;
import com.imc.common.core.web.table.TableData;
import com.imc.framework.context.Context;
import com.imc.framework.model.qe.QueryRequest;
import com.imc.framework.utils.GeneralUtil;
import com.imc.schema.interfaces.IObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author HuangTao
 * @version 1.0
 * @since 2023/4/4 6:05
 */
@Slf4j
@Service
public class DemoUserServiceImpl implements IDemoUserService {

    @Override
    public TableData<JSONObject> getUsers() throws Exception {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addClassDefForQuery(COMContext.CLASS_COM_DEMO_USER);
        List<IObject> query = Context.Instance.getQueryHelper().query(queryRequest, IObject.class);
        return GeneralUtil.generateTableData(query, GeneralUtil.getUsername(), COMContext.CLASS_COM_DEMO_USER);
    }

}
