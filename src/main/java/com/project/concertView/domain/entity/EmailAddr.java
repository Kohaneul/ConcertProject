package com.project.concertView.domain.entity;

import lombok.Getter;
public enum EmailAddr {
    NAVER("@naver.com"),
    GMAIL("@gmail.com"),
    KAKAO("@kakao.com"),
    DAUM("@daum.com"),

    ETC("직접입력");

    @Getter
    private final String value;
    EmailAddr(String value){
        this.value = value;
    }
}
