package com.cc.anno.demo.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.LinkedHashSet;
import java.util.Set;

public class GmpApplicationContextInitializer2 implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        System.err.println("GmpBeanApplicationContextInitializer --> initialize");
        applicationContext.addBeanFactoryPostProcessor(new ConfigurationBeanPostProcessor(new ProcessBeanImpl()));
    }

    protected class ConfigurationBeanPostProcessor implements PriorityOrdered, BeanDefinitionRegistryPostProcessor {

        private ProcessBean processBean;

        public ConfigurationBeanPostProcessor() {
        }

        public ConfigurationBeanPostProcessor(ProcessBean processBean) {
            this.processBean = processBean;
        }

        @Override
        public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
            processBean.addBean(registry);
        }

        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        }

        @Override
        public int getOrder() {
            return Ordered.LOWEST_PRECEDENCE - 666;
        }
    }

    protected interface ProcessBean {
        void addBean(BeanDefinitionRegistry registry);
    }

    protected class ProcessBeanImpl implements ProcessBean {

        @Override
        public void addBean(BeanDefinitionRegistry registry) {
            String[] names = registry.getBeanDefinitionNames();
            Set<String> pkgs = new LinkedHashSet<>();
            for (String name : names) {
                BeanDefinition beanDefinition = registry.getBeanDefinition(name);
                if (beanDefinition instanceof AnnotatedBeanDefinition) {
                    AnnotatedBeanDefinition annotatedBeanDefinition = (AnnotatedBeanDefinition) beanDefinition;
                    tokenScanningPackages(pkgs, annotatedBeanDefinition.getMetadata());
                }
            }
            System.err.println("ProcessBeanImpl, pkgs.size: " + pkgs.size());
        }

        private void tokenScanningPackages(Set<String> pkgs, AnnotationMetadata metadata) {
            AnnotationAttributes attributes =
                    AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(Token.class.getName(), true));
            if (attributes != null) {
                pkgs.add(attributes.getString("value"));
            }
        }
    }
}
