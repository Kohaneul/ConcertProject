package com.project.concertView.domain.dao;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
/**
 * 공연 상세 조회 클래스
 * 참고 : 공연예술통합전산망 API
 * 작성일 : 2023.08.02
 * */
@Getter
@Setter
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class ConcertDetail {

    private String mt10id;      //공연시설ID
    private String prfcast;     //공연출연진
    private String prfcrew;     //공연제작진
    private String prfruntime;  //공연런타임
    private String prfage;      //공연 관람 연령
    private String entrpsnm;    //제작사
    private String pcseguidance;//티켓가격
    private String sty;         //줄거리
    private String dtguidance;  //공연시간

}
