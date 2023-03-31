package com.sbb.question.controller;

import com.sbb.answer.controller.dto.AnswerReq;
import com.sbb.question.domain.Question;
import com.sbb.question.dto.QuestionReq;
import com.sbb.question.dto.QuestionRes;
import com.sbb.question.repository.QuestionRepository;
import com.sbb.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

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
    @GetMapping(value = "question/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id, AnswerReq answerReq)
    {
        Question question = questionService.getQuestion(id);
        model.addAttribute("question", question);

        return "question_detail";
    }
    @GetMapping("question/create")
    public String create(QuestionReq questionReq){

        return "question_form";
    }
    @PostMapping("question/create") // @Valid를 적용하면 @NotEmpty, @Size등 설정한 기능이 동작 !!@BindingResult보다 앞에와야한다!!
    public String create(@Valid QuestionReq questionReq, BindingResult bindingResult){// bindingResult에는 @Valid검증 수행 결과
        if(bindingResult.hasErrors())
            return "question_form";
        questionService.create(questionReq);
        return "redirect:/question/list";
    }

}
