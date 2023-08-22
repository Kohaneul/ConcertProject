package com.project.concertView.web.interceptor;

import com.project.concertView.domain.dao.member.annotation.LoginCheck;
import com.project.concertView.domain.entity.SessionValue;
import com.project.concertView.web.error.LoginFailException;
import com.project.concertView.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {
    private final MemberService memberService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        LoginCheck loginCheck = handlerMethod.getMethodAnnotation(LoginCheck.class);
        if(loginCheck==null){
            throw new LoginFailException();
        }
        return true;


    }

}
