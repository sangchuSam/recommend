package com.example.food.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.food.entity.Guest;

public interface GuestRepository extends MongoRepository<Guest, String>{

	List<Guest> findByGenderAndAgeGroup(String gender, String ageGroup);
}
