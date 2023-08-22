package com.project.concertView.domain.entity;

import lombok.Getter;
public enum EmailAddr {
    NAVER("네이버","naver.com"),
    GMAIL("지메일","gmail.com"),
    KAKAO("카카오","kakao.com"),
    DAUM("다음","daum.com"),

    ETC("직접입력","");
    @Getter
    private final String key;
    @Getter
    private final String value;
    EmailAddr(String key, String value){
        this.key = key;
        this.value = value;
    }
}
