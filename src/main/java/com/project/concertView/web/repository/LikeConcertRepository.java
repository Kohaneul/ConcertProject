package com.project.concertView.web.repository;

import com.project.concertView.domain.dao.concert.LikeConcert;
import com.project.concertView.domain.likeConcert.UpdateLikeConcert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * Mapper : mybatis/likeConcert.xml
 *
 *   각 회원별 찜한 콘서트 리스트
 *
 * */
@Mapper
@Repository
public interface LikeConcertRepository {
    Boolean findLikeConcert(LikeConcert likeConcert);
    String findMt20id(LikeConcert likeConcert);
    @Transactional
    void deleteLikeConcert(UpdateLikeConcert updateLikeConcert);
    @Transactional
    void addLikeConcert(UpdateLikeConcert updateLikeConcert);
    List<String> likeConcertList(Long memberId);
    String likeConcertListByMemberId(Long memberId);
}

