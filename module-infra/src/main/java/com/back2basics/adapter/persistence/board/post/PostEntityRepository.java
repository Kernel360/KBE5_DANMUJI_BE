package com.back2basics.adapter.persistence.board.post;

import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostEntityRepository extends JpaRepository<PostEntity, Long> {

    void deleteByDeletedAtBefore(LocalDateTime threshold);
}
