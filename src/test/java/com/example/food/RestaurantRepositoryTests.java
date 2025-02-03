package com.example.food;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import com.example.food.entity.Restaurant;
import com.example.food.repository.RestaurantRepository;


@DataMongoTest
class RestaurantRepositoryTests {

	@Autowired
	private RestaurantRepository restaurantRepository;
	
	private final Random random = new Random();
	private final String[] categories = {"korean", "chinese", "japan", "western", "cafe", "etc"};
	private final String[] priceLevels = {"low", "medium", "high"};
	
	@Test
	public void testInsertFakeRestaurants() {
	     // ✅ 기존 데이터 삭제 (중복 방지)
	     restaurantRepository.deleteAll();

	     // ✅ 10개 단위로 가짜 데이터 생성 & 저장
	     List<Restaurant> fakeRestaurants = IntStream.range(1, 101).mapToObj(i -> {
	          return Restaurant.builder()
	                .id("Restaurant" + String.format("%03d", i))  // ID = "Restaurant01", "Restaurant02" ...
	                .name("Restaurant Name " + i)  // 이름 = "Restaurant Name 1", "Restaurant Name 2" ...
	                .category(categories[random.nextInt(categories.length)])  // 랜덤 카테고리
	                .rating(Math.round((random.nextDouble() * 5) * 10.0) / 10.0)  // 0~5점 랜덤 평점 (소수점 1자리)
	                .priceLevel(priceLevels[random.nextInt(priceLevels.length)])  // 랜덤 가격대
	                .build();
	     }).toList();

	        restaurantRepository.saveAll(fakeRestaurants); // ✅ MongoDB에 저장

	        // ✅ 저장된 데이터 개수 확인
	        List<Restaurant> savedRestaurants = restaurantRepository.findAll();
	        assert savedRestaurants.size() == 100;  // 10개 삽입되었는지 검증

	        // ✅ 저장된 데이터 출력 (콘솔 확인용)
	        savedRestaurants.forEach(System.out::println);
	    }
	
	

}
