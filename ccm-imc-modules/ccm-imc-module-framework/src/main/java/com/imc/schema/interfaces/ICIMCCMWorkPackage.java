package com.imc.schema.interfaces;

import cn.hutool.core.date.DateTime;

import java.util.List;

/**
 * 工作包
 */
public interface ICIMCCMWorkPackage extends IObject{

    //状态
   DateTime getTWPStatus() throws Exception;

   void setTWPStatus(DateTime TWPStatus) throws Exception;


    /**
     * 获得当前工作包下的所有设计图纸
     * @return
     * @throws Exception
     */
    List<ICIMDocumentMaster> getDocumentList() throws Exception;

    /**
     * 获取工作包的设计数据
     *
     * @return
     * @throws Exception
     */
    List<ICIMCCMDesignObj> getDesignDataList() throws Exception;

    /**
     * 获得任务包，工作包所属任务包
     * @return
     */
    List<ICIMCCMTaskPackage> getTaskPackageList();

    /**
     * 获取工作包下工作步骤
     * @return
     */
    List<ICIMWorkStep> getWorkStepsWithoutDeleted();

    /**
     * 更新工作包进度
     *
     * @param progress 进度
     * @throws Exception
     */
    void updateProgress(Double progress, boolean withTransaction) throws Exception;

    /**
     * 设置包对象为升版状态
     */
    void setObjectRevised() throws Exception;
}
