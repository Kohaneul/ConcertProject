package com.project.concertView.domain.dto;

import com.project.concertView.domain.entity.Signgucode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 공연시설 조회를 위한 DTO 클래스
 * 참고 : 공연예술통합전산망 API
 * 작성일 : 2023.08.02
 * */
@Getter
@Setter
@AllArgsConstructor
public class ConcertPlaceSearchDTO {
    private int cpage;  //현재페이지
    private int rows;   //페이지 당 목록수
    private String shprfnmfct;  //공연시설명
    private String signgucode;  //지역코드
    public ConcertPlaceSearchDTO() {
        this.cpage = 1;
        this.shprfnmfct = "";
        this.signgucode = "";
        this.rows = 10;
    }
    public ConcertPlaceSearchDTO(int cpage, String shprfnmfct,String signgucode){
        this.signgucode  = signgucode;
        this.shprfnmfct = shprfnmfct;
        this.cpage = cpage;
    }
}
