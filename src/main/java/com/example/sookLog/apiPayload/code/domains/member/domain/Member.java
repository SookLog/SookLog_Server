package com.example.sookLog.apiPayload.code.domains.member.domain;

import java.util.ArrayList;
import java.util.List;

import com.example.sookLog.apiPayload.code.domains.diary.domain.Diary;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String name;

	private int happyCount;

	@OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<Diary> diaries = new ArrayList<>();

	public Member(String name) {
		this.name = name;
		this.happyCount = 0; // 초기값 0
	}

	// Diary 추가 시 행복지수 증가
	public void addDiary(Diary diary) {
		diaries.add(diary);
		diary.setMember(this); // 양방향 연관관계 설정
		if (diary.isPositive()) { // Diary가 긍정적인지 확인
			incrementHappyCount();
		}
	}
	public void incrementHappyCount() {
		this.happyCount++;
	}


}
