package com.imc.schema.interfaces.bases;

import com.imc.framework.model.hc.InterfaceDefault;
import com.imc.schema.interfaces.ICOMDemoHouse;

/**
 * @author HuangTao
 * @version 1.0
 * @since 2023/4/3 17:19
 */
public abstract class ICOMDemoHouseBase extends InterfaceDefault implements ICOMDemoHouse {
    public ICOMDemoHouseBase(boolean instantiateRequiredProperties) {
        super(ICOMDemoHouse.class.getSimpleName(), instantiateRequiredProperties);
    }

    /**
     * 设置房屋地址
     *
     * @param address
     */
    @Override
    public void setAddress(String address) throws Exception {
        this.setPropertyValue("ICOMDemoHouse", "COMAddress", address, null, true);
    }

    /**
     * 获取房屋地址
     *
     * @return
     */
    @Override
    public String getAddress() {
        Object actualValue = this.getLatestValue("ICOMDemoHouse", "COMAddress");
        return actualValue != null ? actualValue.toString() : "";
    }
}
