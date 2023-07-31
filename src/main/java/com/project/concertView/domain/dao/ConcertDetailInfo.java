package com.project.concertView.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Getter
@Setter
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class ConcertDetailInfo {
    private ConcertData concertData;
    private ConcertDetail concertDetail;
    private List<StyURL> styurls;

}
