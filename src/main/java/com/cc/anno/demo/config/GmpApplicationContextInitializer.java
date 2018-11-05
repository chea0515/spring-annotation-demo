package com.cc.anno.demo.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.w3c.dom.Element;

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
public class GmpApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        System.err.println("tomcat start --> GmpApplicationContextInitializer");
        //this.context = applicationContext;
        //applicationContext.getBeanFactory();
        applicationContext.addBeanFactoryPostProcessor(new GmpRegistryPostProcessor());
        //new GmpBeanDefinitionParser();
    }

    /* Processor */
    protected class GmpRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
        @Override
        public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
            System.err.println("GmpRegistryPostProcessor -> postProcessBeanDefinitionRegistry");
            String beanName = "gmpRegistryFactoryBean";
            //BeanDefinitionBuilder bdb = BeanDefinitionBuilder.rootBeanDefinition(GmpRegistryFactoryBean.class);
            //registry.registerBeanDefinition(beanName, bdb.getBeanDefinition());
            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(GmpRegistryFactoryBean.class);
            registry.registerBeanDefinition(beanName, beanDefinition);
        }

        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
            System.err.println("GmpRegistryPostProcessor -> postProcessBeanFactory");
        }
    }

    /* FactoryBean */
    protected class GmpRegistryFactoryBean implements FactoryBean<FactoryBeanMapper>, InitializingBean {

        private FactoryBeanMapper mapper;

        @Override
        public FactoryBeanMapper getObject() {
            return this.mapper;
        }

        @Override
        public Class<?> getObjectType() {
            return (this.mapper == null ? null : this.mapper.getClass());
        }

        @Override
        public boolean isSingleton() {
            return true;
        }

        @Override
        public void afterPropertiesSet() {
            if (mapper == null) mapper = new FactoryBeanMapper();
        }
    }

    /* Parser */
    protected class GmpBeanDefinitionParser implements BeanDefinitionParser {
        @Override
        public BeanDefinition parse(Element element, ParserContext parserContext) {
            Object source = parserContext.extractSource(element);
            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(GmpRegistryFactoryBean.class);
            beanDefinition.setSource(source);
            beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
            return beanDefinition;
        }
    }

    protected class FactoryBeanMapper {
        private TokenAnnotationIntrospector tokenIntrospector = new TokenAnnotationIntrospector();

        class TokenAnnotationIntrospector {
        }
    }
}
