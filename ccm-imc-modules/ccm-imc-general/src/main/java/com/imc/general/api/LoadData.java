package com.imc.general.api;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.imc.common.core.model.file.base.UploadedFile;
import com.imc.common.core.utils.file.FileUtils;
import com.imc.common.core.utils.password.PasswordUtils;
import com.imc.framework.api.IServerApi;
import com.imc.framework.api.impl.ServerApiBase;
import com.imc.framework.context.Context;
import com.imc.framework.entity.loader.LoadResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author HuangTao
 * @version 1.0
 * @since 2023/9/14 14:34
 */
@Service
@Slf4j
public class LoadData extends ServerApiBase<List<LoadResult>> {

    private String loaderUid;

    private JSONObject baseData;

    private JSONObject extraData;
    private final List<MultipartFile> files = new ArrayList<>();

    private final List<LoadResult> finalResult = new ArrayList<>();

    public static final String LOADER_UID = "loaderUid";
    public static final String FILE_INFO_LIST = "fileInfoList";
    public static final String BASE_DATA = "baseData";
    public static final String EXTRA_DATA = "extraData";

    @Override
    public void onDeserialize(JSONObject jsonObject) {
        /*
        数据信息填充
         */
        this.loaderUid = this.requestParam.getString(LOADER_UID);
        if (!StringUtils.hasText(loaderUid)) {
            throw new RuntimeException("加载器UID不可为空!");
        }
        this.baseData = null != this.requestParam.getJSONObject(BASE_DATA) ? this.requestParam.getJSONObject(BASE_DATA) : new JSONObject();
        this.extraData = null != this.requestParam.getJSONObject(EXTRA_DATA) ? this.requestParam.getJSONObject(EXTRA_DATA) : new JSONObject();
        /*
        获取文件信息
         */
        JSONArray fileInfoList = this.requestParam.getJSONArray(FILE_INFO_LIST);
        for (int i = 0; i < fileInfoList.size(); i++) {
            JSONObject fileInfo = fileInfoList.getJSONObject(i);
            String filePath = fileInfo.getString(UploadedFile.FILE_PATH);
            if (StringUtils.hasText(filePath)) {
                filePath = PasswordUtils.decrypt(filePath);
                File file = new File(filePath);
                try (FileInputStream fin = new FileInputStream(file)) {
                    MultipartFile multipartFile = FileUtils.getMultipartFile(fin, file.getName());
                    files.add(multipartFile);
                } catch (Exception e) {
                    throw new RuntimeException("文件获取错误!文件名称:" + file.getName());
                }
            }
        }
    }

    /**
     * 主体逻辑
     */
    @Override
    public void onHandle() {
        List<LoadResult> result = Context.Instance.getDataLoaderExporterHelper().loadData(loaderUid, files.toArray(new MultipartFile[0]), baseData, extraData);
        finalResult.addAll(result);
    }

    /**
     * 返回结果
     *
     * @return
     */
    @Override
    public List<LoadResult> onSerialize() {
        return finalResult;
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
    public IServerApi<List<LoadResult>> nullInstance() {
        return new LoadData();
    }
}
