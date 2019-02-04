package com.emil.logreader.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogReaderAspect {

    @Around("@annotation(LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.nanoTime();

        Object proceed = joinPoint.proceed();

        log.debug("{} executed in {} ms",joinPoint.getSignature(),(System.nanoTime() - start) / 1_000_000);
        return proceed;
    }
}
