package com.example.sookLog.apiPayload.code.domains.diary.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.sookLog.apiPayload.code.domains.member.domain.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "diary_id")
	private Long id;

	private LocalDateTime dateTime;

	private String weather;

	private String title;

	private String content;

	private String feeling;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	private Diary(String title, String content, String weather, String feeling, Member member) {
		this.dateTime = LocalDateTime.now(); // 생성 시간 설정
		this.title = title;
		this.content = content;
		this.weather = weather;
		this.feeling = feeling;
		this.member = member;
	}

	public static Diary from(String title, String content, String weather, Member member) {
		return new Diary(title, content, weather, "Pending", member); // 기본 feeling 값은 "Pending"
	}

	public void updateFeeling(String feeling) {
		this.feeling = feeling; // 감정 분석 결과로 feeling 업데이트
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public boolean isPositive() {
		return "positive".equalsIgnoreCase(this.feeling);
	}





}
