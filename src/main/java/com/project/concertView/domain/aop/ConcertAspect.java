package com.project.concertView.domain.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;

@Aspect
@Slf4j
public class ConcertAspect {
    @Pointcut("execution(* com.project.concertView.web..*.*(..))")
    private void setPointCuts(){};

    @Around("setPointCuts()")
    public Object execute(ProceedingJoinPoint joinPoint){
        Object result = null;
        try {
            Arrays.stream(joinPoint.getArgs()).forEach(i->log.info("args={}",i.getClass().getTypeName()));
            result = joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}
