package com.cc.anno.demo.config;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

import java.util.Date;

public class GmpBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    @Override
    protected Class<?> getBeanClass(Element element) {
        return GmpInfo.class;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        String name = element.getAttribute("name");
        String addTime = element.getAttribute("addTime");

        builder.addPropertyValue("name", name);
        builder.addPropertyValue("addTime", addTime);
    }
}
