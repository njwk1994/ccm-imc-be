package com.imc.general.api;

import com.alibaba.fastjson2.JSONObject;
import com.imc.common.core.enums.form.FormPurpose;
import com.imc.common.core.enums.table.TableSelectMode;
import com.imc.common.core.model.parameters.GeneralQueryParam;
import com.imc.common.core.utils.StringUtils;
import com.imc.common.core.utils.exception.HandlerExceptionUtils;
import com.imc.common.core.web.table.TableData;
import com.imc.framework.api.IServerApi;
import com.imc.framework.api.impl.ServerApiBase;
import com.imc.framework.utils.GeneralUtil;
import com.imc.general.service.IGeneralService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class GeneralQuery extends ServerApiBase<TableData<JSONObject>> {
    private GeneralQueryParam queryParam;

    private TableData<JSONObject> tableData;


    @SneakyThrows
    @Override
    public void onHandle() {
        Objects.requireNonNull(this.queryParam, HandlerExceptionUtils.paramsInvalid("generalQueryParam"));
        if (StringUtils.isEmpty(queryParam.getPurpose())) {
            queryParam.setPurpose(FormPurpose.list.name());
        }
        if (StringUtils.isEmpty(queryParam.getSelectMode())) {
            queryParam.setSelectMode(TableSelectMode.none.name());
        }
        tableData = GeneralUtil.generateTableData(queryParam, GeneralUtil.getUsername());
    }

    @Override
    public TableData<JSONObject> onSerialize() {
        return this.tableData;
    }

    @Override
    public IServerApi<TableData<JSONObject>> nullInstance() {
        return new GeneralQuery();
    }

    @Override
    public String getApiId() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void onDeserialize(JSONObject jsonObject) {
        if (null != requestParam) {
            this.queryParam = JSONObject.parseObject(requestParam.toJSONString(), GeneralQueryParam.class);

        }
    }
}
