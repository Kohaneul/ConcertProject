package com.project.concertView.domain.config.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

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
    private void setPointCuts() {
    };
    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    private void setPostMappingLog(){};

    @Around("setPointCuts() && setPostMappingLog()")
    public Object execute(ProceedingJoinPoint joinPoint) {
        Object result = null;
        try {
            result = joinPoint.proceed(joinPoint.getArgs());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        finally{
            Arrays.stream(joinPoint.getArgs()).forEach(i -> log.info("Args={}", i.getClass().getTypeName()));
        }
        return result;
    }


}
