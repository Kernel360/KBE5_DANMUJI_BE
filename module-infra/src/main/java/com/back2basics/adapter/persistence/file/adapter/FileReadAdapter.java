package com.back2basics.adapter.persistence.file.adapter;

import static com.back2basics.infra.exception.file.FileErrorCode.FILE_NOT_FOUND;

import com.back2basics.adapter.persistence.file.FileEntityRepository;
import com.back2basics.adapter.persistence.file.FileMapper;
import com.back2basics.file.model.File;
import com.back2basics.file.port.out.FileReadPort;
import com.back2basics.infra.exception.file.FileException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileReadAdapter implements FileReadPort {

    private final FileEntityRepository fileRepository;
    private final FileMapper fileMapper;

    @Override
    public List<File> getFilesByReferenceId(Long referenceId) {
        return fileRepository.findAllByReferenceId(referenceId).stream()
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
