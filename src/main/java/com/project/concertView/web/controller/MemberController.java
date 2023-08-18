package com.project.concertView.web.controller;

import com.project.concertView.domain.dao.member.Member;
import com.project.concertView.domain.dao.member.SaveMember;
import com.project.concertView.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
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
    public String saveMember(@Valid @ModelAttribute("saveMember")SaveMember saveMember, BindingResult bindingResult){
        checkOrNot(saveMember,bindingResult);
        if(bindingResult.hasErrors()){
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.info("error field ={} cause={}",fieldError.getField(),fieldError.getRejectedValue());
            }
            return "view/member/Register";
        }
        log.info("저장 진행 중");
        Long memberId = memberService.saveInfo(saveMember);
        log.info("memberId={}",memberId);
        log.info("저장 완료");
        return "redirect:/member/info/"+memberId;
    }

    private void checkOrNot(@ModelAttribute("saveMember")SaveMember saveMember,BindingResult bindingResult){
        if(!saveMember.getDuplicateIdCheck()){
            bindingResult.addError(new FieldError("member", "duplicateIdCheck", saveMember.getDuplicateIdCheck(), false, new String[]{"required.loginId.check"}, null, null));
        }
        if(!saveMember.getPasswordEqualsCheck()){
            bindingResult.addError(new FieldError("member", "passwordEqualsCheck", saveMember.getPasswordEqualsCheck(), false, new String[]{"required.password.check"}, null, null));
        }
        if(!saveMember.getSearchAddrCheck()){
            bindingResult.addError(new FieldError("member", "searchAddrCheck", saveMember.getSearchAddrCheck(), false, new String[]{"required.address.check"}, null, null));
        }
    }

    @PostMapping("/send")
    @ResponseBody
    public HashMap<String,Object> send(@RequestBody HashMap<String,Object> sendDTO){
        String loginId = memberService.findLoginId((String) sendDTO.get("loginId"));
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
