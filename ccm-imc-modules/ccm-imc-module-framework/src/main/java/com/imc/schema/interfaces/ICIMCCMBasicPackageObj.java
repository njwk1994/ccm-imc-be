package com.imc.schema.interfaces;

/**
 * 基础包对象
 */
public interface ICIMCCMBasicPackageObj extends IObject{

    //父计划
   String getParentPlan()throws Exception;

   void setParentPlan(String ParentPlan) throws Exception;
   //父计划名城
   String  getParentPlanName() throws Exception;

   void  setParentPlanName(String ParentPlanName)throws Exception;

   //计划权重
    Double getPlannedWeight() throws Exception;

    void  setPlannedWeight(Double PlannedWeight) throws Exception;

    //进度
    Double  getProgress() throws Exception;

    void  setProgress(Double Progress) throws Exception;
    // 完成进度
    Double  getEstimatedProgress() throws Exception;

    void  setEstimatedProgress(Double EstimatedProgress) throws Exception;
    //范围
    String getScope() throws Exception;

    void setScope(String Scope) throws Exception;

    //  状态
    String getState() throws Exception;

    void   setState(String State) throws Exception;
}
