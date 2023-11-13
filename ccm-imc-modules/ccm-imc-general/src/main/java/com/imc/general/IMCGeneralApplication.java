package com.imc.general;


import com.github.yulichang.injector.MPJSqlInjector;
import com.imc.common.security.annotation.EnableCustomConfig;
import com.imc.common.security.annotation.EnableFeignClients;
import com.imc.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 通用模块
 *
 * @author imc
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableFeignClients
@SpringBootApplication(scanBasePackages = {"com.imc.framework", "com.imc.general", "com.imc.schema", "com.imc.modules.general"}, exclude = {MPJSqlInjector.class})
public class IMCGeneralApplication {

    public static void main(String[] args) {
        SpringApplication.run(IMCGeneralApplication.class, args);
        System.out.println("-------通用模块启动成功------");
    }
    /*public static void main(String[] args) {
        //SpringApplication.run(IMCGeneralApplication.class, args);
        new SpringApplicationBuilder(IMCGeneralApplication .class)
                .beanNameGenerator(new ProGuardBeanNameGenerator()).run();
        System.out.println("-------通用模块启动成功------");
    }

    *//**
     * 代码混淆后，包名、类名会存在重复，重写buildDefaultBeanName方法，获取全限定的类名
     *//*
    static class ProGuardBeanNameGenerator extends AnnotationBeanNameGenerator {
        @Override
        protected String buildDefaultBeanName(BeanDefinition definition) {
            return definition.getBeanClassName();
        }
    }*/
}
