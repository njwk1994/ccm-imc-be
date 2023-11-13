package com.ccm.document.action;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.ccm.document.domain.DrawingLoaderPercentageDTO;
import com.ccm.document.helpers.IDocumentQueryHelper;
import com.ccm.document.service.ICCMDocumentBusinessService;
import com.ccm.document.service.IRevisedService;
import com.ccm.modules.documentmanage.IMCDocumentContext;
import com.ccm.modules.documentmanage.constant.DocumentCommon;
import com.ccm.modules.documentmanage.enums.CIMRevisionStatus;
import com.ccm.modules.documentmanage.enums.DesignObjOperateStatus;
import com.ccm.modules.packagemanage.CWAContext;
import com.ccm.modules.schedulermanage.BidSectionContext;
import com.ccm.modules.schedulermanage.ToBidSectionContext;
import com.imc.common.core.enums.frame.PropertyDefinitions;
import com.imc.common.core.model.frame.MJSONObject;
import com.imc.framework.collections.IRelCollection;
import com.imc.framework.collections.impl.ObjectCollection;
import com.imc.framework.context.Context;
import com.imc.framework.entity.loader.struct.*;
import com.imc.framework.enums.RevStatus;
import com.imc.framework.handlers.loader.base.abstr.DataLoadingBeforeAction;
import com.imc.framework.model.qe.QueryRequest;
import com.imc.framework.utils.SchemaUtil;
import com.imc.schema.interfaces.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.ParameterResolutionDelegate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ccm.modules.COMContext.CLASS_CIM_CCM_DOCUMENT_MASTER;
import static com.ccm.modules.COMContext.REL_DOCUMENT_TO_DESIGN_OBJ;

/**
 * @description：图纸导入前动作
 * @author： kekai.huang
 * @create： 2023/10/25 15:00
 */
@Slf4j
@Component
public class DrawingDataLoadingBeforeAction extends DataLoadingBeforeAction {
    @Autowired
    ICCMDocumentBusinessService documentBusinessService;

    @Autowired
    IDocumentQueryHelper documentQueryHelper;

    @Autowired
    IRevisedService revisedService;

