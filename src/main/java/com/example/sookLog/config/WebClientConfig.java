package com.example.sookLog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
	@Bean
	public WebClient webClient(WebClient.Builder builder) {
		return builder.baseUrl("http://MODEL_API_BASE_URL") // 모델 API의 기본 URL
			.build();
	}
}
