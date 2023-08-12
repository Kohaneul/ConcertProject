package com.project.concertView.domain.dao.concert;

import lombok.*;

/**
 * 공연 목록 조회 클래스
 * 참고 : 공연예술통합전산망 API
 * 작성일 : 2023.08.02
 * */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ConcertData {
    private String mt20id;    // 공연ID
    private String prfnm;     // 공연명
    private String prfpdfrom; // 공연 시작일
    private String prfpdto;   //공연 종료일
    private String fcltynm;   //공연 시설명(공연장명)
    private String poster;    //공연 종료일
    private String genrenm;   //공연 장르명
    private String prfstate;  //공연 상태
    private String openrun;   //오픈런 여부
}
