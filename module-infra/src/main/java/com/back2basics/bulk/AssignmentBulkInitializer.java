//package com.back2basics.bulk;
//
//import com.back2basics.adapter.persistence.assignment.AssignmentEntity;
//import com.back2basics.adapter.persistence.assignment.AssignmentEntityRepository;
//import com.back2basics.adapter.persistence.company.CompanyEntity;
//import com.back2basics.adapter.persistence.company.CompanyEntityRepository;
//import com.back2basics.adapter.persistence.project.ProjectEntity;
//import com.back2basics.adapter.persistence.project.ProjectEntityRepository;
//import com.back2basics.adapter.persistence.user.entity.UserEntity;
//import com.back2basics.adapter.persistence.user.repository.UserEntityRepository;
//import com.back2basics.company.model.CompanyType;
//import com.back2basics.user.model.UserType;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
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
//public class AssignmentBulkInitializer implements CommandLineRunner {
//
//    private final AssignmentEntityRepository assignmentRepository;
//    private final CompanyEntityRepository companyRepository;
//    private final ProjectEntityRepository projectRepository;
//    private final UserEntityRepository userRepository;
//
//    @PersistenceContext
//    private final EntityManager entityManager;
//
//    private static final Random RANDOM = new Random();
//    private static final long MAX_USER_ID = 200000L;
//
//    @Override
//    public void run(String... args) {
//        int total = 250_000;
//        int batchSize = 10_000;
//        List<AssignmentEntity> batch = new ArrayList<>();
//
//        List<CompanyEntity> companies = companyRepository.findAll();
//        List<ProjectEntity> projects = projectRepository.findAll();
//        List<Long> userIds = userRepository.findAllUserIds();
//
//        for (int i = 1; i <= total; i++) {
//            AssignmentEntity assignment = AssignmentEntity.builder()
//                .company(randomFrom(companies))
//                .project(randomFrom(projects))
//                .user(new UserEntity(randomFrom(userIds)))
//                .companyType(randomEnum(CompanyType.class))
//                .userType(randomEnum(UserType.class))
//                .build();
//
//            batch.add(assignment);
//
//            if (batch.size() == batchSize) {
//                assignmentRepository.saveAll(batch);
//                batch.clear();
//                log.info("Saved batch up to {}", i);
//            }
//        }
//
//        if (!batch.isEmpty()) {
//            assignmentRepository.saveAll(batch);
//            log.info("Saved final batch of {} assignments", batch.size());
//        }
//    }
//
//    private <T> T randomFrom(List<T> list) {
//        return list.get(RANDOM.nextInt(list.size()));
//    }
//
//    private <T extends Enum<?>> T randomEnum(Class<T> clazz) {
//        T[] values = clazz.getEnumConstants();
//        return values[RANDOM.nextInt(values.length)];
//    }
//}