package com.example.sookLog.jwt;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import io.jsonwebtoken.Claims;
import javax.crypto.SecretKey;
import org.springframework.security.core.GrantedAuthority;
import com.example.sookLog.domain.Member;
import com.example.sookLog.repository.MemberRepository;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenProvider {
	@Value("${jwt.key}")
	private String key;
	private SecretKey secretKey;
	private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30L;
	private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60L * 24 * 7;
	private static final String KEY_ROLE = "role";
	private final MemberRepository memberRepository;

	@PostConstruct
	private void setSecretKey() {
		secretKey = Keys.hmacShaKeyFor(key.getBytes());
	}

	public String generateAccessToken(Authentication authentication) {
		return generateToken(authentication, ACCESS_TOKEN_EXPIRE_TIME);
	}

	// 1. refresh token 발급
	public void generateRefreshToken(Authentication authentication, String accessToken) {
		String refreshToken = generateToken(authentication, REFRESH_TOKEN_EXPIRE_TIME);
		saveOrUpdateRefreshToken(authentication.getName(), refreshToken); // DB에 저장
	}

	private void saveOrUpdateRefreshToken(String name, String refreshToken) {
		Member member = memberRepository.findByName(name)
			.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		member.updateRefreshToken(refreshToken);
		memberRepository.save(member);
	}

	private String generateToken(Authentication authentication, long expireTime) {
		Date now = new Date();
		Date expiredDate = new Date(now.getTime() + expireTime);

		String authorities = authentication.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining());

		return Jwts.builder()
			.setSubject(authentication.getName())
			.claim(KEY_ROLE, authorities)
			.setIssuedAt(now)
			.setExpiration(expiredDate)
			.signWith(secretKey, SignatureAlgorithm.HS512)
			.compact();
	}

	public Authentication getAuthentication(String token) {
		Claims claims = parseClaims(token);
		List<SimpleGrantedAuthority> authorities = getAuthorities(claims);

		// 2. security의 User 객체 생성
		User principal = new User(claims.getSubject(), "", authorities);
		return new UsernamePasswordAuthenticationToken(principal, token, authorities);
	}

	private List<SimpleGrantedAuthority> getAuthorities(Claims claims) {
		return Collections.singletonList(new SimpleGrantedAuthority(
			claims.get(KEY_ROLE).toString()));
	}

	// 3. accessToken 재발급
	public String reissueAccessToken(String accessToken) {
		if (StringUtils.hasText(accessToken)) {
			Member member = memberRepository.findByRefreshToken(accessToken)
				.orElseThrow(() -> new TokenException("Refresh token expired"));
			String refreshToken = member.getRefreshToken();

			if (validateToken(refreshToken)) {
				String newAccessToken = generateAccessToken(getAuthentication(refreshToken));
				member.updateRefreshToken(newAccessToken);
				memberRepository.save(member);
				return newAccessToken;
			}
		}
		return null;
	}

	public boolean validateToken(String token) {
		if (!StringUtils.hasText(token)) {
			return false;
		}

		Claims claims = parseClaims(token);
		return claims.getExpiration().after(new Date());
	}

	private Claims parseClaims(String token) {
		try {
			return Jwts.parserBuilder().setSigningKey(secretKey).build()
				.parseClaimsJws(token).getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		} catch (MalformedJwtException | SecurityException e) {
			throw new TokenException("Invalid token signature");
		}
	}
}
