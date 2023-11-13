package com.imc.schema.interfaces;

/**
 * @author HuangTao
 * @version 1.0
 * @since 2023/4/3 17:16
 */
public interface ICOMDemoHouse extends IObject {

    /**
     * 设置房屋地址
     *
     * @param address
     */
    void setAddress(String address) throws Exception;

    /**
     * 获取房屋地址
     *
     * @return
     */
    String getAddress();
}
