package com.back2basics.adapter.persistence.post;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostEntityRepository extends JpaRepository<PostEntity, Long> {

    /*
        select p.*, u.*
        from posts p
        join users u
            on p.author_id = u.id
        where p.id =:postId
     */
    @Query("select p from PostEntity p join fetch p.author where p.id = :postId")
    Optional<PostEntity> findById(Long postId);

}
