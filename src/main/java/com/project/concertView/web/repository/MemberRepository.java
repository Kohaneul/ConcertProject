package com.project.concertView.web.repository;

import com.project.concertView.domain.dao.member.Member;
import com.project.concertView.domain.dao.member.SaveMember;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Mapper
public interface MemberRepository {

    List<Member> findAll();

    Member findOne(Long id);
    Long findById(SaveMember member);
    @Transactional
    void saveInfo(SaveMember member);
    String findLoginId(String loginId);
    String findEmail(String emaiil);
    String findPhoneNumber(String phoneNumber);
}
