package com.example.sookLog.domain.tree.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sookLog.apiPayload.code.ApiResponse;
import com.example.sookLog.domain.diary.service.DiaryService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tree")
@RequiredArgsConstructor
public class TreeController {
	private final DiaryService diaryService;

	@Operation(summary = "나무 자라는 api ", description = "행복 감정 세서 반환값 주는건데 0부터 9까지 반환 될듯 9면 제일 자란 나무 보여주면 됨 0으로 씨앗")
	@GetMapping("/growth")
	public ResponseEntity<ApiResponse<Integer>> getPositiveCount() {
		int positiveCountModulo = diaryService.getPositiveDiaryCountModulo();
		return ResponseEntity.ok(ApiResponse.onSuccess(positiveCountModulo));
	}
}
