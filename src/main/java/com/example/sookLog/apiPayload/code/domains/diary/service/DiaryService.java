package com.example.sookLog.apiPayload.code.domains.diary.service;

import static com.example.sookLog.apiPayload.code.domains.diary.dto.DiaryDTO.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sookLog.apiPayload.code.domains.diary.domain.Diary;
import com.example.sookLog.apiPayload.code.domains.diary.dto.DiaryDTO;
import com.example.sookLog.apiPayload.code.domains.diary.repository.DiaryRepository;
import com.example.sookLog.apiPayload.code.domains.member.domain.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryService {

	private final DiaryRepository diaryRepository;

	@Transactional
	public void createDiary(DiaryRequest request, Member member) {
		// 정적 팩토리 메서드를 사용해 Diary 객체 생성
		Diary diary = Diary.from(
			request.getTitle(),
			request.getContent(),
			request.getWeather(),
			request.getFeeling(),
			member
		);

		// Diary 저장
		diaryRepository.save(diary);
	}

	public DiaryResponse getDiaryById(Long id) {
		// ID로 Diary 조회, 없으면 예외 발생
		Diary diary = diaryRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Diary not found with ID: " + id));

		// Diary를 DiaryResponse로 변환하여 반환
		return new DiaryResponse(
			diary.getId(),
			diary.getTitle(),
			diary.getContent(),
			diary.getWeather(),
			diary.getFeeling(),
			diary.getDateTime()
		);
	}

}
