package com.example.sookLog.apiPayload.code.domains.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sookLog.apiPayload.code.domains.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
