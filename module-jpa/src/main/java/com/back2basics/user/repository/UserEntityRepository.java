package com.back2basics.user.repository;

import com.back2basics.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByUsername(String username);
}
