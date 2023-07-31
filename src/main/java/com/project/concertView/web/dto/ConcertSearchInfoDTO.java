package com.project.concertView.web.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
public class ConcertSearchInfoDTO {
    private String stDate;
    private String edDate;
    private int rows;
    private int cpage;

    public ConcertSearchInfoDTO() {
        this.stDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));;
        this.edDate = LocalDateTime.now().plusDays(7).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.rows = 10;
        this.cpage = 5;
    }



}
