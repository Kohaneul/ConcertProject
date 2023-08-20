package com.project.concertView.domain.entity;

import lombok.Getter;
public enum EmailAddr {
    NAVER(1," @naver.com"),
    GMAIL(2," @gmail.com"),
    KAKAO(3," @kakao.com"),
    DAUM(4," @daum.com"),

    ETC(5,"직접입력");
    @Getter
    private final Integer key;
    @Getter
    private final String value;
    EmailAddr(Integer key,String value){
        this.key =key;
        this.value = value;
    }
}
