package com.imc.schema.interfaces;

/***
 * 管道基础材料信息
 */
public interface ICIMCDWPipeMaterial extends  IObject{
    // 材料类别
    String getCIMCDWMaterialCategory()throws  Exception;
    void setCIMCDWMaterialCategory(String CIMCDWMaterialCategory)throws  Exception;
    //管道等级
    String getCIMCDWMaterialsClass()throws  Exception;

    void setCIMCDWMaterialsClass(String CIMCDWMaterialsClass)throws  Exception;
    // 材料牌号
    String getCIMCDWMaterialsGrade()throws  Exception;

    void setCIMCDWMaterialsGrade(String CIMCDWMaterialsGrade)throws  Exception;
    // 制造标准
    String getCIMCDWManufacturingCriteria()throws  Exception;

    void setCIMCDWManufacturingCriteria(String CIMCDWManufacturingCriteria)throws  Exception;
    // 壁厚等级
    String getCIMCDWRatSchedule()throws  Exception;

    void setCIMCDWRatSchedule(String CIMCDWRatSchedule)throws  Exception;

}
