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

@Service
@RequiredArgsConstructor
public class ConcertService {
    private final ConcertRepository concertRepository;

    public List<ConcertData> findAllDTO(ConcertSearchInfoDTO concertSearchInfoDTO){
        return concertRepository.findAllDTO(concertSearchInfoDTO);
    }

    public ConcertDetailInfo concertDetailInfo(ConcertDetailInfoDTO concertDetailInfoDTO){
        return concertRepository.findOne(concertDetailInfoDTO);
    }
    public ConcertPlace findConcertHall(ConcertPlaceInfoDTO concertPlaceInfoDTO){
        return concertRepository.findConcertPlace(concertPlaceInfoDTO);
    }
    public ConcertPlace concertService(String mt10id){
        return concertRepository.findConcertPlaceInfo(mt10id);
    }

}
