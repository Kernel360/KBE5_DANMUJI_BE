package com.back2basics.file.service;

import com.back2basics.file.model.File;
import com.back2basics.infra.s3.MimeTypeUtils;

public record FileDownloadResult(String fileName, String fileType, String fileSize, String fileUrl,
                                 byte[] bytes) {

    public static FileDownloadResult toResult(File file, byte[] bytes) {
        return new FileDownloadResult(
            file.getFileName(),
            file.getFileType(),
            file.getFileSize(),
            file.getFileUrl(),
            bytes
        );
    }

    public String mimeType() {
        return MimeTypeUtils.getMimeType(fileName, "application/octet-stream");
    }
}