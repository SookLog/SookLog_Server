package com.example.sookLog.domain.question.service;
import com.example.sookLog.domain.question.domain.Question;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.example.sookLog.domain.question.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService {
	private final QuestionRepository questionRepository;

	public String getRandomQuestion() {
		long count = questionRepository.count(); // 질문 총 개수
		if (count == 0) {
			throw new IllegalArgumentException("No questions available in the database");
		}

		// 랜덤 ID로 질문 가져오기
		long randomId = new Random().nextLong(1, count + 1); // 1부터 count까지 랜덤 값 생성
		return questionRepository.findById(randomId)
			.map(Question::getContent)
			.orElseThrow(() -> new IllegalArgumentException("No question found with the random ID"));
	}
}
