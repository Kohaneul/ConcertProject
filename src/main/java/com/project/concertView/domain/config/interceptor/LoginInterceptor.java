package com.project.concertView.domain.config.interceptor;

import com.project.concertView.domain.entity.SessionValue;
import com.project.concertView.web.error.LoginFailException;
import com.project.concertView.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
