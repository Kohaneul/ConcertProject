package com.project.concertView.web.service;

import com.project.concertView.domain.dao.member.FindPassword;
import com.project.concertView.domain.dao.member.UpdatePassword;
import com.project.concertView.domain.dto.LoginMemberDTO;
import com.project.concertView.domain.dao.member.Member;
import com.project.concertView.domain.dao.member.SaveMember;
import com.project.concertView.domain.entity.SessionValue;
import com.project.concertView.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final HttpSession session;
    public String findByLoginIdFromId(Long id){
        return memberRepository.findByLoginIdFromId(id);
    }
    public List<Member> findAll() {
        return memberRepository.findAll();
    };
    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    };
    public LoginMemberDTO findLoginMemberDTO(Long id) {
        Member member = findOne(id);
        return new LoginMemberDTO(member.getLoginId(), member.getPassword());
    }
    public void saveInfo(SaveMember member) {
        memberRepository.saveInfo(member);
    }

    public String findLoginId(String loginId) {
        return memberRepository.findLoginId(loginId) != null ? null : loginId;
    }
    public String findByLoginId(Long id){
        return memberRepository.findByLoginId(id);
    }
    public Long findById(String member) {
//        saveInfo(member);
        return memberRepository.findById(member);
    }
    public String findEmail(String email) {
        return memberRepository.findEmail(email) != null ? "1" : email;
    }
    public String findPhoneNumber(String phoneNumber) {
        return memberRepository.findPhoneNumber(phoneNumber) != null ? null : phoneNumber;
    }
    public Long loginMember(LoginMemberDTO loginMember) {
        return memberRepository.loginMember(loginMember);
    }
    public Long getLoginUser() {
        log.info("session={}",session.getAttribute(SessionValue.LOGIN_SESSION));
        return (Long) session.getAttribute(SessionValue.LOGIN_SESSION);
    }
    public Long findPassword(FindPassword findPassword){
        return memberRepository.findPassword(findPassword);
    }
    public void updatePassword(UpdatePassword updatePassword){
        memberRepository.updatePassword(updatePassword);
    }



}
