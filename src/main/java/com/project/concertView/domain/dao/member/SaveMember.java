package com.project.concertView.domain.dao.member;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
/**
 * 회원 가입시 정보 저장하는 클래스
 *
 * */
@Getter
@Setter
public class SaveMember {
    @NotBlank(message = "이름을 입력해주세요")
    private String name;    //회원 이름
    @NotBlank(message = "주소를 입력해주세요")
    private String address; //주소

    @NotBlank(message = "상세 주소를 입력해주세요")
    private String detailAddress;   //상세주소

    @NotBlank(message = "이메일 주소를 입력해주세요")
    private String email;   //이메일
    @NotBlank
    private String email2;  //@ 다음 도메인 주소

    @NotBlank(message = "로그인 아이디를 입력해주세요")
    private String loginId; //로그인 아이디
    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;    //비밀번호

    @NotBlank(message = "비밀번호(확인용)를 입력해주세요")
    private String passwordCheck;   //비밀번호 체크
    @NotBlank(message = "연락처를 입력해주세요")
    @Pattern(regexp = "^(01\\d{1})(\\d{3,4})(\\d{4})$", message = "01000000000형식에 맞게 입력해주세요")
    private String phoneNumber; //연락처
    private String emailAccountWrite;   //이메일 @ 뒤에 계정 직접입력시
    private Boolean duplicateIdCheck;   //아이디 중복 체크 여부
    private Boolean passwordEqualsCheck;    // 패스워드 일치 여부
    private Boolean searchAddrCheck;    // 다음 주소 API로 주소 입력시 TRUE 값으로 전달됨
    private Boolean phoneNumberDuplicateCheck;  // 휴대폰번호 중복체크
    private Boolean emailDuplicateCheck;    //이메일 중복체크
    @NotBlank(message = "생년월일을 입력해주세요")
    @Pattern(regexp = "^([12]\\d{3}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01]))$", message = "형식에 맞게 입력해주세요(YYYYMMDD)")
    private String birth;   //생년월일
    public SaveMember() {
        this.duplicateIdCheck = false;
        this.passwordEqualsCheck = false;
        this.searchAddrCheck = false;
        this.phoneNumberDuplicateCheck = false;
        this.emailDuplicateCheck = false;
    }
    public SaveMember(String name, String address, String loginId, String password, Boolean duplicateIdCheck, Boolean passwordEqualsCheck, Boolean searchAddrCheck,
                      Boolean emailDuplicateCheck,Boolean phoneNumberDuplicateCheck,
                      String detailAddress,String email, String email2, String emailAccountWrite,String birth,String phoneNumber) {
        this.name = name;
        this.address = address;
        this.detailAddress = detailAddress;
        this.loginId = loginId;
        this.password = password;
        this.duplicateIdCheck = duplicateIdCheck;
        this.passwordEqualsCheck = passwordEqualsCheck;
        this.searchAddrCheck = searchAddrCheck;
        this.email = email;
        this.email2 = email2;
        this.emailAccountWrite = emailAccountWrite;
        this.birth = birth;
        this.phoneNumber = phoneNumber;
        this.emailDuplicateCheck = emailDuplicateCheck;
        this.phoneNumberDuplicateCheck = phoneNumberDuplicateCheck;
    }

}
