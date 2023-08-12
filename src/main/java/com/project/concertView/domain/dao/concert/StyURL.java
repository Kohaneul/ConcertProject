package com.project.concertView.domain.dao.concert;

import lombok.*;
import org.springframework.stereotype.Component;

/**
 * 공연 상세 조회 클래스 中 소개이미지 목록
 * 참고 : 공연예술통합전산망 API
 * 작성일 : 2023.08.02
 * */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StyURL {
    private String styurl;  //이미지 경로

}
