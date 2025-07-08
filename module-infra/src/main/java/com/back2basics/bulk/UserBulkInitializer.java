//package com.back2basics.test;
//
//import com.back2basics.adapter.persistence.user.entity.UserEntity;
//import com.back2basics.adapter.persistence.user.repository.UserEntityRepository;
//import com.back2basics.user.model.Role;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//import java.util.UUID;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class UserBulkInitializer implements CommandLineRunner {
//
//    private final UserEntityRepository userRepository;
//
//    private static final Random RANDOM = new Random();
//
//    @Override
//    public void run(String... args) {
//        int total = 200_000;
//        int batchSize = 10_000;
//        List<UserEntity> batch = new ArrayList<>();
//
//        for (int i = 1; i <= total; i++) {
//            UserEntity user = UserEntity.builder()
//                // .username("user" + i)
//                .username(UUID.randomUUID().toString().replace("-", "").substring(0, 12))
//                .password("pw1234")
//                .name(generateRandomName())
//                .email("user" + i + "@email.com")
//                .phone("010-0000-" + String.format("%04d", i % 10000))
//                .position("Dev")
//                .role(Role.USER)
//                .company(null) // 또는 임의 CompanyEntity
//                .lastLoginAt(LocalDateTime.now())
//                .build();
//
//            batch.add(user);
//
//            if (batch.size() == batchSize) {
//                userRepository.saveAll(batch);
//                batch.clear();
//                System.out.println("Saved batch up to user" + i);
//            }
//        }
//
//        if (!batch.isEmpty()) {
//            userRepository.saveAll(batch);
//        }
//    }
//
//    private static String generateRandomName() {
//        int length = 6 + RANDOM.nextInt(3); // 이름 길이: 6~8
//        StringBuilder name = new StringBuilder();
//        for (int i = 0; i < length; i++) {
//            char c = (char) ('a' + RANDOM.nextInt(26));
//            name.append(i == 0 ? Character.toUpperCase(c) : c); // 첫 글자 대문자
//        }
//        return name.toString();
//    }
//}
