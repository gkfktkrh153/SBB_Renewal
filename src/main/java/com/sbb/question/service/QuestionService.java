package com.sbb.question.service;

import com.sbb.question.QuestionUpdate;
import com.sbb.question.domain.Question;
import com.sbb.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Transactional
    public Long update(Long id ,QuestionUpdate q){
        Question question1 = questionRepository.findById(id).orElse(null);
        question1.setContent(q.getContent());
        question1.setSubject(q.getSubject());
        return id;
    }
}
