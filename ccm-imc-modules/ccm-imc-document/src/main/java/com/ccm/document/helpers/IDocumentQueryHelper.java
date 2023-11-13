package com.ccm.document.helpers;

import com.imc.schema.interfaces.IObject;

import java.util.List;

/**
 * @description：TODO
 * @author： kekai.huang
 * @create： 2023/10/31 13:50
 */
public interface IDocumentQueryHelper {
    /**
     * 根据UID获得图纸
     * @param uid
     * @return
     */
    IObject getDocumentByUID(String uid);

    /**
     * 根据UID和类定义获得设计数据队形
     * @param uid
     * @param clazz
     * @return
     */
    IObject getDesignByClassDefAndUid(String uid, String clazz);

    /**
     * 根据设计阶段获得图纸
     * @param designPhase
     * @return
     */
    List<IObject> getDocumentsByDesignPhase(String designPhase);

    /**
     * 获得文档类型为IFC的图纸
     * @return
     */
    List<IObject> getIFCDocumentsByDesignPhase(String designPhase);

    /**
     * 根据设计阶段获得设计数据
     * @param designPhase
     * @return
     */
    List<IObject> getDesignsByDesignPhase(String designPhase, String classDefinition);

    /**
     * 根据DN(公称直径)查询外径(公制)
     * @param dn size1 公称直径
     * @return
     */
    String getODByDN(String dn);

    /**
     * 修正系数1
     * @param size2 壁厚
     * @return
     */
    String getCorrectionFactor1BySize2(String size2);

    /**
     * 获取修正系数2
     * @param weldType          焊口形式
     * @param materialCategory  材料类别
     * @param size2             壁厚
     * @return
     */
    String getCorrectionFactor2(String weldType, String materialCategory, String size2) throws Exception;

    /**
     * 获得版本规则
     * @param uid
     * @return
     */
    IObject getRevisionSchemeByUid(String uid);
}
