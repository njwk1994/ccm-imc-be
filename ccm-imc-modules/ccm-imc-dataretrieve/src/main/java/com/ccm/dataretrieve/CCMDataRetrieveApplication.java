package com.ccm.dataretrieve;


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
@SpringBootApplication(scanBasePackages = {"com.imc.framework", "com.ccm.dataretrieve", "com.ccm.modules", "com.imc.schema","com.imc.remote"}, exclude = {MPJSqlInjector.class})
public class CCMDataRetrieveApplication {
    public static void main(String[] args) {
        SpringApplication.run(CCMDataRetrieveApplication.class, args);
        System.out.println("-------数据提取模块启动成功!--------");
    }
}
