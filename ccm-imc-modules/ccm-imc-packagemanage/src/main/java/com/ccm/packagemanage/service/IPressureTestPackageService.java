package com.ccm.packagemanage.service;

import com.alibaba.fastjson2.JSONObject;
import com.ccm.packagemanage.domain.*;
import com.ccm.packagemanage.domain.DesignsQueryByDocumentAndPackageParamDTODTO;
import com.ccm.packagemanage.domain.QueryByPackageParamDTO;
import com.imc.common.core.web.table.TableData;

public interface IPressureTestPackageService {
    /**
     * 根据试压包UID查询可以关联的图纸
     * @param documentsQueryByPackageParam
     * @return
     */
    TableData<JSONObject> selectableDocumentsForPressureTestPackage(QueryByPackageParamDTO documentsQueryByPackageParam) throws Exception;

    /**
     * 根据试压包UID和图纸UID查询可以关联的设计数据
     * @param designsQueryByDocumentAndPackageParamDTO
     * @return
     */
    TableData<JSONObject> selectableComponentsForPressureTestPackage(DesignsQueryByDocumentAndPackageParamDTODTO designsQueryByDocumentAndPackageParamDTO) throws Exception;

    /**
     * 根据试压包查询试压包材料模板信息
     * @param query
     * @return
     */
    TableData<JSONObject> getPTPMaterialTemplatesByPTPackage(QueryByPackageParamDTO query) throws Exception;

    /**
     * 为试压包添加材料
     * @param PTPackageMaterialsParamDTO
     */
    void createPTPackageMaterials(PTPackageMaterialsParamDTO PTPackageMaterialsParamDTO) throws Exception;

    /**
     * 移除试压包下材料
     * @param ptPackageMaterialsParamDTO
     */
    void deletePTPMaterials(PTPackageMaterialsParamDTO ptPackageMaterialsParamDTO) throws Exception;

    /**
     * 添加图纸和设计数据到试压包中
     * @param testRelDocsAndDesignsParamDTO
     */
    void assignDocumentsAndDesignsToPressureTestPackage(TestRelDocsAndDesignsParamDTO testRelDocsAndDesignsParamDTO) throws Exception;

    /**
     * 移除试压包下图纸信息
     * @param packageRelDocumentsParamDTO
     */
    void removeDocumentsFromPressureTestPackage(PackageRelDocumentsParamDTO packageRelDocumentsParamDTO) throws Exception;

    /**
     * 删除设计数据
     * @param testRelDocsAndDesignsParamDTO
     */
    void removeComponentsFromPackage(TestRelDocsAndDesignsParamDTO testRelDocsAndDesignsParamDTO) throws Exception;
}
