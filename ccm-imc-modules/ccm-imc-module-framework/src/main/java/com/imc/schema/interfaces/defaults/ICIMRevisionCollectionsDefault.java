package com.imc.schema.interfaces.defaults;


import com.ccm.modules.documentmanage.CCMDesignContext;
import com.imc.framework.collections.IObjectCollection;
import com.imc.framework.collections.IRelCollection;
import com.imc.framework.collections.impl.ObjectCollection;
import com.imc.framework.context.Context;
import com.imc.framework.entity.loader.struct.LoadClassDefStruct;
import com.imc.schema.interfaces.ICIMRevisionItem;
import com.imc.schema.interfaces.IObject;
import com.imc.schema.interfaces.bases.ICIMRevisionCollectionsBase;

import java.util.*;
import java.util.stream.Collectors;

public class ICIMRevisionCollectionsDefault extends ICIMRevisionCollectionsBase {
    public ICIMRevisionCollectionsDefault(boolean instantiateRequiredProperties) {
        super(instantiateRequiredProperties);
    }

    @Override
    public void updateHasDeletedDesignObjStatusByNewDesignObjUIDs(List<LoadClassDefStruct> designs, IRelCollection docMaterRelatedDesigns, ObjectCollection objectCollection) throws Exception {
        if (designs.isEmpty()) return;
        //按照类型分组 这里面可能已经存在有的类型已经被删了,没有了,所以要额外判断类型
        Map<String, List<LoadClassDefStruct>> groupByClassDef = designs.stream().collect(Collectors.groupingBy(LoadClassDefStruct::getClassDefUid));
        Map<String, List<String>> ldicDeletedDesignObjs = new HashMap<>();
        getHasDeletedDesignObjects(groupByClassDef, ldicDeletedDesignObjs, CCMDesignContext.CLASS_CIM_CCM_PIPE, this.getCIMPipeUIDs());
        getHasDeletedDesignObjects(groupByClassDef, ldicDeletedDesignObjs, CCMDesignContext.CLASS_CIM_CCM_PIPE_COMPONENT, this.getCIMPipeComponentUIDs());
        getHasDeletedDesignObjects(groupByClassDef, ldicDeletedDesignObjs, CCMDesignContext.CLASS_CIM_CCM_SPOOL, this.getCIMSpoolUIDs());
        getHasDeletedDesignObjects(groupByClassDef, ldicDeletedDesignObjs, CCMDesignContext.CLASS_CIM_CCM_WELD, this.getCIMWeldUIDs());
        getHasDeletedDesignObjects(groupByClassDef, ldicDeletedDesignObjs, CCMDesignContext.CLASS_CIM_CCM_SUPPORT, this.getCIMSupportUIDs());
        getHasDeletedDesignObjects(groupByClassDef, ldicDeletedDesignObjs, CCMDesignContext.CLASS_CIM_CCM_BOLT, this.getCCMBoltUIDs());
        getHasDeletedDesignObjects(groupByClassDef, ldicDeletedDesignObjs, CCMDesignContext.CLASS_CIM_CCM_GASKET, this.getCCMGasketUIDs());
        getHasDeletedDesignObjects(groupByClassDef, ldicDeletedDesignObjs, CCMDesignContext.CLASS_CIM_CCM_EQUIP, this.getCCMEquipUIDs());
        getHasDeletedDesignObjects(groupByClassDef, ldicDeletedDesignObjs, CCMDesignContext.CLASS_CIM_CCM_SUB_EQUIP, this.getCCMSubEquipUIDs());
        getHasDeletedDesignObjects(groupByClassDef, ldicDeletedDesignObjs, CCMDesignContext.CLASS_CIM_CCM_CABLE_TRAY, this.getCCMCableTrayUIDs());
        getHasDeletedDesignObjects(groupByClassDef, ldicDeletedDesignObjs, CCMDesignContext.CLASS_CIM_CCM_CABLE_TRAY_COMPONENT, this.getCCMCableTrayComponentUIDs());
        getHasDeletedDesignObjects(groupByClassDef, ldicDeletedDesignObjs, CCMDesignContext.CLASS_CIM_CCM_CABLE, this.getCCMCableUIDs());
        getHasDeletedDesignObjects(groupByClassDef, ldicDeletedDesignObjs, CCMDesignContext.CLASS_CIM_CCM_INSTRUMENT, this.getCCMInstrumentUIDs());
        getHasDeletedDesignObjects(groupByClassDef, ldicDeletedDesignObjs, CCMDesignContext.CLASS_CIM_CCM_JUNCTION_BOX, this.getCCMJunctionBoxUIDs());
        getHasDeletedDesignObjects(groupByClassDef, ldicDeletedDesignObjs, CCMDesignContext.CLASS_CIM_CCM_DUCT_LINE, this.getCCMDuctLineUIDs());
        getHasDeletedDesignObjects(groupByClassDef, ldicDeletedDesignObjs, CCMDesignContext.CLASS_CIM_CCM_DUCT_COMPONENT, this.getCCMDuctComponentUIDs());
        getHasDeletedDesignObjects(groupByClassDef, ldicDeletedDesignObjs, CCMDesignContext.CLASS_CIM_CCM_ST_PART, this.getCCMSTPartUIDs());
        getHasDeletedDesignObjects(groupByClassDef, ldicDeletedDesignObjs, CCMDesignContext.CLASS_CIM_CCM_ST_COMPONENT, this.getCCMSTComponentUIDs());
        getHasDeletedDesignObjects(groupByClassDef, ldicDeletedDesignObjs, CCMDesignContext.CLASS_CIM_CCM_ST_BLOCK, this.getCCMSTBlockUIDs());
        getHasDeletedDesignObjects(groupByClassDef, ldicDeletedDesignObjs, CCMDesignContext.CLASS_CIM_CCM_PIPE_LINE, this.getCCMPipeLineUIDs());
        if (ldicDeletedDesignObjs.isEmpty()) return;
        for (Map.Entry<String, List<String>> entry : ldicDeletedDesignObjs.entrySet()) {
            List<IObject> lcolHasDeleteDesignObjsFromDoc = Context.Instance.getQueryHelper().getObjectsByUIDsAndClassDefinitionUID(entry.getValue(), entry.getKey(), IObject.class);
            if (lcolHasDeleteDesignObjsFromDoc.isEmpty()) continue;
            for (IObject design : lcolHasDeleteDesignObjsFromDoc) {
                ICIMRevisionItem revisionItem = design.toInterface(ICIMRevisionItem.class);
                if (revisionItem == null) continue;
                //开启升版删除 ,变更状态
                revisionItem.setObjectDelete();
                // 2022.12.26 HT 添加施工系统ROP 工作步骤 和 包标记处理开关
//                if (DocumentUtils.constructionStatus) {
//                    log.info("配置文件开关状态:{}", DocumentUtils.constructionStatus);
//                    IROPExecutableItem iropExecutableItem = lobjDesignObject.toInterface(IROPExecutableItem.class);
//                    iropExecutableItem.updateWorkStepForDeletedDesignObj(false);
//                }
                // 2022.12.26 HT 添加施工系统ROP 工作步骤 和 包标记处理开关
            }

        }
    }

