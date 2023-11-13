package com.ccm.packagemanage.datasource;

import com.alibaba.fastjson.JSON;
import com.ccm.modules.packagemanage.enums.DataSourceType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @Author kekai.huang
 * @Date 2023/9/12 11:16
 */
@Slf4j
@Component
public class DataSourceDiscoveryService implements IDataSourceDiscoveryService {

    private Map<DataSourceType, DataSourceDescriptor> dataSourceDescriptorMap = new HashMap<>();

    @PostConstruct
    void init() {
        this.registerDataSources();
    }

    private void registerDataSources() {
        Set<BeanDefinition> ruleNodeBeanDefinitions = getBeanDefinitions(DataSource.class);
        for (BeanDefinition def : ruleNodeBeanDefinitions) {

            try {
                String clazzName = def.getBeanClassName();
                Class<?> clazz = null;
                clazz = Class.forName(clazzName);
                DataSource ruleNodeAnnotation = clazz.getAnnotation(DataSource.class);
                DataSourceType type = ruleNodeAnnotation.type();
                DataSourceDescriptor dataSourceDescriptor = scanDataSource(def, type);
                dataSourceDescriptorMap.put(dataSourceDescriptor.getDataSourceType(), dataSourceDescriptor);
            } catch (ClassNotFoundException e) {
                log.error(e.getLocalizedMessage());
                throw new RuntimeException(e);
            }
        }
    }

    private DataSourceDescriptor scanDataSource(BeanDefinition def, DataSourceType type) {
        DataSourceDescriptor scannedComponent = new DataSourceDescriptor();
        String clazzName = def.getBeanClassName();
        try {
            scannedComponent.setDataSourceType(type);
            Class<?> clazz = Class.forName(clazzName);
            DataSource datasourceAnnotation = clazz.getAnnotation(DataSource.class);
            scannedComponent.setDataSourceClass(clazz);
            scannedComponent.setConfigurationClass(datasourceAnnotation.configClazz());
            scannedComponent.setDefaultConfiguration(JSON.parseObject(JSON.toJSONString(datasourceAnnotation.configClazz().newInstance().defaultConfiguration())));
            log.debug("Processing scanned datasource: {}", scannedComponent);
        } catch (Exception e) {
            log.error("Can't initialize datasource {}, due to {}", def.getBeanClassName(), e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return scannedComponent;
    }

    private Set<BeanDefinition> getBeanDefinitions(Class<? extends Annotation> componentType) {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(componentType));
        Set<BeanDefinition> defs = new HashSet<>();
        defs.addAll(scanner.findCandidateComponents("com.ccm.packagemanage.datasource"));
        return defs;
    }

    @Override
    public Optional<DataSourceDescriptor> getDataSourceByType(DataSourceType dataSourceType) {
        return Optional.ofNullable(dataSourceDescriptorMap.get(dataSourceType));
    }
}
