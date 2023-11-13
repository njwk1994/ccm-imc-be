package com.imc.schema.interfaces;

/**
 * 工作分解结构
 */
public interface ICIMCCMWBS extends IObject{
        //施工区域
        String  getCWA()throws  Exception;

        void  setCWA(String CWA) throws Exception;


        //设计专业
        String  getDesignDiscipline() throws  Exception;

        void  setDesignDiscipline(String DesignDiscipline) throws  Exception;

        //任务包施工阶段
        String getPurpose() throws  Exception;

        void setPurpose(String Purpose) throws  Exception;

        //工作包施工阶段
       String getWPPurpose() throws  Exception;

       void  setWPPurpose(String WPPurpose) throws  Exception;





}
