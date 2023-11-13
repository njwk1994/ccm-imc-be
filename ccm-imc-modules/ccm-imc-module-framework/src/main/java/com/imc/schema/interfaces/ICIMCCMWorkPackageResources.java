package com.imc.schema.interfaces;

import cn.hutool.core.date.DateTime;

/**
 * 工作包资源
 */
public interface ICIMCCMWorkPackageResources extends IObject {

    // 结束使用日期
    DateTime getEndDate() throws Exception;

    void setEndDate(DateTime EndDate) throws Exception;

    //是否具备
    Boolean getHad() throws Exception;

    void setHad(Boolean Had) throws Exception;

    //数量
    Integer getQuantity() throws Exception;

    void setQuantity(Integer Quantity) throws Exception;

    //资源类别
    String getResourcesType() throws Exception;

    void setResourcesType(String ResourcesType) throws Exception;

    //开始使用日期
    DateTime  getStartDate() throws Exception;

    void  setStartDate(DateTime StartDate) throws Exception;
}
