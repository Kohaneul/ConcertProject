package com.project.concertView.domain.config.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;

/**
 * SPRING AOP
 * 정해진 패키지에 속한 클래스 파일에서 @GetMapping 어노테이션이 붙은 메소드 호출될시 로그 표시
 * 로그 내용 : 파라미터 타입
 *
 * */
@Aspect
@Slf4j
public class ConcertAspect {
    //1. Pointcut 대상 : com.project.concertView.web 안에 속해 있는 클래스파일
    @Pointcut("execution(* com.project.concertView.web..*.*(..))")
    private void setPointCuts() {
    };
    //2. Pointcut 대상 : @GetMapping 어노테이션이 있는 메소드
    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    private void setPostMappingLog(){};

    //1+2 교집합인 메소드 호출시 => 매개변수에 대한 로그 찍는다
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
