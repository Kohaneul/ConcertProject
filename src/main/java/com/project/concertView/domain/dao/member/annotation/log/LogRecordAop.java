package com.project.concertView.domain.dao.member.annotation.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;


@Aspect
@Slf4j
public class LogRecordAop {

    @Around("@annotation(logRecord)")
    public Object doLog(ProceedingJoinPoint joinPoint, LogRecord logRecord){
        try {
            log.info("joinPoint={}",joinPoint.getSignature().getName());
            return joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

    }

}
