package com.project.concertView.domain.config;

import com.project.concertView.domain.config.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * 로그인 인터셉터 설정 클래스
 *
 * */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/concert/like/*","/member/info/*","/member/logOut").order(1)
                .excludePathPatterns("/concert/detailView/img");
    }

}
