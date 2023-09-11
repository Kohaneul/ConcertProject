package com.project.concertView.domain.dao.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
/**
 * 비밀번호 변경 클래스
 *
 * */
@Getter
@Setter
@AllArgsConstructor
public class UpdatePassword {
    @NotNull
    private Long id;    //PK값
    @NotBlank(message = "비밀번호 입력해주세요")
    private String password;    //비밀번호
    @NotBlank(message = "비밀번호(확인용) 입력해주세요")
    private String passwordCheck;// 비밀번호(재입력용)
    private Boolean passwordEqualsCheck;    //비밀번호 일치여부
}
