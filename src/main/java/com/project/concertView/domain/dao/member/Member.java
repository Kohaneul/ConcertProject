package com.project.concertView.domain.dao.member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/**
 * 회원 정보를 담은 Member 클래스
 *
 * */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private Long id;    //pk(아이디)
    private String name;  //이름
    private String address; //주소
    private String detailAddress;   //상세주소
    private String email;   //이메일
    private String emailAccountWrite;   //@ 뒤에 이메일 도메인

    private String loginId; //로그인아이디
    private String password;    //비밀번호
    private LocalDateTime joinDay;  //가입일
    private String phoneNumber; //휴대폰번호
    private String birth;   //생년월일
    private List<Long> likeList;    //좋아요 리스트

    public Member(String name, String address, String detailAddress, String email, String loginId, String password, LocalDateTime joinDay, String emailAccountWrite, String phoneNumber, String birth) {
        this.name = name;
        this.address = address;
        this.detailAddress = detailAddress;
        this.email = email;
        this.loginId = loginId;
        this.password = password;
        this.joinDay = joinDay;
        this.emailAccountWrite = emailAccountWrite;
        this.phoneNumber = phoneNumber;
        this.birth = birth;
        this.likeList = new ArrayList<>();
    }

    public void setList(Long concertId){
        likeList.add(concertId);
    }
}
