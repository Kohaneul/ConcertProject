package com.project.concertView.domain.annotation.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
/**
 * @LogRecord 라는 어노테이션 사용을 위한 aop 클래스
 * */

@Aspect
@Slf4j
public class LogRecordAop {

    @Around("@annotation(logRecord)")
    public Object doLog(ProceedingJoinPoint joinPoint, LogRecord logRecord){
        try {
            //@LogRecord 라는 어노테이션이 붙게되면 하기 target 관련한 로그가 찍히게 된다.
            log.info("target={}",joinPoint.getSignature().getName());
            return joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

}
