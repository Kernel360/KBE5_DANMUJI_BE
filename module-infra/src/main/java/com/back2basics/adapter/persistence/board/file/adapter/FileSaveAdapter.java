package com.back2basics.adapter.persistence.board.file.adapter;

import com.back2basics.adapter.persistence.board.file.FileEntity;
import com.back2basics.adapter.persistence.board.file.FileEntityRepository;
import com.back2basics.adapter.persistence.board.file.FileMapper;
import com.back2basics.board.file.model.File;
import com.back2basics.board.file.port.out.FileSavePort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileSaveAdapter implements FileSavePort {

    private final FileEntityRepository fileRepository;
    private final FileMapper fileMapper;

    @Override
    public void saveAll(List<File> files, Long postId) {
        log.info("=== FileSaveAdapter 내부 {}", files.get(0).getFileUrl());
        List<FileEntity> entities = files.stream()
            .map(file -> fileMapper.toEntity(file, postId))
            .toList();
        fileRepository.saveAll(entities);
    }
}
