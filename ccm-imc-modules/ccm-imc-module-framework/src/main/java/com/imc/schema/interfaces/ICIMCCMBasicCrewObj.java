package com.imc.schema.interfaces;

/**
 * 基础车间班组对象
 */
public interface ICIMCCMBasicCrewObj extends IObject{
    //车间/班组
    String getCrew() throws Exception;

    void  setCrew(String Crew) throws Exception;
    //班组规模
    Integer  getCrewsize() throws Exception;

    void setCrewsize(Integer Crewsize) throws Exception;

}
