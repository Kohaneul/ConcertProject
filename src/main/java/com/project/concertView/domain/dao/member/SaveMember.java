package com.project.concertView.domain.dao.member;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
public class SaveMember {
    @NotEmpty
    private String name;
    @NotEmpty
    private String address;
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;
    @NotEmpty
    private String passwordCheck;
    @Range(min = 0,max = 3)
    private Integer num;
    @NotEmpty
    private String duplicateIdCheck;
    @NotEmpty
    private String passwordEqualsCheck;
    @NotEmpty
    private String searchAddrCheck;

    private LocalDateTime joinDay;

    public SaveMember(String name, String address, String loginId, String password,String passwordCheck) {
        this.name = name;
        this.address = address;
        this.loginId = loginId;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.joinDay = LocalDateTime.now();
        this.num = 0;
    }
}
