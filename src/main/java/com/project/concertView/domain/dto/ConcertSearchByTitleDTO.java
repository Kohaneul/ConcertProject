package com.project.concertView.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 공연제목 검색을 통한 공연정보 조회를 위한 DTO 클래스
 * 참고 : 공연예술통합전산망 API
 * 작성일 : 2023.08.02
 * */
@Getter
@Setter
@AllArgsConstructor
public class ConcertSearchByTitleDTO {
    private String stDate;  // 공연시작일
    private String edDate;  // 공연종료일
    private int rows;   // 한페이지에 보여지는 데이터 수 (default 10으로 지정)
    private int cpage;  //현재 페이지
    private String shprfnm; //콘서트 제목(문자열 포함)

    //기본생성자 호출시 클래식이라는 문자열을 포함하며 현 시점 기준 한달전 ~ 한달 후로 조회 가능
    public ConcertSearchByTitleDTO() {
        this.stDate = LocalDateTime.now().minusDays(30).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.edDate = LocalDateTime.now().plusDays(30).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.rows = 10;
        this.cpage = 1;
        this.shprfnm="클래식";
    }




}
