package com.imc.schema.interfaces;

import cn.hutool.core.date.DateTime;

import java.util.List;

/***
 * 计划
 */
public interface ICIMCCMSchedule extends IObject {

    //工作预算
    Double getBudgetedLabor() throws Exception;

    void setBudgetedLabor(Double BudgetedLabor) throws Exception;

    //合同描述
    String getContractDescription() throws Exception;

    void setContractDescription(String ContractDescription) throws Exception;

    //承包商描述
    String getContractorDescription() throws Exception;

    void setContractorDescription(String ContractorDescription) throws Exception;

    //施工计划
    String getCWP() throws Exception;

    void setCWP(String CWP) throws Exception;

    //施工计划区域
    String getCWPArea() throws Exception;

    void  setCWPArea(String CWPArea) throws Exception;
    //施工计划描述
   String  getCWPDescription() throws Exception;

   void setCWPDescription(String CWPDescription) throws Exception;
   //施工计划专业
   String getCWPDiscipline() throws Exception;

   void  setCWPDiscipline(String  CWPDiscipline) throws Exception;

   //施工计划 CWP
    String getCWPEWP() throws Exception;

    void  setCWPEWP(String CWPEWP) throws Exception;
    //最早结束时间
    DateTime  getEarlyEnd() throws Exception;

    void  setEarlyEnd(DateTime EarlyEnd) throws Exception;
   // 最早开始时间
    DateTime  getEarlyStart() throws Exception;

    void  setEarlyStart(DateTime EarlyStart) throws Exception;
    //最迟开始时间
    DateTime   getLateEnd() throws Exception;

    void  setLateEnd(DateTime LateEnd) throws Exception;

    //最迟开始时间
    DateTime  getLateStart() throws Exception;

    void  setLateStart(DateTime LateStart) throws Exception;
    //WBS路径
    String  getWBSPath() throws Exception;

    void setWBSPath(String WBSPath)throws Exception;

    /**
     * 获得计划下关联的策略项
     * @return
     */
    List<ICIMCCMPriorityItem> getSchedulePolicyItems();

}
