package com.imc.schema.interfaces;

import com.imc.framework.collections.IObjectCollection;
import com.imc.framework.collections.IRelCollection;
import com.imc.framework.collections.impl.ObjectCollection;
import com.imc.framework.entity.loader.struct.LoadClassDefStruct;

import java.util.List;

/**
 * 版本管理对象集合接口
 */
public interface ICIMRevisionCollections extends IObject {
    //Weld的UID集合
    String[] getCIMWeldUIDs() throws Exception;

    void setCIMWeldUIDs(String... CIMWeldUIDs) throws Exception;

    //Support的UID集合
    String[] getCIMSupportUIDs() throws Exception;

    void setCIMSupportUIDs(String... CIMSupportUIDs) throws Exception;

    //Spool的UID集合
    String[] getCIMSpoolUIDs() throws Exception;

    void setCIMSpoolUIDs(String... CIMSpoolUIDs) throws Exception;

    //PipeComponent的UID集合
    String[] getCIMPipeComponentUIDs() throws Exception;

    void setCIMPipeComponentUIDs(String... CIMPipeComponentUIDs) throws Exception;

    //Pipe对象的uid集合
    String[] getCIMPipeUIDs() throws Exception;

    void setCIMPipeUIDs(String... CIMPipeUIDs) throws Exception;

    //PipeLine对象的UID集合
    String[] getCCMPipeLineUIDs() throws Exception;

    void setCCMPipeLineUIDs(String... CCMPipeLineUIDs) throws Exception;

    //Bolt对象的uid集合
    String[] getCCMBoltUIDs() throws Exception;

    void setCCMBoltUIDs(String... CCMBoltUIDs) throws Exception;

    //Gasket对象的uid集合
    String[] getCCMGasketUIDs() throws Exception;

    void setCCMGasketUIDs(String... CCMGasketUIDs) throws Exception;

    //Equip对象的uid集合
    String[] getCCMEquipUIDs() throws Exception;

    void setCCMEquipUIDs(String... CCMEquipUIDs) throws Exception;

    // SubEquip对象的uid集合
    String[] getCCMSubEquipUIDs() throws Exception;

    void setCCMSubEquipUIDs(String... CCMSubEquipUIDs) throws Exception;

    //CableTray对象的uid集合
    String[] getCCMCableTrayUIDs() throws Exception;

    void setCCMCableTrayUIDs(String... CCMCableTrayUIDs) throws Exception;

    //CableTrayComponent对象的uid集合
    String[] getCCMCableTrayComponentUIDs() throws Exception;

    void setCCMCableTrayComponentUIDs(String... CCMCableTrayComponentUIDs) throws Exception;

    //Cable对象的uid集合
    String[] getCCMCableUIDs() throws Exception;

    void setCCMCableUIDs(String... CCMCableUIDs) throws Exception;

    //Instrument对象的uid集合
    String[] getCCMInstrumentUIDs() throws Exception;

    void setCCMInstrumentUIDs(String... CCMInstrumentUIDs) throws Exception;

    //DuctLine对象的uid集合
    String[] getCCMDuctLineUIDs() throws Exception;

    void setCCMDuctLineUIDs(String... CCMDuctLineUIDs) throws Exception;

    //DuctComponent对象的uid集合
    String[] getCCMDuctComponentUIDs() throws Exception;

    void setCCMDuctComponentUIDs(String... CCMDuctComponentUIDs) throws Exception;

    //JunctionBox对象的uid集合
    String[] getCCMJunctionBoxUIDs() throws Exception;

    void setCCMJunctionBoxUIDs(String... CCMJunctionBoxUIDs) throws Exception;

    //STPart对象的uid集合
    String[] getCCMSTPartUIDs() throws Exception;

    void setCCMSTPartUIDs(String... CCMSTPartUIDs) throws Exception;

    //STComponent对象的uid集合
    String[] getCCMSTComponentUIDs() throws Exception;

    void setCCMSTComponentUIDs(String... CCMSTComponentUIDs) throws Exception;

    //STBlock对象的uid集合
    String[] getCCMSTBlockUIDs() throws Exception;

    void setCCMSTBlockUIDs(String... CCMSTBlockUIDs) throws Exception;

    /**
     * 图纸下设计数据升版
     * @param designs
     * @param docMaterRelatedDesigns
     * @return
     * @throws Exception
     */
    void updateHasDeletedDesignObjStatusByNewDesignObjUIDs(List<LoadClassDefStruct> designs, IRelCollection docMaterRelatedDesigns, ObjectCollection objectCollection) throws Exception;

    /**
     * 设置属性
     * @param docsDesignObjs
     * @throws Exception
     */
    void setDesignObjUIDs(List<LoadClassDefStruct> docsDesignObjs) throws Exception;
}
