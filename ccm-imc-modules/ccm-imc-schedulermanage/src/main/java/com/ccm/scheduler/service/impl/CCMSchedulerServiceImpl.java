package com.ccm.scheduler.service.impl;

import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.ccm.modules.packagemanage.CWAContext;
import com.ccm.modules.schedulermanage.BidSectionContext;
import com.ccm.modules.schedulermanage.ScheduleContext;
import com.ccm.scheduler.domain.CWAInfoDTO;
import com.ccm.scheduler.domain.RelCWATOSectionDTO;
import com.ccm.scheduler.helpers.ISchedulerQueryHelper;
import com.ccm.scheduler.service.ICCMSchedulerService;
import com.imc.common.core.exception.ServiceException;
import com.imc.common.core.utils.StringUtils;
import com.imc.common.core.web.table.TableConfig;
import com.imc.common.core.web.table.TableData;
import com.imc.common.core.web.table.TableDataSource;
import com.imc.common.core.web.table.TableFieldColumn;
import com.imc.framework.collections.impl.ObjectCollection;
import com.imc.framework.context.Context;
import com.imc.framework.model.qe.QueryRequest;
import com.imc.framework.utils.GeneralUtil;
import com.imc.framework.utils.SchemaUtil;
import com.imc.schema.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.ccm.modules.COMContext.CLASS_CIM_CCM_DOCUMENT_MASTER;

/**
 * @description：TODO
 * @author： kekai.huang
 * @create： 2023/10/27 15:28
 */
@Service
public class CCMSchedulerServiceImpl implements ICCMSchedulerService {

    @Autowired
    ISchedulerQueryHelper schedulerQueryHelper;

    @Override
    public TableData<CWAInfoDTO> getSelectableCWA(String sectionUid) throws Exception {
        if (StringUtils.isEmpty(sectionUid)) {
            throw new ServiceException("请输入标段id");
        }

        // 查询标段
        ICIMCCMBidSection bidSection = schedulerQueryHelper.getBidSectionByUid(sectionUid);
        if (bidSection == null) {
            throw new ServiceException("未找到标段UID:" + sectionUid);
        }

        // 查询标段关联的区域
        List<ICIMCCMCWA> cwas = schedulerQueryHelper.getCWAsByUid(sectionUid);
        List<String> cwaUIDs = new ArrayList<>();
        for (ICIMCCMCWA cwa : cwas) {
            String icimccmcwaCWA = cwa.getCWA();
            cwaUIDs.add(icimccmcwaCWA);
        }
        // 查询施工区域枚举
        IEnumListType enumListType = Context.Instance.getCacheHelper().getSchema("ELT_CWA", IEnumListType.class);
        if (enumListType == null) {
            throw new ServiceException("未找到有效的Enum信息!");
        }
        List<IObject> entries = enumListType.getEntries();
        if (entries == null || entries.isEmpty()) {
            throw new ServiceException("未找到有效的Enum信息!");
        }
        List<IObject> cwaEnums = entries.stream().filter(x -> !cwaUIDs.contains(x.getUid())).collect(Collectors.toList());
        // 获得施工区域列表
        List<CWAInfoDTO> cwaInfoDTOS = new ArrayList<>();
        for (IObject cwaEnum : cwaEnums) {
            CWAInfoDTO cwaInfoDTO = new CWAInfoDTO();
            cwaInfoDTO.setUid(cwaEnum.getName());
            cwaInfoDTO.setDescription(cwaEnum.getDescription());
            cwaInfoDTO.setName(cwaEnum.getName());
            cwaInfoDTOS.add(cwaInfoDTO);
        }
        return getCWATableData(cwaInfoDTOS);
    }

    private TableData<CWAInfoDTO> getCWATableData(List<CWAInfoDTO> cwaInfoDTOS) {
        TableData<CWAInfoDTO> tableData = new TableData<>();

        // 属性列
        List<TableFieldColumn> tableFieldColumns = new ArrayList<>();
        tableFieldColumns.add(new TableFieldColumn("uid", "编码", true));
        tableFieldColumns.add(new TableFieldColumn("description", "描述", true));
        tableFieldColumns.add(new TableFieldColumn("name", "显示名", true));
//        tableFieldColumns.add(new TableFieldColumn("order", "序号", true));
        tableData.setTableColumns(tableFieldColumns);

        // 表格配置
        tableData.setTableConfig(new TableConfig());

        // 表格数据
        if (cwaInfoDTOS.isEmpty()) return tableData;
        TableDataSource<CWAInfoDTO> dataSource = new TableDataSource<>();
        dataSource.setData(cwaInfoDTOS);
        dataSource.setCurrent(1L);
        dataSource.setPageSize((long)cwaInfoDTOS.size());
        dataSource.setTotal((long)cwaInfoDTOS.size());
        tableData.setTableData(dataSource);
        return tableData;
    }

