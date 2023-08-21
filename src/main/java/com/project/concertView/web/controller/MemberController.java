package com.project.concertView.web.controller;
import com.project.concertView.domain.dao.member.Member;
import com.project.concertView.domain.dao.member.SaveMember;
import com.project.concertView.domain.entity.EmailAddr;
import com.project.concertView.domain.entity.Signgucode;
import com.project.concertView.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
    public String saveMember(@ModelAttribute("saveMember") SaveMember saveMember){
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
        Long id = memberService.findById(saveMember);
        return "redirect:/member/info/"+id;
    }

    private void checkOrNot(@ModelAttribute("saveMember")SaveMember saveMember,BindingResult bindingResult){
        if(!saveMember.getDuplicateIdCheck()){
            bindingResult.addError(new FieldError("saveMember", "duplicateIdCheck", saveMember.getDuplicateIdCheck(), false, new String[]{"required.loginId.check"}, null, null));
        }
        if(!saveMember.getPasswordEqualsCheck()){
            bindingResult.addError(new FieldError("saveMember", "passwordEqualsCheck", saveMember.getPasswordEqualsCheck(), false, new String[]{"required.password.check"}, null, null));
        }
        if(!saveMember.getSearchAddrCheck()){
            bindingResult.addError(new FieldError("saveMember", "searchAddrCheck", saveMember.getSearchAddrCheck(), false, new String[]{"required.address.check"}, null, null));
        }
        if(!saveMember.getEmailDuplicateCheck()){
            bindingResult.addError(new FieldError("saveMember", "emailDuplicateCheck", saveMember.getEmailDuplicateCheck(), false, new String[]{"required.email.check"}, null, null));
        }
        if(!saveMember.getPhoneNumberDuplicateCheck()){
            bindingResult.addError(new FieldError("saveMember", "phoneNumberDuplicateCheck", saveMember.getPhoneNumberDuplicateCheck(), false, new String[]{"required.phoneNumber.check"}, null, null));
        }

    }

    @ModelAttribute("email2")
    public EmailAddr[] emailAddrs(){
        return EmailAddr.values();
    }

    @PostMapping("/send")
    @ResponseBody
    public HashMap<String,Object> send(@RequestBody HashMap<String,Object> sendDTO){
        String loginId = memberService.findLoginId((String) sendDTO.get("loginId"));
        log.info("loginId={}",loginId);
        sendDTO.replace("loginId",loginId);
         return sendDTO;
    }

    @PostMapping("/phoneNumberCheck")
    @ResponseBody
    public HashMap<String,Object> phoneNumberSend(@RequestBody HashMap<String,Object> sendDTO){
        String phoneNumber = memberService.findPhoneNumber((String) sendDTO.get("phoneNumber"));
        log.info("phoneNumber={}",phoneNumber);
        sendDTO.replace("phoneNumber",phoneNumber);
        return sendDTO;
    }

    @PostMapping("/emailCheck")
    @ResponseBody
    public HashMap<String,Object> emailSend(@RequestBody HashMap<String,Object> sendDTO){
        String loginId = memberService.findPhoneNumber((String) sendDTO.get("email"));
        log.info("phoneNumber={}",loginId);
        sendDTO.replace("phoneNumber",loginId);
        return sendDTO;
    }


    @GetMapping("/info/{memberId}")
    public String findOne(@PathVariable("memberId")Long memberId,Model model){
        Member member =  memberService.findOne(memberId);
        model.addAttribute("member",member);
        return "view/member/viewMember";
    }
}
