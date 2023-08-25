package com.project.concertView.domain.dao.concert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LikeConcert {
    private Long memberId;
    private String mt20id;
}
