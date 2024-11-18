package com.example.sookLog.domain.diary.dto;

import java.time.LocalDateTime;

import com.example.sookLog.domain.diary.domain.Diary;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class DiaryDTO {
	@Getter
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class DiaryRequest  {

		private String title;
		private String content;
		private String weather; // 사용자 입력 필드
		private LocalDateTime dateTime;
	}
	@Getter
	@AllArgsConstructor
	public static class DiaryResponse  {
		private Long id;
		private String title;
		private String content;
		private String weather;
		private String feeling;
		private LocalDateTime dateTime;


		public DiaryResponse(Long id, String title, String content,  LocalDateTime dateTime) {
			this.id = id;
			this.title = title;
			this.content = content;
			this.dateTime = dateTime;
		}
	}
}
