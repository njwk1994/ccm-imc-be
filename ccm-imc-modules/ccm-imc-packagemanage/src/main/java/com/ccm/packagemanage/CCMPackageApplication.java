package com.ccm.packagemanage;


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
@SpringBootApplication(scanBasePackages = {"com.imc.framework", "com.ccm.packagemanage", "com.ccm.modules", "com.imc.schema","com.imc.remote"}, exclude = {MPJSqlInjector.class})
public class CCMPackageApplication {
    public static void main(String[] args) {
        SpringApplication.run(CCMPackageApplication.class, args);
        System.out.println("-------包模块启动成功!--------");
    }
}
