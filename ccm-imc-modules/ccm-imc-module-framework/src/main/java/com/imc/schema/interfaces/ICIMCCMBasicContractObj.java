package com.imc.schema.interfaces;

/**
 * 基础合同对象
 */
public interface ICIMCCMBasicContractObj extends IObject{

    //合同
    String getContract() throws Exception;

    void  setContract(String Contract) throws Exception;

    //承包商
    String getContractor() throws Exception;

    void  setContractor(String Contractor)throws Exception;



}
