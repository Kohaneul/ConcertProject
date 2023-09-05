package com.project.concertView.web.controller;

import com.project.concertView.domain.dao.concert.LikeConcertInsert;
import com.project.concertView.domain.dao.member.FindPassword;
import com.project.concertView.domain.dao.member.Member;
import com.project.concertView.domain.dao.member.SaveMember;
import com.project.concertView.domain.dao.member.UpdatePassword;
import com.project.concertView.domain.dao.member.annotation.log.LogRecord;
import com.project.concertView.domain.dto.LoginMemberDTO;
import com.project.concertView.domain.entity.EmailAddr;
import com.project.concertView.domain.entity.SessionValue;
import com.project.concertView.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/save")
    public String saveMember(@ModelAttribute("saveMember") SaveMember saveMember) {
        return "view/member/Register";
    }

    @PostMapping("/save")
    public String saveMember(@Valid @ModelAttribute("saveMember") SaveMember saveMember, BindingResult bindingResult) {
        checkOrNot(saveMember, bindingResult);
        if (bindingResult.hasErrors()) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.info("error field ={} cause={}", fieldError.getField(), fieldError.getRejectedValue());
            }
            return "view/member/Register";
        }
        Long id = memberService.findById(saveMember);
        return "redirect:/member/info/" + id;
    }




    private void checkOrNot(@ModelAttribute("saveMember") SaveMember saveMember, BindingResult bindingResult) {
        if (!saveMember.getDuplicateIdCheck()) {
            bindingResult.addError(new FieldError("saveMember", "duplicateIdCheck", saveMember.getDuplicateIdCheck(), false, new String[]{"required.loginId.check"}, null, null));
        }
        if (!saveMember.getPasswordEqualsCheck()) {
            bindingResult.addError(new FieldError("saveMember", "passwordEqualsCheck", saveMember.getPasswordEqualsCheck(), false, new String[]{"required.password.check"}, null, null));
        }
        if (!saveMember.getSearchAddrCheck()) {
            bindingResult.addError(new FieldError("saveMember", "searchAddrCheck", saveMember.getSearchAddrCheck(), false, new String[]{"required.address.check"}, null, null));
        }
        if (!saveMember.getEmailDuplicateCheck()) {
            bindingResult.addError(new FieldError("saveMember", "emailDuplicateCheck", saveMember.getEmailDuplicateCheck(), false, new String[]{"required.email.check"}, null, null));
        }
        if (!saveMember.getPhoneNumberDuplicateCheck()) {
            bindingResult.addError(new FieldError("saveMember", "phoneNumberDuplicateCheck", saveMember.getPhoneNumberDuplicateCheck(), false, new String[]{"required.phoneNumber.check"}, null, null));
        }

    }

    @ModelAttribute("email2")
    public EmailAddr[] emailAddrs() {
        return EmailAddr.values();
    }

    @PostMapping("/send")
    @ResponseBody
    public HashMap<String, Object> send(@RequestBody HashMap<String, Object> sendDTO) {
        String loginId = memberService.findLoginId((String) sendDTO.get("loginId"));
        sendDTO.replace("loginId", loginId);
        return sendDTO;
    }

    @PostMapping("/phoneNumberCheck")
    @ResponseBody
    public HashMap<String, Object> phoneNumberSend(@RequestBody HashMap<String, Object> sendDTO) {
        String phoneNumber = memberService.findPhoneNumber((String) sendDTO.get("phoneNumber"));
        sendDTO.replace("phoneNumber", phoneNumber);
        return sendDTO;
    }

    @PostMapping("/emailCheck")
    @ResponseBody
    public HashMap<String, Object> emailSend(@RequestBody HashMap<String, Object> sendDTO) {
        String e = sendDTO.get("email")+"@"+sendDTO.get("emailAccountWrite");
        String email = memberService.findEmail(e);
//        String emailAccountWrite = "@" + memberService.findEmail((String) sendDTO.get("emailAccountWrite"));
//        email = email + emailAccountWrite;
        log.info("email={}",email);
        sendDTO.replace("email", email);
        return sendDTO;
    }

    @GetMapping("/info/{memberId}")
    @LogRecord
    public String findOne(@PathVariable("memberId") Long memberId, Model model) {
        Member member = memberService.findOne(memberId);
        model.addAttribute("member", member);
        return "view/member/viewMember";
    }


    @GetMapping("/login")
    public String login(@ModelAttribute("loginMember") LoginMemberDTO loginMemberDTO) {
        return "view/member/Login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginMember") LoginMemberDTO loginMemberDTO, BindingResult bindingResult, HttpSession session) {
        bindingResultInsert(loginMemberDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return "view/member/Login";
        }
        session.setAttribute(SessionValue.LOGIN_PK_ID_SESSION, memberService.loginMember(loginMemberDTO));
        session.setAttribute(SessionValue.LOGIN_ID_SESSION, loginMemberDTO.getLoginId());
        log.info("SessionValue={} : {}", SessionValue.LOGIN_PK_ID_SESSION, session.getAttribute(SessionValue.LOGIN_ID_SESSION));
        return "redirect:/concert/detailView";
    }


    @RequestMapping("/logOut")
    public String logOut(HttpSession session) {
        session.removeAttribute(SessionValue.LOGIN_ID_SESSION);
        return "redirect:/concert/detailView";
    }


    private void bindingResultInsert(LoginMemberDTO loginMemberDTO, BindingResult bindingResult) {
        String loginId = memberService.findLoginId(loginMemberDTO.getLoginId());
        loginIdNotFound(loginId, bindingResult);
        Long memberId = memberService.loginMember(loginMemberDTO);
        loginFail(memberId, bindingResult);
    }


    private void loginIdNotFound(String loginId, BindingResult bindingResult) {
        if (loginId != null) {
            bindingResult.addError(new ObjectError("memberLogin", "존재하지 않거나 없는 아이디입니다."));
        }
    }

    private void loginFail(Long memberId, BindingResult bindingResult) {
        if (memberId == null) {
            bindingResult.addError(new ObjectError("memberLogin", "아이디/비밀번호가 틀렸습니다."));
        }
    }
    @GetMapping("/password/find")
    public String passwordFind(@ModelAttribute("findPassword") FindPassword findPassword) {
            return "view/member/PasswordFind";

    }

    @PostMapping("/password/find")
    public String passwordFind(@ModelAttribute("findPassword") FindPassword findPassword, BindingResult bindingResult){
        Long id=  memberService.findPassword(findPassword);
        passwordFind(id,bindingResult);
        if (bindingResult.hasErrors()) {
            return "view/member/PasswordFind";
        }
        return "redirect:/member/password/revise/"+id;
    }

    @GetMapping("/password/revise/{id}")
    public String passwordUpdate(@PathVariable("id") Long id,@ModelAttribute("updatePassword") UpdatePassword updatePassword) {
        updatePassword.setId(id);
        return "view/member/PasswordUpdate";
    }

    @PostMapping("/password/revise/{id}")
    public String passwordUpdate(@Valid @ModelAttribute("updatePassword") UpdatePassword updatePassword,BindingResult bindingResult) {
        memberService.updatePassword(updatePassword);
        if (bindingResult.hasErrors()) {
            return "view/member/PasswordUpdate";
        }
        return "redirect:/member/login";
    }

    private void passwordFind(Long isFindPassword, BindingResult bindingResult) {
        if (isFindPassword==null) {
            bindingResult.addError(new ObjectError("findPassword", "일치하는 회원을 찾을 수 없습니다."));
        }
    }
//    @RequestMapping("/likeConcertSend/{mt20id}")
//    public void mt20idSave(@PathVariable("mt20id") String mt20id,@SessionAttribute("LOGIN_SESSION_ID")Long memberId){
//        log.info("{} like {} ",memberId,{});
//        String mt20id = (String) sendDTO.get("mt20id");
//        memberService.insertLikeConcert(new LikeConcertInsert(memberId,mt20id));
//        log.info("저장 성공={}",mt20id);
//    }


//    @LoginCheck
//    @PostMapping("/login")
//    public ResponseEntity<LoginMemberDTO> memberDTO(){
//        Long memberId = memberService.getLoginUser();
//        LoginMemberDTO member = memberService.findLoginMemberDTO(memberId);
//        return ResponseEntity.ok(member);
//    }

}
