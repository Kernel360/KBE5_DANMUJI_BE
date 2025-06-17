package com.back2basics.adapter.persistence.post.file;

import com.back2basics.infra.s3.S3Util;
import com.back2basics.post.file.File;
import com.back2basics.post.file.FileDeletePort;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileDeleteAdapter implements FileDeletePort {

    private final FileEntityRepository fileRepository;
    private final S3Util s3Util;

    @Override
    @Transactional
    public void deleteAllByPostId(Long postId) {
        fileRepository.deleteByPostId(postId);
    }

    @Override
    public void deleteFromStorage(List<File> files) {
        for (File file : files) {
            String key = extractKeyFromUrl(file.getFileUrl());
            s3Util.deleteFile(key);
        }
    }

    private String extractKeyFromUrl(String url) {
        // 예시: https://domain/uuid_filename.ext → uuid_filename.ext
        return url.substring(url.lastIndexOf("/") + 1);
    }
}