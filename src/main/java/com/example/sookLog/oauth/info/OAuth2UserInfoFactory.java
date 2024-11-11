package com.example.sookLog.oauth.info;

import java.util.Map;

import com.example.sookLog.oauth.entity.ProviderType;
import com.example.sookLog.oauth.info.impl.KakaoOAuth2UserInfo;

public class OAuth2UserInfoFactory {
	public static OAuth2UserInfo getOAuth2UserInfo(ProviderType providerType, Map<String, Object> attributes) {
		switch (providerType) {
			// case NAVER: return new NaverOAuth2UserInfo(attributes);
			case KAKAO: return new KakaoOAuth2UserInfo(attributes);
			default: throw new IllegalArgumentException("Invalid Provider Type.");
		}
	}
}
