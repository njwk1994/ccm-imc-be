package com.ccm.document.service.impl;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.ccm.document.domain.ClearAllDrawingParamDTO;
import com.ccm.document.domain.DrawingLoaderPercentageDTO;
import com.ccm.document.domain.ExportExcelParamDTO;
import com.ccm.document.helpers.IDocumentQueryHelper;
import com.ccm.document.service.ICCMDocumentBusinessService;
import com.ccm.modules.documentmanage.CCMPipeSizeStandardContext;
import com.ccm.modules.documentmanage.constant.DocumentCommon;
import com.ccm.modules.packagemanage.CWAContext;
import com.ccm.modules.packagemanage.WBSContext;
import com.imc.common.core.enums.frame.PropertyDefinitions;
import com.imc.common.core.enums.frame.RelDirection;
import com.imc.common.core.enums.frame.Splitters;
import com.imc.common.core.exception.ServiceException;
import com.imc.common.core.model.parameters.GeneralQueryParam;
import com.imc.common.core.web.table.TableConfig;
import com.imc.common.core.web.table.TableData;
import com.imc.common.core.web.table.TableDataSource;
import com.imc.common.core.web.table.TableFieldColumn;
import com.imc.framework.context.Context;
import com.imc.framework.model.qe.QueryRequest;
import com.imc.framework.utils.GeneralUtil;
import com.imc.schema.interfaces.IObject;
import com.imc.schema.interfaces.IRel;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.ccm.modules.COMContext.*;

@Service()
@Slf4j
public class CCMDocumentBusinessServiceImpl implements ICCMDocumentBusinessService {
    @Autowired
    public RedisTemplate redisTemplate;

    @Autowired
    public IDocumentQueryHelper documentQueryHelper;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public TableData<DrawingLoaderPercentageDTO> selectAllCompleteDocumentInfo(GeneralQueryParam generalQueryParam) {
        Set<String> keys = redisTemplate.keys(DocumentCommon.DRAWING_LOADER_PERCENTAGE_COMPLETE + GeneralUtil.getUsername() + ":*");
        List<String> documentKeys = keys.stream().skip((generalQueryParam.getCurrent() - 1) * generalQueryParam.getPageSize()).limit(generalQueryParam.getPageSize()).collect(Collectors.toList());
        List<DrawingLoaderPercentageDTO> drawingLoaderPercentageDTOS = new ArrayList<>();
        for (String documentKey : documentKeys) {
            DrawingLoaderPercentageDTO drawingLoaderPercentageDTO = (DrawingLoaderPercentageDTO)this.redisTemplate.opsForValue().get(documentKey);
            if (drawingLoaderPercentageDTO != null) {
                drawingLoaderPercentageDTOS.add(drawingLoaderPercentageDTO);
            }
        }

        return getDrawingLoaderPercentages(drawingLoaderPercentageDTOS);
    }

    @Override
    public TableData<DrawingLoaderPercentageDTO> selectAllProgressDocumentInfo(GeneralQueryParam generalQueryParam) {
        Set<String> keys = redisTemplate.keys(DocumentCommon.DRAWING_LOADER_PERCENTAGE_PROGRESS + GeneralUtil.getUsername() + ":*");
        List<String> documentKeys = keys.stream().skip((generalQueryParam.getCurrent() - 1) * generalQueryParam.getPageSize()).limit(generalQueryParam.getPageSize()).collect(Collectors.toList());
        List<DrawingLoaderPercentageDTO> drawingLoaderPercentageDTOS = new ArrayList<>();
        for (String documentKey : documentKeys) {
            DrawingLoaderPercentageDTO drawingLoaderPercentageDTO = (DrawingLoaderPercentageDTO)this.redisTemplate.opsForValue().get(documentKey);
            if (drawingLoaderPercentageDTO != null) {
                drawingLoaderPercentageDTOS.add(drawingLoaderPercentageDTO);
            }
        }

        return getDrawingLoaderPercentages(drawingLoaderPercentageDTOS);
    }

    public TableData<DrawingLoaderPercentageDTO> getDrawingLoaderPercentages(List<DrawingLoaderPercentageDTO> drawingLoaderPercentageDTOS) {
        TableData<DrawingLoaderPercentageDTO> tableData = new TableData<>();
        // 属性列
        List<TableFieldColumn> tableFieldColumns = new ArrayList<>();
        tableFieldColumns.add(new TableFieldColumn("drawingName", "对象", true));
        tableFieldColumns.add(new TableFieldColumn("designObjectsCount", "对象数量", true));
        tableFieldColumns.add(new TableFieldColumn("createUser", "创建用户", true));
        tableFieldColumns.add(new TableFieldColumn("project", "项目", true));
        tableFieldColumns.add(new TableFieldColumn("startTime", "开始时间", true));
        tableFieldColumns.add(new TableFieldColumn("endTime", "结束时间", true));
        tableFieldColumns.add(new TableFieldColumn("timeSpan", "耗时", true));
        tableFieldColumns.add(new TableFieldColumn("processingMsg", "过程信息", true));
        tableFieldColumns.add(new TableFieldColumn("percentage", "进度", true));
        tableData.setTableColumns(tableFieldColumns);

        // 表格配置
        tableData.setTableConfig(new TableConfig());

        // 表格数据
        TableDataSource<DrawingLoaderPercentageDTO> dataSource = new TableDataSource<>();
        dataSource.setData(drawingLoaderPercentageDTOS);
        dataSource.setCurrent(1L);
        dataSource.setTotal((long)drawingLoaderPercentageDTOS.size());
        dataSource.setPageSize((long)drawingLoaderPercentageDTOS.size());
        tableData.setTableData(dataSource);
        return tableData;
    }

