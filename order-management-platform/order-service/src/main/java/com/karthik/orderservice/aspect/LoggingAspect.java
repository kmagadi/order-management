package com.karthik.orderservice.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.*;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(* com.karthik.orderservice.service..*(..))")
    public void serviceLayer() {}

    @Pointcut("execution(* com.karthik.orderservice.controller..*(..))")
    public void controllerLayer() {}

    @Around("serviceLayer() || controllerLayer()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();

        log.info("Entering: {} with arguments {}",
                joinPoint.getSignature(),
                joinPoint.getArgs());

        Object result = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;

        log.info("Exiting: {} executed in {} ms",
                joinPoint.getSignature(),
                executionTime);

        return result;
    }

    @AfterThrowing(pointcut = "serviceLayer()", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {

        log.error("Exception in {}: {}",
                joinPoint.getSignature(),
                ex.getMessage());
    }
}