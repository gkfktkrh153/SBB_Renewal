package com.sbb;

import com.sbb.question.QuestionUpdate;
import com.sbb.question.domain.Question;
import com.sbb.question.repository.QuestionRepository;
import com.sbb.question.service.QuestionService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class SbbApplicationTests {


    @Autowired
    QuestionService questionService;

    @Autowired
    QuestionRepository questionRepository;

    @Test
    void contextLoads() {
        Question question = Question.builder()
                .subject("sbb가 무엇인가요")
                .content("sbb에 대해 알고싶습니다")
                .createDate(LocalDateTime.now())
                .build();

        questionRepository.save(question);

        List<Question> qList = questionRepository.findBySubjectLike("sbb%");
        Question q = qList.get(0);
        Assertions.assertThat("sbb가 무엇인가요?").isEqualTo(q.getSubject());
    }
    @Test
    @Transactional
    void testJpa(){
        Optional<Question> oq = questionRepository.findById(1L);
        Question question = oq.get();

        questionService.update(1L,new QuestionUpdate("update subject5", "update content1"));


        Assertions.assertThat(question.getSubject()).isEqualTo("update subject5");

    }

}
