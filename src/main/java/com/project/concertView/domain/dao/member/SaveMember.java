package com.project.concertView.domain.dao.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
public class SaveMember {
    @NotBlank(message = "이름을 입력해주세요")
    private String name;
    @NotBlank(message = "주소를 입력해주세요")
    private String address;
    @NotBlank(message = "로그인 아이디를 입력해주세요")
    private String loginId;
    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;
    @NotBlank(message = "비밀번호(확인용)를 입력해주세요")
    private String passwordCheck;
    private Boolean duplicateIdCheck;
    private Boolean passwordEqualsCheck;
    private Boolean searchAddrCheck;

    public SaveMember() {
        this.duplicateIdCheck = false;
        this.passwordEqualsCheck = false;
        this.searchAddrCheck = false;
    }

    public SaveMember(String name, String address, String loginId, String password, Boolean duplicateIdCheck, Boolean passwordEqualsCheck, Boolean searchAddrCheck) {
        this.name = name;
        this.address = address;
        this.loginId = loginId;
        this.password = password;
        this.duplicateIdCheck = duplicateIdCheck;
        this.passwordEqualsCheck = passwordEqualsCheck;
        this.searchAddrCheck = searchAddrCheck;
    }
}
