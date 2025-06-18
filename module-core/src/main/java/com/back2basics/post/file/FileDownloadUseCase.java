package com.back2basics.post.file;

import java.io.IOException;

public interface FileDownloadUseCase {

    FileDownloadResult downloadFile(Long userId, Long postId, Long fileId) throws IOException;
}
