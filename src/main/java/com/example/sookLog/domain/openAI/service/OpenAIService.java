package com.example.sookLog.domain.openAI.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenAIService {
	@Value("${openai.api-key}")
	private String apiKey;

	@Value("${openai.url}")
	private String apiUrl;

	public String generateImage(String feeling) {
		RestTemplate restTemplate = new RestTemplate();

		// HTTP 헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + apiKey);
		headers.set("Content-Type", "application/json");

		// 요청 바디 구성
		String prompt = feeling + " illustrate";
		Map<String, Object> requestBody = Map.of(
			"prompt", prompt,
			"n", 1,
			"size", "256x256"
		);

		HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

		// API 호출
		ResponseEntity<Map> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, Map.class);

		// 응답에서 이미지 URL 추출
		List<Map<String, String>> data = (List<Map<String, String>>) response.getBody().get("data");
		if (data == null || data.isEmpty()) {
			throw new IllegalStateException("No image data returned from OpenAI");
		}

		// 첫 번째 URL 반환
		return data.get(0).get("url");
	}
}
