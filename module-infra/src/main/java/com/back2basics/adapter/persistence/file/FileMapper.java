package com.back2basics.adapter.persistence.file;


import com.back2basics.file.model.File;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FileMapper {

    public File toDomain(FileEntity entity) {
        return File.create(
            entity.getId(),
            entity.getPostId(),
            entity.getFileName(),
            entity.getFileUrl(),
            entity.getFileType(),
            entity.getFileSize()
        );
    }

    public FileEntity toEntity(File file, Long postId) {
        log.info("===FileMapper의 toEntity() url: {}", file.getFileUrl());
        return new FileEntity(
            file.getId(),
            postId,
            file.getFileName(),
            file.getFileUrl(),
            file.getFileType(),
            file.getFileSize()
        );
    }
}