//package com.ccm.packagemanage.service.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.ccm.packagemanage.domain.ConstructionTypesQueryParamDTO;
//import com.ccm.modules.packagemanage.enums.PackageType;
//import com.ccm.packagemanage.service.IPackageService;
//import com.imc.common.core.model.frame.MJSONObject;
//import com.imc.framework.collections.impl.ObjectCollection;
//import com.imc.framework.context.Context;
//import com.imc.framework.utils.SchemaUtil;
//import com.imc.schema.interfaces.IObject;
//import com.imc.schema.interfaces.IRel;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import static com.ccm.modules.COMContext.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class PackageServiceImplTest {
//    @Autowired
//    IPackageService packageService;
//
//    @Test
//    public void insertPriority() throws Exception {
//        ObjectCollection objectCollection = new ObjectCollection("superuser");
//        IObject object = SchemaUtil.newIObject(CLASS_CIM_CCM_PRIORITY, "尺寸优先", "尺寸优先", "尺寸优先");
//        object.finishCreate(objectCollection);
//        objectCollection.commit();
//    }
//
//    @Test
//    public void insertCIMCCMPipe() throws Exception {
//        ObjectCollection objectCollection = new ObjectCollection("superuser");
//        IObject object = SchemaUtil.newIObject("CIMCCMPipe", "尺寸优先", "尺寸优先", "尺寸优先");
//        object.finishCreate(objectCollection);
//
//        // 插入关联关系
//        IObject doc = Context.Instance.getQueryHelper().getObjectByUidAndDefinitionUid("DA", CLASS_CIM_CCM_DOCUMENT_MASTER, IObject.class);
//        IRel relationship = SchemaUtil.newRelationship(REL_DOCUMENT_TO_DESIGN_OBJ, doc, object);
//        relationship.finishCreate(objectCollection);
//        objectCollection.commit();
//    }
//
//    /**
//     * 测试获取材料类型
//     * @throws Exception
//     */
//    @Test
//    public void testSelectPackageConstructionTypes() throws Exception {
//        ConstructionTypesQueryParamDTO constructionTypesQueryParamDTO = new ConstructionTypesQueryParamDTO();
//        constructionTypesQueryParamDTO.setUid("1706508496247050241");
//        constructionTypesQueryParamDTO.setNeedConsumeMaterial(true);
//        constructionTypesQueryParamDTO.setPackageType(PackageType.TP);
//        Object oo = packageService.selectPackageConstructionTypes(constructionTypesQueryParamDTO);
//        System.out.println(JSON.toJSONString(oo));
//    }
//
//    @Test
//    public void testGetPTPMaterialTemplatesByPTPackage() {
//        MJSONObject mjsonObject = Context.Instance.getCacheHelper().getSchema("ELT_Discipline");
//        System.out.println(JSON.toJSONString(mjsonObject));
//    }
//}