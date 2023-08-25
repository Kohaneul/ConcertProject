package com.project.concertView.domain.dao.concert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
public class LikeConcertInsert {
    @NotEmpty
    private Long memberId;
    @NotEmpty
    private String mt20id;

}
