package com.tutorial.config.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Aspect
@Order(0)
public class AopAspectConfig {

    private Logger logger = LoggerFactory.getLogger(AopAspectConfig.class);

    @Pointcut("within(com.tutorial.controller.*) && execution(public * *(..))")
    public void poincutAllController() {
    }

    @Pointcut("within(com.tutorial.service.impl.*) && execution(public !static * *(..))")
    public void poincutAllService() {
    }

    @Before(value = "poincutAllController() || poincutAllService()")
    public void before(JoinPoint joinPoint) {
        logger.debug("[@Before] {}", joinPoint);
    }

    @After(value = "poincutAllController() || poincutAllService()")
    public void after(JoinPoint joinPoint) {
        logger.debug("[@After] {}", joinPoint);
    }

    @AfterReturning(value = "poincutAllController() || poincutAllService()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        logger.debug("[@AfterReturning] {} returned with value: {}", joinPoint, result);
    }

    @AfterThrowing(pointcut = "poincutAllController() || poincutAllService()", throwing = "ex")
    public void afterThrowing(Exception ex) throws Throwable {
        logger.debug("[@AfterThrowing] {}", ex);
    }

}