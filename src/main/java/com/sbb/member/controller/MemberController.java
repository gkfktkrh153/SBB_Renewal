package com.sbb.member.controller;

import com.sbb.member.dto.MemberReq;
import com.sbb.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/signup")
    public String signup(MemberReq memberReq)
    {
        return "signup_form";
    }
    @PostMapping("/signup")
    public String signup(@Valid MemberReq memberReq, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()){
            return "signup_form";
        }
        if(!memberReq.getPassword1().equals(memberReq.getPassword2())){
            bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다");
            return "signup_form";
        }
        try{
            memberService.create(memberReq);
        } catch(
            DataIntegrityViolationException e) {
                e.printStackTrace();
                bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
                return "signup_form";
        }catch(Exception e) {
                e.printStackTrace();
                bindingResult.reject("signupFailed", e.getMessage());
                return "signup_form";
            }
        return "redirect:/";
    }
    @GetMapping("/login")
    public String login(){
        return "login_form";
    }
}
