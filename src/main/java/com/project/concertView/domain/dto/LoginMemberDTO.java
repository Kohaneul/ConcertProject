package com.project.concertView.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
public class LoginMemberDTO {
    @NotEmpty(message = "아이디를 입력해주세요")
    private String loginId;
    @NotEmpty(message = "비밀번호를 입력해주세요")
    private String password;
}
