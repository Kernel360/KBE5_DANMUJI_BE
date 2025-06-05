package com.back2basics.adapter.persistence.user.repository;

import com.back2basics.adapter.persistence.user.entity.UserEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByUsername(String username);

    Optional<UserEntity> findByUsername(String username);

    List<UserEntity> findAllByCompany_IdAndDeletedAtIsNull(Long companyId);
}
