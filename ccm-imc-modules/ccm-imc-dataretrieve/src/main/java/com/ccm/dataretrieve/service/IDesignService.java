package com.ccm.dataretrieve.service;

import com.alibaba.fastjson2.JSONObject;
import com.ccm.dataretrieve.entity.ConstructionType;
import com.ccm.dataretrieve.vo.ConstructionTypeSearchVo;
import com.ccm.dataretrieve.vo.ExportDocumentWeldVo;
import com.imc.common.core.model.parameters.ObjParam;
import com.imc.common.core.web.table.TableData;
import com.imc.schema.interfaces.IObject;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 设计数据对象
 */
public interface IDesignService {

    //获取图纸对应的施工分类
    List<ConstructionType> getDocumentConstructionTypes(ConstructionTypeSearchVo constructionTypeSearchVo) throws Exception;

    //导出图纸焊口数据
    void exportDocumentWeld(ExportDocumentWeldVo exportDocumentWeldVo, HttpServletResponse response) throws Exception;

}
