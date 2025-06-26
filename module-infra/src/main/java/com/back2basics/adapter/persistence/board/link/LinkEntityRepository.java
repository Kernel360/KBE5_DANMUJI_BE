package com.back2basics.adapter.persistence.board.link;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkEntityRepository extends JpaRepository<LinkEntity, Long> {

    List<LinkEntity> findAllByPostId(Long postId);

    void deleteAllByPostId(Long postId);
}
