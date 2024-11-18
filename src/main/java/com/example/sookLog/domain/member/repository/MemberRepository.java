package com.example.sookLog.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.sookLog.domain.member.domain.Member;
import com.example.sookLog.oauth.entity.ProviderType;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByProviderIdAndProviderType(String providerId, ProviderType providerType);

	Optional<Member> findByRefreshToken(String accessToken);

	Optional<Member> findByName(String name);
}
