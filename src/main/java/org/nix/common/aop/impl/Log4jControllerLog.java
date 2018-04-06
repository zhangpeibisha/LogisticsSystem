package org.nix.common.aop.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.nix.common.aop.ControllerLog;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class Log4jControllerLog implements ControllerLog {
    private final static Log log = LogFactory.getLog("nix-");

    @Override
    public void before(JoinPoint joinPoint) {
        log.info("===============start method===================");
        log.info(joinPoint.getSignature().getDeclaringTypeName());
        log.info(joinPoint.getSignature().getName());
        log.info(joinPoint.getArgs());
        log.info("==============================================");
    }

    @Override
    public void after(Object returnObject) {
        log.info("+++++++++++++++++method return+++++++++++++++++");
        log.info("return:" + (returnObject.toString().length() > 1024 ? returnObject.hashCode() : returnObject));
        log.info("+++++++++++++++++++++++++++++++++++++++++++++++");
    }
}
