package com.project.concertView.web.repository;

import com.project.concertView.XmlDataParser;
import com.project.concertView.domain.dao.concert.*;
import com.project.concertView.domain.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.util.List;
/**
 * Concert 정보를 담은 REPOSITORY 클래스
 *
 * */
@Repository
@Slf4j
public class ConcertRepository {

    /**1. 공연 정보 조회 클래스
     1)  파라미터
     - ConcertSearchInfoDTO : 일자별 공연 정보 조회 DTO 클래스
     */
    public List<ConcertData> findAllDTO(ConcertSearchInfoDTO concertSearchInfoDTO){
        XmlDataParser xmlDataParser = new XmlDataParser(concertSearchInfoDTO);
        return xmlDataParser.setting(1);
    };

    /**2. 공연 제목 조회 클래스
     1)  파라미터
     - ConcertSearchByTitleDTO : 콘서트 제목을 통한 조회가능한 DTO 클래스
     */
    public List<ConcertData> findByConcertByArtist(ConcertSearchByTitleDTO concertByArtistDTO){
        XmlDataParser xmlDataParser = new XmlDataParser(concertByArtistDTO);
        return xmlDataParser.setting(2);
    };

    /**3. 공연 상세 조회 클래스
     1)  파라미터
     - mt10id : 특정 공연 정보 id 값
     */
    public ConcertDetailInfo findOne(ConcertDetailInfoDTO concertDetailInfoDTO){
        XmlDataParser xmlDataParser = new XmlDataParser(concertDetailInfoDTO);
        return xmlDataParser.setConcertDetailInfo();
    }

    /**4. 공연 시설 상세 조회 클래스
     1)  파라미터
     - ConcertPlaceInfoDTO : 공연 시설 id 값
     */
    public ConcertPlace findConcertPlace(ConcertPlaceInfoDTO concertPlaceInfoDTO){
        XmlDataParser xmlDataParser = new XmlDataParser(concertPlaceInfoDTO);
        return xmlDataParser.setConcertPlace();
    }

    /**5. 공연 장소 상세 조회 클래스
     1)  파라미터
     - ConcertPlaceSearchDTO : 지역코드, 공연시설명
     */
    public List<ConcertPlaceSearch> findConcertPlaceList(ConcertPlaceSearchDTO concertPlaceSearchDTO){
        XmlDataParser xmlDataParser = new XmlDataParser(concertPlaceSearchDTO);
        return xmlDataParser.setConcertHallList();
    }


}
