package com.project.concertView.web.controller;

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
/**
 * 회원 관련 Controller 클래스
 * - 회원가입
 * - 비밀번호 변겅
 * - 회원 정보 조회
 * - 좋아요 누른 공연 조회
 * */

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    // 로그인 페이지
    @GetMapping("/login")
    public String login(@ModelAttribute("loginMember") LoginMemberDTO loginMemberDTO) {
        return "view/member/Login";
    }
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginMember") LoginMemberDTO loginMemberDTO, BindingResult bindingResult, HttpSession session, Model model) {
        //globalError에 대하여 추가하여 bindingResult에 저장
        bindingResultInsert(loginMemberDTO, bindingResult);

        //bindingResult에 하나라도 에러가 발생시 에러표시와 함께 Login 페이지로 다시 돌아감
        if (bindingResult.hasErrors()) {
            return "view/member/Login";
        }
        //그리고 세션에 저장됨
        session.setAttribute(SessionValue.LOGIN_SESSION, memberService.loginMember(loginMemberDTO));
        session.setAttribute(SessionValue.LOGIN_ID_SESSION,loginMemberDTO.getLoginId());
        //로그인 아이디 값을 공유하기 위하여 해당 값을 ThreadLocal 에 넣어두고 model 객체를 통하여 전달함
        log.info("****id={}",loginMemberDTO.getLoginId());
        return "redirect:/concert/detailView";
    }

    //로그아웃 시 저장하였던 session값과 threadLocal 값을 제거해줌
    @RequestMapping("/logOut")
    public String logOut(HttpSession session) {
        session.removeAttribute(SessionValue.LOGIN_SESSION);
        session.removeAttribute(SessionValue.LOGIN_ID_SESSION);
        return "redirect:/concert/detailView";
    }


    //error 메세지 직접 커스텀 하여 bindingResult 에 저장
    private void bindingResultInsert(LoginMemberDTO loginMemberDTO, BindingResult bindingResult) {
        String loginId = memberService.findLoginId(loginMemberDTO.getLoginId());
        //데이터베이스를 통해 조회된 로그인 아이디가 존재하지 않을 시 , bindingResult에 에러내용 저장
        loginIdNotFound(loginId, bindingResult);
        Long memberId = memberService.loginMember(loginMemberDTO);
        //데이터 베이스에 일치하는 아이디, 비밀번호가 존재하지 않을 시 , null 반환 ->  bindingResult에 에러내용 저장
        loginFail(memberId, bindingResult);
    }
    //데이터베이스를 통해 조회된 로그인 아이디가 존재하지 않을 시 , bindingResult에 에러내용 저장

    private void loginIdNotFound(String loginId, BindingResult bindingResult) {
        if (loginId != null) {
            bindingResult.addError(new ObjectError("memberLogin", "존재하지 않거나 없는 아이디입니다."));
        }
    }
    //데이터 베이스에 일치하는 아이디, 비밀번호가 존재하지 않을 시 , null 반환 ->  bindingResult에 에러내용 저장
    private void loginFail(Long memberId, BindingResult bindingResult) {
        if (memberId == null) {
            bindingResult.addError(new ObjectError("memberLogin", "아이디/비밀번호가 틀렸습니다."));
        }
    }

    //1. 회원가입
    @GetMapping("/save")
    public String saveMember(@ModelAttribute("saveMember") SaveMember saveMember) {
        return "view/member/Register";
    }
    @PostMapping("/save")
    public String saveMember(@Valid @ModelAttribute("saveMember") SaveMember saveMember, BindingResult bindingResult) {
        //globalError -> bindingResult 에 저장
        checkOrNot(saveMember, bindingResult);
        if (bindingResult.hasErrors()) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.info("error field ={} cause={}", fieldError.getField(), fieldError.getRejectedValue());
            }
            return "view/member/Register";
        }
        return "redirect:/member/login";
    }


    /**
     * 회원가입시 하기 1 ~ 5번 항목에 대하여 하나라도 FALSE인 항목에 대하여 BindingResult에 담아서 GlobalError 로 표시됨
     * 1. 중복확인 여부
     * 2. 패스워드 = 패스워드(확인용) 일치 여부
     * 3. 다음 주소검색 API를 통한 주소 입력
     * 4. 이메일 중복확인
     * 5. 휴대전화 중복확인
     *
     * */
    private void checkOrNot(@ModelAttribute("saveMember") SaveMember saveMember, BindingResult bindingResult) {
        if (!saveMember.getDuplicateIdCheck()) {
            bindingResult.addError(new FieldError("saveMember", "duplicateIdCheck", saveMember.getDuplicateIdCheck(),false,new String[]{"required.loginId.check"},null,"faewafeafwaef"));
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
    // 이메일 계정 Model 계정
    @ModelAttribute("email2")
    public EmailAddr[] emailAddrs() {
        return EmailAddr.values();
    }

    //로그인 아이디 찾는 클래스
    @PostMapping("/send")
    @ResponseBody
    public HashMap<String, Object> send(@RequestBody HashMap<String, Object> sendDTO) {
        String loginId = memberService.findLoginId((String) sendDTO.get("loginId"));
        sendDTO.replace("loginId", loginId);
        return sendDTO;
    }

    //휴대폰 번호 중복체크 메서드 > 휴대폰 번호 조회시 기존 번호가 존재하면 null 값 반환, 없으면 휴대폰번호 그대로 반환하여 view 전달
    @PostMapping("/phoneNumberCheck")
    @ResponseBody
    public HashMap<String, Object> phoneNumberSend(@RequestBody HashMap<String, Object> sendDTO) {
        String phoneNumber = memberService.findPhoneNumber((String) sendDTO.get("phoneNumber"));
        sendDTO.replace("phoneNumber", phoneNumber);
        return sendDTO;
    }
    //이메일 중복체크 메서드 > 이메일 주소 조회시 기존 주소가 존재하면 null 값 반환, 없으면 그대로 반환하여 view 전달
    @PostMapping("/emailCheck")
    @ResponseBody
    public HashMap<String, Object> emailSend(@RequestBody HashMap<String, Object> sendDTO) {
        String e = sendDTO.get("email")+"@"+sendDTO.get("emailAccountWrite");
        String email = memberService.findEmail(e);
        log.info("email={}",email);
        sendDTO.replace("email", email);
        return sendDTO;
    }
    // 내 정보
    @GetMapping("/info/{memberId}")
    @LogRecord
    public String findOne(@PathVariable("memberId") Long memberId, Model model) {
        Member member = memberService.findOne(memberId);
        model.addAttribute("member", member);
        return "view/member/ViewMember";
    }







    //비밀번호 찾기
    @GetMapping("/password/find")
    public String passwordFind(@ModelAttribute("findPassword") FindPassword findPassword) {
            return "view/member/PasswordFind";
    }
    @PostMapping("/password/find")
    public String passwordFind(@ModelAttribute("findPassword") FindPassword findPassword, BindingResult bindingResult){
        //비밀번호를 위하여 FindPassword에 있는 값들 조회
        Long id=  memberService.findPassword(findPassword);
        //일치하는 내용이 없으면 null값으로 뜨게 되는데 bindingResult에 해당 에러 메세지 담아둠
        passwordFind(id,bindingResult);
        if (bindingResult.hasErrors()) {
            return "view/member/PasswordFind";
        }
        return "redirect:/member/password/revise/"+id;
    }
    //비밀번호 수정
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
    // isFindPassword를 전달받아 비밀번호 찾기
    private void passwordFind(Long isFindPassword, BindingResult bindingResult) {
        if (isFindPassword==null) {
            bindingResult.addError(new ObjectError("findPassword", "일치하는 회원을 찾을 수 없습니다."));
        }
    }
}
