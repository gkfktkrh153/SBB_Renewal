package com.sbb.question.controller;

import com.sbb.answer.controller.dto.AnswerReq;
import com.sbb.member.domain.Member;
import com.sbb.member.service.MemberService;
import com.sbb.question.domain.Question;
import com.sbb.question.dto.QuestionReq;
import com.sbb.question.dto.QuestionRes;
import com.sbb.question.repository.QuestionRepository;
import com.sbb.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final MemberService memberService;

    @GetMapping("/")
    public String home(){
        return "redirect:/question/list";
    }

    @GetMapping("/question/list")
    public String list(Model model, @RequestParam(value ="page", defaultValue = "0") int page){
        Page<Question> paging = questionService.getList(page);
        model.addAttribute("paging", paging);

        return "question_list";
    }
    @GetMapping(value = "/question/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id, AnswerReq answerReq)
    {
        Question question = questionService.getQuestion(id);
        model.addAttribute("question", question);

        return "question_detail";
    }
    @GetMapping("/question/create")
    public String create(QuestionReq questionReq){

        return "question_form";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("question/create") // @Valid를 적용하면 @NotEmpty, @Size등 설정한 기능이 동작 !!@BindingResult보다 앞에와야한다!!
    public String create(@Valid QuestionReq questionReq, BindingResult bindingResult, Principal principal){// bindingResult에는 @Valid검증 수행 결과
        Member user = memberService.getUser(principal.getName());

        if(bindingResult.hasErrors())
            return "question_form";
        questionService.create(questionReq, user);
        return "redirect:/question/list";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/question/modify/{id}")
    public String questionModify(QuestionReq questionReq, @PathVariable("id") Long id, Principal principal)
    {
        Question question = questionService.getQuestion(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다");
        }
        questionReq.setSubject(questionReq.getSubject());
        questionReq.setContent(questionReq.getContent());
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/question/modify/{id}")
    public String questionModify(@Valid QuestionReq questionReq, BindingResult bindingResult,
                                 Principal principal, @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.questionService.modify(question, questionReq);
        return String.format("redirect:/question/detail/%s", id);
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/question/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Long id) {
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.questionService.delete(question);
        return "redirect:/";
    }
}
