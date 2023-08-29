package com.project.concertView.web.service;

import com.project.concertView.domain.dao.concert.ConcertData;
import com.project.concertView.domain.dao.concert.ConcertDetailInfo;
import com.project.concertView.domain.dao.concert.ConcertPlace;
import com.project.concertView.domain.dao.concert.ConcertPlaceSearch;
import com.project.concertView.domain.dto.ConcertDetailInfoDTO;
import com.project.concertView.domain.dto.ConcertPlaceInfoDTO;
import com.project.concertView.domain.dto.ConcertPlaceSearchDTO;
import com.project.concertView.domain.entity.SessionValue;
import com.project.concertView.web.repository.ConcertRepository;
import com.project.concertView.domain.dto.ConcertSearchInfoDTO;
import com.project.concertView.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
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
    /**
     * 4. 공연 시설 조회 클래스
     * 1)  파라미터
     * - ConcertPlaceSearchDTO : 조회하고자 하는 공연시설의 현재페이지, 페이지당 목록 수, 공연 시설명
     */
    public List<ConcertPlaceSearch> findConcertPlaceList(ConcertPlaceSearchDTO concertPlaceSearchDTO){
        return concertRepository.findConcertPlaceList(concertPlaceSearchDTO);
    }


}
