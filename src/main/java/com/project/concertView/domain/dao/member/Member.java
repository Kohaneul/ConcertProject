package com.project.concertView.domain.dao.member;

import com.project.concertView.domain.dao.concert.ConcertData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private Long id;
    private String name;
    private String address;
    private String loginId;
    private String password;
    private LocalDateTime joinDay;
}
