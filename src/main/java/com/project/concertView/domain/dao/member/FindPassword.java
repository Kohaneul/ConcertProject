package com.project.concertView.domain.dao.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
/**
 * 비밀번호 찾기 클래스
 * 사용자가 입력한 loginId, name, phoneNumber, birth, email 에 대한 정보가 DB 내 정보와 일치시 비밀번호 변경이 가능하도록 설정함
 *
 * */
@Getter
@Setter
@AllArgsConstructor
public class FindPassword {
    @NotBlank(message = "로그인 아이디를 입력해주세요")
    private String loginId; //로그인 아이디
    @NotBlank(message = "이름을 입력해주세요")
    private String name;    //이름
    @NotBlank(message = "연락처를 입력해주세요")
    private String phoneNumber; //연락처
    @NotBlank(message = "생년월일을 입력해주세요")
    @Pattern(regexp = "^([12]\\d{3}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01]))$", message = "형식에 맞게 입력해주세요(YYYYMMDD)")
    private String birth;   //생년월일
    @NotBlank(message = "이메일 주소를 입력해주세요")
    private String email;   //이메일주소

}
