package com.project.concertView.web.repository;

import com.project.concertView.domain.dao.concert.ConcertData;
import com.project.concertView.domain.dto.ConcertSearchByTitleDTO;
import com.project.concertView.domain.dto.ConcertSearchInfoDTO;
import com.project.concertView.domain.likeConcert.UpdateLikeConcert;
import com.project.concertView.domain.dao.member.SaveMember;
import com.project.concertView.web.service.ConcertService;
import com.project.concertView.web.service.LikeConcertService;
import com.project.concertView.web.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.Set;

@SpringBootTest
@Slf4j
class LikeConcertRepositoryTest {
    private final MemberService memberService;
    private final LikeConcertService likeConcertService;
    private final ConcertService concertService;

    @Autowired
    public LikeConcertRepositoryTest(LikeConcertService service,MemberService memberService,ConcertService concertService) {
        this.likeConcertService = service;
        this.memberService = memberService;
        this.concertService = concertService;
    }

    @Test
    @DisplayName("회원가입 test")
    void test1(){
     saveMember();
    }

    private void saveMember(){
        SaveMember saveMember = new SaveMember("테스터", "군포시 금정동 849", "tester", "1111", true, true, true, true, true, "1234호", "tester", "@naver.com", null, "19901106", "01012341234");
        memberService.saveMember(saveMember);
    }
    @Test
    @DisplayName("JSON_ARRAY 좋아요 추가 test")
    void test2(){
        saveMember();
        Long id = memberService.findByLoginId("tester");
        log.info("id={}",id);
        likeConcertService.addLikeConcert(new UpdateLikeConcert(id,"PF2265555"));
        likeConcertService.addLikeConcert(new UpdateLikeConcert(id,"PF2265"));
        likeConcertService.addLikeConcert(new UpdateLikeConcert(id,"PF2266"));
        Set<String> strList = likeConcertService.likeConcertDTOs(1L).getMt20id();
        Assertions.assertThat(strList.size()).isEqualTo(3);
    }
    @Test
    @DisplayName("JSON_ARRAY 요소 제거")
    void test3(){
        saveMember();
        Long id = memberService.findByLoginId("tester");
        likeConcertService.addLikeConcert(new UpdateLikeConcert(id,"PF2265555"));
        likeConcertService.addLikeConcert(new UpdateLikeConcert(id,"PF2265"));
        likeConcertService.addLikeConcert(new UpdateLikeConcert(id,"PF2266"));
        likeConcertService.deleteLikeConcert(new UpdateLikeConcert(id,"PF2266"));
        Set<String> strList = likeConcertService.likeConcertDTOs(1L).getMt20id();
        Assertions.assertThat(strList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("좋아요 리스트 찾기")
    void test4(){
        HttpSession session = new MockHttpSession();
        Long id = memberService.findByLoginId("hnko");
        List<ConcertData> concertDataList = concertService.findAllDTO(new ConcertSearchByTitleDTO(), 1L);
        List<ConcertData> myDTO = concertService.findMyDTO(concertDataList, id);
        for (ConcertData concertData : myDTO) {
           log.info("concertData={}",concertData);
        }
        Assertions.assertThat(myDTO.size()).isEqualTo(2);


    }

    @AfterEach
    void delete(){
        memberService.deleteAll(memberService.findByLoginId("tester"));
        Assertions.assertThat(memberService.findByLoginId("tester")).isNull();
    }


}