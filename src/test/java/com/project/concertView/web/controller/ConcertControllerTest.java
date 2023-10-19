package com.project.concertView.web.controller;

import com.project.concertView.domain.dao.concert.ConcertData;
import com.project.concertView.domain.dto.ConcertSearchByTitleDTO;
import com.project.concertView.web.service.ConcertService;
import com.project.concertView.web.service.LikeConcertService;
import com.project.concertView.web.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootTest
@Slf4j
class ConcertControllerTest {
    private final MemberService memberService;
    private final LikeConcertService likeConcertService;
    private final ConcertService concertService;

    @Autowired
    public ConcertControllerTest(LikeConcertService service,MemberService memberService,ConcertService concertService) {
        this.likeConcertService = service;
        this.memberService = memberService;
        this.concertService = concertService;
    }


    @Test
    @DisplayName("검색시 한글 문자열+ 공백 발생시 에러발생 테스트")
    void test1() {
        String stDate = LocalDateTime.now().minusDays(30).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String edDate = LocalDateTime.now().plusDays(30).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String title = "피아노 독주회";
        //공백 포함하여 검색시 NullPointer 발생
        Assertions.assertThatThrownBy(()->{
                concertService.findAllDTO(new ConcertSearchByTitleDTO(stDate, edDate, 100, 1, title), 1L);
        }).isInstanceOf(NullPointerException.class);
    }



    @Test
    @DisplayName("검색시 한글 문자열 공백 치환시 정상작동여부")
    void test2() {
        String stDate = LocalDateTime.now().minusDays(30).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String edDate = LocalDateTime.now().plusDays(30).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String title = " 피아노 독주회 ";
        log.info("title={}",title);
        if(title.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")){
            if(title.contains(" ")){
                title = blankChange(title);
            }
        }
        log.info("title={}",title);

        List<ConcertData> allDTO = concertService.findAllDTO(new ConcertSearchByTitleDTO(stDate, edDate, 100, 1, title), 1L);
        Assertions.assertThat(allDTO).isNotNull();
    }


    String blankChange(String title){
        title.trim();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < title.length(); i++) {
            char c = title.charAt(i);
            if (c == ' ') {
               sb.replace(i,i+1,"+");
            }
            else{
                sb.append(c);
            }

        }
        return sb.toString();
    }

}