package com.project.concertView.web.service;

import com.project.concertView.domain.dao.member.Member;
import com.project.concertView.domain.dao.member.SaveMember;
import com.project.concertView.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private void saveInfo(SaveMember member){
       memberRepository.saveInfo(member);
    };
    public String findLoginId(String loginId){
        return memberRepository.findLoginId(loginId)!=null ? null : loginId ;
    }
    public Long findById(SaveMember member){
        saveInfo(member);
        return memberRepository.findById(member);

    }

}
