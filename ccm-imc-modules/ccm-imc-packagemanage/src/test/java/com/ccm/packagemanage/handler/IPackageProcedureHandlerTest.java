//package com.ccm.packagemanage.handler;
//
//import com.alibaba.fastjson.JSON;
//import com.ccm.packagemanage.domain.PackageProcedureRequest;
//import com.ccm.packagemanage.enums.PackageType;
//import com.ccm.packagemanage.enums.ProcedureType;
//import com.imc.common.core.domain.R;
//import com.imc.framework.utils.SpringBeanUtil;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// * @Author kekai.huang
// * @Date 2023/9/12 13:40
// * @PackageName:com.ccm.packagemanage.handler
// * @ClassName: IPackageProcedureHandlerTest
// * @Description: TODO
// * @Version 1.0
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class IPackageProcedureHandlerTest {
//    @Test
//    public void testExistAndCreatePartialStatusRequestForWP() {
//        PackageProcedureRequest request = new PackageProcedureRequest();
//        Map<String, IPackageProcedureHandler> handlers = SpringBeanUtil.getApplicationContext().getBeansOfType(IPackageProcedureHandler.class);
//        for (Map.Entry<String, IPackageProcedureHandler> entry : handlers.entrySet()) {
//            if (entry.getValue().switchHandle(PackageType.TP, ProcedureType.EN_NewDocCC)) {
//                request.setPackageType(PackageType.TP);
//                request.setProcedureType(ProcedureType.EN_NewDocCC);
//                System.out.println(JSON.toJSONString(entry.getValue().predictionReservation(request)));
//                return;
//            }
//        }
//    }
//}