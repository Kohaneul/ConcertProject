package com.project.concertView.domain;

import com.project.concertView.domain.aop.ConcertAspect;
import com.project.concertView.web.dto.ConcertSearchInfoDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConcertConfig {

    @Bean
    public ConcertSearchInfoDTO concertSearchInfoDTO(){
        return new ConcertSearchInfoDTO();
    }

    @Bean
    public ConcertAspect concertAspect(){
        return new ConcertAspect();
    }

}
