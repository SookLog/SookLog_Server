package com.example.sookLog.domain.diary.controller;

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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/diaries")
@RequiredArgsConstructor
public class DiaryController {
	private final DiaryService diaryService;
	private final MemberRepository memberRepository;
	@PostMapping
	public ResponseEntity<ApiResponse<Void>> createDiary(
		@RequestBody DiaryDTO.DiaryRequest request,
		@RequestParam Long memberId
	) {
		//임시
		//Member member = new Member("Test User");
		//	Member member = memberLoader.getMember();
		//	Member member = diaryService.findMemberById(memberId);
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new IllegalArgumentException("Member not found with ID: " + memberId));

		diaryService.createDiary(request, member);

		return ResponseEntity.ok(ApiResponse.onSuccess(null));
	}

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
}
