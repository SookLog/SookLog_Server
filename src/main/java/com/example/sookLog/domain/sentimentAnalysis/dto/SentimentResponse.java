package com.example.sookLog.domain.sentimentAnalysis.dto;

import lombok.Data;

@Data
public class SentimentResponse {
	private int label;
	private String emotion;
}
