package com.back2basics.post.model;

import lombok.Getter;

@Getter
public class File {

    private final Long id;
    private String fileName;
    private String fileUrl;
    private String fileType;
    private String fileSize;

    public File(Long id, String fileName, String fileUrl, String fileType, String fileSize) {
        this.id = id;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }

    public static File create(Long id, String fileName, String fileUrl, String fileType,
        String fileSize) {
        return new File(id, fileName, fileUrl, fileType, fileSize);
    }

    public void update(String fileName, String fileUrl, String fileType, String fileSize) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }
}