    @Override
    public void execute(List<LoadDataStruct> list) {
        log.debug(JSON.toJSONString(list));

        for (LoadDataStruct dataStruct : list) {
            List<LoadClassDefStruct> loadClassDefStructs = dataStruct.getStandardClassDefStructs();
            String username = dataStruct.getExtraDataStruct().getString(DocumentCommon.CURRENT_USERNAME);
            ObjectCollection objectCollection = new ObjectCollection(username);
            if (loadClassDefStructs == null || loadClassDefStructs.isEmpty()) continue;

            // 文档
            List<LoadClassDefStruct> documents = loadClassDefStructs.stream().filter(x -> x.getClassDefUid().equalsIgnoreCase(CLASS_CIM_CCM_DOCUMENT_MASTER)).collect(Collectors.toList());

            // 设计数据
            List<LoadClassDefStruct> designs = loadClassDefStructs.stream().filter(x -> !x.getClassDefUid().equalsIgnoreCase(CLASS_CIM_CCM_DOCUMENT_MASTER)).collect(Collectors.toList());

            for (LoadClassDefStruct document : documents) {
                // 进度
                DrawingLoaderPercentageDTO drawingLoaderPercentageDTO = documentBusinessService.getProgressDrawingLoaderPercentageByDrawingName(document.getName(), username);

                // 获得新版本的所有设计数据集合
                List<LoadClassDefStruct> docDesigns = revisedService.getDesignsByDocumentUid(document.getUid(), designs, dataStruct.getStandardRelStructs());

                // 图纸升版操作
                drawingLoaderPercentageDTO.setPercentage(16.0);
                drawingLoaderPercentageDTO.setProcessingMsg("图纸开始升版操作...");
                drawingLoaderPercentageDTO.setCreateUser(username);
                documentBusinessService.insertOrUpdateDrawingLoaderPercentage(drawingLoaderPercentageDTO);
                Map<String, String> revisionInfo = null;
                try {
                    revisionInfo = documentRevision(document, docDesigns, username, objectCollection);
                } catch (Exception e) {
                    drawingLoaderPercentageDTO.setPercentage(35.0);
                    drawingLoaderPercentageDTO.setProcessingMsg(ExceptionUtil.getMessage(e));
                    drawingLoaderPercentageDTO.setCreateUser(username);
                    documentBusinessService.insertOrUpdateDrawingLoaderPercentage(drawingLoaderPercentageDTO);
                }

                // 设计数据升版
                drawingLoaderPercentageDTO.setPercentage(36.0);
                drawingLoaderPercentageDTO.setProcessingMsg("设计数据开始升版操作...");
                drawingLoaderPercentageDTO.setCreateUser(username);
                try {
                    designRevision(docDesigns, revisionInfo);
                } catch (Exception e) {
                    drawingLoaderPercentageDTO.setPercentage(50.0);
                    drawingLoaderPercentageDTO.setProcessingMsg(ExceptionUtil.getMessage(e));
                    drawingLoaderPercentageDTO.setCreateUser(username);
                    documentBusinessService.insertOrUpdateDrawingLoaderPercentage(drawingLoaderPercentageDTO);
                }
                drawingLoaderPercentageDTO.setPercentage(50.0);
                drawingLoaderPercentageDTO.setProcessingMsg("图纸开始加载...");
                drawingLoaderPercentageDTO.setCreateUser(username);
                documentBusinessService.insertOrUpdateDrawingLoaderPercentage(drawingLoaderPercentageDTO);
            }
            try {
                objectCollection.commit();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public String getDescription() {
        return "图纸导入前动作";
    }

    @Override
    public String getUniqueId() {
        return DrawingDataLoadingBeforeAction.class.getName();
    }

    /**
     * 设计数据升版操作
     * @param designs
     */
    private void designRevision(List<LoadClassDefStruct> designs, Map<String, String> revisionInfo) throws Exception {
        if (revisionInfo == null) return;
        // 设计数据处理
        for (LoadClassDefStruct design : designs) {
            // 添加标段信息
            designAddBidSection(design);

            // 此设计数据是否存在ICIMRevisionItem接口, 如果有则进行升版操作
            MJSONObject object = Context.Instance.getCacheHelper().getSchema("Realizes." + design.getClassDefUid() + ".ICIMRevisionItem");
            if (object == null) continue;

            // 获得次版本
            String minorRevision = revisionInfo.get("minorRevision");

            // 获得主版本
            String majorRevision = revisionInfo.get("majorRevision");

            // 升版状态
            String revState = CIMRevisionStatus.NEW.getCode();

            // 操作状态
            String operationState = DesignObjOperateStatus.CREATE.getCode();

            // 判断此设计数据是否已经存在
            IObject designObj = documentQueryHelper.getDesignByClassDefAndUid(design.getUid(), design.getClassDefUid());
            if (designObj != null) {
                // 比对上个版本对象是不是已经被删除, 是的话 则表示新增
                ICIMRevisionItem icimRevisionItem = designObj.toInterface(ICIMRevisionItem.class);
                if (null != icimRevisionItem.getCIMRevisionItemRevState() && !CIMRevisionStatus.SUPERSEDED.getCode().equalsIgnoreCase(icimRevisionItem.getCIMRevisionItemRevState())) {
                    if (checkObjectSameAsOtherObj(designObj, design)) {
                        revState = CIMRevisionStatus.CURRENT.getCode();
                        operationState = DesignObjOperateStatus.NONE.getCode();
                    } else {
                        revState = CIMRevisionStatus.REVISED.getCode();
                        operationState = DesignObjOperateStatus.UPDATE.getCode();
                    }

                }
            }

            // 设置升版属性
            designAddRevisionItem(design, majorRevision, minorRevision, revState, operationState);
        }
    }

    /**
     * 判断设计数据是否有属性变化
     * @param designObj
     * @param newDesign
     * @return
     */
    private boolean checkObjectSameAsOtherObj(IObject designObj, LoadClassDefStruct newDesign) {
        for (LoadInterfaceStruct interfaceStruct : newDesign.getInterfaceStructs()) {
            for (LoadPropertyStruct propertyStruct : interfaceStruct.getPropertyStructs()) {
                Object value = designObj.getLatestValue(interfaceStruct.getInterfaceDefUid(), propertyStruct.getPropertyDefUid());
                if (!value.equals(propertyStruct.getValue())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 设计数据添加标段信息
     * @param design
     */
    private void designAddBidSection(LoadClassDefStruct design) {
        // 获得设计数据的施工区域
        String cwa = design.getPropertyMap().get(CWAContext.PROPERTY_CWA);
        if (StringUtils.isEmpty(cwa)) return;

        // 根据施工区域获得标段
        ICIMCCMBidSection bidSection = this.getBidSectionByCWA(cwa);
        if (bidSection == null) return;

        // 添加接口
        List<LoadPropertyStruct> loadPropertyStructs = new ArrayList<>();
        loadPropertyStructs.add(new LoadPropertyStruct(ToBidSectionContext.PROPERTY_TO_BID_SECTION_UID, bidSection.getUid()));
        loadPropertyStructs.add(new LoadPropertyStruct(ToBidSectionContext.PROPERTY_TO_BID_SECTION_NAME, bidSection.getName()));
        LoadInterfaceStruct loadInterfaceStruct = new LoadInterfaceStruct();
        loadInterfaceStruct.setInterfaceDefUid(ToBidSectionContext.INTERFACE_CIM_CCM_TO_BID_SECTION);
        loadInterfaceStruct.setPropertyStructs(loadPropertyStructs);
        design.addInterfaceStruct(loadInterfaceStruct);

        // 添加属性
        design.getPropertyMap().put(ToBidSectionContext.PROPERTY_TO_BID_SECTION_UID, bidSection.getUid());
        design.getPropertyMap().put(ToBidSectionContext.PROPERTY_TO_BID_SECTION_NAME, bidSection.getName());
    }

    /**
     * 图纸升版操作
     * @param document
     * @param username
     * @param objectCollection
     */
    private Map<String, String> documentRevision(LoadClassDefStruct document, List<LoadClassDefStruct> designs, String username, ObjectCollection objectCollection) throws Exception {

        // 获得图纸数据
        IObject documentObj = documentQueryHelper.getDocumentByUID(document.getUid());

        // 获取RevisionScheme
        IObject revisionSchemaObj = documentQueryHelper.getRevisionSchemeByUid(DocumentCommon.VERSION_SCHEMA_UID);
        ICIMRevisionScheme revisionScheme = revisionSchemaObj.toInterface(ICIMRevisionScheme.class);

        // 获得次版本
        String minorRevision = revisionScheme.getFirstRevisionValue(false);

        // 获得主版本
        String majorRevision = revisionScheme.getFirstRevisionValue(true);

        // 升版状态
        String revState = CIMRevisionStatus.CURRENT.getCode();

        // 如果有此图纸则更新版本
        if (documentObj != null) {
            ICIMRevisionItem revisionItem = documentObj.toInterface(ICIMRevisionItem.class);
            if (revisionItem != null) {
                majorRevision = revisionItem.getCIMRevisionItemMajorRevision();
                minorRevision = revisionScheme.getNextMinorRevisionValue(revisionItem.getCIMRevisionItemMinorRevision());
            }

            // 设置当前版本过期
            ICIMDocumentMaster documentMaster = documentObj.toInterface(ICIMDocumentMaster.class);
            ICIMDocumentRevision newestRevision = documentMaster.getNewestRevision();
            if (newestRevision != null) {
                newestRevision.BeginUpdate(objectCollection);
                newestRevision.setRevState(RevStatus.ELT_RSSuperseded);
                newestRevision.FinishUpdate(objectCollection);
                // TODO 设计数据升版操作
                // 获得老版的设计数据UID集合
                IRelCollection relatedDesignObjs = documentMaster.getEnd1Relationships().getRels(REL_DOCUMENT_TO_DESIGN_OBJ, false);
                ICIMRevisionCollections revisionCollections = newestRevision.toInterface(ICIMRevisionCollections.class);
                if (revisionCollections == null) throw new Exception("Revision转换为ICIMRevisionCollections时失败!");
                // 更新设计数据版本状态
                revisionCollections.updateHasDeletedDesignObjStatusByNewDesignObjUIDs(designs, relatedDesignObjs, objectCollection);
            }

            revState = CIMRevisionStatus.REVISED.getCode();
        } else {

        }
        // 更新文档升版状态
        documentAddRevisionItem(document, majorRevision, minorRevision, revState);

        Map<String, String> revisionInfo = new HashMap<>();
        revisionInfo.put("majorRevision", majorRevision);
        revisionInfo.put("minorRevision", minorRevision);
        return revisionInfo;
    }

    /**
     * 获得施工标段信息
     * @param cwa
     * @return
     */
    private ICIMCCMBidSection getBidSectionByCWA(String cwa) {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addClassDefForQuery(BidSectionContext.CLASS_CIM_CCM_BID_SECTION_CWA);
        queryRequest.addQueryCriteria(null, PropertyDefinitions.description.name(), SqlKeyword.EQ, cwa);
        List<IObject> bidSectionCWAs = Context.Instance.getQueryHelper().query(queryRequest, IObject.class);
        if (bidSectionCWAs.isEmpty()) return null;
        IObject cwaObj = bidSectionCWAs.get(0);

        IRel rel = cwaObj.getEnd2Relationships().getRel(BidSectionContext.REL_BID_SECTION_TO_CWA, false);
        return Context.Instance.getQueryHelper().getObjectByUidAndDefinitionUid(rel.getUid1(), BidSectionContext.CLASS_CIM_CCM_BID_SECTION, ICIMCCMBidSection.class);
    }

    /**
     * 文档添加升版属性
     * @param document
     */
    private void documentAddRevisionItem(LoadClassDefStruct document, String majorRevision, String minorRevision, String revState) {
        // 添加接口
        List<LoadPropertyStruct> loadPropertyStructs = new ArrayList<>();
        loadPropertyStructs.add(new LoadPropertyStruct(IMCDocumentContext.REVISION_ITEM_MAJOR_REVISION, majorRevision));
        loadPropertyStructs.add(new LoadPropertyStruct(IMCDocumentContext.REVISION_ITEM_MINOR_REVISION, minorRevision));
        loadPropertyStructs.add(new LoadPropertyStruct(IMCDocumentContext.REVISION_ITEM_REV_STATE, revState));
        LoadInterfaceStruct loadInterfaceStruct = new LoadInterfaceStruct();
        loadInterfaceStruct.setInterfaceDefUid(IMCDocumentContext.INTERFACE_CIM_REVISION_ITEM);
        loadInterfaceStruct.setPropertyStructs(loadPropertyStructs);
        document.addInterfaceStruct(loadInterfaceStruct);

        // 添加属性
        document.getPropertyMap().put(IMCDocumentContext.REVISION_ITEM_MAJOR_REVISION, majorRevision);
        document.getPropertyMap().put(IMCDocumentContext.REVISION_ITEM_MINOR_REVISION, minorRevision);
        document.getPropertyMap().put(IMCDocumentContext.REVISION_ITEM_REV_STATE, revState);
    }

    /**
     * 设计数据添加升版属性
     */
    private void designAddRevisionItem(LoadClassDefStruct design, String majorRevision, String minorRevision, String revState, String operationState) {
        // 添加接口
        List<LoadPropertyStruct> loadPropertyStructs = new ArrayList<>();
        loadPropertyStructs.add(new LoadPropertyStruct(IMCDocumentContext.REVISION_ITEM_MAJOR_REVISION, majorRevision));
        loadPropertyStructs.add(new LoadPropertyStruct(IMCDocumentContext.REVISION_ITEM_MINOR_REVISION, minorRevision));
        loadPropertyStructs.add(new LoadPropertyStruct(IMCDocumentContext.REVISION_ITEM_REV_STATE, revState));
        loadPropertyStructs.add(new LoadPropertyStruct(IMCDocumentContext.REVISION_ITEM_OPERATION_STATE, operationState));
        LoadInterfaceStruct loadInterfaceStruct = new LoadInterfaceStruct();
        loadInterfaceStruct.setInterfaceDefUid(IMCDocumentContext.INTERFACE_CIM_REVISION_ITEM);
        loadInterfaceStruct.setPropertyStructs(loadPropertyStructs);
        design.addInterfaceStruct(loadInterfaceStruct);

        // 添加属性
        design.getPropertyMap().put(IMCDocumentContext.REVISION_ITEM_MAJOR_REVISION, majorRevision);
        design.getPropertyMap().put(IMCDocumentContext.REVISION_ITEM_MINOR_REVISION, minorRevision);
        design.getPropertyMap().put(IMCDocumentContext.REVISION_ITEM_REV_STATE, revState);
        design.getPropertyMap().put(IMCDocumentContext.REVISION_ITEM_OPERATION_STATE, operationState);
    }
}
