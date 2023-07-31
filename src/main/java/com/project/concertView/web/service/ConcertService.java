package com.project.concertView.web.service;

import com.project.concertView.domain.dao.ConcertData;
import com.project.concertView.domain.dao.ConcertDetailInfo;
import com.project.concertView.web.repository.ConcertRepository;
import com.project.concertView.web.dto.ConcertSearchInfoDTO;
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

    public ConcertDetailInfo concertDetailInfo(String mt20id){
        return concertRepository.findOne(mt20id);
    }

}