    @Override
    public void insertOrUpdateDrawingLoaderPercentage(DrawingLoaderPercentageDTO drawingLoaderPercentageDTO) {
        if (StringUtils.isEmpty(drawingLoaderPercentageDTO.getDrawingName())) throw new ServiceException("请输入图纸名称");
        DrawingLoaderPercentageDTO oldDrawingLoaderPercentageDTO = (DrawingLoaderPercentageDTO)this.redisTemplate.opsForValue().get(DocumentCommon.DRAWING_LOADER_PERCENTAGE_PROGRESS + drawingLoaderPercentageDTO.getDrawingName() + ":" + drawingLoaderPercentageDTO.getDrawingName());
        if (oldDrawingLoaderPercentageDTO == null) {
            drawingLoaderPercentageDTO.setStartTime(formatter.format(new Date()));
        }
        this.redisTemplate.opsForValue().set(DocumentCommon.DRAWING_LOADER_PERCENTAGE_PROGRESS + drawingLoaderPercentageDTO.getCreateUser() + ":" + drawingLoaderPercentageDTO.getDrawingName(), drawingLoaderPercentageDTO);
    }

    @Override
    public DrawingLoaderPercentageDTO getProgressDrawingLoaderPercentageByDrawingName(String drawingName, String username) {
        if (StringUtils.isEmpty(drawingName)) throw new ServiceException("请输入图纸名称");
        return (DrawingLoaderPercentageDTO)this.redisTemplate.opsForValue().get(DocumentCommon.DRAWING_LOADER_PERCENTAGE_PROGRESS + username + ":" + drawingName);
    }

    @Override
    public void completeDrawingLoader(String drawingName, String msg, String username) {
        DrawingLoaderPercentageDTO drawingLoaderPercentageDTO = this.getProgressDrawingLoaderPercentageByDrawingName(drawingName, username);
        if (drawingLoaderPercentageDTO == null) return;
        drawingLoaderPercentageDTO.setPercentage(100.0);
        drawingLoaderPercentageDTO.setProcessingMsg(msg);
        drawingLoaderPercentageDTO.setEndTime(formatter.format(new Date()));
        try {
            drawingLoaderPercentageDTO.setTimeSpan(getTimeSpan(drawingLoaderPercentageDTO.getStartTime(), drawingLoaderPercentageDTO.getEndTime()).toString());
        } catch (Exception e) {
            log.error(ExceptionUtil.getMessage(e));
        }
        this.redisTemplate.delete(DocumentCommon.DRAWING_LOADER_PERCENTAGE_PROGRESS + username + ":" + drawingName);
        this.redisTemplate.opsForValue().set(DocumentCommon.DRAWING_LOADER_PERCENTAGE_COMPLETE + username + ":" + drawingLoaderPercentageDTO.getDrawingName(), drawingLoaderPercentageDTO);
    }

    private Double getTimeSpan(String startTime, String endTime) throws ParseException {
        Date firstDateTimeDate = formatter.parse(startTime);
        Date secondDateTimeDate = formatter.parse(endTime);
        long firstDateMilliSeconds = firstDateTimeDate.getTime();
        long secondDateMilliSeconds = secondDateTimeDate.getTime();
        long subDateMilliSeconds = secondDateMilliSeconds - firstDateMilliSeconds;
        return (double) subDateMilliSeconds / 1000;
    }

    @Override
    public void clearAllDrawingLoaderPercentage(ClearAllDrawingParamDTO clearAllDrawingParamDTO) {
        if (StringUtils.isEmpty(clearAllDrawingParamDTO.getStartDate())) throw new ServiceException("请输入日期");
        Set<String> keys = redisTemplate.keys(DocumentCommon.DRAWING_LOADER_PERCENTAGE_COMPLETE + GeneralUtil.getUsername() + ":*");
        Set<String> needDeletes = new HashSet<>();
        for (String key : keys) {
            DrawingLoaderPercentageDTO drawingLoaderPercentageDTO = (DrawingLoaderPercentageDTO)redisTemplate.opsForValue().get(key);
            if (drawingLoaderPercentageDTO == null) continue;
            if (!drawingLoaderPercentageDTO.getStartTime().startsWith(clearAllDrawingParamDTO.getStartDate())) continue;
            needDeletes.add(key);
        }
        this.redisTemplate.delete(keys);
    }

