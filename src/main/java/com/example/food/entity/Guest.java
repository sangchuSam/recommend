package com.example.food.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(collection = "guest")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Guest {

	@Id
	private String id;
	private String gender;
	private String ageGroup;
	private String preferences;
	private LocalDateTime createdAt;
	
	@Builder
	public Guest(String id, String gender, String ageGroup, String preferences) {
	    this.id = id;
	    this.gender = gender;
	    this.ageGroup = ageGroup;
	    this.preferences = preferences;
	    this.createdAt = LocalDateTime.now(); // 현재 시간 자동 입력
	}
}
