package com.project.concertView.web.controller;

import com.project.concertView.domain.dao.ConcertData;
import com.project.concertView.domain.dao.ConcertDetailInfo;
import com.project.concertView.domain.dao.ConcertPlace;
import com.project.concertView.domain.dto.ConcertDetailInfoDTO;
import com.project.concertView.domain.dto.ConcertPlaceInfoDTO;
import com.project.concertView.web.service.ConcertService;
import com.project.concertView.domain.dto.ConcertSearchInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

/**
 * 요청 컨트롤러 클래스
 * 의존관계 : ConcertService.class
 * */
@Controller
@Slf4j
@RequiredArgsConstructor
public class ConcertController {
    private final ConcertService concertService;

    /**1. 공연 정보 조회 클래스
        1)  파라미터
          - ConcertSearchInfoDTO : 일자별 공연 정보 조회 DTO 클래스
          - Model : 해당 DTO를 적용한 객체를 화면단에 보내주는 model 클래스
     */
    @GetMapping("/detailView")
    public String concertInfoView(@ModelAttribute("concertSearchInfoDTO")ConcertSearchInfoDTO concertSearchInfoDTO, Model model){
        //DTO 클래스에 부합하는 정보만 LIST로 반환하여
        List<ConcertData> concertDataList = concertService.findAllDTO(concertSearchInfoDTO);
        //Model 객체를 통하여 화면단 표시
        model.addAttribute("concertDataList",concertDataList);
        return "view/DetailView";
    }

    /**2. 공연 상세 조회 클래스
     1)  파라미터
     - mt20id : 특정 공연 정보 id 값
     - Model :  id 값과 부합하는 객체를 화면단에 보내주는 model 클래스
     */
    @GetMapping("/detailView/img")
    public String imageDetailView(@Param("mt20id") String mt20id,Model model){
        //id 값을 조회하여 이와 일치한 정보를 ConcertDetailInfo 클래스로 반환하여
        ConcertDetailInfo concertDetailInfo = concertService.concertDetailInfo(new ConcertDetailInfoDTO(mt20id));
        //Model 객체를 통하여 화면단에 보내 해당 정보 표시
        model.addAttribute("concertDetailInfo",concertDetailInfo);
        return "view/ImageView";
    }

    /**3. 공연 시설 상세 조회 클래스
     1)  파라미터
     - mt10id : 공연 시설 id 값
     - Model :  id 값과 부합하는 객체를 화면단에 보내주는 model 클래스
     */
    @GetMapping("/detailView/place/{mt10id}")
    public String placeDetailView(@PathVariable("mt10id")String mt10id, Model model){
        //id 값을 조회하여 이와 일치한 정보를 ConcertPlace 클래스로 반환하여
        ConcertPlace concertPlace = concertService.findConcertHall(new ConcertPlaceInfoDTO(mt10id));
        //Model 객체를 통하여 화면단에 보내 해당 정보 표시
        model.addAttribute("concertPlace",concertPlace);
        return "view/PlaceView";
    }

    /**4. 공연 시설 조회 클래스
     1)  파라미터
     - ConcertPlaceInfoDTO : 공연 시설 조회하는 DTO 클래스
     - Model :  id 값과 부합하는 객체를 화면단에 보내주는 model 클래스
     */
    @GetMapping("/place")
    public String placeSearch(@ModelAttribute("concertPlaceInfoDTO")ConcertPlaceInfoDTO concertPlaceInfoDTO,Model model){
        ConcertPlace concertPlace = concertService.findConcertHall(concertPlaceInfoDTO);
        model.addAttribute("concertPlace",concertPlace);
        return "view/PlaceSearch";
    }

}