    @Override
    public void exportDocumentToExcelTemplate(ExportExcelParamDTO exportExcelParamDTO, HttpServletResponse response) throws IOException {
        String path = "excel/"+ exportExcelParamDTO.getDesignPhase() + ".xlsx";

        Map<String, String> sheetMap = new HashMap();
        Map<String, Map<String, String>> propertyMap = new HashMap<>();

        // 读取模板中的配置信息
        ExcelReader excelReader = ExcelUtil.getReader(path);
        int sheetCounts = excelReader.getWorkbook().getNumberOfSheets();
        for (int index = 0; index < sheetCounts; index++) {
            String sheetName = excelReader.getWorkbook().getSheetName(index);
            switch (sheetName) {
                case "SheetMap":
                    sheetMap = this.getSheetMap(ExcelUtil.getReader(path, index));
                    break;
                case "PropertyMap":
                    propertyMap = this.getPropertyMap(ExcelUtil.getReader(path, index));
                    break;
                default:
                    break;
            }
        }

        // 写入数据
        ExcelWriter excelWriter = new ExcelWriter(path);

        // 获得图纸信息
        List<IObject> documents = new ArrayList<>();
        if (StringUtils.isEmpty(exportExcelParamDTO.getDocumentUid())) {
            documents = documentQueryHelper.getDocumentsByDesignPhase(exportExcelParamDTO.getDesignPhase());
        } else {
            IObject document = documentQueryHelper.getDocumentByUID(exportExcelParamDTO.getDocumentUid());
            documents.add(document);
        }
        if (documents.isEmpty()) return;

        // 写入图纸数据
        List<List<Object>> titles = excelReader.read(0, 1);
        writeExcel(propertyMap, "图纸", documents, excelWriter, titles.get(0));

        // 获得文档UID
        List<String> docUids = documents.stream().map(IObject::getUid).collect(Collectors.toList());

        // 写入设计数据
        for (Map.Entry<String, String> sheet : sheetMap.entrySet()) {

            // 跳过图纸
            if ("图纸".equalsIgnoreCase(sheet.getKey())) continue;

            // 获得类定义
            String classDefinitionUid = sheet.getValue();
            QueryRequest queryRequest = new QueryRequest();
            queryRequest.addClassDefForQuery(classDefinitionUid);
            queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + REL_DOCUMENT_TO_DESIGN_OBJ, PropertyDefinitions.uid1.toString(), SqlKeyword.IN, String.join(Splitters.T_COMMA.getMsg(), docUids));
            List<IObject> objects = Context.Instance.getQueryHelper().query(queryRequest, IObject.class);

            // 获得属性列
            try {
                ExcelReader designSheet = ExcelUtil.getReader(path, sheet.getKey());
                titles = designSheet.read(0, 1);
                if (titles.isEmpty()) continue;
                writeExcel(propertyMap, sheet.getKey(), objects, excelWriter, titles.get(0));
            } catch (Exception e) {
                log.error(ExceptionUtil.getMessage(e));
            }
        }

