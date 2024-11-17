package com.example.sookLog.apiPayload.code.domains.diary.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.sookLog.apiPayload.code.domains.diary.domain.Diary;

import jakarta.validation.constraints.NotNull;
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
		private String feeling;
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


	public static DiaryResponse from(Diary diary) {
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


}
