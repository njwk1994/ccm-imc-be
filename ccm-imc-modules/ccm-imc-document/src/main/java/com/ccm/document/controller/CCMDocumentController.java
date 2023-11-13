package com.ccm.document.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ccm.document.domain.ClearAllDrawingParamDTO;
import com.ccm.document.domain.DrawingLoaderPercentageDTO;
import com.ccm.document.domain.ExportExcelParamDTO;
import com.ccm.document.domain.FileInfoDTO;
import com.ccm.document.event.DrawingLoaderEvent;
import com.ccm.document.notify.NotifyCenter;
import com.ccm.document.service.ICCMDocumentBusinessService;
import com.ccm.modules.documentmanage.constant.DocumentCommon;
import com.imc.common.core.domain.R;
import com.imc.common.core.exception.ServiceException;
import com.imc.common.core.model.parameters.GeneralQueryParam;
import com.imc.common.core.utils.ExceptionUtil;
import com.imc.common.core.web.table.TableData;
import com.imc.framework.entity.loader.LoadResult;
import com.imc.framework.utils.GeneralUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "图文档管理")
@RequestMapping("/ccm/documentManagement")
public class CCMDocumentController {
    @Autowired
    ICCMDocumentBusinessService documentBusinessService;

    @ApiOperation("获取所有已完成上传的图纸信息")
    @PostMapping("/selectAllCompleteDocumentInfo")
    public R<TableData<DrawingLoaderPercentageDTO>> selectAllCompleteDocumentInfo(@RequestBody GeneralQueryParam generalQueryParam) {
        try {
            return R.ok(documentBusinessService.selectAllCompleteDocumentInfo(generalQueryParam));
        } catch (Exception ex) {
            log.error(ExceptionUtil.getRootErrorMessage(ex));
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("获取所有未完成上传的图纸信息")
    @PostMapping("/selectAllProgressDocumentInfo")
    public R<TableData<DrawingLoaderPercentageDTO>> selectAllProgressDocumentInfo(@RequestBody GeneralQueryParam generalQueryParam) {
        try {
            return R.ok(documentBusinessService.selectAllProgressDocumentInfo(generalQueryParam));
        } catch (Exception ex) {
            log.error(ExceptionUtil.getRootErrorMessage(ex));
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("清除所有已经完成的进度信息")
    @DeleteMapping("/clearAllDrawingLoaderPercentage")
    public R<String> clearAllDrawingLoaderPercentage(@RequestBody ClearAllDrawingParamDTO clearAllDrawingParamDTO) {
        try {
            documentBusinessService.clearAllDrawingLoaderPercentage(clearAllDrawingParamDTO);
            return R.ok();
        } catch (Exception ex) {
            log.error(ExceptionUtil.getRootErrorMessage(ex));
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("导入数据图纸")
    @PostMapping("/load")
    public R<List<LoadResult>> loadFiles(@RequestPart(value = "files") MultipartFile[] files, String baseData, String extraData) {
        try {
            // 打包文件
            List<FileInfoDTO> fileInfoDTOS = new ArrayList<>();
            for (MultipartFile multipartFile : files) {
                fileInfoDTOS.add(new FileInfoDTO(multipartFile.getName(),multipartFile.getOriginalFilename(),multipartFile.getContentType(),multipartFile.getInputStream()));
            }
            DrawingLoaderEvent drawingLoaderEvent = new DrawingLoaderEvent();
            drawingLoaderEvent.setFiles(fileInfoDTOS);
            drawingLoaderEvent.setBaseData(JSONObject.parseObject(baseData));
            JSONObject extraDataObj = JSONObject.parseObject(extraData);
            if (extraDataObj == null) {
                extraDataObj = new JSONObject();
                extraDataObj.put(DocumentCommon.USERNAME, GeneralUtil.getUsername());
            }
            drawingLoaderEvent.setExtraData(extraDataObj);
            NotifyCenter.publishEvent(drawingLoaderEvent);
            return R.ok();
        } catch (Exception ex) {
            log.error("-------------导入数据失败----------", ex);
            return R.fail(ExceptionUtil.getRootErrorMessage(ex));
        }
    }

    @ApiOperation("导出Excel模板")
    @GetMapping("/exportExcelTemplate")
    public void exportExcelTemplate(@RequestParam String templateFileName, HttpServletResponse response) throws Exception {
        String path = "excel/"+ templateFileName + ".xlsx";

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            ClassPathResource classPathResource = new ClassPathResource(path);
            if (!classPathResource.exists()) {
                throw new Exception("模板不存在");
            }

            //读取要下载的文件，保存到文件输入流
            inputStream = classPathResource.getInputStream();
            outputStream = response.getOutputStream();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");;
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + templateFileName + ".xlsx");

            byte[] buff = new byte[2048];
            int i = 0;
            while ((i = inputStream.read(buff)) != -1) {
                outputStream.write(buff, 0, i);
                outputStream.flush();
            }
        } catch (Exception e) {
            throw new ServiceException("模板下载失败");
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                throw new ServiceException("模板下载失败");
            }
        }
    }

    @ApiOperation("根据设计数据Excel模版导出数据")
    @PostMapping("/exportDocumentToExcelTemplate")
    public void exportDocumentToExcelTemplate(@RequestBody ExportExcelParamDTO exportExcelParamDTO, HttpServletResponse response) throws Exception {
        documentBusinessService.exportDocumentToExcelTemplate(exportExcelParamDTO, response);
    }

    @ApiOperation("导出图纸焊口数据")
    @PostMapping("/exportDocumentWeld")
    public void exportDocumentWeld(@RequestBody ExportExcelParamDTO exportExcelParamDTO, HttpServletResponse response) throws Exception {
        documentBusinessService.exportDocumentWeld(exportExcelParamDTO, response);
    }

    @ApiOperation("导出图纸焊口焊接当量报表")
    @PostMapping("/exportDocumentWeldEquivalent")
    public void exportDocumentWeldEquivalent(@RequestBody ExportExcelParamDTO exportExcelParamDTO, HttpServletResponse response) throws Exception {
        documentBusinessService.exportDocumentWeldEquivalent(exportExcelParamDTO, response);
    }

    @ApiOperation("导出焊接数据")
    @PostMapping("/exportWeldData")
    public void exportWeldData(@RequestBody ExportExcelParamDTO exportExcelParamDTO, HttpServletResponse response) throws Exception {
        documentBusinessService.exportWeldData(exportExcelParamDTO, response);
    }
}
