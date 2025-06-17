package com.back2basics.adapter.persistence.post.file;

import com.back2basics.post.file.File;
import com.back2basics.post.file.FileCreatePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileCreateAdapter implements FileCreatePort {

    private final FileEntityRepository fileRepository;
    private final FileMapper fileMapper;

    @Override
    public void save(File file) {
        fileRepository.save(fileMapper.toEntity(file));
    }
}
