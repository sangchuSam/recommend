package com.example.food.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.food.entity.Like;

public interface LikeRepositoroy extends MongoRepository<Like, String>{

}
