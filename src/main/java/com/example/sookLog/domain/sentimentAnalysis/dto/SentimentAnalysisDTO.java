package com.example.sookLog.domain.sentimentAnalysis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class SentimentAnalysisDTO {
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class SentimentRequest {
		private String content; // 분석할 텍스트
	}

	@Getter
	@NoArgsConstructor
	public static class SentimentResponse {
		private String feeling; // 분석된 감정 결과
	}
}
