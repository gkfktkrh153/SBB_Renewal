package com.sbb.question.repository;

import com.sbb.question.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findBySubjectLike(String subject);

}
