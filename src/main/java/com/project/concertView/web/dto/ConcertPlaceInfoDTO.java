package com.project.concertView.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConcertPlaceInfoDTO {

    private String mt10id;

    public ConcertPlaceInfoDTO(String mt10id) {
        this.mt10id = mt10id;
    }
}