    @Override
    public void setDesignObjUIDs(List<LoadClassDefStruct> docsDesignObjs) throws Exception {
        if (docsDesignObjs.isEmpty()) return;
        Map<String, List<LoadClassDefStruct>> groupByClassDef = docsDesignObjs.stream().collect(Collectors.groupingBy(LoadClassDefStruct::getClassDefUid));
        for (Map.Entry<String, List<LoadClassDefStruct>> entry : groupByClassDef.entrySet()) {
            String lstrClassDef = entry.getKey();
            List<String> uids = entry.getValue().stream().map(LoadClassDefStruct::getUid).collect(Collectors.toList());
            if (lstrClassDef.equalsIgnoreCase(CCMDesignContext.CLASS_CIM_CCM_PIPE)) {
                this.setCIMPipeUIDs(uids.toArray(new String[]{}));
            } else if (lstrClassDef.equalsIgnoreCase(CCMDesignContext.CLASS_CIM_CCM_PIPE_COMPONENT)) {
                this.setCIMPipeComponentUIDs(uids.toArray(new String[]{}));
            } else if (lstrClassDef.equalsIgnoreCase(CCMDesignContext.CLASS_CIM_CCM_SPOOL)) {
                this.setCIMSpoolUIDs(uids.toArray(new String[]{}));
            } else if (lstrClassDef.equalsIgnoreCase(CCMDesignContext.CLASS_CIM_CCM_SUPPORT)) {
                this.setCIMSupportUIDs(uids.toArray(new String[]{}));
            } else if (lstrClassDef.equalsIgnoreCase(CCMDesignContext.CLASS_CIM_CCM_WELD)) {
                this.setCIMWeldUIDs(uids.toArray(new String[]{}));
            } else if (lstrClassDef.equalsIgnoreCase(CCMDesignContext.CLASS_CIM_CCM_PIPE_LINE)) {
                this.setCCMPipeLineUIDs(uids.toArray(new String[]{}));
            } else if (lstrClassDef.equalsIgnoreCase(CCMDesignContext.CLASS_CIM_CCM_BOLT)) {
                this.setCCMBoltUIDs(uids.toArray(new String[]{}));
            } else if (lstrClassDef.equalsIgnoreCase(CCMDesignContext.CLASS_CIM_CCM_GASKET)) {
                this.setCCMGasketUIDs(uids.toArray(new String[]{}));
            } else if (lstrClassDef.equalsIgnoreCase(CCMDesignContext.CLASS_CIM_CCM_EQUIP)) {
                this.setCCMEquipUIDs(uids.toArray(new String[]{}));
            } else if (lstrClassDef.equalsIgnoreCase(CCMDesignContext.CLASS_CIM_CCM_SUB_EQUIP)) {
                this.setCCMSubEquipUIDs(uids.toArray(new String[]{}));
            } else if (lstrClassDef.equalsIgnoreCase(CCMDesignContext.CLASS_CIM_CCM_CABLE)) {
                this.setCCMCableUIDs(uids.toArray(new String[]{}));
            } else if (lstrClassDef.equalsIgnoreCase(CCMDesignContext.CLASS_CIM_CCM_CABLE_TRAY)) {
                this.setCCMCableTrayUIDs(uids.toArray(new String[]{}));
            } else if (lstrClassDef.equalsIgnoreCase(CCMDesignContext.CLASS_CIM_CCM_CABLE_TRAY_COMPONENT)) {
                this.setCCMCableTrayComponentUIDs(uids.toArray(new String[]{}));
            } else if (lstrClassDef.equalsIgnoreCase(CCMDesignContext.CLASS_CIM_CCM_INSTRUMENT)) {
                this.setCCMInstrumentUIDs(uids.toArray(new String[]{}));
            } else if (lstrClassDef.equalsIgnoreCase(CCMDesignContext.CLASS_CIM_CCM_JUNCTION_BOX)) {
                this.setCCMJunctionBoxUIDs(uids.toArray(new String[]{}));
            } else if (lstrClassDef.equalsIgnoreCase(CCMDesignContext.CLASS_CIM_CCM_DUCT_LINE)) {
                this.setCCMDuctLineUIDs(uids.toArray(new String[]{}));
            } else if (lstrClassDef.equalsIgnoreCase(CCMDesignContext.CLASS_CIM_CCM_DUCT_COMPONENT)) {
                this.setCCMDuctComponentUIDs(uids.toArray(new String[]{}));
            } else if (lstrClassDef.equalsIgnoreCase(CCMDesignContext.CLASS_CIM_CCM_ST_PART)) {
                this.setCCMSTPartUIDs(uids.toArray(new String[]{}));
            } else if (lstrClassDef.equalsIgnoreCase(CCMDesignContext.CLASS_CIM_CCM_ST_COMPONENT)) {
                this.setCCMSTComponentUIDs(uids.toArray(new String[]{}));
            } else if (lstrClassDef.equalsIgnoreCase(CCMDesignContext.CLASS_CIM_CCM_ST_BLOCK)) {
                this.setCCMSTBlockUIDs(uids.toArray(new String[]{}));
            }
        }
    }

