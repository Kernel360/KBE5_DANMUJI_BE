package com.back2basics.board.file.service;

import com.back2basics.board.file.port.in.FileDownloadUseCase;
import com.back2basics.board.file.model.File;
import com.back2basics.board.file.port.out.FileReadPort;
import com.back2basics.infra.s3.MimeTypeUtils;
import com.back2basics.infra.s3.S3Util;
import com.back2basics.infra.validation.validator.FileValidator;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileDownloadService implements FileDownloadUseCase {

    private final FileReadPort fileReadPort;
    private final FileValidator fileValidator;
    private final MimeTypeUtils mimeTypeUtils;
    private final S3Util s3Util;

    @Override
    public FileDownloadResult downloadFile(Long userId, Long postId, Long fileId)
        throws IOException {

        // fileValidator.validateDownloadPermission(userId, postId);
        File file = fileReadPort.getFileById(fileId);

        String key = extractKeyFromUrl(file.getFileUrl());
        File safeFile = applySafeMimeType(file);

        byte[] bytes = s3Util.downloadFile(key);

        return FileDownloadResult.toResult(safeFile, bytes);
    }

    private String extractKeyFromUrl(String fileUrl) {
        return fileUrl.replace("https://pub-2823e9b2fef94861b9d9cc553a84821b.r2.dev/", "");
    }

    private File applySafeMimeType(File file) {
        String safeType = mimeTypeUtils.getMimeType(file.getFileName(), file.getFileType());
        return file.withFileType(safeType);
    }

}
