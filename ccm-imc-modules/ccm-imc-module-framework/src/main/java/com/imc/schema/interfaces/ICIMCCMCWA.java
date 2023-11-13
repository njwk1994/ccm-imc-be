package com.imc.schema.interfaces;

/**
 * @description：TODO
 * @author： kekai.huang
 * @create： 2023/10/27 15:48
 */
public interface ICIMCCMCWA extends IObject {

    //施工区域
    String  getCWA()throws  Exception;

    void  setCWA(String CWA) throws Exception;

}
