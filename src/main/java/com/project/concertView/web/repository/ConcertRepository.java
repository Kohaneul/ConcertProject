package com.project.concertView.web.repository;

import com.project.concertView.XmlDataParser;
import com.project.concertView.domain.dao.ConcertData;
import com.project.concertView.domain.dao.ConcertDetailInfo;
import com.project.concertView.domain.dao.ConcertPlace;
import com.project.concertView.web.dto.ConcertPlaceInfoDTO;
import com.project.concertView.web.dto.ConcertSearchInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class ConcertRepository {
    public List<ConcertData> findAllDTO(ConcertSearchInfoDTO concertSearchInfoDTO){
        XmlDataParser xmlDataParser = new XmlDataParser(concertSearchInfoDTO);
        return xmlDataParser.setting();
    };


    public ConcertDetailInfo findOne(String mt20id){
        XmlDataParser xmlDataParser = new XmlDataParser(mt20id);
        return xmlDataParser.setConcertDetailInfo();
    }

    public String findFacility(String shprfnmfct){
        if(shprfnmfct.contains(" ")){
            shprfnmfct = shprfnmfct.split(" ")[0];
        }
        log.info("shprfnmfct={}",shprfnmfct);
        XmlDataParser xmlDataParser = new XmlDataParser(new ConcertPlaceInfoDTO(shprfnmfct));
        return xmlDataParser.setConcertPlace().getFcltynm();
    }

    public ConcertPlace findConcertPlace(ConcertPlaceInfoDTO concertPlaceInfoDTO){
        XmlDataParser xmlDataParser = new XmlDataParser(concertPlaceInfoDTO);
        return xmlDataParser.setConcertPlace();
    }


    public ConcertPlace findConcertPlaceInfo(String mt10id){
        XmlDataParser xmlDataParser = new XmlDataParser(new ConcertPlaceInfoDTO(mt10id));
        return xmlDataParser.setConcertPlace();
    }
}
