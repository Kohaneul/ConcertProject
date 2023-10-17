package com.project.concertView.web.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.concertView.domain.dto.LikeConcertDTO;
import com.project.concertView.domain.likeConcert.UpdateLikeConcert;
import com.project.concertView.web.repository.LikeConcertRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

/**
 * 각 회원별 찜한 콘서트 리스트
 * */
@Service
@RequiredArgsConstructor
@Slf4j
public class LikeConcertService {
    private final LikeConcertRepository likeConcertRepository;

    private Set<String> likeConcertListByMemberId(Long memberId){
        return new Gson().fromJson(likeConcertRepository.likeConcertListByMemberId(memberId),new TypeToken<HashSet<String>>(){}.getType());
    }
    public LikeConcertDTO likeConcertDTOs(Long memberId){
        return new LikeConcertDTO(memberId,likeConcertListByMemberId(memberId));
    }
    public void addLikeConcert(UpdateLikeConcert updateLikeConcert){
        likeConcertRepository.addLikeConcert(updateLikeConcert);
    }
    public void deleteLikeConcert(UpdateLikeConcert updateLikeConcert){
        likeConcertRepository.deleteLikeConcert(updateLikeConcert);
    }

}
