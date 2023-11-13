package com.ccm.document.helpers.impl;

import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.ccm.document.helpers.IDocumentQueryHelper;
import com.ccm.modules.documentmanage.CCMPipeSizeStandardContext;
import com.ccm.modules.documentmanage.CCMWeldCorrectionFactorContext;
import com.ccm.modules.documentmanage.IMCDocumentContext;
import com.ccm.modules.packagemanage.WorkPackageContext;
import com.ccm.modules.packagemanage.enums.DesignPhaseEnum;
import com.ccm.modules.packagemanage.enums.DocStateEnum;
import com.imc.common.core.exception.ServiceException;
import com.imc.framework.helpers.query.impl.QueryHelper;
import com.imc.framework.model.qe.QueryRequest;
import com.imc.schema.interfaces.ICIMDocumentMaster;
import com.imc.schema.interfaces.IObject;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.ccm.modules.COMContext.CLASS_CIM_CCM_DOCUMENT_MASTER;

/**
 * @description：TODO
 * @author： kekai.huang
 * @create： 2023/10/31 13:50
 */
@Service
public class DocumentQueryHelperImpl extends QueryHelper implements IDocumentQueryHelper {
    @Override
    public IObject getDocumentByUID(String uid) {
        return this.getObjectByUidAndDefinitionUid(uid, CLASS_CIM_CCM_DOCUMENT_MASTER, IObject.class);
    }

    @Override
    public IObject getDesignByClassDefAndUid(String uid, String clazz) {
        return this.getObjectByUidAndDefinitionUid(uid, clazz, IObject.class);
    }

    @Override
    public List<IObject> getDocumentsByDesignPhase(String designPhase) {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addClassDefForQuery(CLASS_CIM_CCM_DOCUMENT_MASTER);
        queryRequest.addQueryCriteria(null, WorkPackageContext.PROPERTY_DOC_DESIGN_PHASE, SqlKeyword.EQ, getDesignPhase(designPhase));
        return this.query(queryRequest, IObject.class);
    }

    @Override
    public List<IObject> getIFCDocumentsByDesignPhase(String designPhase) {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addClassDefForQuery(CLASS_CIM_CCM_DOCUMENT_MASTER);
        queryRequest.addQueryCriteria(null, "CIMDocState", SqlKeyword.EQ, DocStateEnum.EN_IFC.getCode());
        queryRequest.addQueryCriteria(null, WorkPackageContext.PROPERTY_DOC_DESIGN_PHASE, SqlKeyword.EQ, getDesignPhase(designPhase));
        return this.query(queryRequest, IObject.class);
    }

    @Override
    public List<IObject> getDesignsByDesignPhase(String designPhase, String classDefinition) {
        return null;
    }

    @Override
    public String getODByDN(String dn) {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addClassDefForQuery(CCMPipeSizeStandardContext.CLASS_CIM_CCM_PIPE_SIZE_STANDARD);
        queryRequest.addQueryCriteria(null, CCMPipeSizeStandardContext.DN, SqlKeyword.EQ, dn);
        List<IObject> pipeStandards = this.query(queryRequest, IObject.class);
        if (pipeStandards.isEmpty()) {
            throw new ServiceException("根据DN(公称直径)查询外径(公制)失败!DN:" + dn);
        }
        return String.valueOf(pipeStandards.get(0).getLatestValue(CCMPipeSizeStandardContext.INTERFACE_CIM_CCM_PIPE_SIZE_STANDARD, CCMPipeSizeStandardContext.OD));
    }

    @Override
    public String getCorrectionFactor1BySize2(String size2) {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addClassDefForQuery(CCMWeldCorrectionFactorContext.CLASS_CIM_CCM_WELD_CORRECTION_FACTOR);
        List<IObject> weldCorrectionFactors = this.query(queryRequest, IObject.class);
        sortWeldCorrectionFactors(weldCorrectionFactors);
        BigDecimal size2BigDecimal = new BigDecimal(size2);
        BigDecimal zero = new BigDecimal("0");

        IObject result = null;
        for (IObject sortedWeldCorrectionFactor : weldCorrectionFactors) {
            BigDecimal thickness = new BigDecimal(String.valueOf(sortedWeldCorrectionFactor.getLatestValue(CCMWeldCorrectionFactorContext.INTERFACE_CIM_CCM_WELD_CORRECTION_FACTOR,CCMWeldCorrectionFactorContext.THICKNESS)));
            BigDecimal sub = NumberUtil.sub(thickness, size2BigDecimal);
            if (0 <= sub.compareTo(zero)) {
                result = sortedWeldCorrectionFactor;
                break;
            }
        }
        if (result == null) {
            throw new ServiceException("未找到壁厚>=" + size2 + "的修正系数1数据!");
        }

        return String.valueOf(result.getLatestValue(CCMWeldCorrectionFactorContext.INTERFACE_CIM_CCM_WELD_CORRECTION_FACTOR, CCMWeldCorrectionFactorContext.CORRECTION_FACTOR_1));
    }

