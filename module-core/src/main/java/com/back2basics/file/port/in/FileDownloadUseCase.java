package com.back2basics.file.port.in;

import com.back2basics.file.service.FileDownloadResult;
import com.back2basics.file.service.FilePresignedUrlResult;
import java.io.IOException;

public interface FileDownloadUseCase {

    FileDownloadResult downloadFile(Long userId, Long referenceId, Long fileId) throws IOException;

    FilePresignedUrlResult getPresignedDownloadUrl(Long userId, Long referenceId, Long fileId);
}
