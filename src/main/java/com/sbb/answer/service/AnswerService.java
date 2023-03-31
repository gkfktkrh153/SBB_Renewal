package com.sbb.answer.service;


import com.sbb.answer.controller.dto.AnswerReq;
import com.sbb.answer.domain.Answer;
import com.sbb.answer.repository.AnswerRepository;
import com.sbb.question.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    public void create(Question question, AnswerReq answerReq){
        Answer answer = Answer.builder()
                        .content(answerReq.getContent())
                        .question(question)
                        .createDate(LocalDateTime.now())
                        .build();
        answerRepository.save(answer);

    }
}
