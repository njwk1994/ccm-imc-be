package com.ccm.packagemanage.helpers;

import com.ccm.modules.packagemanage.enums.PackageType;
import com.imc.framework.helpers.query.IQueryHelper;
import com.imc.schema.interfaces.*;

import java.util.List;

public interface IPackageQueryHelper extends IQueryHelper {
    /**
     * 根据任务包UID获得任务包信息
     * @param uid
     * @return
     */
    ICIMCCMTaskPackage getTaskPackageByUid(String uid);

    /**
     * 根据任务包名称获取任务包
     * @param name
     * @return
     */
    ICIMCCMTaskPackage getTaskPackageByName(String name);

    /**
     * 根据工作包uid获得工作包
     * @param uid
     * @return
     */
    ICIMCCMWorkPackage getWorkPackageByUid(String uid);

    /**
     * 根据工作包名称琥珀的工作包
     * @param name
     * @return
     */
    ICIMCCMWorkPackage getWorkPackageByName(String name);

    /**
     * 根据试压包UID获得试压包
     * @param uid
     * @return
     */
    ICIMCCMPressureTestPackage getPressureTestPackageByUid(String uid);

    /**
     * 获得未关联到当前任务包并且符合当前任务包施工阶段和施工区域的图纸
     * @param uid
     * @param purpose
     * @param cwa
     * @return
     */
    List<ICIMDocumentMaster> getDocumentsByTaskPackageUidAndPurposeAndCWAAndScheduleUID(String uid, String purpose, String cwa, String scheduleUID);

    /**
     * 根据id列表获得图纸
     * @param uids
     * @return
     */
    List<ICIMDocumentMaster> getDocumentsByUIDs(List<String> uids);

    /**
     * 根据UID获得图纸
     * @param uid
     * @return
     */
    ICIMDocumentMaster getDocumentByUID(String uid);

    /**
     * 根据名称集合获得加设图纸
     * @param designPhase
     * @param docNames
     * @return
     */
    List<ICIMDocumentMaster> getDocumentsByDesignPhaseAndDocNames(String designPhase, List<String> docNames);

    /**
     * 根据名称集合、设计类型和状态获得加设图纸
     * @param designPhase
     * @param docState
     * @param docNames
     * @return
     */
    List<ICIMDocumentMaster> getDocumentsByDesignPhaseAndDocStateAndDocNames(String designPhase, String docState, String docNames);

    /**
     * 获得设计数据下的工作步骤
     * @param designUID
     * @param ropWorkStepPhase
     * @param needConsumeMaterial
     * @return
     */
    List<ICIMWorkStep> getWorkStepsByDesignUidAndRopWorkStepPhaseAndConsumeMaterial(String designUID, PackageType packageType, String ropWorkStepPhase, boolean needConsumeMaterial);

    /**
     * 获得设计数据下的所有工作步骤
     * @param designUID
     * @return
     */
    List<ICIMWorkStep> getWorkStepsByDesignUID(String designUID);

    /**
     * 根据设计数据UID和试压包UID获得对应的工作步骤
     * @param pressureTestPackageUID
     * @param designUID
     * @return
     */
    List<ICIMWorkStep> getWorkStepsByDesignUIDAndPressureTestPackage(String pressureTestPackageUID, String designUID);

    /**
     * 根据UID获得工作步骤
     * @param uid
     * @return
     */
    ICIMWorkStep getWorkStepByUID(String uid);

    /**
     * 根据id获得优先级
     * @param uid
     * @return
     */
    ICIMCCMPriority getPriorityByUid(String uid);

    /**
     * 获得所有施工分类
     * @return
     */
    List<ICIMCCMConstructionType> getAllConstructionTypes();

    /**
     * 根据工作包UID和图纸ID范围筛选出对应的设计图纸
     * @param workPackageUID
     * @param documentUIDs
     * @return
     */
    List<ICIMCCMDesignObj> getDesignsByWorkPackageUidAndDocumentUIDs(String workPackageUID, List<String> documentUIDs);

    /**
     * 根据施工阶段和施工区域获得对应的设计数据
     * @param purpose
     * @param cwa
     * @return
     */
    List<ICIMCCMDesignObj> getDesignsByPurposeAndCWA(String purpose, String cwa);

    /**
     * 根据工作包施工阶段和施工区域获得对应的设计数据
     * @param wpPurpose
     * @param cwa
     * @return
     */
    List<ICIMCCMDesignObj> getDesignsByWPPurposeAndCWA(String wpPurpose, String cwa);

    /**
     * 根据文档uid列表和施工区域筛选出对应的设计数据
     * @param documentUIDs 文档id列表
     * @param cwa 施工区域
     * @return
     */
    List<ICIMCCMDesignObj> getDesignsByDocumentUIDsAndCWA(List<String> documentUIDs, String cwa);

    /**
     * 获得文档下所有对用施工区域的设计数据
     * @param documentUID
     * @param cwa
     * @return
     */
    List<ICIMCCMDesignObj> getDesignsByDocumentUIDAndCWA(String documentUID, String cwa);

    /**
     * 根据任务包UID和图纸UID获得设计数据
     * @param taskPackageUID
     * @param documentUID
     * @return
     */
    List<ICIMCCMDesignObj> getDesignsByTaskPackageUIDAndDocumentUID(String taskPackageUID, String documentUID);

    /**
     * 根据试压包UID和图纸UID获得设计数据
     * @param pressureTestPackageUID
     * @param documentUID
     * @return
     */
    List<ICIMCCMDesignObj> getDesignsByPressureTestPackageUIDAndDocumentUID(String pressureTestPackageUID, String documentUID);

    /**
     * 获得图纸下所有的设计数据
     * @param documentUID
     * @param classDef
     * @return
     */
    List<ICIMCCMDesignObj> getDesignsByDocumentUIDAndClassDef(String documentUID, String classDef);

    /**
     * 获得当前图纸下未关联
     * @param documentUID
     * @param uid
     * @param designUIDS
     * @return
     */
    List<ICIMCCMDesignObj> getDesignsByDocumentUIDAndNotRelPressureTestPackageUID(String documentUID, String uid, List<String> designUIDS) throws Exception;

    /**
     * 根据UID获得设计数据
     * @param uid
     * @return
     */
    ICIMCCMDesignObj getDesignByUid(String uid);

    /**
     * 获得所有的材料规格数据
     * @return
     */
    List<ICIMCCMPTPackageMaterialSpecification> getMaterialSpecifications();

    /**
     * 根据UID集合获得材料模板信息
     * @return
     */
    List<IObject> getPTPackageMaterialTemplatesByUIDs(List<String> uids);

    /**
     * 根据UID获得材料信息
     * @param uid
     * @return
     */
    ICIMCCMPTPackageMaterial getPTPackageMaterialByUID(String uid);
}
