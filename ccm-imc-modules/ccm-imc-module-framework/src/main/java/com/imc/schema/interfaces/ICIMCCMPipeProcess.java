package com.imc.schema.interfaces;

/**
 * 管道工艺信息
 */
public interface ICIMCCMPipeProcess extends IObject {

    //喷涂面积
    Double getCoatingArea() throws Exception;

    void setCoatingArea(Double CoatingArea) throws  Exception;

    //喷涂代码
    String getCoatingColor() throws Exception;

    void  setCoatingColor(String CoatingColor) throws Exception;

    //清洁度等级
    String getContaminationLevel()throws  Exception;

    void  setContaminationLevel(String ContaminationLevel) throws Exception;

    //设计压力
    Double getDesignPressure() throws  Exception;

    void setDesignPressure(Double DesignPressure) throws Exception;

    //设计温度
    Double getDesignTemperature() throws Exception;

    void  setDesignTemperature(Double DesignTemperature) throws Exception;
    //介质
    String  getFluidSystem() throws Exception;

    void setFluidSystem(String FluidSystem) throws Exception;

    //保温面积
    Double    getInsulationArea() throws Exception;

    void  setInsulationArea(Double InsulationArea) throws Exception;

    //保温代码
    String getInsulationCode() throws Exception;

    void  setInsulationCode(String InsulationCode) throws  Exception;

    //实验介质
    String getTestMedium() throws Exception;

    void  setTestMedium(String TestMedium) throws Exception;

    //试压压力
    Double getTestPressure() throws Exception;

    void  setTestPressure(Double TestPressure) throws Exception;
    //保温厚度
    Double getTotalInsulThick() throws Exception;

    void  setTotalInsulThick(Double TotalInsulThick) throws Exception;

    //是否伴热
    Boolean getTraceRqmt() throws Exception;

    void setTraceRqmt(Boolean TraceRqmt) throws Exception;




}