    @Override
    public void saveCWATOSection(RelCWATOSectionDTO relCWATOSectionDTO) throws Exception {
        if (StringUtils.isEmpty(relCWATOSectionDTO.getCwa())) {
            throw new ServiceException("请输入施工区域");
        }
        if (StringUtils.isEmpty(relCWATOSectionDTO.getSectionUid())) {
            throw new ServiceException("请输入标段id");
        }
        // 事务
        ObjectCollection objectCollection = new ObjectCollection(GeneralUtil.getUsername());

        // 查询标段
        ICIMCCMBidSection bidSection = schedulerQueryHelper.getBidSectionByUid(relCWATOSectionDTO.getSectionUid());
        if (bidSection == null) {
            throw new ServiceException("未找到标段UID:" + relCWATOSectionDTO.getSectionUid());
        }

        // 插入施工区域
        IObject object = SchemaUtil.newIObject(BidSectionContext.CLASS_CIM_CCM_BID_SECTION_CWA, relCWATOSectionDTO.getCwa(), relCWATOSectionDTO.getCwaDescription(), relCWATOSectionDTO.getCwa());
        object.setValue(CWAContext.INTERFACE_CIM_CCM_ICIMCCMCWA, CWAContext.PROPERTY_CWA, relCWATOSectionDTO.getCwa(), null);
        object.finishCreate(objectCollection);

        // 插入关系
        IRel relationship = SchemaUtil.newRelationship(BidSectionContext.REL_BID_SECTION_TO_CWA, bidSection, object);
        relationship.finishCreate(objectCollection);

        // 提交事务
        objectCollection.commit();
    }

    @Override
    public void dataCollectionByScheduleUID(String scheduleUID) throws Exception {
        if (StringUtils.isEmpty(scheduleUID)) {
            throw new ServiceException("请输入计划UID");
        }

        // 获得计划信息
        ICIMCCMSchedule schedule = schedulerQueryHelper.getScheduleByUid(scheduleUID);
        if (schedule == null) {
            throw new ServiceException("未找到对应的计划信息：" + scheduleUID);
        }

        // 获得计划下关联的策略
        List<ICIMCCMPriorityItem> schedulePolicyItems = schedule.getSchedulePolicyItems();
        if (schedulePolicyItems.isEmpty()) return;

        // 事务
        ObjectCollection objectCollection = new ObjectCollection(GeneralUtil.getUsername());

        // 删除计划下的图纸关系
        List<IRel> docRels = schedule.getEnd1Relationships().getRels(ScheduleContext.REL_SCHEDULE_TO_DOCUMENT, false).getRelsByRelDef(ScheduleContext.REL_SCHEDULE_TO_DOCUMENT);
        for (IRel rel : docRels) {
            rel.Delete(objectCollection);
        }
        objectCollection.commit();

        // 根据策略拼接图纸查询条件
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addClassDefForQuery(CLASS_CIM_CCM_DOCUMENT_MASTER);
        for (ICIMCCMPriorityItem schedulePolicyItem : schedulePolicyItems) {
            // 填充条件
            queryRequest.addQueryCriteria(null, schedulePolicyItem.getPriorityTargetProperty(),convertToOperation(schedulePolicyItem.getOperator()), schedulePolicyItem.getPriorityExpectedValue());
        }

        // 关联图纸
        List<ICIMDocumentMaster> documentMasters = Context.Instance.getQueryHelper().query(queryRequest, ICIMDocumentMaster.class);
        if (!documentMasters.isEmpty()) {
            for (ICIMDocumentMaster documentMaster : documentMasters) {
                IRel relationship = SchemaUtil.newRelationship(ScheduleContext.REL_SCHEDULE_TO_DOCUMENT, schedule, documentMaster);
                relationship.finishCreate(objectCollection);
            }
        }

        // 提交
        objectCollection.commit();
    }

    /**
     * 根据枚举类型匹配条件符号
     *
     * @param operatorStr
     * @return
     */
    private SqlKeyword convertToOperation(String operatorStr) {
        SqlKeyword result;
        if ("ELT_!=_Operator".equalsIgnoreCase(operatorStr)) {
            result = SqlKeyword.NOT;
        } else if ("ELT_<_Operator".equalsIgnoreCase(operatorStr)) {
            result = SqlKeyword.LT;
        } else if ("ELT_<=_Operator".equalsIgnoreCase(operatorStr)) {
            result = SqlKeyword.LE;
        } else if ("ELT_>=_Operator".equalsIgnoreCase(operatorStr)) {
            result = SqlKeyword.GE;
        } else if ("ELT_>_Operator".equalsIgnoreCase(operatorStr)) {
            result = SqlKeyword.GT;
        } else {
            result = SqlKeyword.EQ;
        }
        return result;
    }
}
