package com.example.sookLog.domain.question.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sookLog.apiPayload.code.ApiResponse;
import com.example.sookLog.domain.question.service.QuestionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {
	private final QuestionService questionService;

	@GetMapping("/random")
	public ResponseEntity<ApiResponse<String>> getRandomQuestion() {
		String question = questionService.getRandomQuestion();
		return ResponseEntity.ok(ApiResponse.onSuccess(question));
	}
}
