package com.example.sookLog.domain.diary.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.sookLog.apiPayload.code.ApiResponse;
import com.example.sookLog.domain.member.domain.Member;
import com.example.sookLog.domain.diary.dto.DiaryDTO;
import com.example.sookLog.domain.diary.service.DiaryService;
import com.example.sookLog.domain.member.repository.MemberRepository;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/diaries")
@RequiredArgsConstructor
public class DiaryController {
	private final DiaryService diaryService;
	private final MemberRepository memberRepository;


	@Operation(summary = "다이어리 작성하고 완료 버튼 누르면 이거 api 쓰면 됨", description = "다이어리 쓴 글 넘겨주면 바로 감정분석 결과 나옴")
	@PostMapping
	public ResponseEntity<ApiResponse<DiaryDTO.ModelResponse>> createDiary(
		@RequestBody DiaryDTO.DiaryRequest request,
		@RequestParam Long memberId
	) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new IllegalArgumentException("Member not found with ID: " + memberId));

		// 다이어리를 생성하고 감정 분석 후 저장
		DiaryDTO.ModelResponse modelResponse = diaryService.createDiaryWithFeeling(request, member);

		return ResponseEntity.ok(ApiResponse.onSuccess(modelResponse));
	}

	@Operation(summary = "다이어리 상세조회", description = "그 id로는 다이어리 id주면 되고 다이어리 상세 조회임")
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<DiaryDTO.DiaryResponse>> getDiaryById(@PathVariable Long id) {
		DiaryDTO.DiaryResponse diary = diaryService.getDiaryById(id);
		return ResponseEntity.ok(ApiResponse.onSuccess(diary));
	}

	@PatchMapping("/{id}/feeling")
	public ResponseEntity<ApiResponse<Void>> updateFeeling(
		@PathVariable Long id,
		@RequestParam String feeling
	) {
		diaryService.updateFeeling(id, feeling);
		return ResponseEntity.ok(ApiResponse.onSuccess(null));
	}

	@Operation(summary = "다이어리 한달 달력 감정 반환", description = "캘린더에서 한번에 감정 볼 수 있는 화면에서 쓰면 됨")
	@GetMapping("/monthly-feelings")
	public ResponseEntity<ApiResponse<List<DiaryDTO.FeelingResponse>>> getMonthlyFeelings(
		@RequestParam int year,
		@RequestParam int month
	) {
		List<DiaryDTO.FeelingResponse> feelings = diaryService.getMonthlyFeelings(year, month);
		return ResponseEntity.ok(ApiResponse.onSuccess(feelings));
	}


	@Operation(summary = "짤 생성 api ", description = "짤 생성 api임 날짜 알려주면 그 날짜에 해당하는 감정을 기반으로 이미지 url로 반환됨")
	@GetMapping("/image")
	public ResponseEntity<ApiResponse<String>> getImageByDate(
		@RequestParam LocalDate date
	) {
		// 서비스 호출로 이미지 URL 생성
		String imageUrl = diaryService.getImageForDate(date);

		// 응답 반환
		return ResponseEntity.ok(ApiResponse.onSuccess(imageUrl));
	}
}
