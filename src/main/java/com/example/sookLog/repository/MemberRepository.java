package com.example.sookLog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.sookLog.domain.Member;
import com.example.sookLog.oauth.entity.ProviderType;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByProviderIdAndProviderType(String providerId, ProviderType providerType);
}
