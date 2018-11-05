package com.cc.anno.demo.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @see org.springframework.web.WebApplicationInitializer
 * @see org.springframework.web.context.ServletContextAware
 * @see org.springframework.context.ApplicationContextAware
 * @see org.springframework.context.annotation.ImportBeanDefinitionRegistrar
 * @see org.springframework.context.annotation.ClassPathBeanDefinitionScanner
 * @see org.springframework.beans.factory.BeanFactoryAware
 * @see org.springframework.beans.factory.FactoryBean
 * @see org.springframework.beans.factory.InitializingBean
 * @see org.springframework.beans.factory.BeanNameAware
 * @see org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor
 * @see org.springframework.beans.factory.config.BeanPostProcessor
 * @see org.springframework.beans.BeanInfoFactory
 */
public class GmpApplicationContextInitializer implements
        ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        System.err.println("tomcat start --> GmpApplicationContextInitializer");
    }
}
