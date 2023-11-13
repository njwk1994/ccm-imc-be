package com.imc.schema.interfaces;

import cn.hutool.core.date.DateTime;

/**
 * 基础计划和包对象
 */
public interface ICIMCCMBasicPlanPackageObj extends IObject {
    //实际完成时间
   DateTime getActualEnd() throws Exception;


   void setActualEnd(DateTime ActualEnd) throws Exception;
   //实际开始时间
    DateTime getActualStart() throws Exception;

    void  setActualStart(DateTime ActualStart) throws Exception;

    //计划结束时间
    DateTime  getPlannedEnd()throws Exception;

    void setPlannedEnd(DateTime PlannedEnd) throws Exception;

    //计划开始时间
    DateTime  getPlannedStart() throws Exception;

    void  setPlannedStart(DateTime PlannedStart) throws Exception;








}
