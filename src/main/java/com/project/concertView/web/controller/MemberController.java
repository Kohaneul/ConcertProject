package com.project.concertView.web.controller;

import com.project.concertView.domain.dao.member.Member;
import com.project.concertView.domain.dao.member.SaveMember;
import com.project.concertView.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/save")
    public String saveMember(@ModelAttribute("member") SaveMember saveMember){
        return "view/member/Register";
    }

    @PostMapping("/save")
    public String saveMember(@Valid @ModelAttribute("member")SaveMember saveMember, BindingResult bindingResult){
        if(bindingResult.hasErrors()){

            return "view/member/Register";
        }
        Long memberId = memberService.saveInfo(saveMember);
        log.info("저장 완료");
        return "redirect:/member/info/"+memberId;
    }

    @PostMapping("/send")
    @ResponseBody
    public HashMap<String,Object> send(@RequestBody HashMap<String,Object> sendDTO){
        log.info("log={}",sendDTO.get("loginId").toString());
        String loginId = memberService.findLoginId((String)sendDTO.get("loginId"));
        log.info("loginId={}",loginId);
            sendDTO.replace("loginId",loginId);
         return sendDTO;
    }

    @GetMapping("/info/{memberId}")
    public String findOne(@PathVariable("memberId")Long memberId,Model model){
        Member member =  memberService.findOne(memberId);
        model.addAttribute("member",member);
        return "view/member/viewMember";
    }
}
