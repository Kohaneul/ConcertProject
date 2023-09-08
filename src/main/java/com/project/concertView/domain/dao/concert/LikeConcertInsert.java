package com.project.concertView.domain.dao.concert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
/**
 * 관심있는 공연에 대하여 좋아요 표시해주는 클래스
 * 참고 : 공연예술통합전산망 API
 * */
@Getter
@Setter
@AllArgsConstructor
public class LikeConcertInsert {
    @NotEmpty
    private Long memberId;  //로그인 사용자 아이디
    @NotEmpty
    private String mt20id;  //공연 정보 pk값
}
