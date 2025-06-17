package com.back2basics.post.file;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileCreateService implements FileCreateUseCase {

    private final FileCreatePort fileCreatePort;

    @Override
    public FileCreateResult createFile(FileCreateCommand command) {
        File file = File.create(
            null,
            command.getFileName(),
            command.getFileUrl(),
            command.getFileType(),
            command.getFileSize()
        );

        fileCreatePort.save(file);
        return FileCreateResult.toResult(file);
    }
}
