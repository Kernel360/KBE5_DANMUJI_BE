package com.back2basics.post.file;

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
    private final S3Util s3Util;

    @Override
    public FileDownloadResult downloadFile(Long userId, Long postId, Long fileId)
        throws IOException {

        fileValidator.validateDownloadPermission(userId, postId);
        File file = fileReadPort.getFileById(fileId);

        String key = getFileKey(file.getFileUrl());
        byte[] bytes = s3Util.downloadFile(key);

        return FileDownloadResult.toResult(file, bytes);
    }

    private String getFileKey(String fileUrl) {
        return fileUrl.replace("https://pub-2823e9b2fef94861b9d9cc553a84821b.r2.dev/", "");
    }

}
