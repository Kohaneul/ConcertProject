package com.project.concertView.web.controller;

import com.project.concertView.domain.dao.ConcertData;
import com.project.concertView.domain.dao.ConcertDetailInfo;
import com.project.concertView.web.service.ConcertService;
import com.project.concertView.web.dto.ConcertSearchInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

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


}
