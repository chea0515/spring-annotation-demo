package com.cc.anno.demo.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.w3c.dom.Element;

import java.util.Set;

/**
 * @see org.springframework.web.WebApplicationInitializer
 * @see org.springframework.web.bind.annotation.RequestMapping
 * @see org.springframework.web.context.ServletContextAware
 * @see org.springframework.web.context.ConfigurableWebApplicationContext
 * @see org.springframework.context.ApplicationContextAware
 * @see org.springframework.context.annotation.ImportBeanDefinitionRegistrar
 * @see org.springframework.context.annotation.ClassPathBeanDefinitionScanner
 * @see org.springframework.context.annotation.ConfigurationClassPostProcessor
 * @see org.springframework.beans.factory.BeanFactoryAware
 * @see org.springframework.beans.factory.FactoryBean
 * @see org.springframework.beans.factory.InitializingBean
 * @see org.springframework.beans.factory.BeanNameAware
 * @see org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor
 * @see org.springframework.beans.factory.config.BeanPostProcessor
 * @see org.springframework.beans.BeanInfoFactory
 * @see org.mybatis.spring.annotation.MapperScan
 * @see org.mybatis.spring.mapper.MapperScannerConfigurer
 * @see org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
 * @see org.springframework.core.type.filter.AssignableTypeFilter
 */
public class GmpApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        System.err.println("tomcat start --> GmpApplicationContextInitializer");
        //applicationContext.getBeanFactory();
        applicationContext.addBeanFactoryPostProcessor(new GmpRegistryPostProcessor());
        //applicationContext.refresh();
        //new GmpBeanDefinitionParser();
        String[] bdns = applicationContext.getBeanDefinitionNames();
        for (String bdn : bdns) {
            System.out.println(bdn);
        }
    }

    /* Processor */
    protected class GmpRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
        @Override
        public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
            System.err.println("GmpRegistryPostProcessor -> postProcessBeanDefinitionRegistry");

            String basePackages = "com.cc.anno.demo";
            //ClassPathBeanDefinitionScanner beanScanner = new ClassPathBeanDefinitionScanner(registry, false);
            GmpClassPathBeanDefinitionScanner beanScanner = new GmpClassPathBeanDefinitionScanner(registry, false);
            //ClassPathScanningCandidateComponentProvider beanScanner = new ClassPathScanningCandidateComponentProvider(false, context.getEnvironment());
            //TypeFilter includeFilter = (metadataReader, metadataReaderFactory) -> metadataReader.getClassMetadata().isConcrete();
            TypeFilter includeFilter = new AnnotationTypeFilter(Token.class);
            beanScanner.addIncludeFilter(includeFilter);
            beanScanner.setBasePackages(basePackages);
            beanScanner.registerBean();
            /*
            //Set<BeanDefinition> beanDefinitions = beanScanner.findCandidateComponents(basePackages);
            Set<BeanDefinition> beanDefinitions = beanScanner.findCandidateComponents(basePackages);
            for (BeanDefinition beanDefinition : beanDefinitions) {
                String beanName = beanDefinition.getBeanClassName();
                System.err.println(beanName);
            }*/

            //String beanName = "gmpFactoryBeanMapper";
            //BeanDefinitionBuilder bdb = BeanDefinitionBuilder.rootBeanDefinition(GmpRegistryFactoryBean.class);
            //registry.registerBeanDefinition(beanName, bdb.getBeanDefinition());
            //GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            //beanDefinition.setScope("singleton");
            //beanDefinition.setBeanClass(FactoryBeanMapper.class);
            //beanDefinition.setLazyInit(true);
            //beanDefinition.setAutowireCandidate(true);
            //RootBeanDefinition beanDefinition = new RootBeanDefinition(GmpRegistryFactoryBean.class);
            //registry.registerBeanDefinition(beanName, beanDefinition);
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

    public class FactoryBeanMapper {
        /*
        private TokenAnnotationIntrospector tokenIntrospector = new TokenAnnotationIntrospector();

        class TokenAnnotationIntrospector {
        }*/
    }

    protected class GmpClassPathBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {

        private String[] basePackages;

        public GmpClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
            super(registry);
        }

        public GmpClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
            super(registry, useDefaultFilters);
        }

        public void registerBean() {

            Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
            /*
            for (BeanDefinitionHolder bdh : beanDefinitionHolders) {
                this.getRegistry().registerBeanDefinition(bdh.getBeanName(), bdh.getBeanDefinition());
            }*/
        }

        public void setBasePackages(String... basePackages) {
            this.basePackages = basePackages;
        }
    }
}
