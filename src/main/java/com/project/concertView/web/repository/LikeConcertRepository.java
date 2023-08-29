package com.project.concertView.web.repository;

import com.project.concertView.domain.dao.concert.LikeConcert;
import com.project.concertView.domain.dao.concert.LikeConcertInsert;
import com.project.concertView.web.service.LikeConcertService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Repository
public interface LikeConcertRepository {
    Boolean findLikeConcert(LikeConcert likeConcert);
    String findMt20id(LikeConcert likeConcert);
    @Transactional
    void insertLikeConcert(LikeConcertInsert likeConcertInsert);
    @Transactional
    void deleteLikeConcert(LikeConcertInsert likeConcertInsert);
    List<String> likeConcertList(Long memberId);
}
