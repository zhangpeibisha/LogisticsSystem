package org.nix.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
class AopLog {
    @Autowired
    private ControllerLog log;
    @Pointcut("execution(* org.nix.controller..*(..))")
    public void controllerMethod() {}

    @Before("controllerMethod()")
    public void before(JoinPoint joinPoint) {
        log.before(joinPoint);
    }
    @AfterReturning(returning = "object",pointcut = "controllerMethod()")
    public void after(Object object) {
        log.after(object);
    }
}