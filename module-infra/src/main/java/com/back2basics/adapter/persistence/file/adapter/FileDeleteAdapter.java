package com.back2basics.adapter.persistence.file.adapter;

import static com.back2basics.infra.exception.file.FileErrorCode.FILE_DELETE_FAILED_URL_EMPTY;
import static com.back2basics.infra.exception.file.FileErrorCode.FILE_DELETE_FAILED_WRONG_URL;

import com.back2basics.adapter.persistence.file.FileEntityRepository;
import com.back2basics.file.model.File;
import com.back2basics.file.port.out.FileDeletePort;
import com.back2basics.infra.exception.file.FileException;
import com.back2basics.infra.s3.S3Util;
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
    public void deleteFiles(List<File> files) {
        if (files == null || files.isEmpty()) {
            return;
        }

        List<Long> ids = files.stream()
            .map(File::getId)
            .toList();

        fileRepository.deleteAllByIdInBatch(ids);
    }

    @Override
    public void deleteFromStorage(List<File> files) {
        for (File file : files) {
            String key = extractKeyFromUrl(file.getFileUrl());
            s3Util.deleteFile(key);
        }
    }

    private String extractKeyFromUrl(String url) {
        if (url == null || url.isBlank()) {
            throw new FileException(FILE_DELETE_FAILED_URL_EMPTY);
        }
        int lastSlash = url.lastIndexOf("/");
        if (lastSlash == -1 || lastSlash == url.length() - 1) {
            throw new FileException(FILE_DELETE_FAILED_WRONG_URL);
        }
        return url.substring(lastSlash + 1);
    }
}