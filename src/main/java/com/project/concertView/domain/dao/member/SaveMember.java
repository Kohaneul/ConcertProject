package com.project.concertView.domain.dao.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
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
    private Boolean duplicateIdCheck = false;
    private Boolean passwordEqualsCheck = false;
    private Boolean searchAddrCheck = false;
    private LocalDateTime joinDay;




    public SaveMember(String name, String address, String loginId, String password) {
        this.name = name;
        this.address = address;
        this.loginId = loginId;
        this.password = password;
        this.joinDay = LocalDateTime.now();
    }
}
