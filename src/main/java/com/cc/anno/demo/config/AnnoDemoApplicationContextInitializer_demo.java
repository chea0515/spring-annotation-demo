package com.cc.anno.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

/**
 * 参考类：
 * @see org.springframework.web.WebApplicationInitializer
 * @see org.springframework.web.bind.annotation.RequestMapping
 * @see org.springframework.web.context.ServletContextAware
 * @see org.springframework.web.context.ConfigurableWebApplicationContext
 * @see org.springframework.context.ApplicationContextAware
 * @see org.springframework.context.annotation.ImportBeanDefinitionRegistrar
 * @see ClassPathBeanDefinitionScanner
 * @see org.springframework.context.annotation.ConfigurationClassPostProcessor
 * @see org.springframework.beans.factory.BeanFactoryAware
 * @see FactoryBean
 * @see InitializingBean
 * @see org.springframework.beans.factory.BeanNameAware
 * @see BeanDefinitionRegistryPostProcessor
 * @see org.springframework.beans.factory.config.BeanPostProcessor
 * @see org.springframework.beans.BeanInfoFactory
 * @see org.mybatis.spring.annotation.MapperScan
 * @see org.mybatis.spring.mapper.MapperScannerConfigurer
 * @see org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
 * @see org.springframework.core.type.filter.AssignableTypeFilter
 */
public class AnnoDemoApplicationContextInitializer_demo implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final Logger logger = LoggerFactory.getLogger(AnnoDemoApplicationContextInitializer_demo.class);

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        logger.info("--> AnnoDemoApplicationContextInitializer initialize start");
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
            // 自定义bean扫描
            TypeFilter includeFilter = new AnnotationTypeFilter(InitAnnoDemoBean.class);
            classPathBeanDefinitionScanner.addIncludeFilter(includeFilter);
            // 执行扫描,添加bean
            classPathBeanDefinitionScanner.scan(basePackages);

            //ClassPathScanningCandidateComponentProvider beanScanner = new ClassPathScanningCandidateComponentProvider(false, context.getEnvironment());
            //TypeFilter includeFilter = (metadataReader, metadataReaderFactory) -> metadataReader.getClassMetadata().isConcrete();
            /*
            Set<BeanDefinition> beanDefinitions = beanScanner.findCandidateComponents(basePackages);
            for (BeanDefinition beanDefinition : beanDefinitions) {
                String beanName = beanDefinition.getBeanClassName();
                System.err.println(beanName);
            }*/
            //String beanName = "gmpRegistryFactoryBean";
            //BeanDefinitionBuilder bdb = BeanDefinitionBuilder.rootBeanDefinition(AnnoDemoRegistryFactoryBean.class);
            //BeanDefinitionBuilder bdb = BeanDefinitionBuilder.genericBeanDefinition(AnnoDemoRegistryFactoryBean.class);
            //registry.registerBeanDefinition(beanName, bdb.getBeanDefinition());
            //bdb.setLazyInit(true);
            //bdb.setScope("singleton");
            //bdb.setAutowireMode(AUTOWIRE_BY_TYPE);
            //registry.registerBeanDefinition(beanName, bdb.getRawBeanDefinition());
            //GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            //beanDefinition.setScope("singleton");
            //beanDefinition.setBeanClass(AnnoDemoRegistryFactoryBean.class);
            //beanDefinition.setLazyInit(true);
            //beanDefinition.setAutowireCandidate(true);
            //RootBeanDefinition beanDefinition = new RootBeanDefinition(AnnoDemoRegistryFactoryBean.class);
            //registry.registerBeanDefinition(beanName, beanDefinition);
        }

        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        }
    }

    /* FactoryBean */
    protected class AnnoDemoRegistryFactoryBean implements FactoryBean<AnnoDemoFactoryBeanMapper>, InitializingBean {
        // factory bean mapper
        private AnnoDemoFactoryBeanMapper beanMapper = new AnnoDemoFactoryBeanMapper();

        public AnnoDemoRegistryFactoryBean(BeanDefinitionRegistry registry) {
            beanMapper.setBeanDefinitionRegistry(registry);
        }

        @Override
        public AnnoDemoFactoryBeanMapper getObject() {
            return this.beanMapper;
        }

        @Override
        public Class<?> getObjectType() {
            return this.beanMapper.getClass();
        }

        @Override
        public boolean isSingleton() {
            return true;
        }

        @Override
        public void afterPropertiesSet() {
            beanMapper.deScan();
        }
    }

    private class AnnoDemoFactoryBeanMapper {
        // 扫描器
        private AnnoDemoClassPathBeanDefinitionScanner beanDefinitionScanner;
        // 扫描路径
        private String[] basePackages = {"com.cc.anno.demo"};
        // 扫描过滤器
        private TypeFilter includeFilter = new AnnotationTypeFilter(InitAnnoDemoBean.class);

        public void setBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
            beanDefinitionScanner = new AnnoDemoClassPathBeanDefinitionScanner(registry, false);
        }

        public void deScan() {
            if (beanDefinitionScanner != null) {
                beanDefinitionScanner.addIncludeFilter(includeFilter);
                beanDefinitionScanner.beanPackageScan(basePackages);
            }
        }
    }

    protected class AnnoDemoClassPathBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {
        public AnnoDemoClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
            super(registry);
        }
        public AnnoDemoClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
            super(registry, useDefaultFilters);
        }

        public void beanPackageScan(String... basePackages) {
            super.doScan(basePackages);
            //Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
            //for (BeanDefinitionHolder bdh : beanDefinitionHolders) {
            //  this.getRegistry().registerBeanDefinition(bdh.getBeanName(), bdh.getBeanDefinition());
            //}
        }
    }
}
