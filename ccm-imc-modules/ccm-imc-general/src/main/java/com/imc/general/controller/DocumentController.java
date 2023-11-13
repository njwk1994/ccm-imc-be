package com.imc.general.controller;

import com.alibaba.fastjson2.JSONObject;
import com.imc.common.core.domain.R;
import com.imc.common.core.utils.ExceptionUtil;
import com.imc.common.core.utils.MessageUtils;
import com.imc.common.core.web.table.TableData;
import com.imc.framework.context.Context;
import com.imc.general.service.IDocumentOperateService;
import com.imc.schema.interfaces.IPropertyDef;
import com.imc.schema.interfaces.bases.IPropertyDefBase;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RequestMapping("/document")
@RestController
@Slf4j
@Api(tags = "文档操作相关")
public class DocumentController {

    private final IDocumentOperateService documentOperateService;

    public DocumentController(IDocumentOperateService documentOperateService) {
        this.documentOperateService = documentOperateService;
    }


    @ApiOperation("给指定文档附加文件")
    @PostMapping("/attachFilesToDoc")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "文档的唯一标识", value = "docUid", dataTypeClass = String.class, paramType = "body", required = true)
    })
    public R<Boolean> attachFiles(@RequestPart(value = "files") MultipartFile[] files, @RequestBody JSONObject param) {
        try {
            boolean lblnSuccess = this.documentOperateService.attachFiles(files, param.getString("docUid"));
            return lblnSuccess ? R.ok(true, MessageUtils.get("upload.success")) : R.fail(MessageUtils.get("upload.failed"));
        } catch (Exception ex) {
            log.error("---------附件文件失败!------", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("获取文档的文件信息")
    @GetMapping("/getDocFiles/{docUid}")
    public R<TableData<JSONObject>> getDocumentFiles(@PathVariable String docUid) {
        try {
            return R.ok(this.documentOperateService.getDocumentFiles(docUid), MessageUtils.get("opt.success"));
        } catch (Exception ex) {
            log.error("-----获取文件失败!-----", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("下载指定文件")
    @PostMapping("/downloadFiles")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "需要下载的文件uids,多个以,拼接", value = "uids", dataTypeClass = String.class, paramType = "body", required = true)
    })
    public R<Boolean> downloadSelectedFiles(@RequestBody JSONObject param, HttpServletResponse response) {
        try {
            this.documentOperateService.downloadSelectedFiles(param.getString("uids"), response);
            return R.ok(true, MessageUtils.get("opt.success"));
        } catch (Exception ex) {
            log.error("-----下载文件失败!-----", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("下载指定文档的物理文件")
    @GetMapping("/downloadDocFiles/{docUid}")
    public R<Boolean> downloadDocumentFiles(@PathVariable String docUid,HttpServletResponse response){
        try {
            this.documentOperateService.downloadDocFiles(docUid, response);
            return R.ok(true, MessageUtils.get("opt.success"));
        } catch (Exception ex) {
            log.error("-----下载文件失败!-----", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }



}
