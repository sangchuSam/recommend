package com.example.food.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.food.entity.Restaurant;

public interface RestaurantRepository extends MongoRepository<Restaurant, String>{

}
