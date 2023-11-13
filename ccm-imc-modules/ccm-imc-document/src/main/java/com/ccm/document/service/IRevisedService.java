package com.ccm.document.service;

import com.ccm.document.domain.PackageRevisionParamDTO;
import com.imc.framework.collections.impl.ObjectCollection;
import com.imc.framework.entity.loader.struct.LoadClassDefStruct;
import com.imc.framework.entity.loader.struct.LoadRelStruct;

import java.util.List;

/**
 * @description：各类包升版操作
 * @author： kekai.huang
 * @create： 2023/11/7 10:12
 */
public interface IRevisedService {

    /**
     * 获得图纸下的设计数据
     * @param documentUid
     * @param designs
     * @param relDocDesign
     * @return
     */
    List<LoadClassDefStruct> getDesignsByDocumentUid(String documentUid, List<LoadClassDefStruct> designs, List<LoadRelStruct> relDocDesign);

    /**
     * 根据图纸和设计数据给包及关联关系添加升版标记
     * @param document
     */
    void handlePackageAndRelRevised(LoadClassDefStruct document, List<LoadClassDefStruct> designObjects) throws Exception;

    /**
     * 创建文档版本信息
     * @param documentUid
     */
    void createDocumentRevisionByDocumentUid(String documentUid, List<LoadClassDefStruct> designs, ObjectCollection objectCollection) throws Exception;

    /**
     * 包升版操作
     * @param packageRevisionParamDTO
     */
    void packageRevisionHandler(PackageRevisionParamDTO packageRevisionParamDTO) throws Exception;

    /**
     * 包确认升版
     * @param packageRevisionParamDTO
     */
    void packageConfirmRevision(PackageRevisionParamDTO packageRevisionParamDTO) throws Exception;

    /**
     * 文档升版操作
     * @param documentUID
     */
    void documentConfirmRevision(String documentUID) throws Exception;
}
