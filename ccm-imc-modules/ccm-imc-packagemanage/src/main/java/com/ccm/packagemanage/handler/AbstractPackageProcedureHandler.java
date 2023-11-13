package com.ccm.packagemanage.handler;

import com.ccm.modules.packagemanage.enums.PackageType;
import com.ccm.modules.packagemanage.enums.ProcedureType;

/**
 * @Author kekai.huang
 * @Date 2023/9/12 11:24
 * @PackageName:com.ccm.packagemanage.handler
 * @ClassName: AbstractPackageProcedureHandler
 * @Description: TODO
 * @Version 1.0
 */
public abstract class AbstractPackageProcedureHandler implements IPackageProcedureHandler {

    /**
     * 获得包类型
     * @return
     */
    protected abstract PackageType getPackageType();

    /**
     * 获得存储过程类型
     * @return
     */
    protected abstract ProcedureType getProcedureType();

    @Override
    public boolean switchHandle(PackageType packageType, ProcedureType procedureType) {
        if (getPackageType().equals(packageType) && getProcedureType().equals(procedureType)) {
            return true;
        }
        return false;
    }
}
