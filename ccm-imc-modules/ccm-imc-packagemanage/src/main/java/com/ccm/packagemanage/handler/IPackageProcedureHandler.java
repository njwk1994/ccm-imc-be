package com.ccm.packagemanage.handler;

import com.ccm.packagemanage.domain.PackageProcedureRequest;
import com.ccm.modules.packagemanage.enums.PackageType;
import com.ccm.modules.packagemanage.enums.ProcedureType;

import java.util.Map;

/**
 * @Author kekai.huang
 * @Date 2023/9/12 11:16
 * @Description: 包相关事务处理
 */
public interface IPackageProcedureHandler {

    /**
     * 筛选处理流程
     * @param packageType 包类型
     * @param procedureType 存储过程类型
     * @return
     */
    boolean switchHandle(PackageType packageType, ProcedureType procedureType);

    /**
     * 包预测预留
     * @return
     */
    Map<String, Object> predictionReservation(PackageProcedureRequest request) throws Exception;
}
