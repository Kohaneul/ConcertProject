package com.project.concertView.web.controller;

import com.project.concertView.domain.dao.ConcertData;
import com.project.concertView.domain.dao.ConcertDetailInfo;
import com.project.concertView.domain.dao.ConcertPlace;
import com.project.concertView.web.dto.ConcertPlaceInfoDTO;
import com.project.concertView.web.service.ConcertService;
import com.project.concertView.web.dto.ConcertSearchInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ConcertController {
    private final ConcertService concertService;

    @GetMapping("/detailView")
    public String concertInfoView(@ModelAttribute("concertSearchInfoDTO")ConcertSearchInfoDTO concertSearchInfoDTO, Model model){
        List<ConcertData> concertDataList = concertService.findAllDTO(concertSearchInfoDTO);
        model.addAttribute("concertDataList",concertDataList);
        return "view/DetailView";
    }

    @GetMapping("/detailView/img")
    public String imageDetailView(@Param("mt20id") String mt20id,Model model){
        ConcertDetailInfo concertDetailInfo = concertService.concertDetailInfo(mt20id);
        model.addAttribute("concertDetailInfo",concertDetailInfo);
        return "view/ImageView";
    }

    @GetMapping("/detailView/place/{mt10id}")
    public String placeDetailView(@PathVariable("mt10id")String mt10id, Model model){
        ConcertPlace concertPlace = concertService.findConcertHall(new ConcertPlaceInfoDTO(mt10id));
        model.addAttribute("concertPlace",concertPlace);
        return "view/PlaceView";
    }
    @GetMapping("/place")
    public String placeSearch(@ModelAttribute("concertPlaceInfoDTO")ConcertPlaceInfoDTO concertPlaceInfoDTO,Model model){
        ConcertPlace concertPlace = concertService.findConcertHall(concertPlaceInfoDTO);
        model.addAttribute("concertPlace",concertPlace);
        return "view/PlaceSearch";
    }

}
