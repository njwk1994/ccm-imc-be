package com.ccm.document.service;

import com.ccm.document.domain.ClearAllDrawingParamDTO;
import com.ccm.document.domain.DrawingLoaderPercentageDTO;
import com.ccm.document.domain.ExportExcelParamDTO;
import com.imc.common.core.model.parameters.GeneralQueryParam;
import com.imc.common.core.web.table.TableData;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ICCMDocumentBusinessService {

    /**
     * 获得当前用户所有已完成文档任务信息
     * @param generalQueryParam
     * @return
     */
    TableData<DrawingLoaderPercentageDTO> selectAllCompleteDocumentInfo(GeneralQueryParam generalQueryParam);

    /**
     * 获得当前用户所有正在进行的文档任务信息
     * @param generalQueryParam
     * @return
     */
    TableData<DrawingLoaderPercentageDTO> selectAllProgressDocumentInfo(GeneralQueryParam generalQueryParam);

    /**
     * 插入或更新图纸加载进度
     * @param drawingLoaderPercentageDTO
     */
    void insertOrUpdateDrawingLoaderPercentage(DrawingLoaderPercentageDTO drawingLoaderPercentageDTO);

    /**
     * 根据图纸名称获得图纸进度信息
     * @param drawingName
     * @return
     */
    DrawingLoaderPercentageDTO getProgressDrawingLoaderPercentageByDrawingName(String drawingName, String username);

    /**
     * 图纸加载完成
     * @param drawingName
     */
    void completeDrawingLoader(String drawingName, String msg, String username);

    /**
     * 清除记录
     */
    void clearAllDrawingLoaderPercentage(ClearAllDrawingParamDTO clearAllDrawingParamDTO);

    /**
     * 根据模板导出图纸数据
     * @param exportExcelParamDTO
     * @return
     */
    void exportDocumentToExcelTemplate(ExportExcelParamDTO exportExcelParamDTO, HttpServletResponse response) throws IOException;

    /**
     * 导出图纸焊口数据
     * @param exportExcelParamDTO
     */
    void exportDocumentWeld(ExportExcelParamDTO exportExcelParamDTO, HttpServletResponse response);

    /**
     * 导出图纸焊口焊接当量报表
     * @param exportExcelParamDTO
     * @param response
     */
    void exportDocumentWeldEquivalent(ExportExcelParamDTO exportExcelParamDTO, HttpServletResponse response);

    /**
     * 导出焊接数据
     * @param exportExcelParamDTO
     * @param response
     */
    void exportWeldData(ExportExcelParamDTO exportExcelParamDTO, HttpServletResponse response);
}
