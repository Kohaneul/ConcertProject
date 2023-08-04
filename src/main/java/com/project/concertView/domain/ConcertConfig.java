package com.project.concertView.domain;

import com.project.concertView.domain.aop.ConcertAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * 공연 관련 설정 클래스
 *
 * */
@Configuration
public class ConcertConfig {

    /**
     * AOP : 클래스가 속해있는 패키지가 com.project.concertView.web에 속해있는 클래스의 메소드가 호출될시
     *      로그를 남김 (로그 내용 : 해당 메소드의 파라미터 타입)
     * */
    @Bean
    public ConcertAspect concertAspect(){
        return new ConcertAspect();
    }

}
