package com.example.food.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "like")
public class Like {


    @Id
    private String id; 
    private String guestId; 
    private String restaurantId; 
    private String name;
    private String gender;
	private String ageGroup;
	private String priceLevel;
	private String category;
}
