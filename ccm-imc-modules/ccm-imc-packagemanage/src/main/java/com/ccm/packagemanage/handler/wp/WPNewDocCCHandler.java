package com.ccm.packagemanage.handler.wp;

import com.ccm.modules.packagemanage.WorkPackageContext;
import com.ccm.modules.packagemanage.constant.PackageRequestCommon;
import com.ccm.packagemanage.domain.PackageProcedureRequest;
import com.ccm.modules.packagemanage.enums.PackageType;
import com.ccm.modules.packagemanage.enums.ProcedureType;
import com.ccm.packagemanage.handler.AbstractPackageProcedureHandler;
import com.ccm.packagemanage.service.IPredictionReservationService;
import com.imc.framework.context.Context;
import com.imc.schema.interfaces.IObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author kekai.huang
 * @Date 2023/9/12 11:32
 * @PackageName:com.ccm.packagemanage.handler.wp
 * @ClassName: WPNewDocCCHandler
 * @Description: TODO
 * @Version 1.0
 */
@Service
public class WPNewDocCCHandler extends AbstractPackageProcedureHandler {

    @Autowired
    IPredictionReservationService predictionReservationService;

    @Override
    protected PackageType getPackageType() {
        return PackageType.WP;
    }

    @Override
    protected ProcedureType getProcedureType() {
        return ProcedureType.EN_NewDocCC;
    }

    @Override
    public Map<String, Object> predictionReservation(PackageProcedureRequest packageProcedureRequest) throws Exception {
        IObject workPackage = Context.Instance.getQueryHelper().getObjectByUidAndDefinitionUid(packageProcedureRequest.getPackageId(), WorkPackageContext.CLASS_CIM_CCM_WORK_PACKAGE, IObject.class);
        Map<String, String> requestParams = predictionReservationService.getRequestParams(packageProcedureRequest, workPackage,true);
        packageProcedureRequest.setDrawingNumbers(requestParams.get(PackageRequestCommon.DRAWING_NUMBERS));
        return predictionReservationService.materialExistAndCreatePartialStatusRequest33(
                packageProcedureRequest,
                requestParams.get(PackageRequestCommon.COMMODITY_CODES));
    }
}
