package com.project.concertView.domain.dao.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class UpdatePassword {
    @NotNull
    private Long id;
    @NotBlank(message = "비밀번호 입력해주세요")
    private String password;
    @NotBlank(message = "비밀번호(확인용) 입력해주세요")
    private String passwordChcek;
    private Boolean passwordEqualsCheck;
}
