package com.imc.general.api;

import com.alibaba.fastjson2.JSONObject;
import com.imc.framework.api.IServerApi;
import com.imc.framework.api.impl.ServerApiBase;
import com.imc.framework.model.api.ApiProcessParam;
import com.imc.framework.utils.GeneralUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class LoadSchema extends ServerApiBase<Boolean> {

    @Override
    public String getApiId() {
        return this.getClass().getSimpleName();
    }

    private MultipartFile[] files;


    @Override
    public void onDeserialize(JSONObject jsonObject) {
        if (null != jsonObject) {
            this.files = ApiProcessParam.getFilesFromJSONObject(jsonObject);
        }
    }

    @SneakyThrows
    @Override
    public void onHandle() {
        GeneralUtil.loadFiles(files, GeneralUtil.getUsername());
    }

    @Override
    public Boolean onSerialize() {
        return true;
    }

    @Override
    public IServerApi<Boolean> nullInstance() {
        return new LoadSchema();
    }
}
