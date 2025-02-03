package com.example.food;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;


import com.example.food.entity.Guest;
import com.example.food.repository.GuestRepository;

@DataMongoTest
class GuestRepositoryTest {

    @Autowired
    private GuestRepository guestRepository;
    

    private final Random random = new Random();
    private final String[] genders = {"male", "female"};
    private final String[] ageGroups = {"0", "10", "20", "30", "40", "50", "60", "70", "80", "90"};
    private final String[] categories = {"korean", "chinese", "japan", "western", "cafe", "etc"};
	
//    @Test
//    public void testSaveGuest() {
//        // Given
//        Guest guest = Guest.builder()
//        		.id("User01")
//                .gender("female")
//                .ageGroup("20대")
//                .preferences("한식")
//                .build();
//
//        // When
//        Guest savedGuest = guestRepository.save(guest);
//
//        // Then
//        assertNotNull(savedGuest.getId());
//        System.out.println("✅ 저장된 Guest: " + savedGuest);
//    }
    
    @Test
    public void testInsertFakeGuests() {
        guestRepository.deleteAll(); // ✅ 기존 데이터 삭제 (중복 방지)

        List<Guest> fakeGuests = IntStream.range(1, 101).mapToObj(i -> {
            return Guest.builder()
                .id("User" + String.format("%03d", i))  // "User001", "User002" ...
                .gender(i <= 50 ? "male" : "female")  // ✅ 남 5명, 여 5명 고정
                .ageGroup(ageGroups[random.nextInt(ageGroups.length)])  // ✅ 연령대 랜덤
                .preferences(categories[random.nextInt(categories.length)])  // ✅ 선호 카테고리 랜덤
                .build();
        }).toList();

        guestRepository.saveAll(fakeGuests); // ✅ MongoDB에 저장
        List<Guest> savedGuests = guestRepository.findAll();
        assert savedGuests.size() == 100;  // ✅ 10명 삽입됐는지 확인
        savedGuests.forEach(System.out::println);
    }
}
