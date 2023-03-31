package com.sbb.question.service;

import com.sbb.question.DataNotFoundException;
import com.sbb.question.QuestionUpdate;
import com.sbb.question.domain.Question;
import com.sbb.question.dto.QuestionReq;
import com.sbb.question.dto.QuestionRes;
import com.sbb.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Page<Question> getList(int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        PageRequest pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.questionRepository.findAll(pageable);
    }
    public Question getQuestion(Long id) {
        Optional<Question> question = this.questionRepository.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    public void create(QuestionReq questionReq) {
        Question question = Question.builder().
                content(questionReq.getContent()).
                subject(questionReq.getSubject()).
                createDate(LocalDateTime.now())
                .build();

        questionRepository.save(question);
    }
}
