package com.back2basics.adapter.persistence.user.repository;

import com.back2basics.adapter.persistence.user.entity.UserEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long>,
    JpaSpecificationExecutor<UserEntity> {

    boolean existsByUsername(String username);

    // 영속계층에 내려오기 전 서비스레이어에서 validator에 의한 옵셔널 체크를 하면 되니 여기서 옵셔널을 쓰지 않아도 될것같습니다
    // 추가로 이렇게 하면 멘토님 말씀대로 서비스레이어에서 비즈니스 로직에 의한 예외처리가 가능해집니다.
    // (repository.find 의 결과가 Optional이면 port쪽에서 찾아줄때 예외를 잡아줘야함)
    Optional<UserEntity> findByUsername(String username);

    @Query("select u.id from UserEntity u where u.name = :name")
    Optional<Long> findIdByName(@Param("name") String name);

    List<UserEntity> findAllByCompany_IdAndDeletedAtIsNull(Long companyId);

    Page<UserEntity> findAllByCompany_IdAndDeletedAtIsNull(Long companyId, Pageable pageable);

    Page<UserEntity> findAllByDeletedAtIsNull(Pageable pageable);

    List<UserEntity> findAllByDeletedAtIsNull();

    Page<UserEntity> findAllByDeletedAtIsNotNull(Pageable pageable);

    @Query("SELECT u FROM UserEntity u WHERE u.username IN :usernames")
    List<UserEntity> findAllByUsernames(List<String> usernames);

    @Query("SELECT COUNT(u) FROM UserEntity u WHERE u.deletedAt IS NULL")
    Long getUserCounts();

    @Query("SELECT DISTINCT u.position FROM UserEntity u WHERE u.deletedAt IS NULL")
    List<String> getUserPositions();

    @Override
    @EntityGraph(attributePaths = "company")
        // ★ 이 한 줄이면 끝
    Page<UserEntity> findAll(Specification<UserEntity> spec, Pageable pageable);

    void deleteByDeletedAtBefore(LocalDateTime threshold);
}
