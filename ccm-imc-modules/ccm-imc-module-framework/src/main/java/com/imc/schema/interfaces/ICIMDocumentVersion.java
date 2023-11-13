package com.imc.schema.interfaces;

import cn.hutool.core.date.DateTime;

/**
 * 修订
 */
public interface ICIMDocumentVersion extends IObject {
        // 获取修订号
        Integer getCIMDocVersion()  throws Exception;

        void  setCIMDocVersion(Integer CIMDocVersion) throws  Exception;

        //获取是否签出
        Boolean  getCIMIsDocVersionCheckedOut() throws  Exception;

        void  setCIMIsDocVersionCheckedOut(Boolean CIMIsDocVersionCheckedOut)  throws  Exception;

        //是否过期
        Boolean  getCIMIsDocVersionSuperseded() throws  Exception;

        void setCIMIsDocVersionSuperseded(Boolean  CIMIsDocVersionSuperseded)  throws  Exception;

        //签入日期
        DateTime getCIMVersionCheckInDate() throws  Exception;

        void setCIMVersionCheckInDate(DateTime CIMVersionCheckInDate) throws  Exception;
        //签入人
        String  getCIMVersionCheckInUser()  throws  Exception;

        void setCIMVersionCheckInUser(String CIMVersionCheckInUser )throws  Exception;

        // 过期日期
        DateTime getCIMVersionSupersededDate() throws Exception;

        void  setCIMVersionSupersededDate(DateTime CIMVersionSupersededDate) throws  Exception;








}
