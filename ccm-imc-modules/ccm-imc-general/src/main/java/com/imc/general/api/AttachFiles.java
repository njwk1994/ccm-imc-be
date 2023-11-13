package com.imc.general.api;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.imc.common.core.utils.CollectionUtils;
import com.imc.common.core.utils.CommonUtils;
import com.imc.common.core.utils.exception.HandlerExceptionUtils;
import com.imc.framework.api.IServerApi;
import com.imc.framework.api.impl.ServerApiBase;
import com.imc.framework.collections.IObjectCollection;
import com.imc.framework.collections.impl.ObjectCollection;
import com.imc.framework.utils.GeneralUtil;
import com.imc.schema.interfaces.ICIMFileComposition;
import com.imc.schema.interfaces.IObject;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class AttachFiles extends ServerApiBase<Boolean> {
    private Boolean coverSameNameFile;
    private List<JSONObject> attachFiles;

    public static final String ATTACH_FILE_KEY = "attachFiles";
    public static final String COVER_SAME_NAME_FILE = "coverSameNameFile";

    @Override
    public void onDeserialize(@NotNull JSONObject jsonObject) {
        if (null != this.requestParam) {
            this.coverSameNameFile = this.requestParam.getBoolean(COVER_SAME_NAME_FILE);
            JSONArray jsonArray = this.requestParam.getJSONArray(ATTACH_FILE_KEY);
            if (CollectionUtils.hasValue(jsonArray)) {
                this.attachFiles = CommonUtils.toJSONObjectList(jsonArray);
            }
        }
    }

    @Override
    public void onHandle() {
        if (null == this.contextObj) {
            throw new RuntimeException("can not found context obj");
        }
        if (!CollectionUtils.hasValue(this.attachFiles)) {
            return;
        }
        ICIMFileComposition fileComposition = this.contextObj.toInterface(ICIMFileComposition.class);
        Objects.requireNonNull(fileComposition, HandlerExceptionUtils.convertInterfaceFailMsg(ICIMFileComposition.class.getSimpleName()));
        IObjectCollection<IObject> transaction = new ObjectCollection(GeneralUtil.getUsername());
        try {
            fileComposition.attachFiles(attachFiles, coverSameNameFile, transaction);
            transaction.commit();
        } catch (Exception e) {
            log.error("-----------attach file error", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getApiId() {
        return this.getClass().getSimpleName();
    }

    @Override
    public Boolean onSerialize() {
        return true;
    }

    @Override
    public IServerApi<Boolean> nullInstance() {
        return new AttachFiles();
    }
}
