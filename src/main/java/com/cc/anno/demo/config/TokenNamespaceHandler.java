package com.cc.anno.demo.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class TokenNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("token-configurer", new TokenBeanDefinitionParser());
    }
}
