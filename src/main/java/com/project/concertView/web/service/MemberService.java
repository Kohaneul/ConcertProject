package com.project.concertView.web.service;

import com.project.concertView.domain.dao.concert.LikeConcertInsert;
import com.project.concertView.domain.dao.member.FindPassword;
import com.project.concertView.domain.dao.member.UpdatePassword;
import com.project.concertView.domain.dto.LoginMemberDTO;
import com.project.concertView.domain.dao.member.Member;
import com.project.concertView.domain.dao.member.SaveMember;
import com.project.concertView.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    };
    public void saveMember(SaveMember member) {
        memberRepository.saveInfo(member);
    }
    public void deleteAll(Long id){
        memberRepository.deleteAll(id);
    }
    public String findLoginId(String loginId) {
        return memberRepository.findLoginId(loginId) != null ? null : loginId;
    }
    public Long findByLoginId(String loginId){
        return memberRepository.findByLoginId(loginId);
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
    public Long findPassword(FindPassword findPassword){
        return memberRepository.findPassword(findPassword);
    }
    public void updatePassword(UpdatePassword updatePassword){
        memberRepository.updatePassword(updatePassword);
    }



}
