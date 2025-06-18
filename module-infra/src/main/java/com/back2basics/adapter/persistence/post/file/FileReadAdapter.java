package com.back2basics.adapter.persistence.post.file;

import static com.back2basics.infra.exception.file.FileErrorCode.FILE_NOT_FOUND;

import com.back2basics.infra.exception.file.FileException;
import com.back2basics.post.file.File;
import com.back2basics.post.file.FileReadPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileReadAdapter implements FileReadPort {

    private final FileEntityRepository fileRepository;
    private final FileMapper fileMapper;

    @Override
    public List<File> getFilesByPostId(Long postId) {
        return fileRepository.findAllByPostId(postId).stream()
            .map(fileMapper::toDomain)
            .toList();
    }

    @Override
    public File getFileById(Long fileId) {
        return fileMapper.toDomain(
            fileRepository.findById(fileId).orElseThrow(() -> new FileException(FILE_NOT_FOUND))
        );
    }
}
