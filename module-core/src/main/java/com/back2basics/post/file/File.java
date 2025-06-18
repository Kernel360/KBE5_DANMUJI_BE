package com.back2basics.post.file;

import lombok.Getter;

@Getter
public class File {

    private final Long id;
    private Long postId;
    private String fileName;
    private String fileUrl;
    private String fileType;
    private String fileSize;

    public File(Long id, Long postId, String fileName, String fileUrl, String fileType,
        String fileSize) {
        this.id = id;
        this.postId = postId;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }

    public static File create(Long id, Long postId, String fileName, String fileUrl,
        String fileType,
        String fileSize) {
        return new File(id, postId, fileName, fileUrl, fileType, fileSize);
    }

    public void update(String fileName, String fileUrl, String fileType, String fileSize) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }

    public File withFileType(String newType) {
        return new File(this.id, this.postId, this.fileName, this.fileUrl, newType, this.fileSize);
    }
}
