package com.example.sookLog.domain.sentimentAnalysis.service;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.sookLog.domain.sentimentAnalysis.dto.SentimentAnalysisDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SentimentAnalysisService {
	private final RestTemplate restTemplate = new RestTemplate();
	private final String sentimentAnalysisUrl = "http://your-model-url/analyze"; // 모델 API 엔드포인트

	public String analyzeSentiment(String content) {
		// 요청 헤더 생성
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");

		// 요청 바디 생성
		Map<String, String> requestBody = Map.of("content", content);

		// HTTP 요청 생성
		HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

		// 모델에 요청 보내기
		ResponseEntity<Map> response = restTemplate.postForEntity(sentimentAnalysisUrl, requestEntity, Map.class);

		// 모델의 응답에서 emotion 필드 추출
		Map<String, Object> responseBody = response.getBody();
		if (responseBody == null || !responseBody.containsKey("emotion")) {
			throw new IllegalStateException("Invalid response from sentiment analysis model");
		}

		return (String) responseBody.get("emotion"); // emotion 필드 값 반환
	}
}
