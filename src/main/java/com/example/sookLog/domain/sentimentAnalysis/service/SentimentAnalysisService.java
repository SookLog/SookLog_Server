package com.example.sookLog.domain.sentimentAnalysis.service;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.sookLog.domain.sentimentAnalysis.dto.SentimentAnalysisDTO;
import com.example.sookLog.domain.sentimentAnalysis.dto.SentimentResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SentimentAnalysisService {
	private final RestTemplate restTemplate = new RestTemplate();
	private final String sentimentAnalysisUrl = "http://43.201.233.113:8000/predict/"; // 모델 API 엔드포인트

	public String analyzeSentiment(String content) {
		// 요청 헤더 생성
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// 요청 바디 생성
		Map<String, String> requestBody = Map.of("text", content);

		// HTTP 요청 생성
		HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

		// 모델에 요청 보내기
		ResponseEntity<SentimentResponse> response = restTemplate.postForEntity(sentimentAnalysisUrl, requestEntity, SentimentResponse.class);

		// 응답 처리
		SentimentResponse responseBody = response.getBody();
		if (responseBody == null || responseBody.getEmotion() == null) {
			throw new IllegalStateException("Invalid response from sentiment analysis model");
		}

		return responseBody.getEmotion(); // emotion 필드 값 반환
	}
}



