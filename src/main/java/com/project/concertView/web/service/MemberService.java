package com.project.concertView.web.service;

import com.project.concertView.domain.dao.member.Member;
import com.project.concertView.domain.dao.member.SaveMember;
import com.project.concertView.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public List<Member> findAll(){
        return memberRepository.findAll();
    };

    public Member findOne(Long id){
        return memberRepository.findOne(id);
    };
    public Long saveInfo(SaveMember member){
       return memberRepository.saveInfo(member);
    };
    public String findLoginId(String loginId){
        log.info("MemberService={}",loginId);
        return memberRepository.findLoginId(loginId);
    }

}
