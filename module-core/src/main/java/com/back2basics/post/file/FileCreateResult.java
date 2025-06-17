package com.back2basics.post.file;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileCreateResult {

    private Long id;
    private String fileName;
    private String fileUrl;
    private String fileType;
    private String fileSize;

    public static FileCreateResult toResult(File file) {
        return FileCreateResult.builder()
            .id(file.getId())
            .fileName(file.getFileName())
            .fileUrl(file.getFileUrl())
            .fileType(file.getFileType())
            .fileSize(file.getFileSize())
            .build();
    }
}
