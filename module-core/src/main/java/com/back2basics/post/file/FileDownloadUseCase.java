package com.back2basics.post.file;

public interface FileDownloadUseCase {

    FileDownloadResult downloadFile(Long userId, Long postId, Long fileId);
}
