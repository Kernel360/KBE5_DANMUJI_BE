//package com.back2basics.bulk;
//
//import com.back2basics.adapter.persistence.comment.CommentEntity;
//import com.back2basics.adapter.persistence.comment.CommentEntityRepository;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class CommentBulkInitializer implements CommandLineRunner {
//
//    long start = System.currentTimeMillis();
//
//    private final CommentEntityRepository commentRepository;
//    private static final Random RANDOM = new Random();
//
//    @Override
//    public void run(String... args) {
//        int total = 100000; // 원하는 개수
//        int batchSize = 10_000;
//        List<CommentEntity> batch = new ArrayList<>();
//
//        for (int i = 1; i <= total; i++) {
//            CommentEntity comment = new CommentEntity();
//            comment.setAuthorIp(randomIp());
//            comment.setAuthorId(randomLong(1, 3));
//            comment.setContent("댓글 내용 #" + i);
//            comment.setPostId(randomLong(1,10000)); // 테스트 대상 게시글 ID
//            comment.setParentId(null);
//            comment.setCreatedAt(LocalDateTime.now());
//            comment.setUpdatedAt(LocalDateTime.now());
//
//            batch.add(comment);
//
//            if (batch.size() == batchSize) {
//                commentRepository.saveAll(batch);
//                batch.clear();
//                log.info("Saved batch up to comment {}", i);
//            }
//        }
//
//        if (!batch.isEmpty()) {
//            commentRepository.saveAll(batch);
//            log.info("Saved final batch of {} comments", batch.size());
//        }
//
//        long end = System.currentTimeMillis(); // ⏱ 종료 시간 측정
//        log.info("⏳ 전체 소요 시간: {} ms", (end - start));
//    }
//
//    private long randomLong(long min, long max) {
//        return min + RANDOM.nextInt((int)(max - min + 1));
//    }
//
//    private String randomIp() {
//        return RANDOM.nextInt(256) + "." + RANDOM.nextInt(256) + "." +
//            RANDOM.nextInt(256) + "." + RANDOM.nextInt(256);
//    }
//}
