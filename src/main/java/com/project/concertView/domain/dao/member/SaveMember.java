package com.project.concertView.domain.dao.member;

import com.project.concertView.domain.entity.EmailAddr;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SaveMember {
    @NotBlank(message = "이름을 입력해주세요")
    private String name;
    @NotBlank(message = "주소를 입력해주세요")
    private String address;

    @NotBlank(message = "상세 주소를 입력해주세요")
    private String detailAddress;

    @NotBlank(message = "이메일 주소를 입력해주세요")
    private String email;
    @NotBlank
    private String email2;

    @NotBlank(message = "로그인 아이디를 입력해주세요")
    private String loginId;
    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;
    @NotBlank(message = "비밀번호(확인용)를 입력해주세요")
    private String passwordCheck;

    private String emailAccountWrite;

    private Boolean duplicateIdCheck;
    private Boolean passwordEqualsCheck;
    private Boolean searchAddrCheck;

    public SaveMember() {
        this.duplicateIdCheck = false;
        this.passwordEqualsCheck = false;
        this.searchAddrCheck = false;
        this.email2 = EmailAddr.NAVER.getValue();
    }

    public SaveMember(String name, String address, String loginId, String password, Boolean duplicateIdCheck, Boolean passwordEqualsCheck, Boolean searchAddrCheck,
                      String detailAddress,String email, String email2, String emailAccountWrite) {
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
    }
}
