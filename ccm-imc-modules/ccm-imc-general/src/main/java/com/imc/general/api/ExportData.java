package com.imc.general.api;

import com.alibaba.fastjson2.JSONObject;
import com.imc.framework.api.IServerApi;
import com.imc.framework.api.impl.ServerApiBase;
import com.imc.framework.context.Context;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

/**
 * @author HuangTao
 * @version 1.0
 * @since 2023/9/14 14:34
 */
@Service
@Slf4j
public class ExportData extends ServerApiBase<Void> {

    private String exportUid;
    private JSONObject extraData;

    public static final String EXPORTER_UID = "exportUid";
    public static final String EXTRA_DATA = "extraData";

    @Override
    public void onDeserialize(JSONObject jsonObject) {
        /*
        数据信息填充
         */
        this.exportUid = this.requestParam.getString(EXPORTER_UID);
        if (!StringUtils.hasText(exportUid)) {
            throw new RuntimeException("导出器UID不可为空!");
        }
        this.extraData = null != this.requestParam.getJSONObject(EXTRA_DATA) ? this.requestParam.getJSONObject(EXTRA_DATA) : new JSONObject();
    }

    /**
     * 主体逻辑
     */
    @Override
    public void onHandle() {
        Context.Instance.getDataLoaderExporterHelper().exportData(exportUid, new ArrayList<>(), extraData);
    }

    @Override
    public Void onSerialize() {
        return null;
    }

    /**
     * 获取api id
     *
     * @return {@link String}
     */
    @Override
    public String getApiId() {
        return this.getClass().getSimpleName();
    }

    @Override
    public IServerApi<Void> nullInstance() {
        return new ExportData();
    }
}
