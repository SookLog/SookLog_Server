package com.example.sookLog.domain.sentimentAnalysis.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sookLog.apiPayload.code.ApiResponse;
import com.example.sookLog.domain.sentimentAnalysis.dto.SentimentAnalysisDTO;
import com.example.sookLog.domain.sentimentAnalysis.service.SentimentAnalysisService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sentiment")
@RequiredArgsConstructor
public class SentimentAnalysisController {
	private final SentimentAnalysisService sentimentAnalysisService;

	@PostMapping("/analyze")
	public ResponseEntity<ApiResponse<String>> analyzeSentiment(@RequestBody SentimentAnalysisDTO.SentimentRequest request) {
		// SentimentAnalysisService에서 감정을 분석
		String feeling = sentimentAnalysisService.analyzeSentiment(request.getContent());

		// 감정 결과를 클라이언트에 전달
		return ResponseEntity.ok(ApiResponse.onSuccess(feeling));
	}
}
