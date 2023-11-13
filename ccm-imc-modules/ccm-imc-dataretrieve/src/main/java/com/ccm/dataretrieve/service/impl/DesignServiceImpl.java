package com.ccm.dataretrieve.service.impl;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.ccm.dataretrieve.entity.ConstructionType;
import com.ccm.dataretrieve.service.IDesignService;
import com.ccm.dataretrieve.vo.ConstructionTypeSearchVo;
import com.ccm.dataretrieve.vo.ExportDocumentWeldVo;
import com.ccm.modules.COMContext;
import com.imc.common.core.enums.frame.PropertyDefinitions;
import com.imc.common.core.enums.frame.RelDirection;
import com.imc.common.core.utils.CollectionUtils;
import com.imc.framework.context.Context;
import com.imc.framework.model.qe.QueryRequest;
import com.imc.schema.interfaces.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 设计数据 业务层
 */
@Service
@Slf4j
public class DesignServiceImpl implements IDesignService {


    /**
     * 获取图纸对应的施工分类
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<ConstructionType> getDocumentConstructionTypes(ConstructionTypeSearchVo constructionTypeSearchVo) throws Exception {
        ArrayList<ConstructionType> objects = new ArrayList<>();
        String uid = constructionTypeSearchVo.getUid();
        // null check
        //查询所有设计数据类型
        List<IObject> constructionType = Context.Instance.getQueryHelper().getObjectsByDefinitionUid(COMContext.CLASS_CIM_CCM_CONSTRUCTION_TYPE, IObject.class);
        // 判空。。。
        if (CollectionUtils.hasValue(constructionType)) {
            // 循环设计数据类型获取各个设计数据类型对应的class def uid有没有关联关系的数据
            for (IObject object : constructionType) {
//                Object targetClassDef = object.getLatestValue(COMContext.INTERFACE_CIM_CCM_BASIC_TARGET_OBJ, "TargetClassDef"); //接口名称和属性名称
//                Object targetClassDef = object.getLatestValue("ICIMDocumentMaster", "CIMDocType"); //接口名称和属性名称
                ICIMCCMBasicTargetObj icimccmBasicTargetObj = object.toInterface(ICIMCCMBasicTargetObj.class);
                String targetClassDef = icimccmBasicTargetObj.getTargetClassDef();
                if (ObjectUtils.isEmpty(targetClassDef)) {
                    throw new RuntimeException("目标实体为null");
                }
                String classDef = targetClassDef.toString();
                QueryRequest queryRequest = new QueryRequest();
                queryRequest.addClassDefForQuery(classDef);
                // 3.1 根据关联关系查询条件演示1,ClassDefUID为二端时,添加一端对象属性条件
                queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + "CIMCCMDocument2DesignObj", PropertyDefinitions.uid1.toString(), SqlKeyword.EQ, uid);
                if (constructionTypeSearchVo.isWithDeleted()) {
                    queryRequest.addQueryCriteria(null, "CIMRevisionItemOperationState", SqlKeyword.NE, "EN_Deleted");
                }
                queryRequest.setPage(1L, 1L);
                // 图纸关联的所有和设计数据分类对应的设计数据
                List<IObject> iObjects = Context.Instance.getQueryHelper().query(queryRequest, IObject.class);
                // CIMCCMConstructionType -》 ConstructionTypeVo
                if (CollectionUtils.hasValue(iObjects)) {
                    ConstructionType ConstructionTypes = new ConstructionType();
                    ConstructionTypes.setLabel(object.getDescription());
                    ConstructionTypes.setTargetClassDefUid(object.getDisplayName());
                    objects.add(ConstructionTypes);

                }
            }
        }


        ////////////

        return objects;
    }

    /**
     * 导出图纸焊口数据
     *
     * @param exportDocumentWeldVo
     * @param response
     */
    @Override
    public void exportDocumentWeld(ExportDocumentWeldVo exportDocumentWeldVo, HttpServletResponse response) throws Exception {

        // 写入excel 数据
        try (XSSFWorkbook exportExcel = new XSSFWorkbook()) {
            // 输出流
            ServletOutputStream outputStream = response.getOutputStream();
            // excel 的sheet名称
            XSSFSheet weldSheet = exportExcel.createSheet("焊口");
            XSSFRow titlerow = weldSheet.createRow(0);
            titlerow.createCell(0).setCellValue("焊口号");
            titlerow.createCell(1).setCellValue("区域");
            titlerow.createCell(2).setCellValue("管线");
            titlerow.createCell(3).setCellValue("材料类别");
            titlerow.createCell(4).setCellValue("达因数");

            //
            String filters = exportDocumentWeldVo.getFilters();
            //查询焊口数据
            log.info("导出焊口数据,开始查看焊口数据");
            long currentStart = System.currentTimeMillis();
            QueryRequest queryRequest = new QueryRequest();
            queryRequest.addQueryCriteria(null, "CIMDocState", SqlKeyword.NE, "EN_IFC");
            queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + "CIMDocumentMaster", PropertyDefinitions.uid1.toString(), SqlKeyword.EQ,filters );
            queryRequest.setPage(0L, 0L);


            List<IObject> query = Context.Instance.getQueryHelper().query(queryRequest, IObject.class);
            log.info("导出图纸焊口数据,获取图纸数据{}条,耗时{}ms", query.size(), System.currentTimeMillis() - currentStart);
            List<IObject> objectsByUIDsAndClassDefinitionUID = Context.Instance.getQueryHelper().getObjectsByUIDsAndClassDefinitionUID(new ArrayList<>(), "CIMCCMWeld", IObject.class);


        } catch (Exception e) {
            log.error("获取焊口导出数据失败!失败信息:{}", ExceptionUtil.getMessage(e), ExceptionUtil.getRootCause(e));
            throw new Exception("获取焊口导出数据失败!失败信息:" + ExceptionUtil.getMessage(e));
        }


    }


}
