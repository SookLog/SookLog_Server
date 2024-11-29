package com.example.sookLog.domain.question.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sookLog.apiPayload.code.ApiResponse;
import com.example.sookLog.domain.question.service.QuestionService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {
	private final QuestionService questionService;

	@Operation(summary = "질문 랜덤 생성 api ", description = "질문 아무거나 반환하도록 함")
	@GetMapping("/random")
	public ResponseEntity<ApiResponse<String>> getRandomQuestion() {
		String question = questionService.getRandomQuestion();
		return ResponseEntity.ok(ApiResponse.onSuccess(question));
	}
}
