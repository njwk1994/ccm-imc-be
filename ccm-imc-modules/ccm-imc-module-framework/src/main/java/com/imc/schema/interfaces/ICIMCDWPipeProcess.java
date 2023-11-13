package com.imc.schema.interfaces;

/**
 * 管道工艺信息
 */
public interface ICIMCDWPipeProcess extends IObject {

    //喷涂面积
    Double getCIMCDWCoatingArea() throws Exception;

    void setCIMCDWCoatingArea(Double CIMCDWCoatingArea) throws Exception;

    //喷涂代码
    String getCIMCDWCoatingColor() throws Exception;

    void setCIMCDWCoatingColor(String CIMCDWCoatingColor) throws Exception;

    //清洁度等级
    String getCIMCDWContaminationLevel() throws Exception;

    void setCIMCDWContaminationLevel(String CIMCDWContaminationLevel) throws Exception;

    //设计压力
    Double getCIMCDWDesignPressure() throws Exception;

    void setCIMCDWDesignPressure(Double CIMCDWDesignPressure) throws Exception;

    //设计温度
    Double getCIMCDWDesignTemperature() throws Exception;

    void setCIMCDWDesignTemperature(Double CIMCDWDesignTemperature) throws Exception;

    //介质
    String getCIMCDWFluidSystem() throws Exception;

    void setCIMCDWFluidSystem(String CIMCDWFluidSystem) throws Exception;

    //保温面积
    Double getCIMCDWInsulationArea() throws Exception;

    void setCIMCDWInsulationArea(Double CIMCDWInsulationArea) throws Exception;

    //保温代码
    String getCIMCDWInsulationCode() throws Exception;

    void setCIMCDWInsulationCode(String CIMCDWInsulationCode) throws Exception;
    //实验介质

    String getCIMCDWTestMedium() throws Exception;

    void setCIMCDWTestMedium(String CIMCDWTestMedium) throws Exception;

    //试压压力
    Double getCIMCDWTestPressure() throws Exception;

    void setCIMCDWTestPressure(Double CIMCDWTestPressure) throws Exception;

    //保温厚度
    Double getCIMCDWTotalInsulThick() throws Exception;

    void setCIMCDWTotalInsulThick(Double CIMCDWTotalInsulThick) throws Exception;

    //是否伴热
    Boolean getCIMCDWTraceRqmt() throws Exception;

    void setCIMCDWTraceRqmt(Boolean CIMCDWTraceRqmt) throws Exception;

}