        // 导出
        exportExcelToWeb(excelWriter.getWorkbook(), response);
    }

    @Override
    public void exportDocumentWeld(ExportExcelParamDTO exportExcelParamDTO, HttpServletResponse response) {
        // 使用新的Excel对象写入数据
        try (XSSFWorkbook exportExcel = new XSSFWorkbook()) {
            XSSFSheet weldSheet = exportExcel.createSheet("焊口");
            XSSFRow titleRow = weldSheet.createRow(0);
            titleRow.createCell(0).setCellValue("焊口号");
            titleRow.createCell(1).setCellValue("区域");
            titleRow.createCell(2).setCellValue("管线");
            titleRow.createCell(3).setCellValue("材料类别");
            titleRow.createCell(4).setCellValue("达因数");

            // 获取图纸数据
            List<IObject> documents = documentQueryHelper.getIFCDocumentsByDesignPhase(exportExcelParamDTO.getDesignPhase());
            List<String> docUIDS = documents.stream().map(IObject::getUid).collect(Collectors.toList());

            // 获得焊接数据
            QueryRequest queryRequest = new QueryRequest();
            queryRequest.addClassDefForQuery("CIMCCMWeld");

            // 2022.12.14 HT 查询设计对象重复问题修复
            //weldEngine.addPropertyForQuery(weldRequest, "", "CIMRevisionItemOperationState", operator._isNOT, "EN_Deleted");
//            List<String> revStates = new ArrayList<>();
//            revStates.add(revState.EN_New.name());
//            revStates.add(revState.EN_Current.name());
//            revStates.add(revState.EN_Revised.name());
//            weldEngine.addPropertyForQuery(weldRequest, "", "CIMRevisionItemRevState", operator.in, String.join(",", revStates));
            queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + REL_DOCUMENT_TO_DESIGN_OBJ, PropertyDefinitions.uid1.name(), SqlKeyword.IN, String.join(Splitters.T_COMMA.getMsg(), docUIDS));
            List<IObject> welds = Context.Instance.getQueryHelper().query(queryRequest, IObject.class);

            // 填充数据
            for (int rowNum = 1; rowNum < welds.size(); rowNum++) {
                try {
                    IObject weld = welds.get(rowNum - 1);
                    XSSFRow row = weldSheet.createRow(rowNum);

                    // 焊口号
                    row.createCell(0).setCellValue(weld.getName());

                    // 区域
                    String area = String.valueOf(weld.getLatestValue(INTERFACE_CIM_CCM_PBS, "Area"));
                    IObject object = Context.Instance.getCacheHelper().getSchema(area, IObject.class);
                    row.createCell(1).setCellValue(object.getDescription());

                    // 图纸号(管线)
                    IRel rel = weld.getEnd2Relationships().getRel(REL_DOCUMENT_TO_DESIGN_OBJ, false);
                    if (rel == null) continue;
                    Optional<IObject> document = documents.stream().filter(x -> x.getUid().equals(rel.getUid1())).findFirst();
                    if (!document.isPresent()) continue;
                    row.createCell(2).setCellValue(document.get().getName());

                    // 材料类别
                    String materialCategory = String.valueOf(weld.getLatestValue(INTERFACE_CIM_CCM_PIPE_MATERIAL, "MaterialCategory"));
                    object = Context.Instance.getCacheHelper().getSchema(materialCategory, IObject.class);
                    row.createCell(3).setCellValue(object.getDescription());

                    // 达因数 (size/25 保留一位小数)
                    BigDecimal size1BigDecimal = new BigDecimal(String.valueOf(weld.getLatestValue(INTERFACE_CIM_CCM_PIPE_DIMENSION, "Size1")));
                    BigDecimal staticSize15 = new BigDecimal("15");
                    BigDecimal staticSize20 = new BigDecimal("20");
                    BigDecimal staticSize80 = new BigDecimal("80");
                    if (NumberUtil.equals(size1BigDecimal, staticSize15)) {
                        row.createCell(4).setCellValue("0.50");
                    } else if (NumberUtil.equals(size1BigDecimal, staticSize20)) {
                        row.createCell(4).setCellValue("0.75");
                    } else if (NumberUtil.equals(size1BigDecimal, staticSize80)) {
                        row.createCell(4).setCellValue("3.00");
                    } else {
                        row.createCell(4).setCellValue(NumberUtil.div(size1BigDecimal, 25, 2).toPlainString());
                    }
                } catch (Exception e) {
                    log.error(ExceptionUtil.getMessage(e));
                }
            }

            exportExcelToWeb(exportExcel, response);

        } catch (Exception e) {
            log.error("获取焊口导出数据失败!失败信息:{}", ExceptionUtil.getMessage(e), ExceptionUtil.getRootCause(e));
            throw new ServiceException("获取焊口导出数据失败!失败信息:" + ExceptionUtil.getMessage(e));
        }
    }

    @Override
    public void exportDocumentWeldEquivalent(ExportExcelParamDTO exportExcelParamDTO, HttpServletResponse response) {
        // 使用新的Excel对象写入数据
        try (XSSFWorkbook exportExcel = new XSSFWorkbook()) {
            XSSFSheet weldSheet = exportExcel.createSheet("焊口");
            XSSFRow titleRow = weldSheet.createRow(0);
            titleRow.createCell(0).setCellValue("焊口号");
            titleRow.createCell(1).setCellValue("区域");
            titleRow.createCell(2).setCellValue("管线");
            titleRow.createCell(3).setCellValue("材料类别");
            titleRow.createCell(4).setCellValue("焊接当量");

            // 获取图纸数据
            List<IObject> documents = documentQueryHelper.getIFCDocumentsByDesignPhase(exportExcelParamDTO.getDesignPhase());
            List<String> docUIDS = documents.stream().map(IObject::getUid).collect(Collectors.toList());

            // 获得焊接数据
            QueryRequest queryRequest = new QueryRequest();
            queryRequest.addClassDefForQuery("CIMCCMWeld");
            //weldEngine.addPropertyForQuery(weldRequest, "", "CIMRevisionItemOperationState", operator._isNOT, "EN_Deleted");
//            List<String> revStates = new ArrayList<>();
//            revStates.add(revState.EN_New.name());
//            revStates.add(revState.EN_Current.name());
//            revStates.add(revState.EN_Revised.name());
//            weldEngine.addPropertyForQuery(weldRequest, "", "CIMRevisionItemRevState", operator.in, String.join(",", revStates));
            queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + REL_DOCUMENT_TO_DESIGN_OBJ, PropertyDefinitions.uid1.name(), SqlKeyword.IN, String.join(Splitters.T_COMMA.getMsg(), docUIDS));
            List<IObject> welds = Context.Instance.getQueryHelper().query(queryRequest, IObject.class);

            for (int rowNum = 1; rowNum < welds.size(); rowNum++) {
                try {
                    IObject weld = welds.get(rowNum - 1);
                    XSSFRow row = weldSheet.createRow(rowNum);

                    // 焊口号
                    row.createCell(0).setCellValue(weld.getName());

                    // 区域
                    String area = String.valueOf(weld.getLatestValue(INTERFACE_CIM_CCM_PBS, "Area"));
                    IObject object = Context.Instance.getCacheHelper().getSchema(area, IObject.class);
                    row.createCell(1).setCellValue(object.getDescription());

                    // 图纸号(管线)
                    IRel rel = weld.getEnd2Relationships().getRel(REL_DOCUMENT_TO_DESIGN_OBJ, false);
                    if (rel == null) continue;
                    Optional<IObject> document = documents.stream().filter(x -> x.getUid().equals(rel.getUid1())).findFirst();
                    if (!document.isPresent()) continue;
                    row.createCell(2).setCellValue(document.get().getName());

                    // 材料类别
                    String materialCategory = String.valueOf(weld.getLatestValue(INTERFACE_CIM_CCM_PIPE_MATERIAL, "MaterialCategory"));
                    object = Context.Instance.getCacheHelper().getSchema(materialCategory, IObject.class);
                    row.createCell(3).setCellValue(object.getDescription());

                    // 焊接当量
                    String weldEquivalent;

                    // DN 公称直径
                    String size1 = String.valueOf(weld.getLatestValue(INTERFACE_CIM_CCM_PIPE_DIMENSION, "Size1"));
                    BigDecimal size1BigDecimal = new BigDecimal(size1);

                    // 壁厚
                    String size2 = String.valueOf(weld.getLatestValue(INTERFACE_CIM_CCM_PIPE_DIMENSION, "Size2"));
                    BigDecimal size2BigDecimal = new BigDecimal(size2);

                    // 获取公制的外径
                    String odByDN = documentQueryHelper.getODByDN(size1);
                    if (StringUtils.isEmpty(odByDN)) {
                        throw new ServiceException("获取外径失败!DN:" + size1);
                    }
                    BigDecimal odByDNBigDecimal = new BigDecimal(odByDN);

                    // 修正系数2
                    // 2022.08.12 HT 先去除查询修正系数用到的weldType条件
                    //String correctionFactor2 = ICCMWeldCorrectionFactorUtils.getCorrectionFactor2(weld.getDisplayValue(ICCMWeldCorrectionFactorUtils.WELD_TYPE),weld.getDisplayValue("MaterialCategory"), size2);
                    String correctionFactor2 = documentQueryHelper.getCorrectionFactor2(null, String.valueOf(weld.getLatestValue(INTERFACE_CIM_CCM_PIPE_MATERIAL, "MaterialCategory")), size2);
                    BigDecimal correctionFactor2BigDecimal = new BigDecimal(correctionFactor2);

                    // 管径判断分>2寸(即大于staticSize50) 和  管径<=2寸(即<=staticSize50)
                    BigDecimal staticSize50 = new BigDecimal("50");

                    // 25.4(转换英制除数) * 8(壁厚固定除数)
                    BigDecimal toDiv = NumberUtil.mul(CCMPipeSizeStandardContext.INCH_COEFFICIENT, new BigDecimal("8"));

                    // 管径>2寸
                    if (NumberUtil.isGreater(size1BigDecimal, staticSize50)) {
                        // 修正系数1
                        String correctionFactor1BySize2 = documentQueryHelper.getCorrectionFactor1BySize2(size2);
                        BigDecimal correctionFactor1BigDecimal = new BigDecimal(correctionFactor1BySize2);

                        // 焊接当量数 = 管道外径/25.4*管道壁厚/8*修正系数1*修正系数2  管道外径/25.4(转为英制尺寸)
                        BigDecimal mul = NumberUtil.mul(odByDNBigDecimal, size2BigDecimal, correctionFactor1BigDecimal, correctionFactor2BigDecimal);
                        weldEquivalent = NumberUtil.div(mul, toDiv, 2).toPlainString();
                    } else {    // 管径<=2寸
                        // 焊接当量数 = 管道外径/25.4*管道壁厚/8*修正系数2  管道外径/25.4(转为英制尺寸)
                        BigDecimal mul = NumberUtil.mul(odByDNBigDecimal, size2BigDecimal, correctionFactor2BigDecimal);
                        weldEquivalent = NumberUtil.div(mul, toDiv, 2).toPlainString();
                    }
                    row.createCell(4).setCellValue(weldEquivalent);
                } catch (Exception e) {
                    log.error(ExceptionUtil.getMessage(e));
                }
            }

            exportExcelToWeb(exportExcel, response);

        } catch (Exception e) {
            log.error("获取焊口焊接当量报表失败!失败信息:{}", ExceptionUtil.getMessage(e), ExceptionUtil.getRootCause(e));
            throw new ServiceException("获取焊口焊接当量报表失败!失败信息:" + ExceptionUtil.getMessage(e));
        }
    }

    @Override
    public void exportWeldData(ExportExcelParamDTO exportExcelParamDTO, HttpServletResponse response) {
        // 使用新的Excel对象写入数据
        try (XSSFWorkbook exportExcel = new XSSFWorkbook()) {
            XSSFSheet weldSheet = exportExcel.createSheet("焊口");
            XSSFRow titleRow = weldSheet.createRow(0);
            // 单位代码
            titleRow.createCell(0).setCellValue("单位代码");
            // 装置工区编号(施工区域)
            titleRow.createCell(1).setCellValue("装置工区编号");
            // 管线代号(图纸号)
            titleRow.createCell(2).setCellValue("管线代号");
            // 焊口代号(焊口编号上的数值,待确认)
            titleRow.createCell(3).setCellValue("焊口代号");
            // 材质类型代号
            titleRow.createCell(4).setCellValue("材质类型代号");
            // 材质1(材料牌号)
            titleRow.createCell(5).setCellValue("材质1");
            // 材质2代号
            titleRow.createCell(6).setCellValue("材质2代号");
            // 材料1
            titleRow.createCell(7).setCellValue("材料1");
            // 材料2
            titleRow.createCell(8).setCellValue("材料2");
            // 探伤比例代号
            titleRow.createCell(9).setCellValue("探伤比例代号");
            // 焊缝类型代号(待确认)
            titleRow.createCell(10).setCellValue("焊缝类型代号");
            // 焊接区域（安装/预制）(施工阶段)
            titleRow.createCell(11).setCellValue("焊接区域（安装/预制）");
            // 焊口属性（固定、活动）
            titleRow.createCell(12).setCellValue("焊口属性（固定、活动）");
            // 达因数
            titleRow.createCell(13).setCellValue("达因数");
            // 规格（mm）(再确认)
            titleRow.createCell(14).setCellValue("规格（mm）");
            // 壁厚(再确认)
            titleRow.createCell(15).setCellValue("壁厚");
            // 焊接方法代码
            titleRow.createCell(16).setCellValue("焊接方法代码");
            // 试验压力
            titleRow.createCell(17).setCellValue("试验压力");
            // 焊条代号
            titleRow.createCell(18).setCellValue("焊条代号");
            // 焊丝代号
            titleRow.createCell(19).setCellValue("焊丝代号");
            // 介质代号(管线号第一部分)
            titleRow.createCell(20).setCellValue("介质代号");
            // 单线图号
            titleRow.createCell(21).setCellValue("单线图号");
            // 设计压力
            titleRow.createCell(22).setCellValue("设计压力");
            // 设计温度
            titleRow.createCell(23).setCellValue("设计温度");
            // 坡口代号
            titleRow.createCell(24).setCellValue("坡口代号");
            // 管线等级代号(等级)
            titleRow.createCell(25).setCellValue("管线等级代号");
            // 组件一代号
            titleRow.createCell(26).setCellValue("组件一代号");
            // 组件二代号
            titleRow.createCell(27).setCellValue("组件二代号");
            // 炉批号一
            titleRow.createCell(28).setCellValue("炉批号一");
            // 炉批号二
            titleRow.createCell(29).setCellValue("炉批号二");
            // 所属管段
            titleRow.createCell(30).setCellValue("所属管段");
            // 预热温度
            titleRow.createCell(31).setCellValue("预热温度");
            // 是否需热处理（是，否）
            titleRow.createCell(32).setCellValue("是否需热处理（是，否）");
            // 热处理编号
            titleRow.createCell(33).setCellValue("热处理编号");
            // 焊接位置（1G/2G/3G/4G/5G/6G）
            titleRow.createCell(34).setCellValue("焊接位置（1G/2G/3G/4G/5G/6G）");
            // 外径(再确认)
            titleRow.createCell(35).setCellValue("外径");
            // 硬度检测比例（数值）
            titleRow.createCell(36).setCellValue("硬度检测比例（数值）");
            // 焊接气体保护
            titleRow.createCell(37).setCellValue("焊接气体保护");
            // 是否非标（是/否）
            titleRow.createCell(38).setCellValue("是否非标（是/否）");
            // 壁板号
            titleRow.createCell(39).setCellValue("壁板号");
            // 延长米
            titleRow.createCell(40).setCellValue("延长米");
            // 管道长度
            titleRow.createCell(41).setCellValue("管道长度");


            // 获取图纸数据
            List<IObject> documents = documentQueryHelper.getIFCDocumentsByDesignPhase(exportExcelParamDTO.getDesignPhase());
            List<String> docUIDS = documents.stream().map(IObject::getUid).collect(Collectors.toList());

            // 获得焊接数据
            QueryRequest queryRequest = new QueryRequest();
            queryRequest.addClassDefForQuery("CIMCCMWeld");
            //weldEngine.addPropertyForQuery(weldRequest, "", "CIMRevisionItemOperationState", operator._isNOT, "EN_Deleted");
//            List<String> revStates = new ArrayList<>();
//            revStates.add(revState.EN_New.name());
//            revStates.add(revState.EN_Current.name());
//            revStates.add(revState.EN_Revised.name());
//            weldEngine.addPropertyForQuery(weldRequest, "", "CIMRevisionItemRevState", operator.in, String.join(",", revStates));
            queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + REL_DOCUMENT_TO_DESIGN_OBJ, PropertyDefinitions.uid1.name(), SqlKeyword.IN, String.join(Splitters.T_COMMA.getMsg(), docUIDS));
            List<IObject> welds = Context.Instance.getQueryHelper().query(queryRequest, IObject.class);

            for (int rowNum = 1; rowNum < welds.size(); rowNum++) {
                IObject weld = welds.get(rowNum - 1);
                XSSFRow row = weldSheet.createRow(rowNum);

                // 数据库焊口编号完整值
                String weldName = weld.getName();
                String[] weldNameSplit = weldName.split("-");

                // 单位代码
                row.createCell(0).setCellValue("");
                // 装置工区编号(施工区域)
                String cwa = String.valueOf(weld.getLatestValue(CWAContext.INTERFACE_CIM_CCM_ICIMCCMCWA,CWAContext.PROPERTY_CWA));
                IObject cwaObject = Context.Instance.getCacheHelper().getSchema(cwa, IObject.class);
                row.createCell(1).setCellValue(cwaObject.getDescription());

                // 图纸号(管线)
                IRel rel = weld.getEnd2Relationships().getRel(REL_DOCUMENT_TO_DESIGN_OBJ, false);
                if (rel == null) continue;
                Optional<IObject> document = documents.stream().filter(x -> x.getUid().equals(rel.getUid1())).findFirst();
                if (!document.isPresent()) continue;
                row.createCell(2).setCellValue(document.get().getName());

                // 焊口代号(焊口编号最后一个"-"后面的值)
                row.createCell(3).setCellValue(weldNameSplit[weldNameSplit.length - 1]);
                // 材质类型代号
                row.createCell(4).setCellValue("");
                // 材料类别
                String materialCategory = String.valueOf(weld.getLatestValue(INTERFACE_CIM_CCM_PIPE_MATERIAL,"MaterialCategory"));
                IObject materialObject = Context.Instance.getCacheHelper().getSchema(materialCategory, IObject.class);
                row.createCell(3).setCellValue(materialObject.getDescription());

                // 材质2代号
                row.createCell(6).setCellValue("");
                // 材料1
                row.createCell(7).setCellValue("");
                // 材料2
                row.createCell(8).setCellValue("");
                // 探伤比例代号
                row.createCell(9).setCellValue("");
                // 焊缝类型代号(待确认)
                row.createCell(10).setCellValue("");

                // 焊接区域（安装/预制）(施工阶段)
                String purpose = String.valueOf(weld.getLatestValue(WBSContext.INTERFACE_CIM_CCM_ICIMCCMWBS,WBSContext.PROPERTY_PURPOSE));
                IObject purposeObject = Context.Instance.getCacheHelper().getSchema(purpose, IObject.class);
                row.createCell(11).setCellValue(purposeObject.getDescription());

                // 焊口属性（固定、活动）
                row.createCell(12).setCellValue("");
                // 达因数 (size/25 保留一位小数)
                String size1 = String.valueOf(weld.getLatestValue(INTERFACE_CIM_CCM_PIPE_DIMENSION,"Size1"));
                BigDecimal size1BigDecimal = new BigDecimal(size1);
                BigDecimal staticSize15 = new BigDecimal("15");
                BigDecimal staticSize20 = new BigDecimal("20");
                BigDecimal staticSize80 = new BigDecimal("80");
                if (NumberUtil.equals(size1BigDecimal, staticSize15)) {
                    row.createCell(13).setCellValue("0.50");
                } else if (NumberUtil.equals(size1BigDecimal, staticSize20)) {
                    row.createCell(13).setCellValue("0.75");
                } else if (NumberUtil.equals(size1BigDecimal, staticSize80)) {
                    row.createCell(13).setCellValue("3.00");
                } else {
                    row.createCell(13).setCellValue(NumberUtil.div(size1BigDecimal, 25, 2).toPlainString());
                }
                // 规格（mm）(再确认)
                row.createCell(14).setCellValue("");
                // 壁厚(再确认)
                row.createCell(15).setCellValue("");
                // 焊接方法代码
                row.createCell(16).setCellValue("");
                // 试验压力
                row.createCell(17).setCellValue("");
                // 焊条代号
                row.createCell(18).setCellValue("");
                // 焊丝代号
                row.createCell(19).setCellValue("");
                // 介质代号(管线号第一部分)
                row.createCell(20).setCellValue(weldNameSplit[0]);
                // 单线图号
                row.createCell(21).setCellValue("");
                // 设计压力
                row.createCell(22).setCellValue("");
                // 设计温度
                row.createCell(23).setCellValue("");
                // 坡口代号
                row.createCell(24).setCellValue("");
                // 管线等级代号(等级)
                row.createCell(25).setCellValue(materialObject.getDescription());
                // 组件一代号
                row.createCell(26).setCellValue("");
                // 组件二代号
                row.createCell(27).setCellValue("");
                // 炉批号一
                row.createCell(28).setCellValue("");
                // 炉批号二
                row.createCell(29).setCellValue("");
                // 所属管段
                row.createCell(30).setCellValue("");
                // 预热温度
                row.createCell(31).setCellValue("");
                // 是否需热处理（是，否）
                row.createCell(32).setCellValue("");
                // 热处理编号
                row.createCell(33).setCellValue("");
                // 焊接位置（1G/2G/3G/4G/5G/6G）
                row.createCell(34).setCellValue("");
                // 外径(再确认)
                row.createCell(35).setCellValue("");
                // 硬度检测比例（数值）
                row.createCell(36).setCellValue("");
                // 焊接气体保护
                row.createCell(37).setCellValue("");
                // 是否非标（是/否）
                row.createCell(38).setCellValue("");
                // 壁板号
                row.createCell(39).setCellValue("");
                // 延长米
                row.createCell(40).setCellValue("");
                // 管道长度
                row.createCell(41).setCellValue("");
            }

            // 导出
            exportExcelToWeb(exportExcel, response);
        } catch (Exception e) {
            log.error("获取焊接数据失败!失败信息:{}", ExceptionUtil.getMessage(e), ExceptionUtil.getRootCause(e));
            throw new ServiceException("获取焊接数据失败!失败信息:" + ExceptionUtil.getMessage(e));
        }
    }

    /**
     * 写入数据
     * @param propertyMap
     * @param sheetName
     */
    private void writeExcel(Map<String, Map<String, String>> propertyMap, String sheetName, List<IObject> datas, ExcelWriter excelWriter, List<Object> titles) {
        try {
            excelWriter.setSheet(sheetName);
            // 遍历数据
            int rowIndex = 1;
            for (IObject row : datas) {
                // 遍历属性列
                int colIndex = 0;
                for (Object title : titles) {
                    if (title == null) continue;
                    Map<String, String> property = propertyMap.get(title);
                    Object value = "";
                    if (property != null) {
                        value = row.getLatestValue(property.get("InterfaceDefUid"), property.get("PropertyDefUid"));
                    }
                    if ("图纸号".equalsIgnoreCase(title.toString())) {
                        value = row.getUid();
                    }
                    if (value != null) {
                        IObject object = null;
                        try {
                            object = Context.Instance.getCacheHelper().getSchema(value.toString(), IObject.class);
                            excelWriter.writeCellValue(colIndex, rowIndex, object.getDescription());
                        } catch (Exception e) {
                            excelWriter.writeCellValue(colIndex, rowIndex, value);
                        }
                    } else {
                        excelWriter.writeCellValue(colIndex, rowIndex, "");
                    }
                    colIndex++;
                }
                rowIndex++;
            }
        } catch (Exception e) {
            log.error(sheetName + ":" + ExceptionUtil.getMessage(e));
        }
    }

    /**
     * 获得sheet映射配置
     * @param excelReader
     * @return
     */
    private Map<String, String> getSheetMap(ExcelReader excelReader) {

        // sheet映射配置
        Map<String, String> sheetMap = new HashMap<>();

        // 读取数据
        List<List<Object>> rows = excelReader.read();
        for (List<Object> row : rows) {
            if ("SheetName".equalsIgnoreCase(row.get(0).toString())) continue;
            sheetMap.put(row.get(0).toString(), row.get(1).toString());
        }

        return sheetMap;
    }

    /**
     * 获得property映射配置
     * @param excelReader
     * @return
     */
    private Map<String, Map<String, String>> getPropertyMap(ExcelReader excelReader) {
        // sheet映射配置
        Map<String, Map<String, String>> propertyMap = new HashMap<>();

        // 读取数据
        List<List<Object>> rows = excelReader.read();
        for (List<Object> row : rows) {
            Map<String, String> property = new HashMap<>();
            property.put("InterfaceDefUid", row.get(0).toString());
            property.put("PropertyDefUid", row.get(1).toString());
            property.put("IsRequired", row.get(2).toString());
            property.put("ColumnName", row.get(3).toString());
            propertyMap.put(row.get(3).toString(), property);
        }

        return propertyMap;
    }

    /**
     * 导出Excel
     * @param workbook
     * @param response
     */
    private void exportExcelToWeb(Workbook workbook, HttpServletResponse response) {
        try {
            Throwable var3 = null;

            try {
                OutputStream out = response.getOutputStream();
                Throwable var5 = null;

                try {
                    response.setCharacterEncoding("utf-8");
                    String fileName = URLEncoder.encode("export_" + System.currentTimeMillis(), "UTF-8").replaceAll("\\+", "%20");
                    response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
                    response.setContentType("APPLICATION/OCTET-STREAM");
                    workbook.write(out);
                } catch (Throwable var30) {
                    var5 = var30;
                    throw var30;
                } finally {
                    if (out != null) {
                        if (var5 != null) {
                            try {
                                out.close();
                            } catch (Throwable var29) {
                                var5.addSuppressed(var29);
                            }
                        } else {
                            out.close();
                        }
                    }

                }
            } catch (Throwable var32) {
                var3 = var32;
                throw var32;
            } finally {
                if (workbook != null) {
                    if (var3 != null) {
                        try {
                            workbook.close();
                        } catch (Throwable var28) {
                            var3.addSuppressed(var28);
                        }
                    } else {
                        workbook.close();
                    }
                }

            }

        } catch (Exception var34) {
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            log.error("Excel写入Web失败!错误信息:{}.", ExceptionUtil.getSimpleMessage(var34), ExceptionUtil.getRootCause(var34));
            throw new RuntimeException(String.format("写入Web失败!错误信息:%s", ExceptionUtil.getSimpleMessage(var34)));
        }
    }
}
