package com.example.sookLog.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.example.sookLog.apiPayload.exception.handler.OAuth2FailureHandler;
import com.example.sookLog.apiPayload.exception.handler.OAuth2SuccessHandler;
import com.example.sookLog.jwt.TokenAuthenticationFilter;
import com.example.sookLog.jwt.TokenProvider;
import com.example.sookLog.oauth.service.CustomOAuth2UserService;
import com.example.sookLog.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
	private final OAuth2SuccessHandler successHandler;
	private final OAuth2FailureHandler failureHandler;
	private final CustomOAuth2UserService customOAuthService;
	private final MemberRepository memberRepository;

	@Bean
	public TokenProvider jwtTokenProvider() {
		return new TokenProvider(memberRepository);
	}


	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();

		corsConfiguration.setAllowedOriginPatterns(List.of("*"));
		corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE","PATCH", "HEAD", "OPTIONS"));
		corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
		corsConfiguration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration);

		return source;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(requests -> requests
				.requestMatchers("/test", "/swagger-ui/index.html", "/swagger-ui/**", "/v3/api-docs/**","/swagger-resources/**", "/v3/api-docs").permitAll()
				.anyRequest().authenticated()           // 나머지 URL은 인증 필요
			)
			.addFilterBefore(new TokenAuthenticationFilter(jwtTokenProvider()), UsernamePasswordAuthenticationFilter.class)
			.formLogin(FormLoginConfigurer::disable)
			// OAuth 로그인 설정
			.oauth2Login(customConfigurer -> customConfigurer
				.successHandler(successHandler)
				.failureHandler(failureHandler)
				.userInfoEndpoint(endpointConfig -> endpointConfig.userService(customOAuthService))
			)
			.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
			.csrf(AbstractHttpConfigurer::disable);
		return http.build();
	}

}
