package com.project.concertView.web.repository;

import com.project.concertView.domain.dao.member.FindPassword;
import com.project.concertView.domain.dao.member.UpdatePassword;
import com.project.concertView.domain.dto.LoginMemberDTO;
import com.project.concertView.domain.dao.member.Member;
import com.project.concertView.domain.dao.member.SaveMember;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * Mapper : mybatis/member.xml
 *
 * */
@Repository
@Mapper
public interface MemberRepository {

    List<Member> findAll();
    Member findOne(Long id);
    Long findById(String loginId);
    @Transactional
    void saveInfo(SaveMember member);
    @Transactional
    void deleteAll(Long id);
    String findLoginId(String loginId);
    Long findByLoginId(String loginId);
    String findEmail(String mail);
    String findPhoneNumber(String phoneNumber);
    Long loginMember(LoginMemberDTO loginMember);
    Long findPassword(FindPassword findPassword);
    @Transactional
    void updatePassword(UpdatePassword updatePassword);
    String findByLoginIdFromId(Long id);




}
