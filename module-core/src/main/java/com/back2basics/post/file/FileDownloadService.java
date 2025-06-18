package com.back2basics.post.file;

import com.back2basics.infra.s3.S3Util;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileDownloadService implements FileDownloadUseCase {

    private final FileReadPort fileReadPort;
    private final S3Util s3Util;

    @Override
    public FileDownloadResult downloadFile(Long userId, Long postId, Long fileId)
        throws IOException {

        File file = fileReadPort.getFileById(fileId);
        String key = file.getFileUrl()
            .replace("https://pub-2823e9b2fef94861b9d9cc553a84821b.r2.dev/", "");
        byte[] bytes = s3Util.downloadFile(key);

        return FileDownloadResult.toResult(file, bytes);
    }
}