    private void getHasDeletedDesignObjects(Map<String, List<LoadClassDefStruct>> newDesignObjs, Map<String, List<String>> deletedDesignObjsContainer, String classDef, String[] oriDesignObjUIDs) {
        //如果新的对象中有该类型对象,判断哪些是删除的
        List<String> lcolHasDeletedUIDs = new ArrayList<>();
        if (newDesignObjs.containsKey(classDef)) {
            List<String> lcolNewDesignObjUIDs = newDesignObjs.get(classDef).stream().map(LoadClassDefStruct::getUid).collect(Collectors.toList());
            if (oriDesignObjUIDs != null && oriDesignObjUIDs.length > 0) {
                for (String lstrOriDesignObjUID : oriDesignObjUIDs) {
                    if (lcolNewDesignObjUIDs == null || lcolNewDesignObjUIDs.size() <= 0 || !lcolNewDesignObjUIDs.contains(lstrOriDesignObjUID)) {
                        lcolHasDeletedUIDs.add(lstrOriDesignObjUID);
                    }
                }
            }
        } else {
            //如果新的对象中没有该类型对象,说明该类型对象被全部删除了
            if (oriDesignObjUIDs != null && oriDesignObjUIDs.length > 0) {
                lcolHasDeletedUIDs.addAll(Arrays.asList(oriDesignObjUIDs));
            }
        }
        if (!lcolHasDeletedUIDs.isEmpty()) {
            deletedDesignObjsContainer.put(classDef, lcolHasDeletedUIDs);
        }
    }

}
