package com.project.concertView.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
/**
 * 로그인을 위한 DTO 클래스
 * 작성일 : 2023.09.08
 * */
@Getter
@Setter
@AllArgsConstructor
public class LoginMemberDTO {
    @NotEmpty(message = "아이디를 입력해주세요")
    private String loginId; //로그인 아이디
    @NotEmpty(message = "비밀번호를 입력해 주세요")
    private String password;    //비밀번호
}
