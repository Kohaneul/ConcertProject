package com.project.concertView.web.service;

import com.project.concertView.domain.dao.concert.*;
import com.project.concertView.domain.dto.*;
import com.project.concertView.web.repository.ConcertRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * SERVICE 클래스
 * */
@Service
@Slf4j
@RequiredArgsConstructor
public class ConcertService {
    private final ConcertRepository concertRepository;  // CONCERTREPOSITORY 클래스를 의존주입으로 받음
    private final LikeConcertService likeConcertService;
    /**1. 공연 정보 조회 클래스
     1)  파라미터
     - ConcertSearchInfoDTO : 일자별 공연 정보 조회 DTO 클래스
     */
//    public List<ConcertData> findAllDTO(ConcertSearchInfoDTO concertSearchInfoDTO, HttpSession session){
//        //DTO 클래스에 부합하는 정보만 LIST로 반환
//        List<ConcertData> concertDataList = concertRepository.findAllDTO(concertSearchInfoDTO);
//        loginSessionIsNotNull(concertDataList,memberId(session));
//        return concertDataList;
//    }

    public List<ConcertData> findMyDTO(List<ConcertData> concertDatas, Long id){
        List<ConcertData> concertData = new ArrayList<>();
        List<String> likeList = new ArrayList<>(likeConcertService.likeConcertDTOs(id).getMt20id());
        for (String s : likeList) {
            for (ConcertData cd : concertDatas) {
                if (s.equals(cd.getMt20id())) {
                    concertData.add(cd);
                }
            }
        }
        concertData.forEach(i->i.setLikeOrNot(true));
        return concertData;
    }

    private void loginSessionIsNotNull(List<ConcertData> concertDataList,Long id){
        if(id !=null){
            log.info("id={}",id);
            List<String> likeList = new ArrayList<>(likeConcertService.likeConcertDTOs(id).getMt20id());
            for (String s : likeList) {
                for (ConcertData c : concertDataList) {
                    if(s.equals(c.getMt20id())){
                        c.setLikeOrNot(true);
                    }
                }
            }
        }
    }
//    private Long memberId(HttpSession session){
//        return (Long)session.getAttribute(SessionValue.LOGIN_SESSION);
//    }

    public List<ConcertData> findAllDTO(ConcertSearchByTitleDTO concertByArtistDTO,Long id){
        List<ConcertData> concertDataList = concertRepository.findAllDTO(concertByArtistDTO);
        loginSessionIsNotNull(concertDataList,id);
        return concertDataList;
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
