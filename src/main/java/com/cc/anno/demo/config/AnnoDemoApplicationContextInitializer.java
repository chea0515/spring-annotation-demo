package com.cc.anno.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

/**
 * 定义配置加载
 */
public class AnnoDemoApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final Logger logger = LoggerFactory.getLogger(AnnoDemoApplicationContextInitializer.class);

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        logger.info("AnnoDemoApplicationContextInitializer initialize start: ");
        applicationContext.addBeanFactoryPostProcessor(new AnnoDemoBeanDefinitionRegistryPostProcessor());
    }

    /* Processor */
    protected class AnnoDemoBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
        @Override
        public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
            // 扫描器
            ClassPathBeanDefinitionScanner classPathBeanDefinitionScanner =
                    new ClassPathBeanDefinitionScanner(registry, false);
            // 扫描路径
            String[] basePackages = { "com.cc.anno.demo" };
            // 自定义注解扫描
            TypeFilter includeFilter = new AnnotationTypeFilter(InitAnnoDemoBean.class);
            classPathBeanDefinitionScanner.addIncludeFilter(includeFilter);
            // 执行扫描,添加bean
            classPathBeanDefinitionScanner.scan(basePackages);
        }

        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        }
    }
}
