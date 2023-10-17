package com.project.concertView.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;


@Getter
@AllArgsConstructor
public class LikeConcertDTO {
    private Long memberId;
    private Set<String> mt20id;

}
