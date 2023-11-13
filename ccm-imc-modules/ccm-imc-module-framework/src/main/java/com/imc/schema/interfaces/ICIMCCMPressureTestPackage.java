package com.imc.schema.interfaces;

import com.imc.schema.interfaces.IObject;

import java.util.List;

/**
 * 试压包
 */
public interface ICIMCCMPressureTestPackage extends IObject {

    //化学清洗
    Boolean getChemicalCleaning() throws Exception;

    void  setChemicalCleaning(Boolean ChemicalCleaning) throws Exception;
    //热油清洗
    Boolean getHotOilFlushing() throws Exception;

    void  setHotOilFlushing(Boolean HotOilFlushing) throws Exception;
    //状态
    String getTPTPStatus() throws Exception;

    void  setTPTPStatus(String TPTPStatus) throws Exception;

    /**
     * 获得试压包工作步骤
     * @return
     */
    List<ICIMWorkStep> getWorkSteps();

    /**
     * 获得试压包下的设计数据
     * @return
     */
    List<ICIMCCMDesignObj> getDesignOBJs();

    /**
     * 获得试压包下的设计图纸
     * @return
     */
    List<ICIMDocumentMaster> getDocuments();

    /**
     * 获得试压包材料信息
     * @return
     */
    List<ICIMCCMPTPackageMaterialTemplate> getPTPackageMaterials();

    /**
     * 设置包对象为升版状态
     */
    void setObjectRevised() throws Exception;
}
