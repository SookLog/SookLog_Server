package com.example.sookLog.domain;

import com.example.sookLog.oauth.entity.ProviderType;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String providerId;

	@Enumerated(EnumType.STRING)
	private ProviderType providerType;

	@Column(nullable = false, unique = true)
	private String name;

	private String profileImageUrl;

	private String refreshToken;

	public void updateRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

}
