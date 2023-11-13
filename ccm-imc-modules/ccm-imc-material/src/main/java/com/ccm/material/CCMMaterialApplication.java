package com.ccm.material;


import com.github.yulichang.injector.MPJSqlInjector;
import com.imc.common.security.annotation.EnableFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.imc.common.security.annotation.EnableCustomConfig;
import com.imc.common.swagger.annotation.EnableCustomSwagger2;


/**
 * 演示模块
 *
 * @author imc
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableFeignClients
@SpringBootApplication(scanBasePackages = {"com.imc.framework", "com.ccm.material", "com.ccm.modules", "com.imc.schema","com.imc.remote"}, exclude = {MPJSqlInjector.class})
public class CCMMaterialApplication {
    public static void main(String[] args) {
        SpringApplication.run(CCMMaterialApplication.class, args);
        System.out.println("-------材料模块启动成功!--------");
    }
}
