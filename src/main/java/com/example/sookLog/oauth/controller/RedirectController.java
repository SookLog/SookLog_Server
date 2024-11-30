package com.example.sookLog.oauth.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class RedirectController {

	@GetMapping("/test")
	public void redirectToApp(@RequestParam("accessToken") String accessToken, HttpServletResponse response) throws
		IOException {
		// 앱 딥링크로 리다이렉트
		String appDeepLink = UriComponentsBuilder.fromUriString("myapp://home")
			.queryParam("accessToken", accessToken)
			.build().toUriString();

		response.sendRedirect(appDeepLink);
	}
}
