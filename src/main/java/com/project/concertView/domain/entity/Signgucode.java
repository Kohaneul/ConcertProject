package com.project.concertView.domain.entity;

import lombok.Getter;
/**
 * 공연 장소 조회시 (광역)시별 조회할 수 있도록 ENUM 정의
 * */
public enum Signgucode {
    SEOUL("서울특별시","11"),
    BUSAN("부산광역시","26"),
    DAEGU("대구광역시","27"),
    INCHEON("인천광역시","28"),
    GWANGJU("광주광역시","29"),
    DAEJEON("대전광역시","30"),
    ULSAN("울산광역시","31"),
    SEJONG("세종특별자치시","36"),
    KYEONGGI("경기도","41"),
    KANGWON("강원도","51"),
    CHUNGCHUNGBUKDO("충북","43"),
    CHUNGCHUNGNAMDO("충남","44"),
    JEONBUK("전북","45"),
    JEONNAM("전남","46"),
    KYEONGBUK("경북","47"),
    KYEONGNAM("경남","48"),
    JAEJU("제주","50");
    @Getter
    private final String key;
    @Getter
    private final String value;
    Signgucode(String key, String value){
        this.key = key;
        this.value = value;
    }

}
