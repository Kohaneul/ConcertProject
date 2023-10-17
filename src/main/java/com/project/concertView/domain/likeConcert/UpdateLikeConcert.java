package com.project.concertView.domain.likeConcert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLikeConcert {
    private Long memberId;
    private String mt20id;

}
