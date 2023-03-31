package com.sbb.question.service;

import com.sbb.question.DataNotFoundException;
import com.sbb.question.QuestionUpdate;
import com.sbb.question.domain.Question;
import com.sbb.question.dto.QuestionRes;
import com.sbb.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<QuestionRes> getList(){
        return this.questionRepository.findAll().stream().
                map(question -> new QuestionRes(question))
                .collect(Collectors.toList());
    }
    public Question getQuestion(Long id) {
        Optional<Question> question = this.questionRepository.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

}
