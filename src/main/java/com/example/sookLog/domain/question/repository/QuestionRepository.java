package com.example.sookLog.domain.question.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sookLog.domain.question.domain.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
