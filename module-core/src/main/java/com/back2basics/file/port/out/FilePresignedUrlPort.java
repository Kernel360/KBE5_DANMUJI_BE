package com.back2basics.file.port.out;

public interface FilePresignedUrlPort {

    String generateDownloadUrl(String fileKey);
}
