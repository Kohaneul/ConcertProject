package com.project.concertView.domain.dao.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
public class FindPassword {

    @NotBlank(message = "로그인 아이디를 입력해주세요")
    private String loginId;
    @NotBlank(message = "이름을 입력해주세요")
    private String name;
    @NotBlank(message = "연락처를 입력해주세요")
    private String phoneNumber;
    @NotBlank(message = "생년월일을 입력해주세요")
    @Pattern(regexp = "^([12]\\d{3}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01]))$", message = "형식에 맞게 입력해주세요(YYYYMMDD)")
    private String birth;
    @NotBlank(message = "이메일 주소를 입력해주세요")
    private String email;
    @NotBlank
    private String email2;

}
