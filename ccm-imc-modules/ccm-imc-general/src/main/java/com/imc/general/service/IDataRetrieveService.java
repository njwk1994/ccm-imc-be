package com.imc.general.service;

import com.alibaba.fastjson2.JSONObject;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

public interface IDataRetrieveService {

    /**
     * 导入excel数据
     * Chen  Jing
     *
     * @param file 文件
     */
    void importExcelData(@NotNull MultipartFile file) throws Exception;

    /**
     * xml数据导入
     * Chen  Jing
     *
     * @param files 文件
     */
    void importXmlData(@NotNull MultipartFile[] files) throws Exception;

    /**
     * json数据导入
     * Chen  Jing
     *
     * @param files 文件
     */
    void importJsonData(@NotNull MultipartFile[] files) throws Exception;

    /**
     * 接受发布的JSON数据
     * Chen  Jing
     *
     * @param jsonObject json对象
     */
    void retrievePublishJsonData(@NotNull JSONObject jsonObject) throws Exception;
}
