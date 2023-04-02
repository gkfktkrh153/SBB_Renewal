package com.sbb.answer.service;


import com.sbb.answer.controller.dto.AnswerReq;
import com.sbb.answer.domain.Answer;
import com.sbb.answer.repository.AnswerRepository;
import com.sbb.member.domain.Member;
import com.sbb.question.DataNotFoundException;
import com.sbb.question.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    public void create(Question question, AnswerReq answerReq, Member author){
        Answer answer = Answer.builder()
                        .content(answerReq.getContent())
                        .question(question)
                        .author(author)
                        .createDate(LocalDateTime.now())
                        .build();
        answerRepository.save(answer);

    }
    public Answer getAnswer(Long id) {
        Optional<Answer> answer = this.answerRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

    public void modify(Answer answer, String content) {
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        this.answerRepository.save(answer);
    }
    public void delete(Answer answer) {
        this.answerRepository.delete(answer);
    }
}
