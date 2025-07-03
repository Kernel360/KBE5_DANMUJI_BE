package com.back2basics.board.file.port.out;

public interface FilePresignedUrlPort {
    String generateDownloadUrl(String fileKey);
}
