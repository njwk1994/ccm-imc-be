package com.imc.schema.interfaces;

/**
 * 管道基础材料信息
 */
public interface ICIMCCMPipeMaterial extends IObject{
    //材料类别
   String  getMaterialCategory() throws  Exception;

   void setMaterialCategory(String MaterialCategory) throws  Exception;

   //管道等级
    String getMaterialsClass() throws  Exception;

    void  setMaterialsClass(String MaterialsClass)throws  Exception;

    //材料牌号
    String  getMaterialsGrade() throws Exception;

    void setMaterialsGrade(String MaterialsGrade) throws  Exception;

    // 制造标准
    String getManufacturingCriteria() throws  Exception;

    void setManufacturingCriteria(String ManufacturingCriteria) throws  Exception;
    //壁厚等级
    String  getRatSchedule() throws Exception;

    void  setRatSchedule(String RatSchedule) throws  Exception;



}
