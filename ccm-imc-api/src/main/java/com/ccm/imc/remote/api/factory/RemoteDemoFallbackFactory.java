package com.ccm.imc.remote.api.factory;

import com.ccm.imc.remote.api.RemoteDemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;


/**
 * 工作流远程服务
 *
 * @author imc
 */
@Component
public class RemoteDemoFallbackFactory implements FallbackFactory<RemoteDemoService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteDemoFallbackFactory.class);

    @Override
    public RemoteDemoService create(Throwable throwable) {
        log.error("Demo服务调用失败:{}", throwable.getMessage());
        return new RemoteDemoService() {
        };
    }
}
