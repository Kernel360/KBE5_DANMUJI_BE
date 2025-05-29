package com.back2basics.adapter.persistence.post;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostEntityRepository extends JpaRepository<PostEntity, Long> {

    @Query("SELECT p FROM PostEntity p LEFT JOIN FETCH p.comments WHERE p.id = :id AND p.deletedAt IS NULL")
    Optional<PostEntity> findActivePostWithComments(@Param("id") Long id);

    @Query("SELECT p FROM PostEntity p WHERE p.deletedAt IS NULL")
    List<PostEntity> findAllActivePosts();

    @Query("SELECT p FROM PostEntity p WHERE p.id = :id AND p.deletedAt IS NULL")
    Optional<PostEntity> findActivePostById(@Param("id") Long id);
}
