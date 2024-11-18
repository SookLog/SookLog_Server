package com.example.sookLog.apiPayload.code.domains.diary.controller;

import static com.example.sookLog.apiPayload.code.domains.diary.dto.DiaryDTO.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.sookLog.apiPayload.code.common.ApiResponse;
import com.example.sookLog.apiPayload.code.domains.diary.service.DiaryService;
import com.example.sookLog.apiPayload.code.domains.member.domain.Member;
import com.example.sookLog.apiPayload.code.domains.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/diaries")
@RequiredArgsConstructor
public class DiaryController {

	private final DiaryService diaryService;
	private final MemberRepository memberRepository;
	@PostMapping
	public ResponseEntity<ApiResponse<Void>> createDiary(
		@RequestBody DiaryRequest request,
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
	public ResponseEntity<ApiResponse<DiaryResponse>> getDiaryById(@PathVariable Long id) {
		DiaryResponse diary = diaryService.getDiaryById(id);
		return ResponseEntity.ok(ApiResponse.onSuccess(diary));
	}

}
