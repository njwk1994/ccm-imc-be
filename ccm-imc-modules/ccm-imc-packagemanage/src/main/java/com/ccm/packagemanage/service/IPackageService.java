package com.ccm.packagemanage.service;

import com.alibaba.fastjson2.JSONObject;
import com.ccm.packagemanage.domain.ConstructionTypesQueryParamDTO;
import com.imc.common.core.web.table.TableData;
import com.imc.schema.interfaces.ICIMCCMDesignObj;
import com.imc.schema.interfaces.ICIMCCMPriority;
import com.imc.schema.interfaces.IObject;

import java.util.List;
import java.util.Map;

public interface IPackageService {

    /**
     * 获取材料类型
     * @param constructionTypesQueryParamDTO
     * @return
     * @throws Exception
     */
    TableData<JSONObject> selectPackageConstructionTypes(ConstructionTypesQueryParamDTO constructionTypesQueryParamDTO) throws Exception;

    /**
     * 排除非ifc状态的设计图纸
     */
    Map<IObject, List<ICIMCCMDesignObj>> ensureIFCDocuments(Map<IObject, List<ICIMCCMDesignObj>> documents);

    /**
     * 为每一个图纸计算权重
     * @param documents
     * @param priority
     * @return
     */
    Map<IObject, Double> doCalculateWeightAsPerDocument(Map<IObject, List<ICIMCCMDesignObj>> documents, ICIMCCMPriority priority);

    /**
     * 为对文档象添加权重属性
     * @param mapDocumentWeight
     * @return
     */
    List<IObject> renderCollectionWithPriority(Map<IObject, Double> mapDocumentWeight);

    /**
     * 将设计数据根据文档分组
     * @param compObjs
     * @return
     * @throws Exception
     */
    Map<IObject, List<ICIMCCMDesignObj>> splitCompObjsByDocument(List<ICIMCCMDesignObj> compObjs) throws Exception;

    /**
     * 获得图纸建筑类型
     * @param uid 图纸uid
     * @return
     */
    TableData<JSONObject> selectDocumentConstructionTypes(String uid, boolean showDeleted) throws Exception;

    /**
     * 将预测预留存储过程中返回的数据修改成通用格式
     * @param objectMap
     * @return
     */
    TableData<JSONObject> getPartialStatusRequestByObjects(Map<String, Object> objectMap);
}
