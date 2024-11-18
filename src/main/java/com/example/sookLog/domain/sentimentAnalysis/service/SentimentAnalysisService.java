package com.example.sookLog.domain.sentimentAnalysis.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.sookLog.domain.sentimentAnalysis.dto.SentimentAnalysisDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SentimentAnalysisService {
	private final WebClient webClient;

	public String analyzeSentiment(String content) {
		// 모델 API 호출 및 감정 분석 결과 반환
		SentimentAnalysisDTO.SentimentRequest request = new SentimentAnalysisDTO.SentimentRequest(content);
		SentimentAnalysisDTO.SentimentResponse response = webClient.post()
			.uri("/analyze") // 모델 API의 엔드포인트
			.bodyValue(request)
			.retrieve()
			.bodyToMono(SentimentAnalysisDTO.SentimentResponse.class)
			.block();

		return response.getFeeling(); // 모델이 반환하는 감정 값
	}
}
