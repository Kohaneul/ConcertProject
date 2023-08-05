package com.project.concertView.domain.dto;

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
    private String cpage;
    private String rows;
    private String shprfnmfct;

    public ConcertPlaceSearchDTO() {
        this.cpage = "1";
        this.rows="5";
        this.shprfnmfct = "예술의전당";
    }
}
