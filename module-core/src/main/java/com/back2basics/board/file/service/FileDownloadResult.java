package com.back2basics.board.file.service;

import com.back2basics.board.file.model.File;

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
}