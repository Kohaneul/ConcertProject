package com.project.concertView.web.controller;

import com.project.concertView.domain.dao.concert.*;
import com.project.concertView.domain.dao.member.annotation.log.LogRecord;
import com.project.concertView.domain.dto.*;
import com.project.concertView.domain.entity.SessionValue;
import com.project.concertView.domain.entity.Signgucode;
import com.project.concertView.web.service.ConcertService;
import com.project.concertView.web.service.LikeConcertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 요청 컨트롤러 클래스
 * 의존관계 : ConcertService.class
 * */
@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/concert")
public class ConcertController {
    private final ConcertService concertService;
    private final LikeConcertService likeConcertService;



    /**1. 공연 정보 조회 클래스
        1)  파라미터
          - ConcertSearchInfoDTO : 일자별 공연 정보 조회 DTO 클래스
          - Model : 해당 DTO를 적용한 객체를 화면단에 보내주는 model 클래스
     */
    @GetMapping("/detailView")
    @LogRecord
    public String concertInfoView(@ModelAttribute("concertSearchInfoDTO")ConcertSearchInfoDTO concertSearchInfoDTO, Model model, HttpSession session){
        List<ConcertData> concertDataList = concertService.findAllDTO(concertSearchInfoDTO);
        //DTO 클래스에 부합하는 정보만 LIST로 반환하여
        loginSessionIsNotNull(concertDataList, session);
        //Model 객체를 통하여 화면단 표시
        model.addAttribute("concertDataList",concertDataList);
        return "view/concert/ConcertInfo";
    }

    @GetMapping("/detailView/title")
    @LogRecord
    public String concertByArtist(@ModelAttribute("concertSearchByTitleDTO") ConcertSearchByTitleDTO concertSearchByTitleDTO, Model model, HttpSession session){
        List<ConcertData> concertDataList= concertService.findByConcertByArtist(concertSearchByTitleDTO);
        //DTO 클래스에 부합하는 정보만 LIST로 반환하여
        loginSessionIsNotNull(concertDataList,session);

        model.addAttribute("concertDataList",concertDataList);
        return "view/concert/ConcertSearchByTitle";
    }


    private void loginSessionIsNotNull(List<ConcertData> concertDataList,HttpSession session){
        Long memberId = (Long) session.getAttribute(SessionValue.LOGIN_PK_ID_SESSION);
        if(session.getAttribute(SessionValue.LOGIN_PK_ID_SESSION) !=null){
            concertDataList.forEach(i->i.setLikeOrNot(likeConcertService.likeConcert(new LikeConcert(memberId,i.getMt20id()))));
        }
    }

    @GetMapping("/like/detailView")
    @LogRecord
    public String likeConcertInfoView(@ModelAttribute("concertSearchInfoDTO")ConcertSearchInfoDTO concertSearchInfoDTO,
                                      @SessionAttribute(SessionValue.LOGIN_PK_ID_SESSION)Long memberId, Model model){
        //DTO 클래스에 부합하는 정보만 LIST로 반환하여
        List<ConcertData> concertDataList = concertService.findLikeConcertDTO(concertSearchInfoDTO, memberId);
        //Model 객체를 통하여 화면단 표시
        model.addAttribute("concertDataList",concertDataList);
        return "view/member/LikeConcertList";
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
        return "view/concert/ConcertImage";
    }

    /**3. 포스터 자세히 보기
     1)  파라미터
     - mt20id : 공연 id 값 ->
     - Model :  id 조회하여 이미지 주소값 리스트 화면단에 보내주는 model 클래스
     */
    @GetMapping("/detailView/poster")
    public String posterDetailView(@Param("mt20id")String mt20id,Model model){
        ConcertDetailInfo concertDetailInfo = concertService.concertDetailInfo(new ConcertDetailInfoDTO(mt20id));
        model.addAttribute("styurl",concertDetailInfo.getStyurls());
        return "view/concert/PosterClickView";
    }

    /**4. 공연 시설 상세 조회 클래스
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
        return "view/concert/ConcertPlaceDetail";
    }

    /**5. 공연 시설 조회 클래스
     1)  파라미터
     - ConcertPlaceInfoDTO : 공연 시설 조회하는 DTO 클래스
     - Model :  id 값과 부합하는 객체를 화면단에 보내주는 model 클래스
     */
    @GetMapping("/place")
    @LogRecord
    public String placeSearch(@ModelAttribute("concertPlaceSearchDTO") ConcertPlaceSearchDTO concertPlaceSearchDTO, Model model){
        List<ConcertPlaceSearch> concertPlaceList = concertService.findConcertPlaceList(concertPlaceSearchDTO);
        model.addAttribute("concertPlaceList",concertPlaceList);
        return "view/concert/ConcertPlaceSearch";
    }

    @ModelAttribute("signgucode")
    public Signgucode[] signguCode(){
        return Signgucode.values();
    }
    @RequestMapping("/like/{mt20id}")
    public String likeConcert(@PathVariable("mt20id")String mt20id,
                              @SessionAttribute(SessionValue.LOGIN_PK_ID_SESSION)Long memberId){
        likeConcertService.insertLikeConcert(new LikeConcertInsert(memberId,mt20id));
        log.info("저장완료={} : {}",memberId, mt20id);
        return "redirect:/concert/detailView";
    }

    @RequestMapping("/like/delete/{mt20id}")
    public String deleteLikeConcert(@PathVariable("mt20id")String mt20id,  @SessionAttribute(SessionValue.LOGIN_PK_ID_SESSION)Long memberId){
        likeConcertService.deleteLikeConcert(new LikeConcertInsert(memberId,mt20id));
        log.info("좋아요 취소 완료={} : {}",memberId, mt20id);
        return "redirect:/concert/like/detailView";
    }



}


