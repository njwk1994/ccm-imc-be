package com.ccm.packagemanage.handler.tp;

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

import static com.ccm.modules.packagemanage.TaskPackageContext.CLASS_CIM_CCM_TASK_PACKAGE;

/**
 * @Author kekai.huang
 * @Date 2023/9/12 11:29
 * @PackageName:com.ccm.packagemanage.handler.tp
 * @ClassName: TPNewDocHandler
 * @Description: TODO
 * @Version 1.0
 */
@Service
public class TPNewDocHandler extends AbstractPackageProcedureHandler {

    @Autowired
    IPredictionReservationService predictionReservationService;

    @Override
    protected PackageType getPackageType() {
        return PackageType.TP;
    }

    @Override
    protected ProcedureType getProcedureType() {
        return ProcedureType.EN_NewDoc;
    }

    @Override
    public Map<String, Object> predictionReservation(PackageProcedureRequest packageProcedureRequest) throws Exception {
        IObject taskPackage = Context.Instance.getQueryHelper().getObjectByUidAndDefinitionUid(packageProcedureRequest.getPackageId(), CLASS_CIM_CCM_TASK_PACKAGE, IObject.class);
        Map<String, String> requestParams = predictionReservationService.getRequestParams(packageProcedureRequest, taskPackage,false);
        packageProcedureRequest.setDrawingNumbers(requestParams.get(PackageRequestCommon.DRAWING_NUMBERS));
        return predictionReservationService.materialExistAndCreatePartialStatusRequest33(
                packageProcedureRequest,
                requestParams.get(PackageRequestCommon.COMMODITY_CODES));
    }
}
