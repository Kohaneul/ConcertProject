package com.project.concertView.domain.config.interceptor;

import com.project.concertView.domain.entity.SessionValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * SPRING INTERCEPTOR
 * 정해진 path 중 세션 값이 존재하지 않으면
 * 해당 path 진입 x -> 로그인 페이지 경로로 리다이랙트
 *
 * */
@RequiredArgsConstructor
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if(session.getAttribute(SessionValue.LOGIN_PK_ID_SESSION)!=null){
            return true;
        }
        else{
            response.sendRedirect("/member/login");
        }
        return false;

    }

}
