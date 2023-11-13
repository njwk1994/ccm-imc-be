package com.imc.schema.interfaces;

import java.util.List;

public interface ICOMDemoUser extends IObject {

    /**
     * 设置性别
     *
     * @param sex
     */
    void setSex(String sex) throws Exception;

    /**
     * 获取性别
     *
     * @return
     */
    String getSex();

    /**
     * 设置年龄
     *
     * @param age
     */
    void setAge(int age) throws Exception;

    /**
     * 获取年龄
     *
     * @return
     */
    int getAge();

    /**
     * 获取用户所有的房子
     *
     * @return
     */
    List<IObject> getHouseObjList() throws Exception;

    /**
     * 获取用户所有的房子
     *
     * @return
     */
    List<ICOMDemoHouse> getHouseList() throws Exception;
}
