package com.imc.schema.interfaces;

/**
 * 管道外形尺寸信息
 */
public interface ICIMCCMPipeDimension extends IObject{

     //长度
    Double getLength() throws  Exception;


    void setLength(Double Length) throws  Exception;

    //重量
    Double  getWeight() throws  Exception;

    void  setWeight(Double Weight) throws Exception;
    //尺寸1
    Double  getSize1() throws  Exception;

    void  setSize1(Double Size1) throws Exception;
    //尺寸2
    Double  getSize2() throws  Exception;

    void  setSize2(Double Size2) throws Exception;

    //尺寸3
    Double  getSize3() throws  Exception;

    void  setSize3(Double Size3) throws Exception;

    //尺寸4
    Double  getSize4() throws  Exception;

    void  setSize4(Double Size4) throws Exception;
    //尺寸5
    Double  getSize5() throws  Exception;

    void  setSize5(Double Size5) throws Exception;

}
