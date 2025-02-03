package com.example.food;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import com.example.food.entity.Guest;
import com.example.food.entity.Like;
import com.example.food.entity.Restaurant;
import com.example.food.repository.GuestRepository;
import com.example.food.repository.LikeRepositoroy;
import com.example.food.repository.RestaurantRepository;

@DataMongoTest
class LikeRepositoryTests {

	@Autowired
	private LikeRepositoroy likeRepository;
	
	@Autowired
	private GuestRepository guestRepository;
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
    private final Random random = new Random();
    private final String[] categories = {"korean", "chinese", "japan", "western", "cafe", "etc"};
    private final String[] priceLevels = {"low", "medium", "high"};
    private final String[] ageGroups = {"0", "10", "20", "30", "40", "50", "60", "70", "80", "90"};
    private final String[] genders = {"male", "female"};

    @BeforeEach
    void setup() {
        // ✅ 기존 데이터 삭제 (중복 방지)
        likeRepository.deleteAll();
    }
    
    @Test
    @DisplayName("랜덤 가짜 Like 데이터 100개 삽입 테스트")
    void testInsertFakeLikes() {
        // ✅ Guest와 Restaurant 데이터 가져오기
        List<Guest> guests = guestRepository.findAll();
        List<Restaurant> restaurants = restaurantRepository.findAll();

        assertFalse(guests.isEmpty(), "❌ Guest 데이터가 없습니다!");
        assertFalse(restaurants.isEmpty(), "❌ Restaurant 데이터가 없습니다!");

        // ✅ 랜덤으로 Guest와 Restaurant 매칭하여 Like 데이터 생성
        List<Like> fakeLikes = IntStream.range(0, 100)
                .mapToObj(i -> {
                    Guest randomGuest = guests.get(random.nextInt(guests.size())); // 랜덤 사용자 선택
                    Restaurant randomRestaurant = restaurants.get(random.nextInt(restaurants.size())); // 랜덤 음식점 선택

                    return Like.builder()
                            .guestId(randomGuest.getId())
                            .restaurantId(randomRestaurant.getId())
                            .name("Restaurant Name " + (i+1))
                            .gender(genders[random.nextInt(genders.length)])  // ✅ 랜덤 성별 추가!
                            .ageGroup(ageGroups[random.nextInt(ageGroups.length)])
                            .priceLevel(priceLevels[random.nextInt(priceLevels.length)])
                            .category(categories[random.nextInt(categories.length)])
                            .build();
                }).toList();

        // ✅ MongoDB에 저장
        likeRepository.saveAll(fakeLikes);

        // ✅ 저장된 데이터 개수 확인
        List<Like> savedLikes = likeRepository.findAll();
        assertEquals(100, savedLikes.size(), "❌ 저장된 Like 데이터 개수가 100개가 아닙니다!");

        // ✅ 저장된 데이터 일부 출력 (디버깅용)
        savedLikes.stream().limit(5).forEach(System.out::println);
    }

}
