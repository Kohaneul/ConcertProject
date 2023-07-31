package com.project.concertView.domain.dao;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class ConcertDetail {

    private String mt10id;
    private String prfcast;
    private String prfcrew;
    private String prfruntime;
    private String prfage;
    private String entrpsnm;
    private String pcseguidance;
    private String sty;
    private String dtguidance;

}
