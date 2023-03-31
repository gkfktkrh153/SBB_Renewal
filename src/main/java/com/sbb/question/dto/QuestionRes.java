package com.sbb.question.dto;

import com.sbb.answer.domain.Answer;
import com.sbb.question.domain.Question;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class QuestionRes {

    private Long id;

    private String subject;

    private String content;

    private LocalDateTime createDate;

    private List<Answer> answerList;

    public QuestionRes(Question question) {
        this.id = question.getId();
        this.subject = question.getSubject();
        this.content = question.getContent();
        this.createDate = question.getCreateDate();
        this.answerList = question.getAnswerList();
    }
}
