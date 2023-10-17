package com.project.concertView.domain.dto;

import com.project.concertView.domain.entity.Signgucode;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 * 공연정보 조회를 위한 DTO 클래스
 * 참고 : 공연예술통합전산망 API
 * 작성일 : 2023.08.02
 * */
@Getter
@Setter
@AllArgsConstructor
public class ConcertSearchInfoDTO {
    private String stDate;  //공연 시작일
    private String edDate;  //공연 종료일
//    private int rows = 10;   // 한페이지에 보여지는 데이터 수 (default 10으로 지정)
//    private int cpage;  //현재 페이지
    private String signgucode; //공연장소에 대한 지역코드


    //기본생성자 호출시 현일자 공연정보 ~ 일주일 뒤 공연정보에 대한 내용이 셋팅되도록 설정함
    public ConcertSearchInfoDTO() {
        this.stDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.edDate = LocalDateTime.now().plusDays(7).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        this.cpage = 1;
        this.signgucode = Signgucode.valueOf("SEOUL").getValue();
    }

}
