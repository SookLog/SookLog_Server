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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/diaries")
@RequiredArgsConstructor
public class DiaryController {

	private final DiaryService diaryService;

	@PostMapping
	public ResponseEntity<ApiResponse<Void>> createDiary(
		@RequestBody DiaryRequest request,
		@RequestParam Member member
	) {
		//	Member member = memberLoader.getMember();
		diaryService.createDiary(request, member);
		return ResponseEntity.ok(ApiResponse.onSuccess(null));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<DiaryResponse>> getDiaryById(@PathVariable Long id) {
		DiaryResponse diary = diaryService.getDiaryById(id);
		return ResponseEntity.ok(ApiResponse.onSuccess(diary));
	}

}
