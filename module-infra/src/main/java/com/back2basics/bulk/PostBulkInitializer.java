//package com.back2basics.bulk;
//
//import com.back2basics.adapter.persistence.board.post.PostEntity;
//import com.back2basics.adapter.persistence.board.post.PostEntityRepository;
//import com.back2basics.board.post.model.PostPriority;
//import com.back2basics.board.post.model.PostType;
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
//public class PostBulkInitializer implements CommandLineRunner {
//
//    private final PostEntityRepository postRepository;
//    private static final Random RANDOM = new Random();
//
//    @Override
//    public void run(String... args) {
//        int total = 500_000;
//        int batchSize = 10_0000;
//        List<PostEntity> batch = new ArrayList<>();
//
//        for (int i = 1; i <= total; i++) {
//            PostEntity post = new PostEntity(
//                null,                         // id (auto-generated)
//                null,                         // parentId
//                //,           // projectId
//                22L,
//                randomIp(),                   // authorIp
//                1L,         // authorId
//                randomTitle(),
//                "This is dummy content for post #" + i,
//                randomEnum(PostType.class),
//                randomEnum(PostPriority.class),
//                randomLong(132, 137)             // projectStepId
//            );
//
//            batch.add(post);
//
//            if (batch.size() == batchSize) {
//                postRepository.saveAll(batch);
//                batch.clear();
//                log.info("Saved batch up to post {}", i);
//            }
//        }
//
//        if (!batch.isEmpty()) {
//            postRepository.saveAll(batch);
//            log.info("Saved final batch of {} posts", batch.size());
//        }
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
//
//    private <T extends Enum<?>> T randomEnum(Class<T> enumClass) {
//        T[] constants = enumClass.getEnumConstants();
//        return constants[RANDOM.nextInt(constants.length)];
//    }
//
//    private String randomTitle() {
//        int length = 8 + RANDOM.nextInt(5); // 8~12글자
//        StringBuilder sb = new StringBuilder("Post Title ");
//        for (int i = 0; i < length; i++) {
//            char c = (char) ('a' + RANDOM.nextInt(26));
//            sb.append(c);
//        }
//        return sb.toString();
//    }
//
//}
//
