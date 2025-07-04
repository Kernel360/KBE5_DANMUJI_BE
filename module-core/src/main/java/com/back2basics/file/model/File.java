package com.back2basics.file.model;

import lombok.Getter;

@Getter
public class File {

    private final Long id;
    private final ContentType contentType;
    private final Long referenceId;
    private String fileName;
    private String fileUrl;
    private String fileType;
    private String fileSize;

    public File(Long id, ContentType contentType, Long referenceId, String fileName, String fileUrl,
        String fileType, String fileSize) {
        this.id = id;
        this.contentType = contentType;
        this.referenceId = referenceId;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }

    public static File create(Long id, ContentType contentType, Long referenceId, String fileName,
        String fileUrl,
        String fileType,
        String fileSize) {
        return new File(id, contentType, referenceId, fileName, fileUrl, fileType, fileSize);
    }

    public void update(String fileName, String fileUrl, String fileType, String fileSize) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }

    public File withContentType(String newType) {
        return new File(this.id, this.contentType, this.referenceId, this.fileName, this.fileUrl,
            newType, this.fileSize);
    }

    public String getFileKey() {
        String prefix = "https://danmuji." + "[YOUR_R2_SUBDOMAIN]" + ".r2.cloudflarestorage.com/";
        return fileUrl.replace(prefix, "");
    }

}
