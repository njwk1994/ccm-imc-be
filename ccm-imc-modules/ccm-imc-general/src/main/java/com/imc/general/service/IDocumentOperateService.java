package com.imc.general.service;

import com.alibaba.fastjson2.JSONObject;
import com.imc.common.core.web.table.TableData;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import java.util.Map;

public interface IDocumentOperateService {
    /**
     * 附加文件
     * Chen  Jing
     *
     * @param files      文件
     * @param pstrDocUid 文档唯一标识
     * @return boolean
     */
    boolean attachFiles(@NotEmpty MultipartFile[] files, String pstrDocUid) throws Exception;

    /**
     * 得到文档文件
     * Chen  Jing
     *
     * @param pstrDocUid pstr doc uid
     * @return {@code TableData<CIMFileModel> }
     */
    TableData<JSONObject> getDocumentFiles(@NotNull String pstrDocUid) throws Exception;

    /**
     * 下载选中文件
     * Chen  Jing
     *
     * @param pstrFileUids uid pstr文件
     * @param response     响应
     */
    void downloadSelectedFiles(@NotEmpty String pstrFileUids,@NotNull HttpServletResponse response) throws Exception;

    /**
     * 下载文档文件
     * Chen  Jing
     *
     * @param pstrDocUid 文档唯一标识
     * @param response   响应
     */
    void downloadDocFiles(@NotEmpty String pstrDocUid,@NotNull HttpServletResponse response) throws Exception;
}
