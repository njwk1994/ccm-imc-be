package com.imc.schema.interfaces;

import com.imc.framework.collections.IObjectCollection;

import java.util.List;

/**
 * 任务包
 */
public interface ICIMCCMTaskPackage extends IObject {
    //状态
    String getTTPStatus() throws Exception;

    void setTTPStatus(String TTPStatus) throws Exception;

    /**
     * 获得当前工作包下的所有设计图纸
     * @return
     * @throws Exception
     */
    List<ICIMDocumentMaster> getDocumentList() throws Exception;

    /**
     * 获取任务包的设计数据
     *
     * @return
     * @throws Exception
     */
    List<ICIMCCMDesignObj> getDesignDataList() throws Exception;

    /**
     * 获取任务包的工作步骤
     *
     * @return
     * @throws Exception
     */
    List<ICIMWorkStep> getWorkStepsWithoutDeletedList() throws Exception;

    /**
     * 获取任务包同施工阶段的工作步骤
     *
     * @return
     * @throws Exception
     */
    List<ICIMWorkStep> getWorkStepsWithSamePurpose() throws Exception;

    /**
     * 更新任务包进度
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
