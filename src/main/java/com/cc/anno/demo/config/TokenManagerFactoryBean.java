package com.cc.anno.demo.config;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

public class TokenManagerFactoryBean implements ServletContextAware, FactoryBean<TokenManager>, InitializingBean, BeanNameAware {

    private TokenManager tokenManager;
    private ServletContext servletContext;

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public void afterPropertiesSet() {
        System.err.println("TokenManagerFactoryBean --> afterPropertiesSet");
        this.tokenManager = new TokenManager();
        if (this.servletContext != null) {
            System.err.println(this.servletContext.getContextPath());
        }
    }

    @Override
    public Class<?> getObjectType() {
        return TokenManager.class;
    }

    @Override
    public TokenManager getObject() {
        return this.tokenManager;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void setBeanName(String name) {
        System.err.println(name);
    }
}
