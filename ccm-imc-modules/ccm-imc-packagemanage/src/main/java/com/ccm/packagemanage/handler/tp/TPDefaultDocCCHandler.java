package com.ccm.packagemanage.handler.tp;

import com.ccm.packagemanage.domain.PackageProcedureRequest;
import com.ccm.modules.packagemanage.enums.PackageType;
import com.ccm.modules.packagemanage.enums.ProcedureType;
import com.ccm.packagemanage.handler.AbstractPackageProcedureHandler;
import com.ccm.packagemanage.service.IPredictionReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author kekai.huang
 * @Date 2023/9/12 11:28
 * @PackageName:com.ccm.packagemanage.handler.tp
 * @ClassName: TPDefaultDocCCHandler
 * @Description: TODO
 * @Version 1.0
 */
@Service
public class TPDefaultDocCCHandler extends AbstractPackageProcedureHandler {

    @Autowired
    IPredictionReservationService predictionReservationService;

    @Override
    protected PackageType getPackageType() {
        return PackageType.TP;
    }

    @Override
    protected ProcedureType getProcedureType() {
        return ProcedureType.EN_DefaultDocCC;
    }

    @Override
    public Map<String, Object> predictionReservation(PackageProcedureRequest packageProcedureRequest) {
        return predictionReservationService.taskExistAndCreatePartialStatusRequest(packageProcedureRequest);
    }
}
