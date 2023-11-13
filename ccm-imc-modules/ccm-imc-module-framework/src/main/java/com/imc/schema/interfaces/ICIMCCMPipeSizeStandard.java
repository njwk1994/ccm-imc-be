package com.imc.schema.interfaces;

/**
 * 管段标准尺寸
 */
public interface ICIMCCMPipeSizeStandard extends IObject {

    //标准
    String getStandard() throws Exception;

    void setStandard(String Standard) throws Exception;

    //壁厚
    Double getWallThickness() throws Exception;

    void setWallThickness(Double WallThickness) throws Exception;

    //英制尺寸
    Double getInch() throws Exception;

    void setInch(Double Inch) throws Exception;

    //公称直径
    Double getDN() throws Exception;

    void setDN(Double DN) throws Exception;

    //外径
    Double getod() throws Exception;

    void setod(Double od) throws Exception;

    //壁厚等级
    String getRatingSchedule() throws Exception;

    void setRatingSchedule(String RatingSchedule) throws Exception;


}
