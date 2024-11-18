package com.example.sookLog.domain.diary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sookLog.domain.diary.domain.Diary;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
	long countByFeeling(String feeling);
}
