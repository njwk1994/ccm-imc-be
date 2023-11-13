package com.ccm.scheduler.service.impl;

import cn.hutool.core.util.XmlUtil;
import com.ccm.modules.packagemanage.BasicPlanPackageContext;
import com.ccm.modules.packagemanage.ProjectConfigContext;
import com.ccm.modules.schedulermanage.ScheduleContext;
import com.ccm.scheduler.service.ICCMP6APIService;
import com.ccm.scheduler.service.ICCMP6HandleService;
import com.imc.common.core.enums.frame.PropertyDefinitions;
import com.imc.common.core.exception.ServiceException;
import com.imc.framework.collections.impl.ObjectCollection;
import com.imc.framework.context.Context;
import com.imc.framework.model.qe.QueryRequest;
import com.imc.framework.utils.GeneralUtil;
import com.imc.framework.utils.SchemaUtil;
import com.imc.schema.interfaces.ICIMCCMProjectConfig;
import com.imc.schema.interfaces.IObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.ccm.modules.schedulermanage.ScheduleContext.*;
import static com.ccm.scheduler.constant.P6Common.*;

/**
 * @Author kekai.huang
 * @Date 2023/9/13 11:14
 * @PackageName:com.ccm.scheduler.service.impl
 * @ClassName: CCMP6HandleServiceImpl
 * @Description: TODO
 * @Version 1.0
 */
@Service
public class CCMP6HandleServiceImpl implements ICCMP6HandleService {

    @Autowired
    ICCMP6APIService iccmp6APIService;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);

    private static Map<String, String> DATA_STRING = new HashMap<String, String>();
    static {
        DATA_STRING.put("PlannedStartDate", BasicPlanPackageContext.PROPERTY_PLANNED_START);
        DATA_STRING.put("PlannedFinishDate", BasicPlanPackageContext.PROPERTY_PLANNED_END);
        DATA_STRING.put("ActualStartDate", BasicPlanPackageContext.PROPERTY_ACTUAL_START);
        DATA_STRING.put("ActualFinishDate", BasicPlanPackageContext.PROPERTY_ACTUAL_END);
        DATA_STRING.put("RemainingEarlyStartDate", ScheduleContext.PROPERTY_EARLY_START);
        DATA_STRING.put("RemainingEarlyFinishDate", ScheduleContext.PROPERTY_EARLY_END);
        DATA_STRING.put("RemainingLateStartDate", ScheduleContext.PROPERTY_LATE_START);
        DATA_STRING.put("RemainingLateFinishDate", ScheduleContext.PROPERTY_LATE_END);
    }

    @Override
    public void syncSchedule() throws Exception {
        // 获取项目配置
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addClassDefForQuery(ProjectConfigContext.CLASS_CIM_CCM_PROJECT_CONFIG);
        List<ICIMCCMProjectConfig> projectConfigs = Context.Instance.getQueryHelper().query(queryRequest, ICIMCCMProjectConfig.class);
        if (projectConfigs.size() <= 0) {
            throw new ServiceException("获取项目配置失败!");
        }

        // P6参数
        String p6WSUrl = projectConfigs.get(0).getP6WebserviceURL();
        String p6ProjectName = projectConfigs.get(0).getP6ProjectName();
        String p6ProjectLoginName = projectConfigs.get(0).getP6ProjectLoginName();
        String p6ProjectPassword = projectConfigs.get(0).getP6ProjectPassword();

        // 检查连接状态
        iccmp6APIService.isServiceAvailable(p6WSUrl, p6ProjectName, p6ProjectLoginName, p6ProjectPassword);

        // 获取项目信息
        String objectId = iccmp6APIService.readProjects(p6WSUrl, p6ProjectName, p6ProjectLoginName, p6ProjectPassword);

        // 导出项目数据
        String xmlStr = iccmp6APIService.exportProject(objectId, p6WSUrl, p6ProjectName, p6ProjectLoginName, p6ProjectPassword);
        Map<String, Object> xmlMap = XmlUtil.xmlToMap(xmlStr);
        Map<String, Object> projectMap = (Map<String, Object>) xmlMap.get(TAG_PROJECT);

        // 映射WBS信息
        List<Map<String, Object>> wbsList = (List<Map<String, Object>>) projectMap.get(TAG_WBS);
        HashMap<String, Map<String, Object>> wbsNameMap = new HashMap<>();
        wbsList.forEach(m -> {
            wbsNameMap.put(m.get("ObjectId").toString(), m);
        });
        List<Map<String, Object>> activities = (List<Map<String, Object>>) projectMap.get(TAG_ACTIVITY);

        // 获取所有计划
        queryRequest = new QueryRequest();
        queryRequest.addClassDefForQuery(CLASS_CIM_CCM_SCHEDULE);
        List<IObject> iObjects =  Context.Instance.getQueryHelper().query(queryRequest, IObject.class);
        Map<String, IObject> existed = new HashMap<>();
        List<String> toDelete = new ArrayList<>();
        iObjects.forEach(i -> {
            existed.put(i.getName(), i);
            toDelete.add(i.getName());
        });

        // 创建事务
        ObjectCollection objectCollection = new ObjectCollection(GeneralUtil.getUsername());

        // 将获取的计划和当前的计划做对比，进行新增删除及更新操作
        List<String> toChanges = new ArrayList<>();
        for (Map<String, Object> activity : activities) {
            // 判端计划需要新增还是更新
            Object name = activity.get("Id");
            Object description = activity.get("Name");
            Object wbsPath = activity.get("WBSObjectId");
            Map<String, Object> wbsInfo = wbsNameMap.get(wbsPath.toString());

            // 根据计划名称获得计划
            IObject iObject = existed.get(name.toString());
            if (null == iObject) {
                // 不存在则创建
                iObject = SchemaUtil.newIObject(CLASS_CIM_CCM_SCHEDULE, name.toString(), description.toString(), description.toString());
                iObject.setValue(null, PropertyDefinitions.name.toString(), name, null);
                iObject.setValue(null, PROPERTY_WBS_PATH, wbsInfo.get("Name"), null);
                dataFormat(dateFormat, activity, iObject);
                iObject.finishCreate(objectCollection);
            } else {
                // 存在则更新
                iObject.BeginUpdate(objectCollection);
                iObject.setValue(null, PropertyDefinitions.name.toString(), name, null);
                iObject.setValue(null, PROPERTY_WBS_PATH, wbsInfo.get("Name"), null);
                dataFormat(dateFormat, activity, iObject);
                iObject.FinishUpdate(objectCollection);
            }
            toChanges.add(name.toString());
        }

        // 处理删除数据
        toDelete.removeAll(toChanges);
        for (String s : toDelete) {
            IObject iObject = existed.get(s);
            iObject.Delete();
        }

        // 提交事务
        objectCollection.commit();
    }

    private void dataFormat(SimpleDateFormat dateFormat, Map<String, Object> activity, IObject iObject) throws Exception {
        for (String s : DATA_STRING.keySet()) {
            Object o = activity.get(s);
            if (!org.springframework.util.StringUtils.isEmpty(o)) {
                Date parse = dateFormat.parse(activity.get(s).toString());
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String format = df.format(parse);
                iObject.setValue(null, DATA_STRING.get(s), format, null);
            } else {
                iObject.setValue(null, DATA_STRING.get(s), "", null);
            }
        }
    }
}
