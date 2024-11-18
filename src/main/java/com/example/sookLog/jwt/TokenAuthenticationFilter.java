package com.example.sookLog.jwt;

import static org.springframework.http.HttpHeaders.*;

import java.io.IOException;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

	private final TokenProvider tokenProvider;

	private static final List<String> EXCLUDED_URLS = List.of(
		"/test",
		"/login",
		"/swagger-ui/**",
		"/v3/api-docs/**"
	);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String requestURI = request.getRequestURI();

		// 요청 URL이 허용된 URL 목록에 포함되어 있다면 필터 건너뛰기
		if (isExcludedUrl(requestURI)) {
			filterChain.doFilter(request, response);
			return;
		}
		String accessToken = resolveToken(request);

		// 로그로 필터가 실행되는지 확인
		System.out.println("TokenAuthenticationFilter: Filtering request");

		if (!StringUtils.hasText(accessToken)) {
			System.out.println("Token is missing.");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("Access token is missing or invalid.");
			return;
		}

		// 기존 토큰 검증 로직
		if (tokenProvider.validateToken(accessToken)) {
			setAuthentication(accessToken);
		} else {
			String reissueAccessToken = tokenProvider.reissueAccessToken(accessToken);
			if (StringUtils.hasText(reissueAccessToken)) {
				setAuthentication(reissueAccessToken);
				response.setHeader(AUTHORIZATION, TokenKey.TOKEN_PREFIX + reissueAccessToken);
			} else {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write("Access token is expired and cannot be reissued.");
				return;
			}
		}

		filterChain.doFilter(request, response);
	}

	private void setAuthentication(String accessToken) {
		Authentication authentication = tokenProvider.getAuthentication(accessToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private String resolveToken(HttpServletRequest request) {
		String token = request.getHeader(AUTHORIZATION);
		if (ObjectUtils.isEmpty(token) || !token.startsWith(TokenKey.TOKEN_PREFIX)) {
			return null;
		}
		return token.substring(TokenKey.TOKEN_PREFIX.length());
	}

	private boolean isExcludedUrl(String requestURI) {
		return EXCLUDED_URLS.stream().anyMatch(requestURI::startsWith);
	}
}
