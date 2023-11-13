package com.imc.general.api;

import com.alibaba.fastjson2.JSONObject;
import com.imc.common.core.utils.CollectionUtils;
import com.imc.common.core.utils.exception.HandlerExceptionUtils;
import com.imc.framework.api.IServerApi;
import com.imc.framework.api.impl.ServerApiBase;
import com.imc.framework.context.Context;
import com.imc.framework.utils.GeneralUtil;
import com.imc.schema.interfaces.ICIMFileComposition;
import com.imc.schema.interfaces.ICIMPhysicalFile;
import com.imc.schema.interfaces.IObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class DownloadFiles extends ServerApiBase<Boolean> {
    @SneakyThrows
    @Override
    public void onHandle() {
        if (!CollectionUtils.hasValue(this.contextObjs)) {
            return;
        }
        List<JSONObject> fileSavedInfo = new ArrayList<>();
        //1.获取所有FileSavedInfo
        for (IObject temp : this.contextObjs) {
            this.addFileSavedInfoIntoContainer(temp, fileSavedInfo);
        }
        //2.下载 返回inputStream
        if (CollectionUtils.hasValue(fileSavedInfo)) {
            Context.Instance.getFileOperateHelper().downloadPhysicalFiles(fileSavedInfo, GeneralUtil.getUsername(), getHttpServletResponse());
        }
    }

    /**
     * CHEN JING
     * 2023/09/01 14:11:04
     *
     * @param fileCompositionOrFile  文件对象或文件集合对象
     * @param fileSavedInfoContainer 保存文件存储信息的容器
     */
    private void addFileSavedInfoIntoContainer(@NotNull IObject fileCompositionOrFile, @NotNull List<JSONObject> fileSavedInfoContainer) throws Exception {
        if (fileCompositionOrFile.getClassBase().hasInterface(ICIMFileComposition.class.getSimpleName())) {
            ICIMFileComposition fileComposition = fileCompositionOrFile.toInterface(ICIMFileComposition.class);
            Objects.requireNonNull(fileComposition, HandlerExceptionUtils.convertInterfaceFailMsg(ICIMFileComposition.class.getSimpleName()));
            List<IObject> files = fileComposition.getFiles();
            if (!CollectionUtils.hasValue(files)) {
                return;
            }
            for (IObject file : files) {
                ICIMPhysicalFile physicalFile = file.toInterface(ICIMPhysicalFile.class);
                if (null == physicalFile) {
                    continue;
                }
                List<JSONObject> fileSaveInfo = physicalFile.getFileSaveInfo();
                if (CollectionUtils.hasValue(fileSaveInfo)) {
                    fileSavedInfoContainer.addAll(fileSaveInfo);
                }
            }
        } else {
            ICIMPhysicalFile physicalFile = fileCompositionOrFile.toInterface(ICIMPhysicalFile.class);
            if (null == physicalFile) {
                return;
            }
            List<JSONObject> fileSaveInfo = physicalFile.getFileSaveInfo();
            if (CollectionUtils.hasValue(fileSaveInfo)) {
                fileSavedInfoContainer.addAll(fileSaveInfo);
            }
        }
    }

    @Override
    public Boolean onSerialize() {
        return null;
    }

    @Override
    public IServerApi<Boolean> nullInstance() {
        return new DownloadFiles();
    }

    @Override
    public String getApiId() {
        return this.getClass().getSimpleName();
    }

}
