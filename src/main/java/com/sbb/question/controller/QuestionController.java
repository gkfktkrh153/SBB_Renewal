package com.sbb.question.controller;

import com.sbb.question.domain.Question;
import com.sbb.question.dto.QuestionRes;
import com.sbb.question.repository.QuestionRepository;
import com.sbb.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
    public String list(Model model){
        List<QuestionRes> questionList = questionService.getList();
        model.addAttribute("questionList", questionList);

        return "question_list";
    }
}
