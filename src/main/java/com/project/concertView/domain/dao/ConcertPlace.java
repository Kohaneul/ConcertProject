package com.project.concertView.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 공연시설 상세 조회 클래스
 * 참고 : 공연예술통합전산망 API
 * 작성일 : 2023.08.02
 * */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConcertPlace {
    private String mt10id;      //공연시설 id
    private String fcltynm;     //공연시설명
    private String mt13cnt;     //공연장 수
    private String seatscale;   //객석 수
    private String fcltychartr; //시설특성
    private String telno;       //전화번호
    private String relateurl;   //홈페이지
    private String adres;       //주소
    private String opende;      //개관연도
    private String la;          //위도
    private String lo;          //경도

}
