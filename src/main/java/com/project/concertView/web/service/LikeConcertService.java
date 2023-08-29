package com.project.concertView.web.service;

import com.project.concertView.domain.dao.concert.LikeConcert;
import com.project.concertView.domain.dao.concert.LikeConcertInsert;
import com.project.concertView.web.repository.LikeConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeConcertService {
    private final LikeConcertRepository likeConcertRepository;

    public Boolean likeConcert(LikeConcert likeConcert){
        return likeConcertRepository.findLikeConcert(likeConcert);
    }

    public String findMt20id(LikeConcert likeConcert){
       return likeConcertRepository.findMt20id(likeConcert);
    }

    public void insertLikeConcert(LikeConcertInsert likeConcertInsert){
        likeConcertRepository.insertLikeConcert(likeConcertInsert);
    }
    public void deleteLikeConcert(LikeConcertInsert likeConcertInsert){
        likeConcertRepository.deleteLikeConcert(likeConcertInsert);
    }
    public List<String> likeConcertList(Long memberId){
        return likeConcertRepository.likeConcertList(memberId);
    }
}
