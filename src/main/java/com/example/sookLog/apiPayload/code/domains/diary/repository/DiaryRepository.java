package com.example.sookLog.apiPayload.code.domains.diary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sookLog.apiPayload.code.domains.diary.domain.Diary;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
}
