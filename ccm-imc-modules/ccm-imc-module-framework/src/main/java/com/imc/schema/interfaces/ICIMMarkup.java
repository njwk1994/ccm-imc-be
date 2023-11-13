package com.imc.schema.interfaces;

import cn.hutool.json.JSON;
import com.alibaba.fastjson2.JSONObject;

/**
 * 标注
 */
public interface ICIMMarkup extends IObject{

    //标注文件分享状态
    String  getCIMMarkupSharedStatus()throws  Exception;

    void  setCIMMarkupSharedStatus(String CIMMarkupSharedStatus) throws  Exception;

    //所属标签
    String getCIMMarkupLabels()throws Exception;

    void   setCIMMarkupLabels(String CIMMarkupLabels)throws  Exception;

    //备注说明
    String getCIMMarkupRemark() throws  Exception;

    void   setCIMMarkupRemark(String CIMMarkupRemark) throws Exception;

    //是否锁定标注
    Boolean getCIMIsMarkupLocked() throws  Exception;

    void  setCIMIsMarkupLocked(Boolean CIMIsMarkupLocked)throws  Exception;

    //标注内容
    JSONObject getCIMMarkupContent()throws  Exception;

    void  setCIMMarkupContent(JSONObject CIMMarkupContent)throws  Exception;

    //标注类型
    String   getCIMMarkupType() throws Exception;

    void  setCIMMarkupType(String CIMMarkupType)throws  Exception;

    //标注的合并状态
    String getCIMMarkupConsolidatedStatus()throws  Exception;

    void setCIMMarkupConsolidatedStatus(String CIMMarkupConsolidatedStatus)throws Exception;
    //标注的签出状态
    String getCIMMarkupCheckedoutStatus() throws  Exception;

    void  setCIMMarkupCheckedoutStatus(String CIMMarkupCheckedoutStatus) throws  Exception;










}
