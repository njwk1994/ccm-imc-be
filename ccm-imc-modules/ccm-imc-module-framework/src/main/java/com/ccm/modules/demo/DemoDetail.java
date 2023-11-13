package com.ccm.modules.demo;

import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.imc.common.core.enums.frame.PropertyDefinitions;
import com.imc.common.core.enums.frame.RelDirection;
import com.imc.common.core.enums.frame.Splitters;
import com.imc.common.core.model.result.ResultInfo;
import com.imc.framework.collections.impl.ObjectCollection;
import com.imc.framework.context.Context;
import com.imc.framework.model.qe.QueryRequest;
import com.imc.framework.utils.GeneralUtil;
import com.imc.framework.utils.SchemaUtil;
import com.imc.schema.interfaces.IObject;
import com.imc.schema.interfaces.IRel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author HuangTao
 * @version 1.0
 * @since 2023/4/3 17:50
 */
public class DemoDetail {

    public void demo() throws Exception {
        /*
         查询
         */
        // 1.根据 UID ClassDefUID ClassDef实现的对应接口class查询到相关数据
        IObject objectByUidAndDefinitionUid = Context.Instance.getQueryHelper().getObjectByUidAndDefinitionUid("UID", "ClassDefUID", IObject.class);
        // 2.根据 UID集合 ClassDefUID ClassDef实现的对应接口class批量查询到相关数据
        List<IObject> objectsByUIDsAndClassDefinitionUID = Context.Instance.getQueryHelper().getObjectsByUIDsAndClassDefinitionUID(new ArrayList<>(), "ClassDefUID", IObject.class);
        // 3.自定义条件查询
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addClassDefForQuery("ClassDefUID");
        // 3.1 根据关联关系查询条件演示1,ClassDefUID为二端时,添加一端对象属性条件
        queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + "RelDefUID", PropertyDefinitions.name1.toString(), SqlKeyword.EQ, "Not Empty Value");
        // 3.2 根据关联关系查询条件演示2,ClassDefUID为一端时,添加二端对象属性条件
        queryRequest.addQueryCriteria(RelDirection.From1To2.getPrefix() + "RelDefUID", PropertyDefinitions.name2.toString(), SqlKeyword.EQ, "Not Empty Value");
        // 3.3 模糊查询条件演示
        queryRequest.addQueryCriteria(null, PropertyDefinitions.name.toString(), SqlKeyword.LIKE, "*Not Empty Value*");
        // 3.4 IN/NOT IN 查询条件演示,可以使用String.join()方法使用Splitters.T_COMMA.getMsg()拼接集合
        ArrayList<String> objects = new ArrayList<>();
        objects.add("a");
        objects.add("b");
        objects.add("c");
        objects.add("d");
        queryRequest.addQueryCriteria(null, PropertyDefinitions.name.toString(), SqlKeyword.IN, String.join(Splitters.T_COMMA.getMsg(), objects));
        // 3.5 IS NULL/IS NOT NULL 查询条件演示
        queryRequest.addQueryCriteria(null, PropertyDefinitions.name.toString(), SqlKeyword.IS_NULL, null);
        // 3.6 自定义条件实际查询操作
        List<IObject> iObjects = Context.Instance.getQueryHelper().query(queryRequest, IObject.class);

        /*
         注意!!! 事务控制对象 所有增删改操作都需要放入ObjectCollection对象,并且在最终使用 commit() 方法提交事务进行对数据库数据的增删改
         */
        ObjectCollection objectCollection = new ObjectCollection(GeneralUtil.getUsername());
        /*
        新增
         */
        // 1.创建新对象
        IObject object = SchemaUtil.newIObject("ClassDefUID", "name", "description", "displayName");
        // 1.1 属性值填充
        // 1.1.1 属性填充方法1 通过 接口UID 属性UID 属性值 的Map进行属性填充
        Map<String, Map<String, String>> interfaceProperties = new HashMap<String, Map<String, String>>();
        Map<String, String> properties = new HashMap<>();
        properties.put("propertyUID1", "value");
        properties.put("propertyUID2", "value");
        properties.put("propertyUID3", "value");
        interfaceProperties.put("InterfaceDefUID", properties);
        object.setProperties(interfaceProperties);
        // 1.1.2 属性填充方法2 转换为对应接口填充对应接口绑定的属性 IObject只是演示用才作为toInterface的class,实际需要替换为正确的class
        IObject interfaceObj = object.toInterface(IObject.class);
        interfaceObj.setName("value");
        interfaceObj.setDescription("value");
        // 1.1.3 属性填充方法3 new Object()为演示中替代具体值的示例,实际值是Object类型的对象
        // 推荐
        object.setValue("InterfaceDefUID", "propertyUID", new Object(), null);
        // 不推荐
        object.setValue(null, "propertyUID", new Object(), null);
        // 1.2 创建完对象后必须标记创建结束,否则提交事务时无法成功写入数据
        object.finishCreate(objectCollection);
        // 2.创建新关联关系 end1和end2可以为新创建的对象或者查询出的对象
        IObject end1 = SchemaUtil.newIObject("ClassDefUID", "end1", "description", "displayName");
        end1.finishCreate(objectCollection);
        IObject end2 = SchemaUtil.newIObject("ClassDefUID", "end2", "description", "displayName");
        end2.finishCreate(objectCollection);
        // 2.1 单个关联关系创建
        IRel relationship = SchemaUtil.newRelationship("RelDefUID", end1, end2);
        // 2.2 创建完关联关系对象后必须标记创建结束,否则提交事务时无法成功写入数据
        relationship.finishCreate(objectCollection);
        // 2.3 批量关联关系创建
        SchemaUtil.createRelationships1To2("RelDefUID", end1, objectCollection, new ArrayList<>());
        /*
        更新 更新需要先查询出对应数据再进行操作
         */
        IObject toUpdate = Context.Instance.getQueryHelper().getObjectByUidAndDefinitionUid("UID", "ClassDefUID", IObject.class);
        // 标记更新开始
        toUpdate.BeginUpdate(objectCollection);
        // setValue方法同Create
        // 标记更新结束
        toUpdate.FinishUpdate(objectCollection);
        /*
        删除 删除需要先查询出对应数据再进行操作
         */
        IObject toDelete = Context.Instance.getQueryHelper().getObjectByUidAndDefinitionUid("UID", "ClassDefUID", IObject.class);
        // 物理删除 内置直接提交事务
        toDelete.Delete();
        // 物理删除 手动控制提交事务
        toDelete.Delete(objectCollection);
        // 逻辑删除
        toDelete.Terminate(objectCollection);
        /*
         注意!!!  final 所有增删改操作完成之后需要提交事务,否则所有修改无法写入数据库
         */
        ResultInfo<IObject> commit = objectCollection.commit();
    }
}
