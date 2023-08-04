package com.project.concertView.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
/**
 * 공연정보 상세 조회를 위한 DTO 클래스
 * 참고 : 공연예술통합전산망 API
 * 작성일 : 2023.08.02
 * */
@Getter
@Setter
@AllArgsConstructor
public class ConcertDetailInfoDTO {
    private String mt20id;  //
}
