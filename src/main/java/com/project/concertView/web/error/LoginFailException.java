package com.project.concertView.web.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginFailException extends RuntimeException{
    private String message;

    public LoginFailException() {
        this.message = "인증에 실패하였습니다.";
    }
}
