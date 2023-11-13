package com.imc.schema.interfaces;

public interface ICIMROPRuleGroup {

    /**\
     * 获取生效的定义对象
     * @return
     */
    String getCIMROPGroupClassDefinitionUID() ;

    /**
     * 设置生效的定义对象
     * @param CIMROPGroupClassDefinitionUID
     * @throws Exception
     */
    void setCIMROPGroupClassDefinitionUID(String CIMROPGroupClassDefinitionUID) throws Exception;

    /**
     * 获取规则组条目变更状态
     * @return
     */
    String getCIMROPGroupItemRevState();

    /**
     * 设置规则组条目变更状态
     * @param CCIMROPGroupItemRevStat
     * @throws Exception
     */
    void setCIMROPGroupItemRevStat(String CCIMROPGroupItemRevStat) throws Exception;


    /**
     * 获取规则组是否已经被更新过
     * @return
     */
    Boolean getCIMROPGroupItemsHasUpdated();

    /**
     * 设置获取规则组是否已经被更新过
     * @param CIMROPGroupItemsHasUpdated
     * @throws Exception
     */
    void setCIMROPGroupItemsHasUpdated(Boolean CIMROPGroupItemsHasUpdated) throws Exception;


    /**
     * 获取规则组步骤是否已经被更新过
     * @return
     */
    Boolean getCIMROPGroupWorkStepHasUpdated();

    /**
     * 设置获取规则组步骤是否已经被更新过
     * @param CIMROPGroupWorkStepHasUpdated
     * @throws Exception
     */
    void setCIMROPGroupWorkStepHasUpdated(Boolean CIMROPGroupWorkStepHasUpdated) throws Exception;


    /**
     * 获取是否已经处理变更
     * @return
     */
    Boolean getCIMROPHasHandleChange();

    /**
     * 设置是否已经处理变更
     * @param CIMROPHasHandleChange
     * @throws Exception
     */
    void setCIMROPHasHandleChange(Boolean CIMROPHasHandleChange) throws Exception;

    /**
     * 获取规则组步骤变更状态
     * @return
     */
    String getCIMROPGroupWorkStepRevState() ;

    /**
     * 设置规则组步骤变更状态
     * @param CIMROPGroupWorkStepRevState
     * @throws Exception
     */
    void setCIMROPGroupWorkStepRevState(String CIMROPGroupWorkStepRevState) throws Exception;


    /**
     * 获取规则组顺序号
     * @return
     */
    Integer getCIMROPGroupOrder() ;

    /**
     * 设置规则组顺序号
     * @param CIMROPGroupOrder
     * @throws Exception
     */
    void setCIMROPGroupOrder(Integer CIMROPGroupOrder) throws Exception;


}
