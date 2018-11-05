package com.cc.anno.demo.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class GmpBeanNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        System.err.println("GmpBeanNamespaceHandler -> init");
        registerBeanDefinitionParser("gmpInfo", new GmpBeanDefinitionParser());
    }
}
