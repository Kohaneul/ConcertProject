package com.project.concertView.domain.dao.concert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * 관심있는 공연에 대하여 좋아요를 누르게 하여 이를 화면단에 보여주는 클래스
 * 참고 : 공연예술통합전산망 API
 * */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LikeConcert {
    private Long memberId;  //로그인 사용자 아이디
    private String mt20id;  //공연 정보 pk값
}
