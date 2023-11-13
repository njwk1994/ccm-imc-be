package com.imc.general.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.imc.common.core.utils.CommonUtils;
import com.imc.framework.collections.impl.ObjectCollection;
import com.imc.framework.excel.util.TemplateExcelUtility;
import com.imc.framework.utils.GeneralUtil;
import com.imc.framework.utils.SchemaUtil;
import com.imc.general.service.IDataRetrieveService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class DataRetrieveServiceImpl implements IDataRetrieveService {


    @Override
    public void importExcelData(@NotNull MultipartFile file) throws Exception {
        JSONObject lobjJson = TemplateExcelUtility.importTemplateExcel(file);
        if (lobjJson.isEmpty()) {
            throw new Exception("未能正确解析出Excel内容信息");
        }
        this.retrievePublishJsonData(lobjJson);
    }

    /**
     * xml数据导入
     * Chen  Jing
     *
     * @param files 文件
     */
    @Override
    public void importXmlData(@NotNull MultipartFile[] files) throws Exception {
        GeneralUtil.loadFiles(files, GeneralUtil.getUsername());
    }

    /**
     * json数据导入
     * Chen  Jing
     *
     * @param files 文件
     */
    @Override
    public void importJsonData(@NotNull MultipartFile[] files) throws Exception {
        GeneralUtil.loadFiles(files, GeneralUtil.getUsername());
    }

    /**
     * 接受发布的JSON数据
     * Chen  Jing
     *
     * @param jsonObject json对象
     */
    @Override
    public void retrievePublishJsonData(@NotNull JSONObject jsonObject) throws Exception {
        List<Map<String, Map<String, Map<String, String>>>> lcolObjInfoContainer = new ArrayList<>();
        JSONArray lcolObjects = jsonObject.getJSONArray("objects");
        List<JSONObject> lcolObjs = CommonUtils.toJSONObjectList(lcolObjects);
        for (JSONObject lobjSchema : lcolObjs) {
            lcolObjInfoContainer.add(CommonUtils.converterJSONObjectToStandardFormat(lobjSchema));
        }
        lcolObjects = jsonObject.getJSONArray("rels");
        lcolObjs = CommonUtils.toJSONObjectList(lcolObjects);
        for (JSONObject lobjSchema : lcolObjs) {
            lcolObjInfoContainer.add(CommonUtils.converterJSONObjectToStandardFormat(lobjSchema));
        }
        String lstrUsername = GeneralUtil.getUsername();
        ObjectCollection lobjTransaction = new ObjectCollection(lstrUsername);
        SchemaUtil.loadObjectsByMapInfos(lcolObjInfoContainer, lobjTransaction, lstrUsername);
        lobjTransaction.commit();
    }
}
