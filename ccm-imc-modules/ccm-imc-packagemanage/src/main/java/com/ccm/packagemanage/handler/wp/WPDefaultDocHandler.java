package com.ccm.packagemanage.handler.wp;

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
 * @Date 2023/9/12 11:34
 * @PackageName:com.ccm.packagemanage.handler.wp
 * @ClassName: WPDefaultDocHandler
 * @Description: TODO
 * @Version 1.0
 */
@Service
public class WPDefaultDocHandler extends AbstractPackageProcedureHandler {

    @Autowired
    IPredictionReservationService predictionReservationService;

    @Override
    protected PackageType getPackageType() {
        return PackageType.WP;
    }

    @Override
    protected ProcedureType getProcedureType() {
        return ProcedureType.EN_DefaultDoc;
    }

    @Override
    public Map<String, Object> predictionReservation(PackageProcedureRequest packageProcedureRequest) {
        return predictionReservationService.workExistAndCreateNewStatusRequest(packageProcedureRequest);
    }
}
