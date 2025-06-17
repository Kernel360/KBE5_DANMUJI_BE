package com.back2basics.adapter.persistence.post.file;

import com.back2basics.post.file.File;
import com.back2basics.post.file.FileSavePort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileSaveAdapter implements FileSavePort {

    private final FileEntityRepository fileRepository;
    private final FileMapper fileMapper;

    @Override
    public void saveAll(List<File> files, Long postId) {
        List<FileEntity> entities = files.stream()
            .map(file -> fileMapper.toEntity(file, postId))
            .toList();
        fileRepository.saveAll(entities);
    }
}
