package com.project.concertView.web.repository;

import com.project.concertView.XmlDataParser;
import com.project.concertView.domain.dao.ConcertData;
import com.project.concertView.domain.dao.ConcertDetail;
import com.project.concertView.domain.dao.ConcertDetailInfo;
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

}
