package com.back2basics.adapter.persistence.post.file;


import com.back2basics.post.file.File;
import org.springframework.stereotype.Component;

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