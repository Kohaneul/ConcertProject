package com.project.concertView.domain.dao.concert;

import lombok.*;

/**
 * 공연 상세 조회 클래스 中 소개이미지 목록
 * 참고 : 공연예술통합전산망 API
 * */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StyURL {
    private String styurl;  //이미지 경로
}
