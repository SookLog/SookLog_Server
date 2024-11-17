package com.example.sookLog.apiPayload.code.domains.member.service;

import org.springframework.stereotype.Service;

import com.example.sookLog.apiPayload.code.domains.diary.repository.DiaryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiaryService {

	private final DiaryRepository diaryRepository;
}
