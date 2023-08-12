package com.project.concertView.domain.dao.concert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * 공연시설 조회 클래스
 * 참고 : 공연예술통합전산망 API
 * 작성일 : 2023.08.02
 * */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConcertPlaceSearch {
    private String mt10id;        //공연시설id
    private String fcltynm;       //공연시설명
    private String mt13cnt;      //공연장 수
    private String fcltychartr; //시설특성
    private String sidonm;      //지역(시도)
    private String gugunnm;     //지역(구군)
    private String opende;      //개관연도
}
