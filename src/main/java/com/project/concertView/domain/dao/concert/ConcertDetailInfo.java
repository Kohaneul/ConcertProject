package com.project.concertView.domain.dao.concert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
/**
 * 공연 상세 조회 클래스 : 공연 상세조회 클래스 + 공연목록 조회 클래스 + 소개이미지 목록
 * 참고 : 공연예술통합전산망 API
 * 작성일 : 2023.08.02
 * */
@Getter
@Setter
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class ConcertDetailInfo {
    private ConcertData concertData;    //공연 목록 조회 클래스
    private ConcertDetail concertDetail; //공연 상세 조회 클래스
    private List<StyURL> styurls;   //소개이미지 목록

}
