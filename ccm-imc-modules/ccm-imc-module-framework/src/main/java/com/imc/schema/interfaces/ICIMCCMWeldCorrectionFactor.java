package com.imc.schema.interfaces;

/**
 *焊口当量修正系数
 */
public interface ICIMCCMWeldCorrectionFactor extends IObject {
    //修正系列序号
    Integer getCorrectionFactorNum() throws Exception;

    void setCorrectionFactorNum(Integer CorrectionFactorNum) throws Exception;

    //厚度
    Double getThickness() throws Exception;

    void setThickness(Double Thickness) throws Exception;

    //焊口形式
    String getWeldType() throws Exception;

    void setWeldType(String WeldType) throws Exception;

    //修正系数1
    Double getCorrectionFactor1() throws Exception;

    void setCorrectionFactor1(Double CorrectionFactor1) throws Exception;

    //修正系数2
    Double getCorrectionFactor2() throws Exception;

    void setCorrectionFactor2(Double CorrectionFactor2) throws Exception;


}
