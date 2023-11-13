package com.imc.schema.interfaces;

import cn.hutool.core.date.DateTime;

/**
 * 上传到minio
 */
public interface ICIMMinioFile extends  IObject{

    //桶名
    String   getCIMFileBucketName() throws  Exception;

    void setCIMFileBucketName(String CIMFileBucketName) throws  Exception;


    //存入桶后的文件名
    String  getCIMFileBucketObjName() throws  Exception;

    void  setCIMFileBucketObjName(String CIMFileBucketObjName) throws  Exception;

    //  上传时间
    DateTime getCIMFileUploadDate() throws  Exception;

    void  setCIMFileUploadDate(DateTime  CIMFileUploadDate) throws  Exception;
    //   文件url
    String  getCIMFileMinioUrl() throws  Exception;

    void  setCIMFileMinioUrl(String  CIMFileMinioUrl) throws  Exception;


}
