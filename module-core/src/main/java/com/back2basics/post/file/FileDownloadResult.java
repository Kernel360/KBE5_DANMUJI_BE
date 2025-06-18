package com.back2basics.post.file;

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