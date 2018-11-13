package com.cc.anno.demo.config;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.AbstractAdvisingBeanPostProcessor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;

@InitAnnoDemoBean
public class AnnoDemoMethodPostProcessor extends AbstractAdvisingBeanPostProcessor implements InitializingBean {
    // token
    private Class<? extends Annotation> tokenAnnotationType = Token.class;

    @Override
    public void afterPropertiesSet() throws Exception {
        // 基于类的注解
        Pointcut typePointcut = new AnnotationMatchingPointcut(this.tokenAnnotationType, true);
        // 基于方法的注解
        Pointcut methodPointcut = AnnotationMatchingPointcut.forMethodAnnotation(this.tokenAnnotationType);
        // 将类上的注解映射到每个方法上
        ComposablePointcut composablePointcut = new ComposablePointcut(typePointcut);
        Pointcut pointcut = composablePointcut.union(methodPointcut);
        this.advisor = new DefaultPointcutAdvisor(pointcut, new GmpMethodInterceptor());
    }

    /* aop: 基于方法拦截 */
    protected class GmpMethodInterceptor implements MethodInterceptor {

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            Token tokenAnn = AnnotationUtils.findAnnotation(invocation.getMethod(), Token.class);
            if (tokenAnn == null) {
                tokenAnn = AnnotationUtils.findAnnotation(invocation.getThis().getClass(), Token.class);
            }

            String val = tokenAnn.value();
            System.err.println("--> token annotation value: " + val);

            return invocation.proceed();
        }
    }
}