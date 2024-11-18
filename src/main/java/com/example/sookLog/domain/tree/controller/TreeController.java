package com.example.sookLog.domain.tree.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sookLog.apiPayload.code.ApiResponse;
import com.example.sookLog.domain.diary.service.DiaryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tree")
@RequiredArgsConstructor
public class TreeController {
	private final DiaryService diaryService;

	@GetMapping("/growth")
	public ResponseEntity<ApiResponse<Integer>> getPositiveCount() {
		int positiveCountModulo = diaryService.getPositiveDiaryCountModulo();
		return ResponseEntity.ok(ApiResponse.onSuccess(positiveCountModulo));
	}
}
