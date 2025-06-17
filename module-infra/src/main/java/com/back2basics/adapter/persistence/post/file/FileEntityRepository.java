package com.back2basics.adapter.persistence.post.file;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileEntityRepository extends JpaRepository<FileEntity, Integer> {

    List<FileEntity> findAllByPostId(Long postId);

    void deleteByPostId(Long postId);
}
