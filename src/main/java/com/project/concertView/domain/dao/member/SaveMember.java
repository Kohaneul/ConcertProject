package com.project.concertView.domain.dao.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveMember {
    @NotEmpty(message = "이름 누락")
    private String name;
    @NotEmpty(message = "주소 누락")
    private String address;
    @NotEmpty(message = "로그인 아이디 누락")
    private String loginId;
    @NotEmpty(message = "비밀번호 누락")
    private String password;
    @NotEmpty(message = "이름 누락")
    private String passwordCheck;
    @NotNull
    private Boolean duplicateIdCheck;
    @NotNull
    private Boolean passwordEqualsCheck;
    @NotNull
    private Boolean searchAddrCheck;

    private LocalDateTime joinDay;

    public SaveMember(String name, String address, String loginId, String password,Boolean duplicateIdCheck, Boolean searchAddrCheck, String passwordCheck) {
        this.name = name;
        this.address = address;
        this.loginId = loginId;
        this.password = password;
        this.duplicateIdCheck = duplicateIdCheck;
        this.searchAddrCheck = searchAddrCheck;
        this.passwordCheck = passwordCheck;
        this.joinDay = LocalDateTime.now();
    }
}
