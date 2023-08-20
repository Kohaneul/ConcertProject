package com.project.concertView.domain.dao.member;

import com.project.concertView.domain.dao.concert.ConcertData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private Long id;
    private String name;
    private String address;
    private String detailAddress;
    private String email;
    private String email2;
    private String loginId;
    private String password;
    private LocalDateTime joinDay;
    private String emailAccountWrite;
    private String phoneNumber;
    private String birth;

}
