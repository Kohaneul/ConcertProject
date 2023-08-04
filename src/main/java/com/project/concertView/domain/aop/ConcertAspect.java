package com.project.concertView.domain.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;
/**
 * SPRING AOP
 * 정해진 클래스가 호출될시 로그 표시
 * 로그 내용 : 파라미터 타입
 *
 * */
@Aspect
@Slf4j
public class ConcertAspect {
    @Pointcut("execution(* com.project.concertView.web..*.*(..))")
    private void setPointCuts(){};

    @Around("setPointCuts()")
    public Object execute(ProceedingJoinPoint joinPoint){
        Object result = null;
        try {
            log.info("{} 호출",joinPoint.getSignature().getName());
            Arrays.stream(joinPoint.getArgs()).forEach(i->log.info("매개변수 타입={}",i.getClass().getTypeName()));
            result = joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}
