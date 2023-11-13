package com.ccm.imc.remote.api;

import com.ccm.imc.remote.api.constants.ApiExtConstant;
import com.ccm.imc.remote.api.factory.RemoteDemoFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;


/**
 * 用户服务
 *
 * @author imc
 */
@FeignClient(contextId = "remoteDemoService", value = ApiExtConstant.SERVICE_WF, fallbackFactory = RemoteDemoFallbackFactory.class)
public interface RemoteDemoService {

}
