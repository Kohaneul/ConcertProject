package com.project.concertView.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * 공연 상세 조회 클래스 : 공연 상세조회 클래스 + 공연목록 조회 클래스 + 소개이미지 목록
 * 참고 : 공연예술통합전산망 API
 * 작성일 : 2023.08.02
 * */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConcertPlace {
    private String mt10id;
    private String fcltynm;
    private String mt13cnt;
    private String seatscale;
    private String fcltychartr;
    private String telno;
    private String relateurl;
    private String adres;
    private String opende;

    private String la;
    private String lo;

}
