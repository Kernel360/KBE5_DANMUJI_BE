package com.back2basics.adapter.persistence.file;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileEntityRepository extends JpaRepository<FileEntity, Long> {

    List<FileEntity> findAllByReferenceId(Long referenceId);

    void deleteByReferenceId(Long referenceId);
}
