package com.imc.schema.interfaces.bases;

import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.ccm.modules.COMContext;
import com.imc.common.core.enums.frame.PropertyDefinitions;
import com.imc.common.core.enums.frame.RelDirection;
import com.imc.framework.context.Context;
import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.framework.model.qe.QueryRequest;
import com.imc.schema.interfaces.ICOMDemoHouse;
import com.imc.schema.interfaces.ICOMDemoUser;
import com.imc.schema.interfaces.IObject;

import java.util.List;

public abstract class ICOMDemoUserBase extends InterfaceDefault implements ICOMDemoUser {


    public ICOMDemoUserBase(boolean instantiateRequiredProperties) {
        super(COMContext.INTERFACE_COM_DEMO_USER, instantiateRequiredProperties);
    }

    /**
     * 设置性别
     *
     * @param sex
     */
    @Override
    public void setSex(String sex) throws Exception {
        this.setPropertyValue(COMContext.INTERFACE_COM_DEMO_USER, COMContext.PROPERTY_COM_SEX, sex, null, true);
    }

    /**
     * 获取性别
     *
     * @return
     */
    @Override
    public String getSex() {
        Object actualValue = this.getLatestValue(COMContext.INTERFACE_COM_DEMO_USER, COMContext.PROPERTY_COM_SEX);
        return actualValue != null ? actualValue.toString() : "";
    }

    /**
     * 设置年龄
     *
     * @param age
     */
    @Override
    public void setAge(int age) throws Exception {
        this.setPropertyValue(COMContext.INTERFACE_COM_DEMO_USER, "COMAge", age, null, true);
    }

    /**
     * 获取年龄
     *
     * @return
     */
    @Override
    public int getAge() {
        Object actualValue = this.getLatestValue(COMContext.INTERFACE_COM_DEMO_USER, "COMAge");
        return actualValue != null ? Integer.parseInt(actualValue.toString()) : 0;
    }

    /**
     * 获取用户所有的房子
     *
     * @return
     */
    @Override
    public List<IObject> getHouseObjList() throws Exception {
        return this.getEnd1Relationships().getRels(COMContext.REL_COM_USER_TO_HOUSE, false).getEnd2s(null).getList();
    }

    /**
     * 获取用户所有的房子
     *
     * @return
     */
    @Override
    public List<ICOMDemoHouse> getHouseList() throws Exception {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.addClassDefForQuery("COMDemoHouse");
        queryRequest.addQueryCriteria(RelDirection.From2To1.getPrefix() + COMContext.REL_COM_USER_TO_HOUSE, PropertyDefinitions.uid.toString(), SqlKeyword.EQ, this.getUid());
        return Context.Instance.getQueryHelper().query(queryRequest, ICOMDemoHouse.class);
    }
}
