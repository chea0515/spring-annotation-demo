package com.cc.anno.demo.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class GmpApplicationContextInitializer3 implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        System.err.println("GmpApplicationContextInitializer3 -> initialize");
        applicationContext.addBeanFactoryPostProcessor(new GmpBeanFactoryPostProcessor());
        new GmpFactoryBean();
    }

    protected class GmpBeanFactoryPostProcessor implements BeanDefinitionRegistryPostProcessor {

        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        }

        @Override
        public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
            System.err.println("GmpApplicationContextInitializer3 -> GmpBeanFactoryPostProcessor -> postProcessBeanDefinitionRegistry");
            //GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            //beanDefinition.setBeanClass(GmpFactoryBean.class);
            //beanDefinition.setLazyInit(true);
            //registry.registerBeanDefinition("gmpFactoryBean2", beanDefinition);
        }
    }

    protected class GmpFactoryBean implements FactoryBean<FactoryBeanMapper>, InitializingBean {

        private FactoryBeanMapper factoryBeanMapper;

        @Override
        public FactoryBeanMapper getObject() throws Exception {
            return factoryBeanMapper;
        }

        @Override
        public Class<?> getObjectType() {
            return FactoryBeanMapper.class;
        }

        @Override
        public boolean isSingleton() {
            return true;
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            System.err.println("GmpApplicationContextInitializer3 -> GmpFactoryBean -> afterPropertiesSet");
        }
    }

    protected class FactoryBeanMapper {
    }
}
