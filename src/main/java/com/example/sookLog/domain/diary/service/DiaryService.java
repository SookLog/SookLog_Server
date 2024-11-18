package com.example.sookLog.domain.diary.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sookLog.domain.member.domain.Member;
import com.example.sookLog.domain.diary.domain.Diary;
import com.example.sookLog.domain.diary.dto.DiaryDTO;
import com.example.sookLog.domain.diary.repository.DiaryRepository;
import com.example.sookLog.domain.member.repository.MemberRepository;
import com.example.sookLog.domain.sentimentAnalysis.service.SentimentAnalysisService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryService {
	private final DiaryRepository diaryRepository;
	private final MemberRepository memberRepository;
	private final SentimentAnalysisService sentimentAnalysisService;
	public Member findMemberById(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new IllegalArgumentException("Member not found with ID: " + memberId));
	}
	@Transactional
	public void createDiary(DiaryDTO.DiaryRequest request, Member member) {
		// 사용자 입력값으로 Diary 생성
		/*Diary diary = Diary.from(
			request.getTitle(),
			request.getContent(),
			request.getWeather(),
			member
		);

		// Diary 저장
		diaryRepository.save(diary);*/
		//Member member = new Member("Test User");
		memberRepository.save(member); // Member를 먼저 저장

		// Diary 생성 및 저장
		Diary diary = Diary.from(request.getTitle(), request.getContent(),request.getWeather(),member);
		diaryRepository.save(diary);

		// 감정 분석 후 feeling 업데이트
		String feeling = sentimentAnalysisService.analyzeSentiment(request.getContent());
		diary.updateFeeling("feeling"); // 저장 후 감정 업데이트
	}

	public DiaryDTO.DiaryResponse getDiaryById(Long id) {
		// ID로 Diary 조회, 없으면 예외 발생
		Diary diary = diaryRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Diary not found with ID: " + id));

		// Diary를 DiaryResponse로 변환하여 반환
		return new DiaryDTO.DiaryResponse(
			diary.getId(),
			diary.getTitle(),
			diary.getContent(),
			diary.getWeather(),
			diary.getFeeling(),
			diary.getDateTime()
		);
	}

	@Transactional
	public void updateFeeling(Long diaryId, String feeling) {
		Diary diary = diaryRepository.findById(diaryId)
			.orElseThrow(() -> new IllegalArgumentException("Diary not found with ID: " + diaryId));

		diary.updateFeeling(feeling); // 감정 업데이트
	}

	public int getPositiveDiaryCountModulo() {
		// "긍정" 상태의 다이어리 개수 가져오기
		long positiveCount = diaryRepository.countByFeeling("행복");

		// 긍정 개수를 10으로 나눈 나머지를 반환
		return (int) (positiveCount % 10);
	}

	public List<DiaryDTO.FeelingResponse> getMonthlyFeelings(int year, int month) {
		// 시작일과 종료일 계산
		LocalDate startDate = LocalDate.of(year, month, 1);
		LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

		// 해당 월의 일기 가져오기
		List<Diary> diaries = diaryRepository.findByDateTimeBetween(startDate.atStartOfDay(), endDate.atTime(23, 59, 59));

		// Diary 엔티티를 DTO로 변환
		return diaries.stream()
			.map(diary -> new DiaryDTO.FeelingResponse(diary.getDateTime().toLocalDate(), diary.getFeeling()))
			.collect(Collectors.toList());
	}
}
