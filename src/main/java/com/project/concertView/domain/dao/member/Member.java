package com.project.concertView.domain.dao.member;

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
    private String loginId;
    private String password;
    private LocalDateTime joinDay;
    private String emailAccountWrite;
    private String phoneNumber;
    private String birth;

    public Member(String name, String address, String detailAddress, String email, String loginId, String password, LocalDateTime joinDay, String emailAccountWrite, String phoneNumber, String birth) {
        this.name = name;
        this.address = address;
        this.detailAddress = detailAddress;
        this.email = email;
        this.loginId = loginId;
        this.password = password;
        this.joinDay = joinDay;
        this.emailAccountWrite = emailAccountWrite;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
    }
}
