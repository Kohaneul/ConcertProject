package com.project.concertView.web.service;

import com.project.concertView.domain.dao.ConcertData;
import com.project.concertView.domain.dao.ConcertDetailInfo;
import com.project.concertView.domain.dao.ConcertPlace;
import com.project.concertView.domain.dto.ConcertDetailInfoDTO;
import com.project.concertView.domain.dto.ConcertPlaceInfoDTO;
import com.project.concertView.web.repository.ConcertRepository;
import com.project.concertView.domain.dto.ConcertSearchInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * SERVICE 클래스
 *
 * */
@Service
@RequiredArgsConstructor
public class ConcertService {
    private final ConcertRepository concertRepository;  // CONCERTREPOSITORY 클래스를 의존주입으로 받음
    /**1. 공연 정보 조회 클래스
     1)  파라미터
     - ConcertSearchInfoDTO : 일자별 공연 정보 조회 DTO 클래스
     */
    public List<ConcertData> findAllDTO(ConcertSearchInfoDTO concertSearchInfoDTO){
        return concertRepository.findAllDTO(concertSearchInfoDTO);
    }

    /**2. 공연 상세 조회 클래스
     1)  파라미터
     - mt10id : 특정 공연 정보 id 값
     */
    public ConcertDetailInfo concertDetailInfo(ConcertDetailInfoDTO concertDetailInfoDTO){
        return concertRepository.findOne(concertDetailInfoDTO);
    }
    /**3. 공연 시설 상세 조회 클래스
     1)  파라미터
     - ConcertPlaceInfoDTO : 공연 시설 id 값
     */
    public ConcertPlace findConcertHall(ConcertPlaceInfoDTO concertPlaceInfoDTO){
        return concertRepository.findConcertPlace(concertPlaceInfoDTO);
    }


}
