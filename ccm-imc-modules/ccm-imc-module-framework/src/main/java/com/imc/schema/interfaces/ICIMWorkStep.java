package com.imc.schema.interfaces;

/**
 * 工作步骤
 */
public interface ICIMWorkStep extends IObject {
    //是否消耗
    Boolean getWSConsumeMaterial();

    void setWSConsumeMaterial(Boolean WSConsumeMaterial) throws Exception;

    //工步使用的ROP规则
    String getWSROPRule() throws Exception;

    void setWSROPRule(String WSROPRule) throws Exception;

    //工步任务包执行阶段
    String getWSTPProcessPhase() throws Exception;

    void setWSTPProcessPhase(String WSTPProcessPhase) throws Exception;

    //工步工作包执行阶段
    String getWSWPProcessPhase() throws Exception;

    void setWSWPProcessPhase(String WSWPProcessPhase) throws Exception;

    //工序状态
    String getWSStatus() throws Exception;

    void setWSStatus(String WSStatus) throws Exception;

    //组件名称
    String getWSComponentName() throws Exception;

    void setWSComponentName(String WSComponentName) throws Exception;

    //组件描述
    String getWSComponentDesc() throws Exception;

    void setWSComponentDesc(String WSComponentDesc) throws Exception;

    //权重
    Double getWSWeight() throws Exception;

    void setWSWeight(Double WSWeight) throws Exception;

    boolean hasActualCompletedDate();

    /**
     * 是否为图纸/ROP升版删除状态
     *
     * @return
     * @throws Exception
     */
    boolean isDeleteStatus() throws Exception;
}