    @Override
    public String getCorrectionFactor2(String weldType, String materialCategory, String size2) throws Exception {
        IObject matchedWeldCorrectionFactor = getMatchedWeldCorrectionFactor(weldType, materialCategory, size2);
        if (matchedWeldCorrectionFactor == null) {
            throw new ServiceException("未找到材料类别为" + materialCategory + ",壁厚>=" + size2 + "的修正系数2数据!");
        }
        return String.valueOf(matchedWeldCorrectionFactor.getLatestValue(CCMWeldCorrectionFactorContext.INTERFACE_CIM_CCM_WELD_CORRECTION_FACTOR, CCMWeldCorrectionFactorContext.CORRECTION_FACTOR_2));
    }

    @Override
    public IObject getRevisionSchemeByUid(String uid) {
        return this.getObjectByUidAndDefinitionUid(uid, IMCDocumentContext.REVISION_SCHEMA, IObject.class);
    }

    private IObject getMatchedWeldCorrectionFactor(String weldType, String materialCategory, String size2) throws Exception {
        IObject result = null;
        BigDecimal size2BigDecimal = new BigDecimal(size2);
        BigDecimal zero = new BigDecimal("0");
        List<IObject> sortedWeldCorrectionFactors = getSortedWeldCorrectionFactors(weldType, materialCategory);
        for (IObject sortedWeldCorrectionFactor : sortedWeldCorrectionFactors) {
            BigDecimal thickness = new BigDecimal(String.valueOf(sortedWeldCorrectionFactor.getLatestValue(CCMWeldCorrectionFactorContext.INTERFACE_CIM_CCM_WELD_CORRECTION_FACTOR, CCMWeldCorrectionFactorContext.THICKNESS)));
            BigDecimal sub = NumberUtil.sub(thickness, size2BigDecimal);
            if (0 <= sub.compareTo(zero)) {
                result = sortedWeldCorrectionFactor;
                break;
            }
        }
        return result;
    }

    private List<IObject> getSortedWeldCorrectionFactors(String weldType, String materialCategory) throws Exception {
//        IObject materialCategoryEnumObj = CIMContext.Instance.ProcessCache().itemByName(materialCategory, classDefinitionType.EnumEnum.name());

        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addClassDefForQuery(CCMWeldCorrectionFactorContext.CLASS_CIM_CCM_WELD_CORRECTION_FACTOR);
        // 2022.08.12 HT 先去除查询修正系数用到的weldType条件
        //correctionFactor1Engine.addPropertyForQuery(correctionFactor1Request, "", WELD_TYPE, operator.equal, weldType);
        queryRequest.addQueryCriteria(null, "MaterialCategory", SqlKeyword.EQ, materialCategory);
        List<IObject> weldCorrectionFactors = this.query(queryRequest, IObject.class);
        sortWeldCorrectionFactors(weldCorrectionFactors);
        return weldCorrectionFactors;
    }

    private void sortWeldCorrectionFactors(List<IObject> weldCorrectionFactors) {
        if (weldCorrectionFactors.size() > 1) {
            weldCorrectionFactors.sort((o1, o2) -> {
                String thickness1 = String.valueOf(o1.getLatestValue(CCMWeldCorrectionFactorContext.INTERFACE_CIM_CCM_WELD_CORRECTION_FACTOR, CCMWeldCorrectionFactorContext.THICKNESS));
                String thickness2 = String.valueOf(o2.getLatestValue(CCMWeldCorrectionFactorContext.INTERFACE_CIM_CCM_WELD_CORRECTION_FACTOR, CCMWeldCorrectionFactorContext.THICKNESS));
                BigDecimal thickness1BigDecimal = new BigDecimal(thickness1);
                BigDecimal thickness2BigDecimal = new BigDecimal(thickness2);
                return Double.compare(thickness1BigDecimal.doubleValue(), thickness2BigDecimal.doubleValue());
            });
        }
    }

    private String getDesignPhase(String designPhase) {
        switch (designPhase) {
            case "DetailDesignTemplate":
                return DesignPhaseEnum.EN_DETAIL_DESIGN.getCode();
            case "ShopDesignTemplate":
                return DesignPhaseEnum.EN_SHOP_DESIGN.getCode();
            default:
                return null;
        }
    }
}
